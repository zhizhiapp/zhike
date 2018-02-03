package com.example.administrator.zhike;


import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.adapter.ExercisesHomeAdapter;
import com.example.administrator.adapter.IntelligenceHomeAdapter;
import com.example.administrator.adapter.KnowledgeHomeAdapter;
import com.example.administrator.adapter.MicroClassHomeAdapter;
import com.example.administrator.adapter.ZhenTiHomeAdapter;
import com.example.administrator.constants.MyList;
import com.example.administrator.constants.ServiceApi;
import com.example.administrator.entity.CollectionEntity;
import com.example.administrator.entity.HomeExercisesEntity;
import com.example.administrator.entity.HomeIntelligenceEntity;
import com.example.administrator.entity.HomeKnowledgeEntity;
import com.example.administrator.entity.HomeMicroClassEntity;
import com.example.administrator.entity.HomeMyhightscoreEntity;
import com.example.administrator.entity.HomeZhenTiEntity;
import com.example.administrator.entity.SubjectEntity;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.GlideImageLoader;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SystemUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.transformer.CubeOutTransformer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import base.BaseApplication;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class HomePageActivity extends Fragment implements View.OnClickListener {
    RecyclerView mIcroRecycler;
    RecyclerView mExercRecycler;
    RecyclerView mKnowlRecycler;
    RecyclerView mZhentiRecycler;
    RecyclerView mIntelliRecycler;
    ScrollView scrollView;
    MicroClassHomeAdapter mIcroClassAdapter;
    ExercisesHomeAdapter mExercisesAdapter;
    KnowledgeHomeAdapter mKnowledgeAdapter;
    ZhenTiHomeAdapter mZhentiAdapter;
    IntelligenceHomeAdapter mIntelliAdapter;
    ImageView homeSearch;// 搜索
    ImageView homeChoiceImg;// 课程选择
    LinearLayout homeChoice;//课程选择
    RelativeLayout homeIntelligenttest;//智能测试
    LinearLayout homeMicroclass;//微课
    LinearLayout homeExercises;//习题
    LinearLayout homeKnowledge;//知识库
    LinearLayout homeZhenti;//真题
    Button homeLearningAnalysis;//智能分析
    Button homeSolution;//智慧方案
    LinearLayout homeRanking;//排名
    TextView homeAfewNames;//第几名
    LinearLayout homeMasteryRate;//掌握率
    TextView homePercentage;//百分比
    TextView homeChoiceTitle;//百分比
    Button homeMicroclassMore;//微课更多
    Button homeExercisesMore;//习题更多
    Button homeKnowledgeMore;//知识库更多
    Button homeZhentiMore;//真题更多
    Button homeIntelligenceMore;//智能咨询更多
    ImageView homeArtificial;
    ImageView homeNews;

    private Spinner spinner;
    private ServiceApi service;
    private Banner banner;
    ArrayList<String> images = new ArrayList<>();

    private View fragmentView;

    private int loadInt = 0;
    SharedPreferences sp;
    int userId = 0;
    private AlertDialog alertDialog;
    /**
     * 当前科目I
     */
    private int subjectId = 2;
    MainActivity activity;
    Intent intent;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.activity_homepage, container, false);
            initView(fragmentView);
        }
        return fragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (fragmentView != null) {
            ViewGroup viewGroup = (ViewGroup) fragmentView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(fragmentView);
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        service = RxjavaRetrofitUtils.RxJavaRetrofit();
        initChoiceSubjects();
        initBanner();
        //默认科目ID为2 项目ID为1
        initData(subjectId, 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        if (intent == null) {
            intent = new Intent();
        }
        switch (v.getId()) {
            //搜索
            case R.id.home_search:
                intent.setClass(getActivity(), HomeSeleteActivity.class);
                startActivity(intent);
                break;
            //登陆
            case R.id.home_choice: //课程选择
                spinner.performClick();
                break;

            case R.id.home_intelligenttest://智能测试
                HomeIntelligentTestActivity.startActivity(getActivity(), MyList.subjectId);
                break;

            case R.id.home_microclass: //微课
                if (activity == null) {
                    activity = (MainActivity) getActivity();
                }
                activity.mSwitch();
                break;
            case R.id.home_exercises: //习题
                intent.setClass(getActivity(), ExercisesActivity.class);
                startActivity(intent);
                break;
            case R.id.home_knowledge://知识库
                intent.setClass(getActivity(), KnowledgeActivity.class);
                startActivity(intent);
                break;
            case R.id.home_zhenti://真题
                intent.setClass(getActivity(), ZhentiActivity.class);
                startActivity(intent);
                break;
            case R.id.home_analysis_analysis: //智能分析
                if (bundle == null) {
                    bundle = new Bundle();
                }
                bundle.putString("title", "智能分析");
                bundle.putString("HomePageActivity", "Intelligent");
                intent.setClass(getActivity(), SchemeAnalysisActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            //智慧方案
            case R.id.home_analysis_programme:
                if (bundle == null) {
                    bundle = new Bundle();
                }
                bundle.putString("title", "智慧方案");
                bundle.putString("HomePageActivity", "WisdomScheme");
                intent.setClass(getActivity(), SchemeAnalysisActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            //排名
            case R.id.home_ranking:
                if (sp.getString("token", "").length() > 0) {
                    intent.setClass(getActivity(), RankingActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }

                break;
            //掌握率
            case R.id.home_mastery_rate:
                break;
            //微课更多
            case R.id.home_microclass_more:
                if (activity == null) {
                    activity = (MainActivity) getActivity();
                }
                activity.mSwitch();
                break;
            //习题更多
            case R.id.home_exercises_more:
                intent.setClass(getActivity(), ExercisesActivity.class);
                startActivity(intent);
                break;
            //知识库更多
            case R.id.home_knowledge_more:
                intent.setClass(getActivity(), KnowledgeActivity.class);
                startActivity(intent);
                break;
            //真题更多
            case R.id.home_zhenti_more:
                intent.setClass(getActivity(), ZhentiActivity.class);
                startActivity(intent);
                break;
            //智能资讯更多
            case R.id.home_intelligence_more:
                intent.setClass(getActivity(), IntelligenceMoreActivity.class);
                startActivity(intent);
                break;
            //客服
            case R.id.home_artificial:
                intent.setClass(getActivity(), HomeArtificialActivity.class);
                startActivity(intent);
                break;
            //消息
            case R.id.home_news:
                intent.setClass(getActivity(), HomeNewsActivity.class);
                startActivity(intent);
                break;
        }
    }


    public void initView(View v) {
        if (v != null) {
            spinner = (Spinner) v.findViewById(R.id.Spinner);
            scrollView = (ScrollView) v.findViewById(R.id.home_scrollview);
            scrollView.setVerticalScrollBarEnabled(false);
            mIcroRecycler = (RecyclerView) v.findViewById(R.id.homepage_microclass);
            mIcroRecycler.setNestedScrollingEnabled(false);
            mExercRecycler = (RecyclerView) v.findViewById(R.id.homepage_exercises);
            mExercRecycler.setNestedScrollingEnabled(false);
            mKnowlRecycler = (RecyclerView) v.findViewById(R.id.homepage_knowledge);
            mKnowlRecycler.setNestedScrollingEnabled(false);
            mZhentiRecycler = (RecyclerView) v.findViewById(R.id.homepage_zhenti);
            mZhentiRecycler.setNestedScrollingEnabled(false);
            mIntelliRecycler = (RecyclerView) v.findViewById(R.id.homepage_intelligence);
            mIntelliRecycler.setNestedScrollingEnabled(false);
            homeSearch = (ImageView) v.findViewById(R.id.home_search);
            homeChoiceImg = (ImageView) v.findViewById(R.id.home_choice_img);
            homeChoice = (LinearLayout) v.findViewById(R.id.home_choice);
            homeIntelligenttest = (RelativeLayout) v.findViewById(R.id.home_intelligenttest);
            homeMicroclass = (LinearLayout) v.findViewById(R.id.home_microclass);
            homeExercises = (LinearLayout) v.findViewById(R.id.home_exercises);
            homeKnowledge = (LinearLayout) v.findViewById(R.id.home_knowledge);
            homeZhenti = (LinearLayout) v.findViewById(R.id.home_zhenti);
            homeLearningAnalysis = (Button) v.findViewById(R.id.home_analysis_analysis);
            homeSolution = (Button) v.findViewById(R.id.home_analysis_programme);
            homeRanking = (LinearLayout) v.findViewById(R.id.home_ranking);
            homeAfewNames = (TextView) v.findViewById(R.id.home_afew_names);
            homeMasteryRate = (LinearLayout) v.findViewById(R.id.home_mastery_rate);
            homePercentage = (TextView) v.findViewById(R.id.home_percentage);
            homeChoiceTitle = (TextView) v.findViewById(R.id.home_choice_title);
            homeMicroclassMore = (Button) v.findViewById(R.id.home_microclass_more);
            homeExercisesMore = (Button) v.findViewById(R.id.home_exercises_more);
            homeKnowledgeMore = (Button) v.findViewById(R.id.home_knowledge_more);
            homeZhentiMore = (Button) v.findViewById(R.id.home_zhenti_more);
            homeIntelligenceMore = (Button) v.findViewById(R.id.home_intelligence_more);
            banner = (Banner) v.findViewById(R.id.banner);

            homeArtificial = (ImageView) v.findViewById(R.id.home_artificial);
            homeNews = (ImageView) v.findViewById(R.id.home_news);


            homeArtificial.setOnClickListener(this);
            homeNews.setOnClickListener(this);
            homeSearch.setOnClickListener(this);
            homeChoice.setOnClickListener(this);
            homeIntelligenttest.setOnClickListener(this);
            homeMicroclass.setOnClickListener(this);
            homeExercises.setOnClickListener(this);
            homeKnowledge.setOnClickListener(this);
            homeZhenti.setOnClickListener(this);
            homeLearningAnalysis.setOnClickListener(this);
            homeSolution.setOnClickListener(this);
            homeRanking.setOnClickListener(this);
            homeMasteryRate.setOnClickListener(this);
            homeMicroclassMore.setOnClickListener(this);
            homeExercisesMore.setOnClickListener(this);
            homeKnowledgeMore.setOnClickListener(this);
            homeZhentiMore.setOnClickListener(this);
            homeIntelligenceMore.setOnClickListener(this);

            sp = getActivity().getSharedPreferences("xs", MODE_PRIVATE);
            userId = sp.getInt("id", 0);
        }
    }

    /**
     * 根据ID加载数据
     *
     * @param id        科目ID
     * @param projectId 项目ID
     */
    private void initData(int id, int projectId) {
        MyList.subjectId = id;
        subjectId = id;
        alertDialog = getLoading();
        alertDialog.show();

        if (sp.getString("token", "").length() > 0) {
            getHomeMyhightscore(id, userId);
        } else {
            homeAfewNames.setText("--");
            homePercentage.setText("--");
        }

        initMicroClass(id);
        initExercises(id);
        initKnowledge(id);
        initZhenTi(id);
        initIntelligence(projectId);
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
                            view = LayoutInflater.from(getActivity()).inflate(R.layout.item_list, viewGroup, false);
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
                                homeChoiceTitle.setText(s);
                                MyList.name = s;
                                initData(id, projectid);
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
        Call<HomeMyhightscoreEntity> call = api.getHomeMyhightscore(map, SystemUtils.getHeader(getActivity()));
        call.enqueue(new Callback<HomeMyhightscoreEntity>() {
            @Override
            public void onResponse(Call<HomeMyhightscoreEntity> call, Response<HomeMyhightscoreEntity> response) {
                if (response.body().getCode().equals("-1")) {
                    homeAfewNames.setText("- -");
                    homePercentage.setText("- -");
                } else if (response.body().getCode().equals("1")) {
                    homeAfewNames.setText(response.body().getList().getNumber() + ""); // number
                    MyList.Number = response.body().getList().getNumber();
                    homePercentage.setText(response.body().getList().getMastery_rate() + "%");
                }
            }

            @Override
            public void onFailure(Call<HomeMyhightscoreEntity> call, Throwable t) {

            }
        });


    }


    //微课
    public void initMicroClass(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("subjectid", id);
        map.put("page", 1);
        map.put("perNum", 4);
        DescendingEncryption.init(map);
        final ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<HomeMicroClassEntity> call = api.getVideo(map, SystemUtils.getHeader(getActivity()));
        call.enqueue(new Callback<HomeMicroClassEntity>() {
            @Override
            public void onResponse(Call<HomeMicroClassEntity> call, Response<HomeMicroClassEntity> response) {
                loadInt++;
                ArrayList<HomeMicroClassEntity.list> data = response.body().getList();
                if (mIcroClassAdapter == null) {
                    mIcroClassAdapter = new MicroClassHomeAdapter(data);
                    mIcroRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    mIcroRecycler.setNestedScrollingEnabled(false);
                    mIcroRecycler.setItemAnimator(new DefaultItemAnimator());
                    mIcroRecycler.setAdapter(mIcroClassAdapter);
                } else {
                    mIcroClassAdapter.setNewData(data);
                }
                MicroAdapterOnItemClickLiter();
                shoppingcartOnItemClickLitener();
                loadDismiss();

            }

            @Override
            public void onFailure(Call<HomeMicroClassEntity> call, Throwable t) {
                loadInt++;
                loadDismiss();
            }
        });
    }

    //习题
    public void initExercises(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("subjectid", id);
        map.put("page", 1);
        map.put("perNum", 5);
        DescendingEncryption.init(map);
        ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<HomeExercisesEntity> call = api.getExercises(map, SystemUtils.getHeader(getActivity()));
        call.enqueue(new Callback<HomeExercisesEntity>() {
            @Override
            public void onResponse(Call<HomeExercisesEntity> call, Response<HomeExercisesEntity> response) {
                loadInt++;
                ArrayList<HomeExercisesEntity.list> data = response.body().getList();
                if (mExercisesAdapter == null) {
                    mExercisesAdapter = new ExercisesHomeAdapter(data);
                    mExercRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mExercRecycler.setItemAnimator(new DefaultItemAnimator());
                    mExercRecycler.setAdapter(mExercisesAdapter);
                } else {
                    mExercisesAdapter.setNewData(data);
                }
                //                ExercisesAdapterOnItemClickLiter();
                loadDismiss();
            }

            @Override
            public void onFailure(Call<HomeExercisesEntity> call, Throwable t) {
                loadInt++;
                loadDismiss();
            }
        });
    }

    //知识库
    public void initKnowledge(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("subjectid", id);
        map.put("page", 1);
        map.put("perNum", 5);
        DescendingEncryption.init(map);
        ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<HomeKnowledgeEntity> call = api.getKnowledge(map, SystemUtils.getHeader(getActivity()));
        call.enqueue(new Callback<HomeKnowledgeEntity>() {
            @Override
            public void onResponse(Call<HomeKnowledgeEntity> call, Response<HomeKnowledgeEntity> response) {
                loadInt++;
                ArrayList<HomeKnowledgeEntity.list> data = response.body().getList();
                if (mKnowledgeAdapter == null) {
                    mKnowledgeAdapter = new KnowledgeHomeAdapter(data);
                    mKnowlRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mKnowlRecycler.setItemAnimator(new DefaultItemAnimator());
                    mKnowlRecycler.setAdapter(mKnowledgeAdapter);
                } else {
                    mKnowledgeAdapter.setNewData(data);
                }

                //                KnowledgeAdapterOnItemClickLiter();
                loadDismiss();
            }

            @Override
            public void onFailure(Call<HomeKnowledgeEntity> call, Throwable t) {
                loadInt++;
                loadDismiss();
            }
        });

    }

    //真题
    public void initZhenTi(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("subjectid", id);
        map.put("perNum", 4);
        DescendingEncryption.init(map);
        ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<HomeZhenTiEntity> call = api.getZhenTi(map, SystemUtils.getHeader(getActivity()));
        call.enqueue(new Callback<HomeZhenTiEntity>() {
            @Override
            public void onResponse(Call<HomeZhenTiEntity> call, Response<HomeZhenTiEntity> response) {
                loadInt++;
                ArrayList<HomeZhenTiEntity.list> data = response.body().getList();
                Log.i("TAG", data.size() + "xxxx");
                if (mZhentiAdapter == null) {
                    mZhentiAdapter = new ZhenTiHomeAdapter(data);
                    mZhentiRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mZhentiRecycler.setItemAnimator(new DefaultItemAnimator());
                    mZhentiRecycler.setAdapter(mZhentiAdapter);
                } else {
                    mZhentiAdapter.setNewData(data);
                }
                //                ZhenTiAdapterOnItemClickLiter();
                loadDismiss();
            }

            @Override
            public void onFailure(Call<HomeZhenTiEntity> call, Throwable t) {
                loadInt++;
                loadDismiss();
            }
        });

    }

    //智能资讯
    public void initIntelligence(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectid", id);
        map.put("page", 1);
        map.put("perNum", 2);
        DescendingEncryption.init(map);
        final ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<HomeIntelligenceEntity> call = api.getJournalism(map, SystemUtils.getHeader(getActivity()));
        call.enqueue(new Callback<HomeIntelligenceEntity>() {
            @Override
            public void onResponse(Call<HomeIntelligenceEntity> call, Response<HomeIntelligenceEntity> response) {
                loadInt++;
                ArrayList<HomeIntelligenceEntity.list> data = response.body().getList();
                if (mIntelliAdapter == null) {
                    mIntelliAdapter = new IntelligenceHomeAdapter(data);
                    mIntelliRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mIntelliRecycler.setHasFixedSize(true);
                    mIntelliRecycler.setItemAnimator(new DefaultItemAnimator());
                    mIntelliRecycler.setAdapter(mIntelliAdapter);
                } else {
                    mIntelliAdapter.setNewData(data);
                }
                loadDismiss();
            }

            @Override
            public void onFailure(Call<HomeIntelligenceEntity> call, Throwable t) {
                loadInt++;
                loadDismiss();
            }
        });
    }

    private void loadDismiss() {
        if (alertDialog != null && loadInt == 5) {
            loadInt = 0;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    alertDialog.dismiss();
                }
            }, 1000);
        }
    }


    public AlertDialog getLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog alertDialog = builder.create();
        alertDialog.setMessage("加载中...");
        return alertDialog;
    }

    //设置轮播图片
    public void setBanner() {
        banner.setImages(images)
                .setBannerAnimation(CubeOutTransformer.class)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setIndicatorGravity(BannerConfig.CENTER)
                .setDelayTime(50000)
                .setImageLoader(new GlideImageLoader())
                .start();
    }

    /**
     * 初始化轮播图
     */
    public void initBanner() {
        Call<String> resultObser = service.getCarousel(DescendingEncryption.getDefault(), SystemUtils.getHeader(getActivity()));
        resultObser.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body().length() > 0) {
                        JSONObject resultJson = new JSONObject(response.body());
                        String status = resultJson.getString("code");
                        if (status.equals("1") || status.equals("-1")) {
                            JSONArray array = resultJson.getJSONArray("list");
                            for (int h = 0; h < array.length(); h++) {
                                JSONObject object = (JSONObject) array.get(h);
                                images.add(object.has("thumb") ? "http://www.xiongsongai.com" + object.getString("thumb") : "");
                            }
                            setBanner();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable e) {
                e.printStackTrace();
            }
        });


    }

    public void MicroAdapterOnItemClickLiter() {
        mIcroClassAdapter.setOnItemClickLitener(new MicroClassHomeAdapter.OnItemClickLiter() {
            @Override
            public void onItemClick(int id, String status, int type) {
                Collection(id, status, type);
            }
        });
    }

    public void shoppingcartOnItemClickLitener() {
        mIcroClassAdapter.setOnItemClickLitener(new MicroClassHomeAdapter.shoppingcartOnItemClickLiter() {
            @Override
            public void onItemClick(int id, String status) {
                Log.i("TAG","status==="+status);
                Log.i("TAG","id==="+id);
                if (status.equals("true")) {
                    Log.i("TAG","eeeeeeeeeeeeeeeeeeeeee");
                    addCart(id);
                } else if (status.equals("false")) {
                    Log.i("TAG","rrrrrrrrrrrrrrrrrrrrrr");
                    delCart(id);
                }

            }
        });
    }


    //添加购物车接口
    public void addCart(int vid) {
        Map<String, Object> map = new HashMap<>();
        map.put("vid", vid);
        DescendingEncryption.init(map);
        final ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<ResponseBody> call = api.addCart(map, SystemUtils.getHeader(getActivity()));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    String  code = object.getString("code");
                    Log.i("TAG","code===="+code);
                    if (code.equals("1")){
                        initMicroClass(subjectId);
                    }else {
                        Toast.makeText(getActivity(),"该视频已在购物车，无需再次添加！",Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("TAG","id===xxx");

            }
        });
    }
    //删除购物车接口
    public void delCart(int cid) {
        Map<String, Object> map = new HashMap<>();
        map.put("vid", cid);
        DescendingEncryption.init(map);
        final ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<ResponseBody> call = api.delCart(map, SystemUtils.getHeader(getActivity()));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("TAG","id==="+response.code());
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    String  code = object.getString("code");
                    Log.i("TAG","code===="+code);
                    if (code.equals("1")){
                        initMicroClass(subjectId);
                    }else {
                        Toast.makeText(getActivity(),"删除失败或其它错误信息",Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("TAG","id===xxx");
            }
        });
    }


    //    public void ExercisesAdapterOnItemClickLiter() {
    //        mExercisesAdapter.setOnItemClickLitener(new ExercisesHomeAdapter.OnItemClickLiter() {
    //            @Override
    //            public void onItemClick(int id) {
    //
    //            }
    //        });
    //    }


    //    public void ZhenTiAdapterOnItemClickLiter() {
    //        mZhentiAdapter.setOnItemClickLitener(new ZhenTiHomeAdapter.OnItemClickLiter() {
    //            @Override
    //            public void onItemClick(int id, String status, int type) {
    //                Collection(id, status, type);
    //
    //            }
    //        });
    //    }
    //
    //    public void KnowledgeAdapterOnItemClickLiter() {
    //        mKnowledgeAdapter.setOnItemClickLitener(new KnowledgeHomeAdapter.OnItemClickLiter() {
    //            @Override
    //            public void onItemClick(int id, String status, int type) {
    //                Log.i("TAG", id + ""+status+""+type);
    //                Collection(id, status, type);
    //            }
    //        });
    //    }


    public void Collection(int id, String status, final int type) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("otherid", id);
        map.put("status", status);
        DescendingEncryption.init(map);
        final ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<CollectionEntity> call = api.Collection(map, SystemUtils.getHeader(BaseApplication.getContext()));
        call.enqueue(new Callback<CollectionEntity>() {
            @Override
            public void onResponse(Call<CollectionEntity> call, Response<CollectionEntity> response) {
                if (response.body().getCode().equals("1")) {
                    if (type == 1) {//习题
                        initExercises(subjectId);
                    } else if (type == 2) {//真题
                        Log.i("TAG", "subjectId===" + subjectId);
                        initZhenTi(subjectId);
                    } else if (type == 3) {//威客
                        initMicroClass(subjectId);
                    } else if (type == 4) {//知识库
                        initKnowledge(subjectId);
                    }
                } else if (response.body().getCode().equals("0")) {
                    Toast.makeText(BaseApplication.getContext(), "操作失败", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CollectionEntity> call, Throwable t) {
                Toast.makeText(BaseApplication.getContext(), "网络异常", Toast.LENGTH_LONG).show();
            }
        });
    }


}