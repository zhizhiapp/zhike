package com.example.administrator.zhike;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.adapter.ListSubjectAdapter;
import com.example.administrator.adapter.MyWrongTitleAdapter;
import com.example.administrator.constants.MyList;
import com.example.administrator.constants.ServiceApi;
import com.example.administrator.entity.MyWrongTitleRecordsEntity;
import com.example.administrator.entity.SubjectEntity;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.FullyLinearLayoutManager;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SystemUtils;
import com.example.administrator.zhike.view.LinearLayoutColorDecoration;
import com.example.administrator.zhike.view.MicroClassPow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import base.BaseApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWrongTitleRecordsActivity extends BaseActivity {

    ImageView mReturn;
    LinearLayout mChoiceLayout;
    TextView mChoiceText;
    ImageView mChoiceImg;
    TextView mNum;
    TextView mTitle;
    RecyclerView mRecycler;
    TextView mExercises;
    MicroClassPow pow;
    MyWrongTitleAdapter mAdapter;
    ScrollView scrollView;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.my_wrong_title_records_return://返回
                finish();
                return;
            case R.id.my_wrong_title_records_choice://课程选择
                initChoiceSubjects();
                return;
            case R.id.my_wrong_title_records_exercises://习题
                startActivity(ExercisesActivity.class);
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
        return R.layout.activity_my_wrong_title_records;
    }

    @Override
    public void initView(View view) {
        mReturn = $(R.id.my_wrong_title_records_return);
        mChoiceLayout = $(R.id.my_wrong_title_records_choice);
        mChoiceText = $(R.id.my_wrong_title_records_choice_text);
        mChoiceImg = $(R.id.my_wrong_title_records_choice_img);
        mNum = $(R.id.my_wrong_title_records_num);
        mTitle = $(R.id.my_wrong_title_records_title);
        mRecycler = $(R.id.my_wrong_title_records_recycler);
        mExercises = $(R.id.my_wrong_title_records_exercises);
        scrollView = $(R.id.my_wrong_title_records_scroll);

    }

    @Override
    public void setListener() {
        mReturn.setOnClickListener(this);
        mChoiceLayout.setOnClickListener(this);
        mExercises.setOnClickListener(this);
        scrollView.setVerticalScrollBarEnabled(false);
        mRecycler.setNestedScrollingEnabled(false);
    }

    @Override
    public void doBusiness(Context mContext) {

        MyWrongTitleRecords(MyList.subjectId);

    }


    public void MyWrongTitleRecords(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("subjectid", id);
        DescendingEncryption.init(map);
        ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<MyWrongTitleRecordsEntity> call = api.myErrQuest(map, SystemUtils.getHeader(MyWrongTitleRecordsActivity.this));
        call.enqueue(new Callback<MyWrongTitleRecordsEntity>() {
            @Override
            public void onResponse(Call<MyWrongTitleRecordsEntity> call, Response<MyWrongTitleRecordsEntity> response) {
                if (response.body().getList().getChapter_list().size() > 0) {
                    mRecycler.setVisibility(View.VISIBLE);
                    mExercises.setVisibility(View.GONE);
                    ArrayList<MyWrongTitleRecordsEntity.list.chapterList> data = response.body().getList().getChapter_list();
                    mNum.setText(response.body().getList().getAll_error_number() + "");
                    mTitle.setText(response.body().getList().getError_max_name().equals("null") || response.body().getList().getError_max_name().equals("") ? "你很棒哦，没有错题哦，快去联系吧！" : response.body().getList().getError_max_name() + "一片掺淡，要多多练习");
                    if (mAdapter == null) {
                        mAdapter = new MyWrongTitleAdapter(data);
                        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(MyWrongTitleRecordsActivity.this, LinearLayoutManager.VERTICAL, false);
                        mRecycler.setLayoutManager(manager);
                        mRecycler.addItemDecoration(new LinearLayoutColorDecoration(LinearLayoutManager.VERTICAL, getResources().getDimensionPixelSize(R.dimen.x15), getResources().getColor(R.color.bj), getResources().getDimensionPixelSize(R.dimen.x15)));//设置颜色  和 分割线
                        mRecycler.setAdapter(mAdapter);
                    } else {
                        mAdapter.setNewData(data);
                    }
                } else {
                    mRecycler.setVisibility(View.GONE);
                    mExercises.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<MyWrongTitleRecordsEntity> call, Throwable t) {
                showToast("网络异常");
            }
        });

    }


    /**
     * 获取主页科目选择数据
     */
    private void initChoiceSubjects() {
        BaseApplication.getChoiceSubjects(new BaseApplication.SubjectListener() {
            @Override
            public void onData(final SubjectEntity entity) {
                pow = new MicroClassPow(MyWrongTitleRecordsActivity.this, mChoiceImg);// 实例化SelectPicPopupWindow
                View menuWindowView = pow.getContentView();
                ListView listView = (ListView) menuWindowView.findViewById(R.id.home_list);
                ListSubjectAdapter adapter = new ListSubjectAdapter(MyWrongTitleRecordsActivity.this, entity.getList());
                listView.setAdapter(adapter);
                pow.showAsDropDown(findViewById(R.id.my_wrong_title_records_choice_text), -getResources().getDimensionPixelSize(R.dimen.x10), 0);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        SubjectEntity.listEntity entity1 = entity.getList().get(position);
                        Log.i("TAG", entity1.getName());
                        MyWrongTitleRecords(entity1.getId());
                    }
                });
            }
        });
    }


}
