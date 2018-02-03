package com.example.administrator.zhike;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomeNewsActivity extends BaseActivity {
    ImageView nReturn;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.home_news_return:
                finish();
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
        return R.layout.activity_home_news;
    }

    @Override
    public void initView(View view) {
       nReturn=$(R.id.home_news_return);
    }

    @Override
    public void setListener() {
        nReturn.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
