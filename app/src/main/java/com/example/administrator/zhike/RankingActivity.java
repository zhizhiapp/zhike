package com.example.administrator.zhike;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.adapter.RankingAdapter;
import com.example.administrator.constants.MyList;
import com.example.administrator.constants.ServiceApi;
import com.example.administrator.entity.HomeMyhightscoreEntity;
import com.example.administrator.entity.MyhightscoreEntity;
import com.example.administrator.entity.SubjectEntity;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.FullyLinearLayoutManager;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SystemUtils;
import com.example.administrator.zhike.view.CircleImageView;
import com.example.administrator.zhike.view.LinearLayoutItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;

import base.BaseApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RankingActivity extends BaseActivity {
    ImageView eReturn;
    LinearLayout eChoice;
    TextView eChoiceText;
    ImageView eChoiceImg;
    ScrollView scrollView;
    CircleImageView header;
    TextView myName;
    TextView myRate;
    TextView myRanking;
    RecyclerView recycler;
    RankingAdapter adapter;
    Spinner spinner;
    SharedPreferences sp;
    private ServiceApi service;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.rankin_return://返回
                finish();
                return;
            case R.id.rankin_choice://课程选择
                spinner.performClick();
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
        return R.layout.activity_ranking;
    }

    @Override
    public void initView(View view) {
        eReturn = $(R.id.rankin_return);
        eChoice = $(R.id.rankin_choice);
        eChoiceText = $(R.id.rankin_choice_text);
        eChoiceImg = $(R.id.rankin_choice_img);

        scrollView = $(R.id.rankin_scroll);
        header = $(R.id.rankin_header);
        myName = $(R.id.rankin_name);
        myRate = $(R.id.rankin_rate);
        myRanking = $(R.id.rankin_ranking);
        recycler = $(R.id.rankin_recycler);
        spinner = $(R.id.rankin_choice_spinner);


    }

    @Override
    public void setListener() {
        eReturn.setOnClickListener(this);
        eChoice.setOnClickListener(this);
        recycler.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动
        scrollView.setVerticalScrollBarEnabled(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        sp = getSharedPreferences("xs", MODE_PRIVATE);


        service = RxjavaRetrofitUtils.getApi();
        initChoiceSubjects();
        index();
        getHomeMyhightscore(MyList.subjectId, sp.getInt("id", 0));
        getMyhightscore(MyList.subjectId, sp.getInt("id", 0));


    }

    public void index() {
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(RankingActivity.this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
        recycler.addItemDecoration(new LinearLayoutItemDecoration(LinearLayoutManager.VERTICAL, getResources().getDimensionPixelSize(R.dimen.x5)));//设置颜色  和 分割线

    }

    //获取所有人的排名情况
    private void getMyhightscore(int subjectid, int uid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subjectid);
        map.put("uid", uid);
        map.put("type", 2);//是否为首页：1=首页 2=其它
        DescendingEncryption.init(map);


        Call<MyhightscoreEntity> call = service.getMyhightscore(map, SystemUtils.getHeader(RankingActivity.this));
        call.enqueue(new Callback<MyhightscoreEntity>() {
            @Override
            public void onResponse(Call<MyhightscoreEntity> call, Response<MyhightscoreEntity> response) {
                if (response.body().getCode().equals("-1")) {
                    showToast("无做题记录，无排名无掌握率信息");
                } else if (response.body().getCode().equals("1")) {
                    ArrayList<MyhightscoreEntity.list> data = response.body().getList();
                    if (adapter == null) {
                        adapter = new RankingAdapter(response.body().getList());
                        recycler.setAdapter(adapter);
                        recycler.setItemAnimator(new DefaultItemAnimator());
                    } else {
                        adapter.setNewData(data);
                    }
                }
            }

            @Override
            public void onFailure(Call<MyhightscoreEntity> call, Throwable t) {
                showToast("网络异常");
            }
        });

    }


    /**
     * 获取主页排行数据
     */
    private void getHomeMyhightscore(int subjectid, int uid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subjectid);
        map.put("uid", uid);
        map.put("type", 1);//是否为首页：1=首页 2=其它
        DescendingEncryption.init(map);
        final ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<HomeMyhightscoreEntity> call = api.getHomeMyhightscore(map, SystemUtils.getHeader(RankingActivity.this));
        call.enqueue(new Callback<HomeMyhightscoreEntity>() {
            @Override
            public void onResponse(Call<HomeMyhightscoreEntity> call, Response<HomeMyhightscoreEntity> response) {
                String headimgurl = sp.getString("headimgurl", "");
                String nickname = sp.getString("nickname", "");
                myName.setText(nickname.equals("")||nickname.equals("null")?"用户":nickname);//名字
                if (headimgurl.equals("") || headimgurl.equals("null")) {
                    header.setImageResource(R.drawable.icon_error);
                } else {
                    Glide.with(BaseApplication.getContext()).load(headimgurl).into(header);
                }
                if (response.body().getCode().equals("-1")) {
                    myRate.setText("0");
                    myRanking.setText("第0名");
                } else if (response.body().getCode().equals("1")) {
                    String rateStr = String.format("%.2f", response.body().getList().getMastery_rate());
                    myRate.setText(rateStr + "");
                    myRanking.setText("第"+response.body().getList().getNumber() +"名");
                }
            }

            @Override
            public void onFailure(Call<HomeMyhightscoreEntity> call, Throwable t) {

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
                spinner.setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return entity.getList().size();
                    }

                    @Override
                    public Object getItem(int i) {
                        return entity.getList().get(i);
                    }

                    @Override
                    public long getItemId(int i) {
                        return i;
                    }

                    @Override
                    public View getView(int i, View view, ViewGroup viewGroup) {
                        ViewHolder viewHolder;
                        if (view == null) {
                            view = LayoutInflater.from(RankingActivity.this).inflate(R.layout.item_list, viewGroup, false);
                            viewHolder = new ViewHolder(view);
                            view.setTag(viewHolder);
                        } else {
                            viewHolder = (ViewHolder) view.getTag();
                        }
                        final String s = entity.getList().get(i).getName();
                        final int id = entity.getList().get(i).getId();
                        final int projectid = entity.getList().get(i).getProjectid();
                        viewHolder.textView.setText(s);
                        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                eChoiceText.setText(s);
                                getMyhightscore(id, sp.getInt("id", 0));
                                getHomeMyhightscore(id, sp.getInt("id", 0));
                                //手动隐藏spinner
                                spinner.setVisibility(View.GONE);
                                spinner.setVisibility(View.VISIBLE);
                            }
                        });
                        return view;
                    }

                    class ViewHolder {
                        private TextView textView;

                        public ViewHolder(View view) {
                            textView = (TextView) view.findViewById(R.id.item_list_text);
                        }
                    }

                });
            }
        });
    }


}
