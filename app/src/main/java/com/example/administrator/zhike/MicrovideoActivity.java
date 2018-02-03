package com.example.administrator.zhike;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.adapter.MyPagerAdapter;
import com.example.administrator.adapter.PagerCatalogAdapter;
import com.example.administrator.constants.MyConstants;
import com.example.administrator.constants.ServiceApi;
import com.example.administrator.entity.VideoCatalogEntity;
import com.example.administrator.entity.VideoEntity;
import com.example.administrator.entity.VideoInfoEntity;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.FullyLinearLayoutManager;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SystemUtils;
import com.example.administrator.zhike.view.LinearLayoutItemDecoration;
import com.example.administrator.zhike.view.MicroClassPow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MicrovideoActivity extends BaseActivity {

    ImageView eReturn;
    LinearLayout eChoice;
    TextView eChoiceText;
    ImageView eChoiceImg;
    MicroClassPow pow;
    TextView eSection;
    Button synopsis;
    Button catalog;
    Button relevant;

    List<View> listViews;
    ViewPager mPager;
    View vSynopsis, vCatalog, vRelevant;

    ImageView erweima;
    ScrollView SScroll;
    TextView text1;
    TextView text2;
    TextView text3;
    ImageView Imag1, Imag2, Imag3, Imag4;

    VideoEntity.VideoList entity;

    RecyclerView exerRecycler;
    RecyclerView CatalogRecycler;

    JCVideoPlayerStandard playerStandard;
    ServiceApi service;

    PagerCatalogAdapter pcAdapter;


    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.microvideo_return://返回
                finish();
                return;
            case R.id.microvideo_choice://课程选择
                return;
            case R.id.microvideo_synopsis://简介
                mPager.setCurrentItem(0);
                return;
            case R.id.microvideo_catalog://目录
                mPager.setCurrentItem(1);
                return;
            case R.id.microvideo_relevant://相关习题
                mPager.setCurrentItem(2);
                return;
            case R.id.pager_synopsis_layout_abstract://课程简介
                if (text1.getVisibility() == View.VISIBLE) {
                    text1.setVisibility(View.GONE);
                    setImg(Imag1, R.drawable.pager_synopsis_open);
                } else {
                    text1.setVisibility(View.VISIBLE);
                    setImg(Imag1, R.drawable.pager_synopsis_shut);
                }
                return;
            case R.id.pager_synopsis_layout_teacher://老师介绍
                if (text2.getVisibility() == View.VISIBLE) {
                    text2.setVisibility(View.GONE);
                    setImg(Imag2, R.drawable.pager_synopsis_open);
                } else {
                    text2.setVisibility(View.VISIBLE);
                    setImg(Imag2, R.drawable.pager_synopsis_shut);
                }
                return;
            case R.id.pager_synopsis_layout_introduce://智课介绍
                if (text3.getVisibility() == View.VISIBLE) {
                    text3.setVisibility(View.GONE);
                    setImg(Imag3, R.drawable.pager_synopsis_open);
                } else {
                    text3.setVisibility(View.VISIBLE);
                    setImg(Imag3, R.drawable.pager_synopsis_shut);
                }
                return;
            case R.id.pager_synopsis_layout_erweima://二维码
                if (erweima.getVisibility() == View.VISIBLE) {
                    erweima.setVisibility(View.GONE);
                    setImg(Imag4, R.drawable.pager_synopsis_open);
                } else {
                    erweima.setVisibility(View.VISIBLE);
                    setImg(Imag4, R.drawable.pager_synopsis_shut);
                }
                return;
        }
    }

    @Override
    public void initParms(Bundle parms) {
        entity = (VideoEntity.VideoList) parms.getSerializable("play");
        Log.i("TAG", entity.getUrl());

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_microvideo;
    }

    @Override
    public void initView(View view) {
        eReturn = $(R.id.microvideo_return);
        eChoice = $(R.id.microvideo_choice);
        eChoiceText = $(R.id.microvideo_choice_text);
        eChoiceImg = $(R.id.microvideo_choice_img);
        mPager = $(R.id.microvideo_choice_viewpager);

        eSection = $(R.id.microvideo_section);

        synopsis = $(R.id.microvideo_synopsis);
        catalog = $(R.id.microvideo_catalog);
        relevant = $(R.id.microvideo_relevant);
        playerStandard = $(R.id.microvideo_video);
    }

    @Override
    public void setListener() {
        eReturn.setOnClickListener(this);
        eChoice.setOnClickListener(this);
        synopsis.setOnClickListener(this);
        catalog.setOnClickListener(this);
        relevant.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        eSection.setText(entity.getTitle());
//        boolean setUp = playerStandard.setUp(entity.getUrl(), JCVideoPlayer.SCREEN_LAYOUT_LIST, "");


        boolean setUp = playerStandard.setUp("http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4", JCVideoPlayer.SCREEN_LAYOUT_NORMAL, "");
        if (setUp) {
            Glide.with(MicrovideoActivity.this).load(MyConstants.Service_URL + entity.getThumb()).into(playerStandard.thumbImageView);
        }
        playerStandard.startButton.performClick(); //模拟用户点击开始按钮，NORMAL状态下点击开始播放视频，播放中点击暂停视频


        InitViewPager();
        service = RxjavaRetrofitUtils.getApi();
        getinfo(entity.getId());//视频id
        getcatalog(2);// 科目id


    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }


    //pager_synopsis_shut


    //洗

    private void InitViewPager() {
        listViews = new ArrayList<>();
        LayoutInflater mInflater = getLayoutInflater();

        vSynopsis = mInflater.inflate(R.layout.pager_synopsis, null);
        SScroll = (ScrollView) vSynopsis.findViewById(R.id.pager_synopsis_scroll);

        LinearLayout layoutAbstract = (LinearLayout) vSynopsis.findViewById(R.id.pager_synopsis_layout_abstract);
        layoutAbstract.setOnClickListener(this);
        text1 = (TextView) vSynopsis.findViewById(R.id.pager_synopsis_abstract_contont);
        Imag1 = (ImageView) vSynopsis.findViewById(R.id.pager_synopsis_img_abstract);

        LinearLayout layoutTeacher = (LinearLayout) vSynopsis.findViewById(R.id.pager_synopsis_layout_teacher);
        layoutTeacher.setOnClickListener(this);
        text2 = (TextView) vSynopsis.findViewById(R.id.pager_synopsis_teacher_contont);
        Imag2 = (ImageView) vSynopsis.findViewById(R.id.pager_synopsis_img_teacher);

        LinearLayout layoutIntroduce = (LinearLayout) vSynopsis.findViewById(R.id.pager_synopsis_layout_introduce);
        layoutIntroduce.setOnClickListener(this);
        text3 = (TextView) vSynopsis.findViewById(R.id.pager_synopsis_introduce_contont);
        Imag3 = (ImageView) vSynopsis.findViewById(R.id.pager_synopsis_img_introduce);

        LinearLayout layoutErweima = (LinearLayout) vSynopsis.findViewById(R.id.pager_synopsis_layout_erweima);
        erweima = (ImageView) vSynopsis.findViewById(R.id.pager_synopsis_img_erweima);
        Imag4 = (ImageView) vSynopsis.findViewById(R.id.pager_synopsis_simg_erweima);
        layoutErweima.setOnClickListener(this);

        SScroll.setVerticalScrollBarEnabled(false);
        listViews.add(vSynopsis);
        ////////////目录///////////////////目录//////////////////////目录////////////////目录////////// //

        vCatalog = mInflater.inflate(R.layout.pager_catalog, null);
        CatalogRecycler = (RecyclerView) vCatalog.findViewById(R.id.pager_catalog_recycler);
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(MicrovideoActivity.this, LinearLayoutManager.VERTICAL, false);
        CatalogRecycler.setLayoutManager(manager);
        CatalogRecycler.addItemDecoration(new LinearLayoutItemDecoration(LinearLayoutManager.VERTICAL, getResources().getDimensionPixelSize(R.dimen.x1)));//设置颜色  和 分割线


        listViews.add(vCatalog);
        //////////相关习题////////////相关习题//////////////////相关习题/////////////////相关习题///////////
        vRelevant = mInflater.inflate(R.layout.pager_relevant, null);
        exerRecycler = (RecyclerView) vRelevant.findViewById(R.id.pager_relevant_recycler);
        ArrayList<String> lists = new ArrayList<>();
        lists.add("第一章");
        lists.add("第二章");
        lists.add("第三章");
        lists.add("第四章");

        FullyLinearLayoutManager mg = new FullyLinearLayoutManager(MicrovideoActivity.this, LinearLayoutManager.VERTICAL, false);
        exerRecycler.setLayoutManager(mg);
        exerRecycler.addItemDecoration(new LinearLayoutItemDecoration(LinearLayoutManager.VERTICAL, getResources().getDimensionPixelSize(R.dimen.x1)));//设置颜色  和 分割线
//                final PagerRelevantAdapter prAdapter = new PagerRelevantAdapter(MicrovideoActivity.this, lists);
//                pcRecycler.setAdapter(prAdapter);
//                pcRecycler.setItemAnimator(new DefaultItemAnimator());
//
//                prAdapter.setOnItemClickLitener(new PagerRelevantAdapter.OnItemClickLiter() {
//                    @Override
//                    public void onItemClick(int position, RecyclerView recycler) {
//                        prAdapter.setPosition(position);
//                        prAdapter.notifyDataSetChanged();
//                    }
//                });
//                //获取相关习题
//                prAdapter.setItemClick(new PagerRelevantAdapter.ItemClick() {
//                    @Override
//                    public void onItemClick(String url) {
//                        Log.i("TAG", "子视频=============" + url);
//                    }
//                });


        listViews.add(vRelevant);

        MyPagerAdapter adapte = new MyPagerAdapter(listViews);
        mPager.setAdapter(adapte);
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }


    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    synopsis.setBackgroundColor(getResources().getColor(R.color.baiS));
                    catalog.setBackgroundColor(getResources().getColor(R.color.wvideo));
                    relevant.setBackgroundColor(getResources().getColor(R.color.wvideo));
                    break;
                case 1:
                    synopsis.setBackgroundColor(getResources().getColor(R.color.wvideo));
                    catalog.setBackgroundColor(getResources().getColor(R.color.baiS));
                    relevant.setBackgroundColor(getResources().getColor(R.color.wvideo));

                    break;
                case 2:
                    synopsis.setBackgroundColor(getResources().getColor(R.color.wvideo));
                    catalog.setBackgroundColor(getResources().getColor(R.color.wvideo));
                    relevant.setBackgroundColor(getResources().getColor(R.color.baiS));
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


    public void getinfo(int id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("vid", id);
        DescendingEncryption.init(map);
        Call<VideoInfoEntity> call = service.getinfo(map, SystemUtils.getHeader(MicrovideoActivity.this));
        call.enqueue(new Callback<VideoInfoEntity>() {
            @Override
            public void onResponse(Call<VideoInfoEntity> call, Response<VideoInfoEntity> response) {
                text1.setText((response.body().getList().getDescription() + "").equals("null") ? "" : response.body().getList().getDescription());
                text2.setText((response.body().getList().getTeacher_introd() + "").equals("null") ? "" : response.body().getList().getTeacher_introd());
                text3.setText((response.body().getList().getCourse_introd() + "").equals("null") ? "" : response.body().getList().getCourse_introd());
            }

            @Override
            public void onFailure(Call<VideoInfoEntity> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getcatalog(int id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", id);
        DescendingEncryption.init(map);
        Call<VideoCatalogEntity> call = service.getcatalog(map, SystemUtils.getHeader(MicrovideoActivity.this));
        call.enqueue(new Callback<VideoCatalogEntity>() {
            @Override
            public void onResponse(Call<VideoCatalogEntity> call, Response<VideoCatalogEntity> response) {
                Log.i("TAG", response.body().getList().size() + "");
                pcAdapter = new PagerCatalogAdapter(MicrovideoActivity.this, response.body().getList());
                CatalogRecycler.setAdapter(pcAdapter);
                CatalogRecycler.setItemAnimator(new DefaultItemAnimator());
                onItemClick();
            }

            @Override
            public void onFailure(Call<VideoCatalogEntity> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getVidPract(int id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", id);
        DescendingEncryption.init(map);
        Call<VideoCatalogEntity> call = service.getcatalog(map, SystemUtils.getHeader(MicrovideoActivity.this));
        call.enqueue(new Callback<VideoCatalogEntity>() {
            @Override
            public void onResponse(Call<VideoCatalogEntity> call, Response<VideoCatalogEntity> response) {
                Log.i("TAG", response.body().getList().size() + "");
                pcAdapter = new PagerCatalogAdapter(MicrovideoActivity.this, response.body().getList());
                CatalogRecycler.setAdapter(pcAdapter);
                CatalogRecycler.setItemAnimator(new DefaultItemAnimator());
                onItemClick();
            }

            @Override
            public void onFailure(Call<VideoCatalogEntity> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public void onItemClick() {
        pcAdapter.setOnItemClickLitener(new PagerCatalogAdapter.OnItemClickLiter() {//章的点击
            @Override
            public void onItemClick(int position) {
                pcAdapter.setPosition(position);
                pcAdapter.notifyDataSetChanged();
            }
        });
        //获取所点击播放视频的路径
        pcAdapter.setItemClick(new PagerCatalogAdapter.ItemClick() {
            @Override
            public void onItemClick(String url) {  //播放视频
                Log.i("TAG", "子视频=============" + url);
                if (playerStandard != null) {
                    playerStandard.release();
                }
//                boolean setUp = playerStandard.setUp(url, JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
                boolean setUp = playerStandard.setUp("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4", JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
                if (setUp) {
                    Glide.with(MicrovideoActivity.this).load(MyConstants.Service_URL + entity.getThumb()).into(playerStandard.thumbImageView);
                }
                playerStandard.startButton.performClick(); //模拟用户点击开始按钮，NORMAL状态下点击开始播放视频，播放中点击暂停视频


            }
        });
    }


}
