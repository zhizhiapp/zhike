package com.example.administrator.zhike;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.adapter.MyOpenClassAdapter;
import com.example.administrator.constants.ServiceApi;
import com.example.administrator.entity.MyOpenVdEntity;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.FullyLinearLayoutManager;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SystemUtils;
import com.example.administrator.zhike.view.LinearLayoutColorDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOpenClassActivity extends BaseActivity {//公开课
    ImageView myOpenReturn;
    RecyclerView myOpenRecycler;
    ServiceApi api;
    MyOpenClassAdapter openClassAdapter;
    LinearLayout layout;
    TextView micro;
    TextView exercises;
    TextView knowledge;
    TextView zhenti;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.my_openclass_return:
                finish();
                return;
            case R.id.my_openclass_micro:
                return;
            case R.id.my_openclass_exercises:
                startActivity(ExercisesActivity.class);
                return;
            case R.id.my_openclass_knowledge:
                startActivity(KnowledgeActivity.class);
                return;
            case R.id.my_openclass_zhenti:
                startActivity(ZhentiActivity.class);
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
        return R.layout.activity_my_openclass;
    }

    @Override
    public void initView(View view) {
        myOpenReturn = $(R.id.my_openclass_return);
        myOpenRecycler = $(R.id.my_openclass_recycler);
        micro = $(R.id.my_openclass_micro);
        exercises = $(R.id.my_openclass_exercises);
        knowledge = $(R.id.my_openclass_knowledge);
        zhenti = $(R.id.my_openclass_zhenti);
        layout = $(R.id.my_openclass_layout);
    }

    @Override
    public void setListener() {
        myOpenReturn.setOnClickListener(this);
        myOpenRecycler.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动
        micro.setOnClickListener(this);
        exercises.setOnClickListener(this);
        knowledge.setOnClickListener(this);
        zhenti.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        api = RxjavaRetrofitUtils.getApi();
        myOpenClass(1,10);
    }


    //公开课
    public void myOpenClass( int page, int pageNum) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("pageNum", pageNum);
        DescendingEncryption.init(map);
        Call<MyOpenVdEntity> call = api.getOpenVd(map, SystemUtils.getHeader(MyOpenClassActivity.this));
        call.enqueue(new Callback<MyOpenVdEntity>() {
            @Override
            public void onResponse(Call<MyOpenVdEntity> call, Response<MyOpenVdEntity> response) {
                ArrayList<MyOpenVdEntity.list> data = response.body().getList();
                Log.i("TAG", data.size() + "xxxx");
                if (data.size() <= 0) {
                    layout.setVisibility(View.VISIBLE);
                    return;
                }
                if (openClassAdapter == null) {
                    openClassAdapter = new MyOpenClassAdapter(data);
                    FullyLinearLayoutManager manager = new FullyLinearLayoutManager(MyOpenClassActivity.this, LinearLayoutManager.VERTICAL, false);
                    myOpenRecycler.setLayoutManager(manager);
                    myOpenRecycler.addItemDecoration(new LinearLayoutColorDecoration(LinearLayoutManager.VERTICAL, getResources().getDimensionPixelSize(R.dimen.x15), getResources().getColor(R.color.bj), getResources().getDimensionPixelSize(R.dimen.x15)));//设置颜色  和 分割线
                    myOpenRecycler.setAdapter(openClassAdapter);
                } else {
                    openClassAdapter.setNewData(data);
                }
                OpenClassmOnItemClickLitener();
                OpenClassmPlayerOnItemClickLitener();
            }

            @Override
            public void onFailure(Call<MyOpenVdEntity> call, Throwable t) {
            }
        });
    }


    public void OpenClassmOnItemClickLitener() {
        openClassAdapter.setOnItemClickLitener(new MyOpenClassAdapter.OnItemClickLiter() {
            @Override
            public void onItemClick(int position) {
                openClassAdapter.setPosition(position);
                openClassAdapter.notifyDataSetChanged();
            }
        });
    }

    public void OpenClassmPlayerOnItemClickLitener() {
        openClassAdapter.setOnItemClickLitener(new MyOpenClassAdapter.PlayerOnItemClickLiter() {
            @Override
            public void onItemClick(JCVideoPlayerStandard playerStandard) {
                playerStandard.startButton.performClick(); //模拟用户点击开始按钮，NORMAL状态下点击开始播放视频，播放中点击暂停视频
            }
        });
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













}
