package com.example.administrator.zhike;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends BaseActivity {
    ImageView aReturn;
    TextView version;
    Button aZk;
    Button aOpinion;
    Button aUpdate;
    Button aZhenCe;
    Button aZeRen;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.about_return://返回
                finish();
                return;
            case R.id.about_zk://关于智课
                startActivity(AboutZkActivity.class);
                return;
            case R.id.about_opinion://意见反馈
                return;
            case R.id.about_update://版本更新
                return;
            case R.id.about_zhence://政策
                return;
            case R.id.about_zeren://责任
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
        return R.layout.activity_about;
    }

    @Override
    public void initView(View view) {
        aReturn = $(R.id.about_return);
        version = $(R.id.about_version);
        aZk = $(R.id.about_zk);
        aOpinion = $(R.id.about_opinion);
        aUpdate = $(R.id.about_update);
        aZhenCe = $(R.id.about_zhence);
        aZeRen = $(R.id.about_zeren);
    }

    @Override
    public void setListener() {
        aReturn.setOnClickListener(this);
        aZk.setOnClickListener(this);
        aOpinion.setOnClickListener(this);
        aUpdate.setOnClickListener(this);
        aZhenCe.setOnClickListener(this);
        aZeRen.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
