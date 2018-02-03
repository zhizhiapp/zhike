package com.example.administrator.zhike;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.adapter.MyPagerAdapter;
import com.example.administrator.constants.ServiceApi;
import com.example.administrator.utils.RxjavaRetrofitUtils;

import java.util.ArrayList;
import java.util.List;

public class MyLodingActivity extends BaseActivity {
    Button mMicroClass;
    Button mOpen;
    ImageView mReturn;
    ViewPager sPager;
    List<View> lists = new ArrayList<>();
    View view1, view2;
    LinearLayout MicroLayout,OpenLayout;
    TextView Micro,Open;

    int subjectId = 2;//科目id
    int projectId = 1;//项目id
    private ServiceApi service;
    private SharedPreferences sp;
    int pageNun = 10;//条数
    int page = 1;//页

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.my_loding_return://返回
                finish();
                return;
            case R.id.my_loding_micro_class://微课
                mMicroClass.setBackgroundColor(getResources().getColor(R.color.baiS));
                mOpen.setBackgroundColor(getResources().getColor(R.color.myloding));
                mMicroClass.setClickable(false);
                mOpen.setClickable(true);
                sPager.setCurrentItem(0);
                return;
            case R.id.my_loding_open://公开课
                mMicroClass.setBackgroundColor(getResources().getColor(R.color.myloding));
                mOpen.setBackgroundColor(getResources().getColor(R.color.baiS));
                mMicroClass.setClickable(true);
                mOpen.setClickable(false);
                sPager.setCurrentItem(1);
                return;

            case R.id.my_loding_micro_microclass://微课

                return;
            case R.id.my_loding_open_openclass://公开课
                startActivity(MyOpenClassActivity.class);
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
        return R.layout.activity_my_loding;
    }

    @Override
    public void initView(View view) {
        mReturn = $(R.id.my_loding_return);
        mMicroClass = $(R.id.my_loding_micro_class);
        mOpen = $(R.id.my_loding_open);
        sPager = $(R.id.my_loding_pager);
    }

    @Override
    public void setListener() {
        mReturn.setOnClickListener(this);
        mMicroClass.setOnClickListener(this);
        mOpen.setOnClickListener(this);

    }


    @Override
    public void doBusiness(Context mContext) {
        sp = getSharedPreferences("xs", MODE_PRIVATE);
        service = RxjavaRetrofitUtils.getApi();
        index();


    }

    public void index() {
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.activity_my_loding_micro_class, null);
        MicroLayout = (LinearLayout) view1.findViewById(R.id.my_loding_micro_layout);
        Micro = (TextView) view1.findViewById(R.id.my_loding_micro_microclass);
        Micro.setOnClickListener(this);

        lists.add(view1);

        view2 = inflater.inflate(R.layout.activity_my_loding_open_class, null);
        OpenLayout = (LinearLayout) view2.findViewById(R.id.my_loding_open_layout);
        Open = (TextView) view2.findViewById(R.id.my_loding_open_openclass);
        Open.setOnClickListener(this);
        lists.add(view2);

        MyPagerAdapter adapte = new MyPagerAdapter(lists);
        sPager.setAdapter(adapte);
        sPager.setCurrentItem(0);
        sPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }


    //页卡切换监听
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    mMicroClass.setBackgroundColor(getResources().getColor(R.color.baiS));
                    mOpen.setBackgroundColor(getResources().getColor(R.color.myloding));
                    mMicroClass.setClickable(false);
                    mOpen.setClickable(true);
                    break;
                case 1:
                    mMicroClass.setBackgroundColor(getResources().getColor(R.color.myloding));
                    mOpen.setBackgroundColor(getResources().getColor(R.color.baiS));
                    mMicroClass.setClickable(true);
                    mOpen.setClickable(false);
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }


}
