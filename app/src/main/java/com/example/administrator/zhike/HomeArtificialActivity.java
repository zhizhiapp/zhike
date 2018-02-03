package com.example.administrator.zhike;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.zhike.view.KeFuPoWindow;


public class HomeArtificialActivity extends BaseActivity {
    ImageView aReturn;
    ImageView aCall;

    //客服
    private int width, height; // 获得屏幕的宽高
    private LinearLayout aLayout;
    private KeFuPoWindow menuWindow;
    private View menuWindowView;

    @Override
    public void widgetClick(View v) {

        switch (v.getId()) {
            case R.id.home_artificial_return:
                finish();
                return;
            case R.id.home_artificial_call:
                Popup();//弹框
                menuWindow.showAtLocation(findViewById(R.id._home_artificial), Gravity.CENTER, 20, 0);// BOTTOM
                aLayout.setVisibility(View.VISIBLE);
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
        return R.layout.activity_home_artificial;
    }

    @Override
    public void initView(View view) {
        aReturn = $(R.id.home_artificial_return);
        aCall = $(R.id.home_artificial_call);
        aLayout = $(R.id.home_artificial_layout);
    }

    @Override
    public void setListener() {
        aReturn.setOnClickListener(this);
        aCall.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }


    private void Popup(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
        menuWindow = new KeFuPoWindow(HomeArtificialActivity.this, aLayout);// 实例化SelectPicPopupWindow
        menuWindowView = menuWindow.getContentView();
        LinearLayout layout = (LinearLayout) menuWindowView.findViewById(R.id.pu_window_kefu_qlayout);

        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        layout.measure(w, h);
        int hhh = layout.getMeasuredHeight();
        menuWindow.setWidth((int) (width * 0.8));// 设置菜单的宽  (int) (d.getWidth() * 0.8)
        menuWindow.setHeight(hhh);// 设置菜单的高  200
    }

}
