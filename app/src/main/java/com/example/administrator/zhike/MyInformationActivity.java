package com.example.administrator.zhike;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.administrator.constants.ServiceApi;
import com.example.administrator.entity.UpdateInfoEntity;
import com.example.administrator.entity.UploadImgEntity;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SystemUtils;
import com.example.administrator.zhike.view.CircleImageView;
import com.example.administrator.zhike.view.SexPopupWindow;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyInformationActivity extends BaseActivity {
    ImageView mReturn;
    Button mPreservation;
    CircleImageView mHeader;
    EditText mName;
    EditText mMain;
    EditText mSchool;
    EditText mMajor;
    TextView mSex;
    Button mAddress;
    TextView mCall;
    int requestCode = 101;//请求码
    ServiceApi service;
    SharedPreferences sp;
    LinearLayout layout;
    SexPopupWindow popupWindow;

    private Uri imageUri;
    String picPath;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_FROM_AIBUM = 2;
    AlertDialog.Builder dialog;
    File outputImg;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.my_information_return://返回
                setResult(3);
                finish();
                return;
            case R.id.my_information_header://头像
                getImg();
                return;
            case R.id.my_information_sex://性别
                getSex();
                popupWindow.showAtLocation(MyInformationActivity.this.findViewById(R.id._my_information), Gravity.BOTTOM, 0, 0);
                layout.setVisibility(View.VISIBLE);
                return;
            case R.id.my_information_preservation:///保存
                updateInfo();
                if (outputImg != null && outputImg.exists()) {
                    updateInfo(outputImg);//保存图片
                }

                return;
            case R.id.my_information_address://地址
                Intent intent = new Intent();
                intent.setClass(MyInformationActivity.this, ProvincialUrbanAreaActivity.class);
                startActivityForResult(intent, requestCode);

                return;
        }

    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_my_information;
    }

    @Override
    public void initView(View view) {
        mReturn = $(R.id.my_information_return);
        mPreservation = $(R.id.my_information_preservation);
        mHeader = $(R.id.my_information_header);
        mName = $(R.id.my_information_name);
        mCall = $(R.id.my_information_call);
        mMain = $(R.id.my_information_main);
        mSchool = $(R.id.my_information_school);
        mMajor = $(R.id.my_information_major);
        mSex = $(R.id.my_information_sex);
        mAddress = $(R.id.my_information_address);
        layout = $(R.id.my_information_layout);
    }

    @Override
    public void setListener() {
        mReturn.setOnClickListener(this);
        mPreservation.setOnClickListener(this);
        mAddress.setOnClickListener(this);
        mSex.setOnClickListener(this);
        mHeader.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        service = RxjavaRetrofitUtils.getApi();
        sp = getSharedPreferences("xs", MODE_PRIVATE);
        mName.setText(sp.getString("nickname", ""));
        mCall.setText(sp.getString("mobile", ""));
        mMain.setText(sp.getString("email", ""));
        mSchool.setText(sp.getString("colleges", ""));
        mMajor.setText(sp.getString("major", ""));
        if (sp.getInt("sex", 0) == 0) {
            mSex.setText("未知");
        } else if (sp.getInt("sex", 0) == 1) {
            mSex.setText("男");
        } else if (sp.getInt("sex", 0) == 2) {
            mSex.setText("女");
        }
        mAddress.setText(sp.getString("address", ""));
        Glide.with(this).load(sp.getString("headimgurl", "")).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(mHeader);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == 102) {
            String provinceName = data.getStringExtra("province");
            String cityName = data.getStringExtra("city");
            mAddress.setText(provinceName + " " + cityName);
        }

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    Glide.with(this).load(imageUri).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(mHeader);
                    break;
                case CHOOSE_FROM_AIBUM:
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4以上的系统使用这个方法处理照片
                        handleImageOnKitKat(data);
                    } else {
                        //4.4以下的使用这个方法处理
                        handleImageBeforeKitKat(data);
                    }

                default:
                    break;
            }
        }


    }

    //更新
    private void updateInfo() {
        String nameStr = mName.getText().toString().trim();
        String email = mMain.getText().toString().trim();
        String colleges = mSchool.getText().toString().trim();
        String major = mMajor.getText().toString().trim();
        String sexStr = mSex.getText().toString().trim();
        String addressStr = mAddress.getText().toString().trim();
        int sex = 0;
        if (sexStr.equals("未知")) {
            sex = 0;
        } else if (sexStr.equals("男")) {
            sex = 1;
        } else if (sexStr.equals("女")) {
            sex = 2;
        }

        if (TextUtils.isEmpty(nameStr)) {
            showToast("请填写您的昵称");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            showToast("请填写您的邮箱");
            return;
        }
        if (TextUtils.isEmpty(colleges)) {
            showToast("请填写您的院校");
            return;
        }
        if (TextUtils.isEmpty(major)) {
            showToast("请填写您的专业");
            return;
        }
        if (TextUtils.isEmpty(major)) {
            showToast("请填写您的地区");
            return;
        }


        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email + "");//邮箱地址
        map.put("colleges", colleges + "");//目标院校
        map.put("major", major + "");//意向专业
        map.put("nickname", nameStr + "");//昵称
        map.put("sex", sex);//性别 0：未知 1：男 2女
        map.put("address", addressStr + "");//地址
        Log.i("TAG", "map==" + map);
        DescendingEncryption.init(map);
        Call<UpdateInfoEntity> call = service.updateInfo(map, SystemUtils.getHeader(MyInformationActivity.this));
        call.enqueue(new Callback<UpdateInfoEntity>() {
            @Override
            public void onResponse(Call<UpdateInfoEntity> call, Response<UpdateInfoEntity> response) {
                Log.i("TAG", "map==" + response.body().getCode());
                Log.i("TAG", "map==" + response.body().getMsg());
                try {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("email", response.body().getList().getEmail());
                    editor.putString("colleges", response.body().getList().getColleges());
                    editor.putString("major", response.body().getList().getMajor());
                    editor.putString("nickname", response.body().getList().getNickname());
                    editor.putInt("sex", response.body().getList().getSex());
                    editor.putString("address", response.body().getList().getAddress());
                    editor.commit();
                    showToast("保存成功");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<UpdateInfoEntity> call, Throwable t) {
                showToast("网络异常");
            }
        });
    }


    private void getSex() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        popupWindow = new SexPopupWindow(MyInformationActivity.this, layout);// 实例化SelectPicPopupWindow
        View popupWindowView = popupWindow.getContentView();
        LinearLayout layout = (LinearLayout) popupWindowView.findViewById(R.id.layout_poputView);
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        layout.measure(w, h);
        int hhh = layout.getMeasuredHeight();

        popupWindow.setWidth(width);// 设置菜单的宽
        popupWindow.setHeight(hhh);// 设置菜单的高  200
    }

    public void sex(View view) {
        switch (view.getId()) {
            case R.id.sex_nan:
                mSex.setText("男");
                popupWindow.dismiss();
                break;
            case R.id.sex_nv:
                mSex.setText("女");
                popupWindow.dismiss();
                break;

            default:
                break;
        }
    }


    //头像
    private void updateInfo(final File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("userImg", file.getName(), requestBody);
        Call<UploadImgEntity> call = service.uploadImg(DescendingEncryption.getDefault(), SystemUtils.getHeader(MyInformationActivity.this), body);
        call.enqueue(new Callback<UploadImgEntity>() {
            @Override
            public void onResponse(Call<UploadImgEntity> call, Response<UploadImgEntity> response) {
                if (response.body().getCode().equals("0")) {
                    showToast("更新失败或其它错误信息");
                } else if (response.body().getCode().equals("1")) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("headimgurl", response.body().getList().getHeadimgurl());
                    Log.i("TAG", "headimgurl==" + response.body().getList().getHeadimgurl());
                    editor.commit();
                    showToast("保存成功");
                }
            }

            @Override
            public void onFailure(Call<UploadImgEntity> call, Throwable t) {
                Log.i("TAG", "失败");
            }
        });

    }


    public void getImg() {
        dialog = new AlertDialog.Builder(MyInformationActivity.this);
        dialog.setTitle("请选择照片来源")
                .setItems(new String[]{"相册", "照相机"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0://相册
                                        if (ContextCompat.checkSelfPermission(MyInformationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(MyInformationActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                                        } else {
                                            //打开相册
                                            openAlbum();
                                        }
                                        break;
                                    case 1://相机
                                        openCamara();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }).create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "你拒绝了许可", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //打开相册
    private void openAlbum() {
        //打开相册
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_FROM_AIBUM);
    }

    //打开相机
    private void openCamara() {
        //创建file对象，用于存储拍照后的图片
        outputImg = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImg.exists()) {
                outputImg.delete();
            }
            outputImg.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(MyInformationActivity.this, "com.example.cameraalbumtest.fileprovider", outputImg);
        } else {
            imageUri = Uri.fromFile(outputImg);
        }

        //启动相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的uri，则通过document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri则使用普通的方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的URI则直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath);//根据图片路径显示图片
    }


    //通过传过来的条件，获取图片的真实路径
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过URI和selection来获取真是的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            //将照片显示出来
            outputImg = new File(imagePath);
            Glide.with(this).load(imagePath).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(mHeader);
        } else {
            showToast("未能获得图像!");
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }


}
