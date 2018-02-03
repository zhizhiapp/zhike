package com.example.administrator.zhike;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyLearningRecordsActivity extends BaseActivity {
    ImageView rReturn;
    TextView rFootprint;
    TextView rMarking;
    ViewPager pager;
    List<View> listViews;
    View view1, view2;


    RecyclerView fooRecycler;
    RecyclerView markinRecycler;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.my_records_return://返回
                finish();
                return;
            case R.id.my_records_footprint://足迹
                rFootprint.setBackgroundColor(getResources().getColor(R.color.baiS));
                rMarking.setBackgroundColor(getResources().getColor(R.color.myloding));
                rFootprint.setClickable(false);
                rMarking.setClickable(true);
                pager.setCurrentItem(0);
                return;
            case R.id.my_records_marking://阅卷
                rFootprint.setBackgroundColor(getResources().getColor(R.color.myloding));
                rMarking.setBackgroundColor(getResources().getColor(R.color.baiS));
                rFootprint.setClickable(true);
                rMarking.setClickable(false);
                pager.setCurrentItem(1);
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
        return R.layout.activity_my_learning_records;
    }

    @Override
    public void initView(View view) {
        rReturn = $(R.id.my_records_return);
        rFootprint = $(R.id.my_records_footprint);
        rMarking = $(R.id.my_records_marking);
        pager = $(R.id.my_records_pager);

    }

    @Override
    public void setListener() {
        rReturn.setOnClickListener(this);
        rFootprint.setOnClickListener(this);
        rMarking.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        index();
    }


    private void index() {
        listViews = new ArrayList<>();
        LayoutInflater mInflater = getLayoutInflater();

        view1 = mInflater.inflate(R.layout.pager_my_footprint, null);//足迹
        fooRecycler = (RecyclerView) view1.findViewById(R.id.pager_my_footprint_recycler);
        fooRecycler.setNestedScrollingEnabled(false);
        listViews.add(view1);

        view2 = mInflater.inflate(R.layout.pager_my_marking, null);//阅卷
        markinRecycler = (RecyclerView) view2.findViewById(R.id.pager_my_marking_recycler);
        markinRecycler.setNestedScrollingEnabled(false);
        listViews.add(view2);


        MyPagerAdapter adapte = new MyPagerAdapter(listViews);
        pager.setAdapter(adapte);
        pager.setCurrentItem(0);
        pager.setOnPageChangeListener(new MyOnPageChangeListener());

    }


    //页卡切换监听
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    rFootprint.setBackgroundColor(getResources().getColor(R.color.baiS));
                    rMarking.setBackgroundColor(getResources().getColor(R.color.myloding));
                    break;
                case 1:
                    rFootprint.setBackgroundColor(getResources().getColor(R.color.myloding));
                    rMarking.setBackgroundColor(getResources().getColor(R.color.baiS));

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


    //      足迹
    //    public void aaaaa( int id) {
    //            Map<String, Object> map = new HashMap<>();
    //            map.put("subjectid", id);
    //            map.put("perNum", 4);
    //            DescendingEncryption.init(map);
    //            ServiceApi api = RxjavaRetrofitUtils.getApi();
    //            Call<HomeKnowledgeEntity> call = api.getKnowledge(map, SystemUtils.getHeader(MyLearningRecordsActivity.this));
    //            call.enqueue(new Callback<HomeZhenTiEntity>() {
    //                @Override
    //                public void onResponse(Call<HomeZhenTiEntity> call, Response<HomeZhenTiEntity> response) {
    //                    ArrayList<HomeZhenTiEntity.list> data = response.body().getList();
    //                    Log.i("TAG", data.size() + "xxxx");
    //                    if (mZhentiAdapter == null) {
    //                        mZhentiAdapter = new ZhenTiHomeAdapter(data);
    //                        fooRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    //                        fooRecycler.setItemAnimator(new DefaultItemAnimator());
    //                        fooRecycler.setAdapter(mZhentiAdapter);
    //                    } else {
    //                        mZhentiAdapter.setNewData(data);
    //                    }
    //                }
    //
    //                @Override
    //                public void onFailure(Call<HomeZhenTiEntity> call, Throwable t) {
    //                }
    //            });
    //
    //    }

    //          阅卷
    //    public void aaaaa( int id) {
    //            Map<String, Object> map = new HashMap<>();
    //            map.put("subjectid", id);
    //            map.put("perNum", 4);
    //            DescendingEncryption.init(map);
    //            ServiceApi api = RxjavaRetrofitUtils.getApi();
    //            Call<HomeKnowledgeEntity> call = api.getKnowledge(map, SystemUtils.getHeader(MyLearningRecordsActivity.this));
    //            call.enqueue(new Callback<HomeZhenTiEntity>() {
    //                @Override
    //                public void onResponse(Call<HomeZhenTiEntity> call, Response<HomeZhenTiEntity> response) {
    //                    ArrayList<HomeZhenTiEntity.list> data = response.body().getList();
    //                    Log.i("TAG", data.size() + "xxxx");
    //                    if (mZhentiAdapter == null) {
    //                        mZhentiAdapter = new ZhenTiHomeAdapter(data);
    //                        fooRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    //                        fooRecycler.setItemAnimator(new DefaultItemAnimator());
    //                        fooRecycler.setAdapter(mZhentiAdapter);
    //                    } else {
    //                        mZhentiAdapter.setNewData(data);
    //                    }
    //                }
    //
    //                @Override
    //                public void onFailure(Call<HomeZhenTiEntity> call, Throwable t) {
    //                }
    //            });
    //
    //    }

}
