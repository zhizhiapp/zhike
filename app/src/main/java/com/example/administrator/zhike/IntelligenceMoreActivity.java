package com.example.administrator.zhike;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.adapter.IntelligenceMoreAdapter;
import com.example.administrator.constants.ServiceApi;
import com.example.administrator.entity.IntelligenceMoreEntity;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SystemUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntelligenceMoreActivity extends BaseActivity {

    ImageView moreReturn;
    RecyclerView moreRecycler;
    IntelligenceMoreAdapter mIntelliAdapter;
    int page = 1;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.intelligence_more_return:
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
        return R.layout.activity_intelligence_more;
    }

    @Override
    public void initView(View view) {
        moreReturn = $(R.id.intelligence_more_return);
        moreRecycler = $(R.id.intelligence_more_recycler);
    }

    @Override
    public void setListener() {
        moreReturn.setOnClickListener(this);
        moreRecycler.setNestedScrollingEnabled(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        getIntelligenceList(1, page, 10);
    }


    //智能资讯
    public void getIntelligenceList(int projectid, int page, int perNum) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectid", projectid);
        map.put("page", page);
        map.put("perNum", perNum);
        DescendingEncryption.init(map);
        final ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<IntelligenceMoreEntity> call = api.getIntelligenceList(map, SystemUtils.getHeader(IntelligenceMoreActivity.this));
        call.enqueue(new Callback<IntelligenceMoreEntity>() {
            @Override
            public void onResponse(Call<IntelligenceMoreEntity> call, Response<IntelligenceMoreEntity> response) {
                ArrayList<IntelligenceMoreEntity.list> data = response.body().getList();
                if (mIntelliAdapter == null) {
                    mIntelliAdapter = new IntelligenceMoreAdapter(data);
                    moreRecycler.setLayoutManager(new LinearLayoutManager(IntelligenceMoreActivity.this));
                    moreRecycler.setHasFixedSize(true);
                    moreRecycler.setItemAnimator(new DefaultItemAnimator());
                    moreRecycler.setAdapter(mIntelliAdapter);
                } else {
                    mIntelliAdapter.setNewData(data);
                }
            }

            @Override
            public void onFailure(Call<IntelligenceMoreEntity> call, Throwable t) {
            }
        });
    }
}
