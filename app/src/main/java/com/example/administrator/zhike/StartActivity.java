package com.example.administrator.zhike;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.administrator.utils.PermissionsChecker;

public class StartActivity extends BaseActivity {
    private SharedPreferences sp;
    // 所需的全部权限
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA};
    private PermissionsChecker mPermissionsChecker; // 权限检测器
    private static final int REQUEST_CODE = 0; // 请求码

    @Override
    public void widgetClick(View v) {
    }

    @Override
    public void initParms(Bundle parms) {
        //解析bundle内容或者设置是否旋转，沉浸，全屏
    }

    @Override
    public View bindView() {

        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_start;
    }

    @Override
    public void initView(View view) {
    }

    @Override
    public void setListener() {
    }

    @Override
    public void doBusiness(Context mContext) {
        sp = getSharedPreferences("xs", MODE_PRIVATE);
        mPermissionsChecker = new PermissionsChecker(StartActivity.this);// 权限检查器
    }




    @Override
    public void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            if (PermissionsActivity.X == 200) {
                PermissionsActivity.X = 100;
                finish();
                return;
            }
            PermissionsActivity.startActivityForResult(StartActivity.this, REQUEST_CODE, PERMISSIONS);
        }else {
            StartA();
        }
    }



    private void StartA() {// 两秒后关闭提示
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sp.getBoolean("true", false)) {// 第二次以后进去登录界面
                    startActivity(MainActivity.class);
                } else {// 第一次登录 进来引导页
                    startActivity(GuidePageActivity.class);
                    Editor editor = sp.edit();
                    editor.putBoolean("true", true);
                    editor.commit();
                }
                finish();
            }
        }, 3000);
    }
















    /*
    String  url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513342601304&di=7bf06253dd2df0ca5f953968d4e29933&imgtype=0&src=http%3A%2F%2Fimg2.100bt.com%2Fupload%2Fttq%2F20120831%2F1346410585052.jpg";
    public void ss(){
        new Yibu().execute(url);
    }
    class Yibu extends AsyncTask<String, Void, Bitmap> {//url void 结果
        @Override
        protected void onPreExecute() {//开始前
            super.onPreExecute();
            Log.i("11","开始前");
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Log.i("11","开始");
            String Parth = "http://imgsrc.baidu.com/imgad/pic/item/960a304e251f95cace85282bc3177f3e6709523e.jpg";
            Bitmap bitmap = null;
            try {
                URL url = new URL(Parth);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(50000);
                conn.setRequestMethod("GET");
                conn.setUseCaches(false);//设置缓存
                conn.setDoInput(true);

                if (conn.getResponseCode() == 200) {
                    InputStream stream =  conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(stream);
                    stream.close();//关闭流
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            Log.i("11","完成");
            imageView.setImageBitmap(result);
        }

    }
*/


}
