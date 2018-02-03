package com.example.administrator.zhike;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class GuidePageActivity extends BaseActivity {
    private ViewPager viewPager;
    private int img[]={R.drawable.guide_dot_1,R.drawable.guide_dot_1,R.drawable.guide_dot_1};
    private List<View> list =new ArrayList<View>();//向上转型
    private static int Yes=0;//到达最后一个界面
    private static int No=1;//不在最后一个界面
    private ImageView tiaozhuan;
    private ImageView lvse;
    private int kuan;//绿色的圆圈的宽
    private int DQ;  //当前滑动的界面



    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.tiaozhuan:


                startActivity(MainActivity.class);
                finish();
                return;
        }
    }









    //设置页面监听
    class OnPageChange implements  OnPageChangeListener{
        @Override
        public void onPageSelected(int arg0) {

            TranslateAnimation animation =new TranslateAnimation(kuan * DQ, kuan * arg0, 0, 0);
            animation.setDuration(1);//设置圆圈速度
            animation.setFillAfter(true);//设置圆圈会动
            lvse.startAnimation(animation);//告诉程序   是这个lvse控件  要执行上面的动画效果

            if (arg0 == img.length-1) {//当前界面等于3的时候   指是到达了最后一个界面
                handler.sendEmptyMessageDelayed(Yes, 100);//第一个参数是标记    第二个参数是   图片显示的时间
            }else if (DQ ==img.length-1) {//当前界面  小于或者等于2的时候指的就是离开最后一个界面 了
                handler.sendEmptyMessageDelayed(No, 500);//第一个参数是标记    第二个参数是   图片隐藏的时间
            }
            DQ = arg0;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
    //设置适配器
    class PagerAda extends  PagerAdapter{
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager)container).addView(list.get(position));
            return list.get(position);
        }
        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager)container).removeView(list.get(position));
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
        return R.layout.activity_guidepage;
    }

    @Override
    public void initView(View view) {
        tiaozhuan = $(R.id.tiaozhuan);
        viewPager = $(R.id.viewpager);
        lvse = $(R.id.activity_img_lvse);
    }

    @Override
    public void setListener() {
        tiaozhuan.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        for (int i = 0; i < img.length; i++) {
            ImageView image =new ImageView(GuidePageActivity.this);
            image.setImageResource(img[i]);
            image.setScaleType(ImageView.ScaleType.FIT_XY);//图片占满屏幕
            list.add(image);//存图片
        }
        lvse.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                kuan  =	lvse.getWidth();//拿到绿色圆圈的宽
                return true;
            }
        });

        viewPager.setOnPageChangeListener(new OnPageChange());//设置页面监听
        PagerAda adapter = new PagerAda();
        viewPager.setAdapter(adapter);
    }

    Handler handler =new Handler(){
        public void handleMessage(Message msg) {
            if (msg.what == Yes ) {//最后
                tiaozhuan.setVisibility(View.VISIBLE);
            }else if (msg.what == No) {
                tiaozhuan.setVisibility(View.GONE);
            }
        }
    };







    @Override
    public void onResume() {
        super.onResume();

    }












}