package com.example.administrator.zhike;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.adapter.HistoryAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class HomeSeleteActivity extends BaseActivity {
    ImageView sReturn;
    EditText sContont;
    TextView homeSelete;
    TextView homeDelete;
    RecyclerView homeHistory;
    RecyclerView homeHot;
    SharedPreferences sp;


    private Set<String> historyBuildSet;
    private ArrayList<String> historyBuildInfos;
    HistoryAdapter historyAdapter;


    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.home_selete_return:
                finish();
                return;
            case R.id.home_selete://搜索
                String name = sContont.getText().toString().trim();
                if (null == name)
                    name = "";
                if (!historyBuildSet.contains(name)) {
                    historyBuildSet.add(name);
                    Editor edit = sp.edit();
                    edit.putStringSet("selete", historyBuildSet);
                    edit.commit();
                }


                return;
            case R.id.home_selete_delete://删除
                Editor edit = sp.edit();
                edit.remove("selete");
                edit.commit();
                setdata();
                historyAdapter.notifyDataSetChanged();

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
        return R.layout.activity_home_selete;
    }

    @Override
    public void initView(View view) {
        sReturn = $(R.id.home_selete_return);
        sContont = $(R.id.home_selete_contont);
        homeSelete = $(R.id.home_selete);
        homeDelete = $(R.id.home_selete_delete);
        homeHistory = $(R.id.home_selete_history);
        homeHot = $(R.id.home_selete_hot);
    }

    @Override
    public void setListener() {
        sReturn.setOnClickListener(this);
        homeSelete.setOnClickListener(this);
        homeDelete.setOnClickListener(this);
        homeHistory.setNestedScrollingEnabled(false);
        homeHot.setNestedScrollingEnabled(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        sp = getSharedPreferences("xs", MODE_PRIVATE);
        setdata();

    }


    public void setdata() {
        historyBuildInfos = new ArrayList<>();
        historyBuildSet = new HashSet<>(sp.getStringSet("selete", new HashSet<String>()));
        if (historyBuildSet == null) {
            historyBuildSet = new TreeSet<>();
        } else {
            for (String historyBuildInfo : historyBuildSet) {
                historyBuildInfos.add(historyBuildInfo);
            }
        }

        if (historyAdapter == null) {
            historyAdapter = new HistoryAdapter(historyBuildInfos);
            homeHistory.setLayoutManager(new GridLayoutManager(HomeSeleteActivity.this, 2));
            homeHistory.setNestedScrollingEnabled(false);
            homeHistory.setItemAnimator(new DefaultItemAnimator());
            homeHistory.setAdapter(historyAdapter);
        } else {
            historyAdapter.setNewData(historyBuildInfos);
        }
        HistoryonItemClick();
    }


    public void HistoryonItemClick() {
        historyAdapter.setOnItemClickLitener(new HistoryAdapter.OnItemClickLiter() {
            @Override
            public void onItemClick(String contont) {
                showToast(contont);
            }
        });
    }


}
