package com.example.administrator.zhike;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AboutZkActivity extends BaseActivity {
    ImageView zReturn;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.about_zk_return:
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
        return R.layout.activity_about_zk;
    }

    @Override
    public void initView(View view) {
        zReturn = $(R.id.about_zk_return);
    }

    @Override
    public void setListener() {
        zReturn.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
