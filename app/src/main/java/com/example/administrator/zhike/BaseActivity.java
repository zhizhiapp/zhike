package com.example.administrator.zhike;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    //是否沉浸状态栏
    private boolean isSetStatusBar = false;
    //是否允许全屏
    private boolean mAllowFullScreen = true;
    //是否禁止旋转屏幕
    private boolean isAllowScreenRoate = false;
    //当前Activity渲染的视图View
    private View mContextView = null;
    //日志输出标志
    protected final String TAG = this.getClass().getSimpleName();

    //View点击
    public abstract void widgetClick(View v);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  Log.d(TAG, "BaseActivity-->onCreate()");
        Bundle bundle = getIntent().getExtras();
        initParms(bundle);
        View mView = bindView();
        if (null == mView) {
            mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        } else
            mContextView = mView;
       /* if (mAllowFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);//不要标题
        }*/
        if (isSetStatusBar) {
            steepStatusBar();
        }
        setContentView(mContextView);
        if (!isAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        initView(mContextView);
        setListener();
        doBusiness(this);
    }

    //沉浸状态栏
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    //初始化参数
    public abstract void initParms(Bundle parms);

    //绑定视图
    public abstract View bindView();

    //绑定布局
    public abstract int bindLayout();

    //初始化控件
    public abstract void initView(final View view);

    //绑定控件
    protected <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    //设置监听
    public abstract void setListener();

    @Override
    public void onClick(View v) {
        widgetClick(v);
    }

    //业务操作
    public abstract void doBusiness(Context mContext);


    //页面跳转
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    //携带数据的页面跳转
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    //页面跳转
    public void startActivityForResult(Class<?> clz, int i) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        startActivityForResult(intent, i);
    }

    //含有Bundle通过Class打开编辑界面
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    //设置图片
    public void setImg(ImageView imageView, int icon) {
        imageView.setImageResource(icon);
    }

    //设置字体颜色
    public void setColoor(TextView textView, int color) {
        textView.setTextColor(getResources().getColor(color));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //   Log.d(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        //    Log.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //    Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //   Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //   Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //  Log.d(TAG, "onDestroy()");
    }

    //简化Toast
    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //是否允许屏幕旋转
    public void setScreenRoate(boolean isAllowScreenRoate) {
        this.isAllowScreenRoate = isAllowScreenRoate;
    }

    //是否允许全屏
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    //是否设置沉浸状态栏
    public void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }


}