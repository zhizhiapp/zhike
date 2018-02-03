package com.example.administrator.zhike;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.administrator.constants.MyList;
import com.example.administrator.constants.ServiceApi;
import com.example.administrator.entity.HomeMyhightscoreEntity;
import com.example.administrator.entity.LearningSituationEntity;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SystemUtils;
import com.example.administrator.zhike.view.CircleImageView;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class MyActivity extends Fragment implements View.OnClickListener {
    LinearLayout myProgramme;
    LinearLayout myLearningRecords;
    LinearLayout myWrongTitleRecords;
    LinearLayout myCollection;
    LinearLayout myShare;
    LinearLayout myInformation;
    LinearLayout mySign;
    LinearLayout myForum;
    LinearLayout myShoppingCart;
    LinearLayout myOrder;
    LinearLayout myAbout;
    LinearLayout myOpen;
    LinearLayout myLoding;
    LinearLayout myNoLogin;
    LinearLayout myYesLogin;
    LinearLayout myNews;
    Button myLogin;
    CircleImageView mHeader;
    ScrollView myScroll;
    Intent intent;
    SharedPreferences sp;
    int result = 10;
    Bundle bundle;

    TextView mName, mRanking, mRankingNum, mRankingList;
    TextView mSx, mYy, mLj, mXz;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my, container, false);


        index(view);
        return view;
    }


    private void index(View view) {
        myScroll = (ScrollView) view.findViewById(R.id.my_scroll);
        myScroll.setVerticalScrollBarEnabled(false);
        myProgramme = (LinearLayout) view.findViewById(R.id.my_programme);
        myLearningRecords = (LinearLayout) view.findViewById(R.id.my_learning_records);
        myWrongTitleRecords = (LinearLayout) view.findViewById(R.id.my_wrong_title_records);
        myCollection = (LinearLayout) view.findViewById(R.id.my_collection);
        myShare = (LinearLayout) view.findViewById(R.id.my_share);
        myInformation = (LinearLayout) view.findViewById(R.id.my_information);
        mySign = (LinearLayout) view.findViewById(R.id.my_sign);
        myForum = (LinearLayout) view.findViewById(R.id.my_forum);
        myShoppingCart = (LinearLayout) view.findViewById(R.id.my_shopping_cart);
        myOrder = (LinearLayout) view.findViewById(R.id.my_order);
        myAbout = (LinearLayout) view.findViewById(R.id.my_about);
        myNoLogin = (LinearLayout) view.findViewById(R.id.my_no_login);
        myYesLogin = (LinearLayout) view.findViewById(R.id.my_yes_login);
        myOpen = (LinearLayout) view.findViewById(R.id.my_open);
        myLoding = (LinearLayout) view.findViewById(R.id.my_loding);
        myNews = (LinearLayout) view.findViewById(R.id.my_news);
        myLogin = (Button) view.findViewById(R.id.my_login);
        mHeader = (CircleImageView) view.findViewById(R.id.my_header);

        mName = (TextView) view.findViewById(R.id.my_name);
        mRanking = (TextView) view.findViewById(R.id.my_ranking);
        mRankingNum = (TextView) view.findViewById(R.id.my_ranking_num);
        mRankingList = (TextView) view.findViewById(R.id.my_ranking_list);

        mSx = (TextView) view.findViewById(R.id.my_sx);
        mYy = (TextView) view.findViewById(R.id.my_yy);
        mLj = (TextView) view.findViewById(R.id.my_lj);
        mXz = (TextView) view.findViewById(R.id.my_xz);

        myProgramme.setOnClickListener(this);
        myNews.setOnClickListener(this);
        myLearningRecords.setOnClickListener(this);
        myWrongTitleRecords.setOnClickListener(this);
        myCollection.setOnClickListener(this);
        myShare.setOnClickListener(this);
        myInformation.setOnClickListener(this);
        mySign.setOnClickListener(this);
        myForum.setOnClickListener(this);
        myShoppingCart.setOnClickListener(this);
        myOrder.setOnClickListener(this);
        myAbout.setOnClickListener(this);
        myLogin.setOnClickListener(this);
        myOpen.setOnClickListener(this);
        myLoding.setOnClickListener(this);
        mRankingList.setOnClickListener(this);


        isLogin();

    }

    private void isLogin() {
        sp = getActivity().getSharedPreferences("xs", MODE_PRIVATE);
        if (sp.getString("token", "").length() > 0) {//登陆过
            myNoLogin.setVisibility(View.GONE);
            myYesLogin.setVisibility(View.VISIBLE);
            Glide.with(this).load(sp.getString("headimgurl", "")).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(mHeader);
            mName.setText(sp.getString("nickname", ""));
            mRanking.setText(MyList.name + "排名");
            if (MyList.Number == 0) {
                mRankingNum.setText("0");
            } else {
                mRankingNum.setText(MyList.Number + "");
            }
            getMyhightscore(MyList.subjectId, sp.getInt("id", 0));

        } else {
            myNoLogin.setVisibility(View.VISIBLE);
            myYesLogin.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (intent == null) {
            intent = new Intent();
        }

        switch (v.getId()) {
            case R.id.my_login://注册登陆
                intent.setClass(getActivity(), LoginActivity.class);
                startActivityForResult(intent, result);
                return;
            case R.id.my_ranking_list://查看排行榜
                intent.setClass(getActivity(), RankingActivity.class);
                startActivity(intent);
                return;
            case R.id.my_programme://智慧分析和方案
                if (bundle == null) {
                    bundle = new Bundle();
                }
                bundle.putString("title", "智能分析");
                bundle.putString("HomePageActivity", "Intelligent");
                intent.setClass(getActivity(), SchemeAnalysisActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                return;
            case R.id.my_learning_records://学习记录
                intent.setClass(getActivity(), MyLearningRecordsActivity.class);
                startActivity(intent);
                return;
            case R.id.my_wrong_title_records://我的错题
                intent.setClass(getActivity(), MyWrongTitleRecordsActivity.class);
                startActivity(intent);
                return;
            case R.id.my_collection://我的收藏
                intent.setClass(getActivity(), MyCollectionActivity.class);
                startActivity(intent);
                return;
            case R.id.my_loding://我的下载
                intent.setClass(getActivity(), MyLodingActivity.class);
                startActivity(intent);
                return;
            case R.id.my_open://我的公开课
                intent.setClass(getActivity(), MyOpenClassActivity.class);
                startActivity(intent);
                return;
            case R.id.my_share://推荐好友
                InvitingFriendsActivity.startActivity(getActivity());
                return;
            case R.id.my_information://个人信息
                intent.setClass(getActivity(), MyInformationActivity.class);
                startActivity(intent);
                return;
            case R.id.my_sign://签到
                intent.setClass(getActivity(), SignActivity.class);
                startActivityForResult(intent, 1);
                return;
            case R.id.my_forum://论坛
                //                Intent intent2 = new Intent(getActivity(),LoginActivity.class);
                //                startActivity(intent2);
                return;
            case R.id.my_shopping_cart://购物车
                intent.setClass(getActivity(), MyShoppingCartActivity.class);
                startActivity(intent);
                return;
            case R.id.my_order://我的订单
                //                Intent intent2 = new Intent(getActivity(),LoginActivity.class);
                //                startActivity(intent2);
                return;
            case R.id.my_news://消息中心
                return;
            case R.id.my_about://关于我们
                intent.setClass(getActivity(), AboutActivity.class);
                startActivity(intent);
                return;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == 20) {
            if (sp.getString("token", "").length() > 0) {//登陆过
                myNoLogin.setVisibility(View.GONE);
                myYesLogin.setVisibility(View.VISIBLE);
                Glide.with(this).load(sp.getString("headimgurl", "")).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(mHeader);
                mName.setText(sp.getString("nickname", ""));
                getMyhightscore(MyList.subjectId, sp.getInt("id", 0));
                getHomeMyhightscore(MyList.subjectId, sp.getInt("id", 0));
            } else {
                myNoLogin.setVisibility(View.VISIBLE);
                myYesLogin.setVisibility(View.GONE);
            }
        } else if (requestCode == 1 && resultCode == 3) {
            Glide.with(this).load(sp.getString("headimgurl", "")).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(mHeader);
        }
    }

    //排行数据
    private void getMyhightscore(int subjectid, int uid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subjectid);
        map.put("uid", uid);
        map.put("type", 3);//是否为首页：1=首页 2=其它
        DescendingEncryption.init(map);
        final ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<LearningSituationEntity> call = api.getLearningSituation(map, SystemUtils.getHeader(getActivity()));
        call.enqueue(new Callback<LearningSituationEntity>() {
            @Override
            public void onResponse(Call<LearningSituationEntity> call, Response<LearningSituationEntity> response) {
                if (response.body().getCode().equals("-1")) {
                    mSx.setText("0");
                    mYy.setText("0");
                    mLj.setText("0");
                    mXz.setText("0");
                } else if (response.body().getCode().equals("1")) {
                    LearningSituationEntity.list list1 = response.body().getList().get(0);
                    Log.i("TAG", "-----" + list1.getMastery_rate());
                    if (list1.getMastery_rate() > 0) {
                        mSx.setText(list1.getMastery_rate() + "%");
                    } else {
                        mSx.setText("0");
                    }

                    LearningSituationEntity.list list2 = response.body().getList().get(1);
                    if (list2.getMastery_rate() > 0) {
                        mYy.setText(list2.getMastery_rate() + "%");
                    } else {
                        mYy.setText("0");
                    }


                    LearningSituationEntity.list list3 = response.body().getList().get(2);
                    if (list3.getMastery_rate() > 0) {
                        mLj.setText(list3.getMastery_rate() + "%");
                    } else {
                        mLj.setText("0");
                    }


                    LearningSituationEntity.list list4 = response.body().getList().get(3);
                    if (list4.getMastery_rate() > 0) {
                        mXz.setText(list4.getMastery_rate() + "%");
                    } else {
                        mXz.setText("0");
                    }
                }
            }

            @Override
            public void onFailure(Call<LearningSituationEntity> call, Throwable t) {

            }
        });


    }

    private void getHomeMyhightscore(int subjectid, int uid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subjectid);
        map.put("uid", uid);
        map.put("type", 1);//是否为首页：1=首页 2=其它
        DescendingEncryption.init(map);
        final ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<HomeMyhightscoreEntity> call = api.getHomeMyhightscore(map, SystemUtils.getHeader(getActivity()));
        call.enqueue(new Callback<HomeMyhightscoreEntity>() {
            @Override
            public void onResponse(Call<HomeMyhightscoreEntity> call, Response<HomeMyhightscoreEntity> response) {
                if (response.body().getCode().equals("-1")) {
                    mRanking.setText("学习排行");
                    mRankingNum.setText("0");
                } else if (response.body().getCode().equals("1")) {
                    mRanking.setText(MyList.name + "排名");
                    mRankingNum.setText(response.body().getList().getNumber() + "");
                }
            }

            @Override
            public void onFailure(Call<HomeMyhightscoreEntity> call, Throwable t) {

            }
        });


    }


}