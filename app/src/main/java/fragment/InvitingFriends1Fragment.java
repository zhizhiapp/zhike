package fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.zhike.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import base.BaseApplication;

public class InvitingFriends1Fragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    private View fragmentView;
    private ImageView photoImageView;
    private String pictureName;
    private FrameLayout containerView;
    private boolean isSavePhoto = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fl_inviting_friends_1, container, false);
            initView(fragmentView);
        }
        return fragmentView;
    }

    private void initView(View view) {
        if (view != null) {
            view.findViewById(R.id.iv_take_a_photo).setOnClickListener(this);
            photoImageView = (ImageView) view.findViewById(R.id.iv_photo);
            containerView = (FrameLayout) view.findViewById(R.id.fl_container);
            containerView.setOnLongClickListener(this);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isSavePhoto = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (fragmentView != null) {
            ViewGroup viewGroup = (ViewGroup) fragmentView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(fragmentView);
            }
        }
    }

    private void openPhoto() {
        String[] menus = {"拍照", "从相册中获取"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(menus, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    String path = BaseApplication.getInvitingFriendsImgPath();
                    if (!TextUtils.isEmpty(path)) {
                        File file = new File(path);
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        pictureName = System.currentTimeMillis() + ".jpg";
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            //7.0 以上用这个
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    FileProvider.getUriForFile(getContext(),
                                            "com.example.cameraalbumtest.fileprovider",
                                            new File(file, pictureName)));
                            startActivityForResult(intent, 1);
                        } else {
                            //以下用这个
                            Uri uri = Uri.fromFile(new File(file, pictureName));
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            startActivityForResult(intent, 1);
                        }
                    }
                } else {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, 2);
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == 1) {
                //拍完照回来
                isSavePhoto = true;
                //跳过缓存
                Glide.with(BaseApplication.getContext())
                        .load(new File(BaseApplication.getInvitingFriendsImgPath(), pictureName))
                        .centerCrop()
                        .into(photoImageView);

            } else {
                //打开相册回来
                isSavePhoto = true;
                Uri uri = data.getData();
                Glide.with(BaseApplication.getContext()).load(uri).into(photoImageView);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_take_a_photo:
                openPhoto();
                break;
        }
    }

    public Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    @Override
    public boolean onLongClick(View view) {
        if (view.getId() == R.id.fl_container) {
            if (isSavePhoto) {
                try {
                    String path = BaseApplication.getInvitingFriendsImgPath();
                    if (!TextUtils.isEmpty(path)) {
                        File pathFile = new File(path);
                        if (!pathFile.exists()) {
                            pathFile.mkdir();
                        }
                        File fileName = new File(path, "QR_CODE.JPG");
                        Bitmap bitmap = createViewBitmap(containerView);
                        FileOutputStream stream = new FileOutputStream(fileName);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        stream.flush();
                        stream.close();
                        if (fileName.exists()) {
                            Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "保存失败", Toast.LENGTH_SHORT).show();
                        }
                        //更新系统相册
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        Uri uri = Uri.fromFile(fileName);
                        intent.setData(uri);
                        getActivity().sendBroadcast(intent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

}
