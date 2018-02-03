package com.example.administrator.zhike;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.adapter.CollectionExerciseAdapter;
import com.example.administrator.adapter.CollectionIntelligenceAdapter;
import com.example.administrator.adapter.CollectionKnowledgeAdapter;
import com.example.administrator.adapter.CollectionMicroClassAdapter;
import com.example.administrator.adapter.CollectionOpenClassAdapter;
import com.example.administrator.adapter.CollectionZhenTiAdapter;
import com.example.administrator.adapter.MyPagerAdapter;
import com.example.administrator.constants.ServiceApi;
import com.example.administrator.entity.MyCollectionExerciseEntity;
import com.example.administrator.entity.MyCollectionIntelligenceEntity;
import com.example.administrator.entity.MyCollectionKnowledgeEntity;
import com.example.administrator.entity.MyCollectionMicroClassEntity;
import com.example.administrator.entity.MyCollectionOpenClassEntity;
import com.example.administrator.entity.MyCollectionZhenTiEntity;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.FullyLinearLayoutManager;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SystemUtils;
import com.example.administrator.zhike.view.LinearLayoutColorDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCollectionActivity extends BaseActivity {

    ImageView mReturn;
    TextView microclass, exercises, zhenti, knowledge, openClass, intelligence;
    ViewPager pager;
    View vMicroclass, vExercises, vZhenti, vKnowledge, vOpenClass, vIntelligence;
    List<View> listViews;
    RecyclerView rMicroclass, rExercises, rZhenti, rKnowledge, rOpenClass, rIntelligence;
    ServiceApi api;
    CollectionMicroClassAdapter microClassAdapter;
    CollectionExerciseAdapter exerciseAdapter;
    CollectionZhenTiAdapter zhenTiAdapter;
    CollectionKnowledgeAdapter knowledgeAdapter;
    CollectionOpenClassAdapter openClassAdapter;
    CollectionIntelligenceAdapter intelligenceAdapter;

    LinearLayout MicrLayout, exercisesLayout, zhentiLayout, knowledgeLayout, openclassLayout, intelligenceLayout;
    @Override
    public void widgetClick(View v) {

        switch (v.getId()) {
            case R.id.my_collection_return:
                finish();

                return;
            case R.id.my_collection_microclass:
                microclass.setBackgroundColor(getResources().getColor(R.color.baiS));
                exercises.setBackgroundColor(getResources().getColor(R.color.myloding));
                zhenti.setBackgroundColor(getResources().getColor(R.color.myloding));
                knowledge.setBackgroundColor(getResources().getColor(R.color.myloding));
                openClass.setBackgroundColor(getResources().getColor(R.color.myloding));
                intelligence.setBackgroundColor(getResources().getColor(R.color.myloding));
                microclass.setClickable(false);
                exercises.setClickable(true);
                zhenti.setClickable(true);
                knowledge.setClickable(true);
                openClass.setClickable(true);
                intelligence.setClickable(true);
                pager.setCurrentItem(0);
                return;
            case R.id.my_collection_exercises:
                microclass.setBackgroundColor(getResources().getColor(R.color.myloding));
                exercises.setBackgroundColor(getResources().getColor(R.color.baiS));
                zhenti.setBackgroundColor(getResources().getColor(R.color.myloding));
                knowledge.setBackgroundColor(getResources().getColor(R.color.myloding));
                openClass.setBackgroundColor(getResources().getColor(R.color.myloding));
                intelligence.setBackgroundColor(getResources().getColor(R.color.myloding));
                microclass.setClickable(true);
                exercises.setClickable(false);
                zhenti.setClickable(true);
                knowledge.setClickable(true);
                openClass.setClickable(true);
                intelligence.setClickable(true);
                pager.setCurrentItem(1);
                return;
            case R.id.my_collection_zhenti:
                microclass.setBackgroundColor(getResources().getColor(R.color.myloding));
                exercises.setBackgroundColor(getResources().getColor(R.color.myloding));
                zhenti.setBackgroundColor(getResources().getColor(R.color.baiS));
                knowledge.setBackgroundColor(getResources().getColor(R.color.myloding));
                openClass.setBackgroundColor(getResources().getColor(R.color.myloding));
                intelligence.setBackgroundColor(getResources().getColor(R.color.myloding));
                microclass.setClickable(true);
                exercises.setClickable(true);
                zhenti.setClickable(false);
                knowledge.setClickable(true);
                openClass.setClickable(true);
                intelligence.setClickable(true);
                pager.setCurrentItem(2);
                return;
            case R.id.my_collection_knowledge:
                microclass.setBackgroundColor(getResources().getColor(R.color.myloding));
                exercises.setBackgroundColor(getResources().getColor(R.color.myloding));
                zhenti.setBackgroundColor(getResources().getColor(R.color.myloding));
                knowledge.setBackgroundColor(getResources().getColor(R.color.baiS));
                openClass.setBackgroundColor(getResources().getColor(R.color.myloding));
                intelligence.setBackgroundColor(getResources().getColor(R.color.myloding));
                microclass.setClickable(true);
                exercises.setClickable(true);
                zhenti.setClickable(true);
                knowledge.setClickable(false);
                openClass.setClickable(true);
                intelligence.setClickable(true);
                pager.setCurrentItem(3);
                return;
            case R.id.my_collection_openClass:
                microclass.setBackgroundColor(getResources().getColor(R.color.myloding));
                exercises.setBackgroundColor(getResources().getColor(R.color.myloding));
                zhenti.setBackgroundColor(getResources().getColor(R.color.myloding));
                knowledge.setBackgroundColor(getResources().getColor(R.color.myloding));
                openClass.setBackgroundColor(getResources().getColor(R.color.baiS));
                intelligence.setBackgroundColor(getResources().getColor(R.color.myloding));
                microclass.setClickable(true);
                exercises.setClickable(true);
                zhenti.setClickable(true);
                knowledge.setClickable(true);
                openClass.setClickable(false);
                intelligence.setClickable(true);
                pager.setCurrentItem(4);
                return;
            case R.id.my_collection_intelligence:
                microclass.setBackgroundColor(getResources().getColor(R.color.myloding));
                exercises.setBackgroundColor(getResources().getColor(R.color.myloding));
                zhenti.setBackgroundColor(getResources().getColor(R.color.myloding));
                knowledge.setBackgroundColor(getResources().getColor(R.color.myloding));
                openClass.setBackgroundColor(getResources().getColor(R.color.myloding));
                intelligence.setBackgroundColor(getResources().getColor(R.color.baiS));
                microclass.setClickable(true);
                exercises.setClickable(true);
                zhenti.setClickable(true);
                knowledge.setClickable(true);
                openClass.setClickable(true);
                intelligence.setClickable(false);
                pager.setCurrentItem(5);
                return;
            case R.id.pager_my_collection_text_microclass:
                finish();
                return;
            case R.id.pager_my_collection_text_exercises:
                finish();
                return;
            case R.id.pager_my_collection_text_zhenti:
                finish();
                return;
            case R.id.pager_my_collection_text_knowledge:
                finish();
                return;
            case R.id.pager_my_collection_text_openclass:
                finish();
                return;
            case R.id.pager_my_collection_text_intelligence:
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
        return R.layout.activity_my_collection;
    }

    @Override
    public void initView(View view) {
        mReturn = $(R.id.my_collection_return);
        microclass = $(R.id.my_collection_microclass);
        exercises = $(R.id.my_collection_exercises);
        zhenti = $(R.id.my_collection_zhenti);
        knowledge = $(R.id.my_collection_knowledge);
        openClass = $(R.id.my_collection_openClass);
        intelligence = $(R.id.my_collection_intelligence);
        pager = $(R.id.my_collection_pager);
    }

    @Override
    public void setListener() {
        mReturn.setOnClickListener(this);
        microclass.setOnClickListener(this);
        exercises.setOnClickListener(this);
        zhenti.setOnClickListener(this);
        knowledge.setOnClickListener(this);
        openClass.setOnClickListener(this);
        intelligence.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        api = RxjavaRetrofitUtils.getApi();
        index();
    }


    private void index() {
        listViews = new ArrayList<>();
        LayoutInflater mInflater = getLayoutInflater();

        vMicroclass = mInflater.inflate(R.layout.pager_my_collection_microclass, null);//微课
        rMicroclass = (RecyclerView) vMicroclass.findViewById(R.id.pager_my_collection_microclass_recycler);
        rMicroclass.setNestedScrollingEnabled(false);
        MicrLayout = (LinearLayout) vMicroclass.findViewById(R.id.pager_my_collection_microclass_layout);
        TextView MicrText = (TextView) vMicroclass.findViewById(R.id.pager_my_collection_text_microclass);
        MicrText.setOnClickListener(this);
        listViews.add(vMicroclass);

        vExercises = mInflater.inflate(R.layout.pager_my_collection_exercises, null);//习题
        rExercises = (RecyclerView) vExercises.findViewById(R.id.pager_my_collection_exercises);
        rExercises.setNestedScrollingEnabled(false);
        exercisesLayout = (LinearLayout) vExercises.findViewById(R.id.pager_my_collection_exercises_layout);
        TextView exercisesText = (TextView) vExercises.findViewById(R.id.pager_my_collection_text_exercises);
        exercisesText.setOnClickListener(this);
        listViews.add(vExercises);

        vZhenti = mInflater.inflate(R.layout.pager_my_collection_zhenti, null);//真题
        rZhenti = (RecyclerView) vZhenti.findViewById(R.id.pager_my_collection_zhenti);
        rZhenti.setNestedScrollingEnabled(false);
        zhentiLayout = (LinearLayout) vZhenti.findViewById(R.id.pager_my_collection_zhenti_layout);
        TextView zhentiText = (TextView) vZhenti.findViewById(R.id.pager_my_collection_text_zhenti);
        zhentiText.setOnClickListener(this);
        listViews.add(vZhenti);

        vKnowledge = mInflater.inflate(R.layout.pager_my_collection_knowledge, null);//知识库
        rKnowledge = (RecyclerView) vKnowledge.findViewById(R.id.pager_my_collection_knowledge);
        rKnowledge.setNestedScrollingEnabled(false);
        knowledgeLayout = (LinearLayout) vKnowledge.findViewById(R.id.pager_my_collection_knowledge_layout);
        TextView knowledgeText = (TextView) vKnowledge.findViewById(R.id.pager_my_collection_text_knowledge);
        knowledgeText.setOnClickListener(this);
        listViews.add(vKnowledge);

        vOpenClass = mInflater.inflate(R.layout.pager_my_collection_openclass, null);//公开课
        rOpenClass = (RecyclerView) vOpenClass.findViewById(R.id.pager_my_collection_openclass);
        rOpenClass.setNestedScrollingEnabled(false);
        openclassLayout = (LinearLayout) vOpenClass.findViewById(R.id.pager_my_collection_openclass_layout);
        TextView openclassText = (TextView) vOpenClass.findViewById(R.id.pager_my_collection_text_openclass);
        openclassText.setOnClickListener(this);
        listViews.add(vOpenClass);

        vIntelligence = mInflater.inflate(R.layout.pager_my_collection_intelligence, null);//资讯
        rIntelligence = (RecyclerView) vIntelligence.findViewById(R.id.pager_my_collection_intelligence);
        rIntelligence.setNestedScrollingEnabled(false);
        intelligenceLayout = (LinearLayout) vIntelligence.findViewById(R.id.pager_my_collection_intelligence_layout);
        TextView intelligenceText = (TextView) vIntelligence.findViewById(R.id.pager_my_collection_text_intelligence);
        intelligenceText.setOnClickListener(this);
        listViews.add(vIntelligence);


        MyPagerAdapter adapte = new MyPagerAdapter(listViews);
        pager.setAdapter(adapte);
        pager.setCurrentItem(0);
        pager.setOnPageChangeListener(new MyOnPageChangeListener());

        indexData();

    }


    //页卡切换监听
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    microclass.setBackgroundColor(getResources().getColor(R.color.baiS));
                    exercises.setBackgroundColor(getResources().getColor(R.color.myloding));
                    zhenti.setBackgroundColor(getResources().getColor(R.color.myloding));
                    knowledge.setBackgroundColor(getResources().getColor(R.color.myloding));
                    openClass.setBackgroundColor(getResources().getColor(R.color.myloding));
                    intelligence.setBackgroundColor(getResources().getColor(R.color.myloding));
                    microclass.setClickable(false);
                    exercises.setClickable(true);
                    zhenti.setClickable(true);
                    knowledge.setClickable(true);
                    openClass.setClickable(true);
                    intelligence.setClickable(true);
                    break;
                case 1:
                    microclass.setBackgroundColor(getResources().getColor(R.color.myloding));
                    exercises.setBackgroundColor(getResources().getColor(R.color.baiS));
                    zhenti.setBackgroundColor(getResources().getColor(R.color.myloding));
                    knowledge.setBackgroundColor(getResources().getColor(R.color.myloding));
                    openClass.setBackgroundColor(getResources().getColor(R.color.myloding));
                    intelligence.setBackgroundColor(getResources().getColor(R.color.myloding));
                    microclass.setClickable(true);
                    exercises.setClickable(false);
                    zhenti.setClickable(true);
                    knowledge.setClickable(true);
                    openClass.setClickable(true);
                    intelligence.setClickable(true);
                    break;
                case 2:
                    microclass.setBackgroundColor(getResources().getColor(R.color.myloding));
                    exercises.setBackgroundColor(getResources().getColor(R.color.myloding));
                    zhenti.setBackgroundColor(getResources().getColor(R.color.baiS));
                    knowledge.setBackgroundColor(getResources().getColor(R.color.myloding));
                    openClass.setBackgroundColor(getResources().getColor(R.color.myloding));
                    intelligence.setBackgroundColor(getResources().getColor(R.color.myloding));
                    microclass.setClickable(true);
                    exercises.setClickable(true);
                    zhenti.setClickable(false);
                    knowledge.setClickable(true);
                    openClass.setClickable(true);
                    intelligence.setClickable(true);
                    break;
                case 3:
                    microclass.setBackgroundColor(getResources().getColor(R.color.myloding));
                    exercises.setBackgroundColor(getResources().getColor(R.color.myloding));
                    zhenti.setBackgroundColor(getResources().getColor(R.color.myloding));
                    knowledge.setBackgroundColor(getResources().getColor(R.color.baiS));
                    openClass.setBackgroundColor(getResources().getColor(R.color.myloding));
                    intelligence.setBackgroundColor(getResources().getColor(R.color.myloding));
                    microclass.setClickable(true);
                    exercises.setClickable(true);
                    zhenti.setClickable(true);
                    knowledge.setClickable(false);
                    openClass.setClickable(true);
                    intelligence.setClickable(true);
                    break;
                case 4:
                    microclass.setBackgroundColor(getResources().getColor(R.color.myloding));
                    exercises.setBackgroundColor(getResources().getColor(R.color.myloding));
                    zhenti.setBackgroundColor(getResources().getColor(R.color.myloding));
                    knowledge.setBackgroundColor(getResources().getColor(R.color.myloding));
                    openClass.setBackgroundColor(getResources().getColor(R.color.baiS));
                    intelligence.setBackgroundColor(getResources().getColor(R.color.myloding));
                    microclass.setClickable(true);
                    exercises.setClickable(true);
                    zhenti.setClickable(true);
                    knowledge.setClickable(true);
                    openClass.setClickable(false);
                    intelligence.setClickable(true);
                    break;
                case 5:
                    microclass.setBackgroundColor(getResources().getColor(R.color.myloding));
                    exercises.setBackgroundColor(getResources().getColor(R.color.myloding));
                    zhenti.setBackgroundColor(getResources().getColor(R.color.myloding));
                    knowledge.setBackgroundColor(getResources().getColor(R.color.myloding));
                    openClass.setBackgroundColor(getResources().getColor(R.color.myloding));
                    intelligence.setBackgroundColor(getResources().getColor(R.color.baiS));
                    microclass.setClickable(true);
                    exercises.setClickable(true);
                    zhenti.setClickable(true);
                    knowledge.setClickable(true);
                    openClass.setClickable(true);
                    intelligence.setClickable(false);
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


    public void indexData() {
        myCollectionMicroClass(3, 1, 15);//微课
        myCollectionExercise(1, 1, 15);//习题
        myCollectionZhenTi(7, 1, 15);//真题
        myCollectionKnowledge(4, 1, 15);//知识库
        myCollectionOpenClass(6, 1, 15);//公开课
        myCollectionIntelligence(5, 1, 15);//新闻

    }

    //微课
    public void myCollectionMicroClass(int type, int page, int pageNum) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("page", page);
        map.put("pageNum", pageNum);
        DescendingEncryption.init(map);
        Call<MyCollectionMicroClassEntity> call = api.myCollectionMicroClass(map, SystemUtils.getHeader(MyCollectionActivity.this));
        call.enqueue(new Callback<MyCollectionMicroClassEntity>() {
            @Override
            public void onResponse(Call<MyCollectionMicroClassEntity> call, Response<MyCollectionMicroClassEntity> response) {
                ArrayList<MyCollectionMicroClassEntity.list> data = response.body().getList();
                if (data.size() <= 0) {
                    MicrLayout.setVisibility(View.VISIBLE);
                    return;
                }
                if (microClassAdapter == null) {
                    microClassAdapter = new CollectionMicroClassAdapter(data);
                    FullyLinearLayoutManager manager = new FullyLinearLayoutManager(MyCollectionActivity.this, LinearLayoutManager.VERTICAL, false);
                    rMicroclass.setLayoutManager(manager);
                    rMicroclass.addItemDecoration(new LinearLayoutColorDecoration(LinearLayoutManager.VERTICAL, getResources().getDimensionPixelSize(R.dimen.x15), getResources().getColor(R.color.bj), getResources().getDimensionPixelSize(R.dimen.x15)));//设置颜色  和 分割线
                    rMicroclass.setAdapter(microClassAdapter);
                } else {
                    microClassAdapter.setNewData(data);
                }
                MicroClassmOnItemClickLitener();
                MicroClassmPlayerOnItemClickLitener();
            }

            @Override
            public void onFailure(Call<MyCollectionMicroClassEntity> call, Throwable t) {
            }
        });
    }

    //习题
    public void myCollectionExercise(int type, int page, int pageNum) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("page", page);
        map.put("pageNum", pageNum);
        DescendingEncryption.init(map);
        Call<MyCollectionExerciseEntity> call = api.myCollectionExercise(map, SystemUtils.getHeader(MyCollectionActivity.this));
        call.enqueue(new Callback<MyCollectionExerciseEntity>() {
            @Override
            public void onResponse(Call<MyCollectionExerciseEntity> call, Response<MyCollectionExerciseEntity> response) {
                ArrayList<MyCollectionExerciseEntity.list> data = response.body().getList();
                Log.i("TAG", data.size() + "xxxx");
                if (data.size() <= 0) {
                    exercisesLayout.setVisibility(View.VISIBLE);
                    return;
                }

                if (exerciseAdapter == null) {
                    exerciseAdapter = new CollectionExerciseAdapter(data);
                    FullyLinearLayoutManager manager = new FullyLinearLayoutManager(MyCollectionActivity.this, LinearLayoutManager.VERTICAL, false);
                    rExercises.setLayoutManager(manager);
                    rExercises.addItemDecoration(new LinearLayoutColorDecoration(LinearLayoutManager.VERTICAL, getResources().getDimensionPixelSize(R.dimen.x15), getResources().getColor(R.color.bj), getResources().getDimensionPixelSize(R.dimen.x15)));//设置颜色  和 分割线
                    rExercises.setAdapter(exerciseAdapter);
                } else {
                    exerciseAdapter.setNewData(data);
                }
            }

            @Override
            public void onFailure(Call<MyCollectionExerciseEntity> call, Throwable t) {
            }
        });
    }

    //真题
    public void myCollectionZhenTi(int type, int page, int pageNum) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("page", page);
        map.put("pageNum", pageNum);
        DescendingEncryption.init(map);
        Call<MyCollectionZhenTiEntity> call = api.myCollectionZhenTi(map, SystemUtils.getHeader(MyCollectionActivity.this));
        call.enqueue(new Callback<MyCollectionZhenTiEntity>() {
            @Override
            public void onResponse(Call<MyCollectionZhenTiEntity> call, Response<MyCollectionZhenTiEntity> response) {
                ArrayList<MyCollectionZhenTiEntity.list> data = response.body().getList();
                Log.i("TAG", data.size() + "xxxx");
                if (data.size() <= 0) {
                    zhentiLayout.setVisibility(View.VISIBLE);
                    return;
                }
                if (zhenTiAdapter == null) {
                    zhenTiAdapter = new CollectionZhenTiAdapter(data);
                    FullyLinearLayoutManager manager = new FullyLinearLayoutManager(MyCollectionActivity.this, LinearLayoutManager.VERTICAL, false);
                    rZhenti.setLayoutManager(manager);
                    rZhenti.addItemDecoration(new LinearLayoutColorDecoration(LinearLayoutManager.VERTICAL, getResources().getDimensionPixelSize(R.dimen.x15), getResources().getColor(R.color.bj), getResources().getDimensionPixelSize(R.dimen.x15)));//设置颜色  和 分割线
                    rZhenti.setAdapter(zhenTiAdapter);
                } else {
                    zhenTiAdapter.setNewData(data);
                }
            }

            @Override
            public void onFailure(Call<MyCollectionZhenTiEntity> call, Throwable t) {
            }
        });
    }

    //知识库
    public void myCollectionKnowledge(int type, int page, int pageNum) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("page", page);
        map.put("pageNum", pageNum);
        DescendingEncryption.init(map);
        Call<MyCollectionKnowledgeEntity> call = api.myCollectionKnowledge(map, SystemUtils.getHeader(MyCollectionActivity.this));
        call.enqueue(new Callback<MyCollectionKnowledgeEntity>() {
            @Override
            public void onResponse(Call<MyCollectionKnowledgeEntity> call, Response<MyCollectionKnowledgeEntity> response) {
                ArrayList<MyCollectionKnowledgeEntity.list> data = response.body().getList();
                Log.i("TAG", data.size() + "xxxx");
                if (data.size() <= 0) {
                    knowledgeLayout.setVisibility(View.VISIBLE);
                    return;
                }
                if (knowledgeAdapter == null) {
                    knowledgeAdapter = new CollectionKnowledgeAdapter(data);
                    FullyLinearLayoutManager manager = new FullyLinearLayoutManager(MyCollectionActivity.this, LinearLayoutManager.VERTICAL, false);
                    rKnowledge.setLayoutManager(manager);
                    rKnowledge.addItemDecoration(new LinearLayoutColorDecoration(LinearLayoutManager.VERTICAL, getResources().getDimensionPixelSize(R.dimen.x15), getResources().getColor(R.color.bj), getResources().getDimensionPixelSize(R.dimen.x15)));//设置颜色  和 分割线
                    rKnowledge.setAdapter(knowledgeAdapter);
                } else {
                    knowledgeAdapter.setNewData(data);
                }
            }

            @Override
            public void onFailure(Call<MyCollectionKnowledgeEntity> call, Throwable t) {
            }
        });
    }

    //公开课
    public void myCollectionOpenClass(int type, int page, int pageNum) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("page", page);
        map.put("pageNum", pageNum);
        DescendingEncryption.init(map);
        Call<MyCollectionOpenClassEntity> call = api.myCollectionOpenClass(map, SystemUtils.getHeader(MyCollectionActivity.this));
        call.enqueue(new Callback<MyCollectionOpenClassEntity>() {
            @Override
            public void onResponse(Call<MyCollectionOpenClassEntity> call, Response<MyCollectionOpenClassEntity> response) {
                ArrayList<MyCollectionOpenClassEntity.list> data = response.body().getList();
                Log.i("TAG", data.size() + "xxxx");
                if (data.size() <= 0) {
                    openclassLayout.setVisibility(View.VISIBLE);
                    return;
                }
                if (openClassAdapter == null) {
                    openClassAdapter = new CollectionOpenClassAdapter(data);
                    FullyLinearLayoutManager manager = new FullyLinearLayoutManager(MyCollectionActivity.this, LinearLayoutManager.VERTICAL, false);
                    rOpenClass.setLayoutManager(manager);
                    rOpenClass.addItemDecoration(new LinearLayoutColorDecoration(LinearLayoutManager.VERTICAL, getResources().getDimensionPixelSize(R.dimen.x15), getResources().getColor(R.color.bj), getResources().getDimensionPixelSize(R.dimen.x15)));//设置颜色  和 分割线
                    rOpenClass.setAdapter(openClassAdapter);
                } else {
                    openClassAdapter.setNewData(data);
                }
                OpenClassmOnItemClickLitener();
                OpenClassmPlayerOnItemClickLitener();
            }

            @Override
            public void onFailure(Call<MyCollectionOpenClassEntity> call, Throwable t) {
            }
        });
    }

    //资讯
    public void myCollectionIntelligence(int type, int page, int pageNum) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("page", page);
        map.put("pageNum", pageNum);
        DescendingEncryption.init(map);
        Call<MyCollectionIntelligenceEntity> call = api.myCollectionIntelligence(map, SystemUtils.getHeader(MyCollectionActivity.this));
        call.enqueue(new Callback<MyCollectionIntelligenceEntity>() {
            @Override
            public void onResponse(Call<MyCollectionIntelligenceEntity> call, Response<MyCollectionIntelligenceEntity> response) {
                ArrayList<MyCollectionIntelligenceEntity.list> data = response.body().getList();
                if (data.size() <= 0) {
                    intelligenceLayout.setVisibility(View.VISIBLE);
                    return;
                }
                Log.i("TAG", data.size() + "xxxx");
                if (intelligenceAdapter == null) {
                    intelligenceAdapter = new CollectionIntelligenceAdapter(data);
                    FullyLinearLayoutManager manager = new FullyLinearLayoutManager(MyCollectionActivity.this, LinearLayoutManager.VERTICAL, false);
                    rIntelligence.setLayoutManager(manager);
                    rIntelligence.addItemDecoration(new LinearLayoutColorDecoration(LinearLayoutManager.VERTICAL, getResources().getDimensionPixelSize(R.dimen.x15), getResources().getColor(R.color.bj), getResources().getDimensionPixelSize(R.dimen.x15)));//设置颜色  和 分割线
                    rIntelligence.setAdapter(intelligenceAdapter);
                } else {
                    intelligenceAdapter.setNewData(data);
                }
            }

            @Override
            public void onFailure(Call<MyCollectionIntelligenceEntity> call, Throwable t) {
            }
        });
    }


    public void MicroClassmOnItemClickLitener() {
        microClassAdapter.setOnItemClickLitener(new CollectionMicroClassAdapter.OnItemClickLiter() {
            @Override
            public void onItemClick(int position) {
                microClassAdapter.setPosition(position);
                microClassAdapter.notifyDataSetChanged();
            }
        });
    }

    public void MicroClassmPlayerOnItemClickLitener() {
        microClassAdapter.setOnItemClickLitener(new CollectionMicroClassAdapter.PlayerOnItemClickLiter() {
            @Override
            public void onItemClick(JCVideoPlayerStandard playerStandard) {
                playerStandard.startButton.performClick(); //模拟用户点击开始按钮，NORMAL状态下点击开始播放视频，播放中点击暂停视频
            }
        });
    }

    public void OpenClassmOnItemClickLitener() {
        openClassAdapter.setOnItemClickLitener(new CollectionOpenClassAdapter.OnItemClickLiter() {
            @Override
            public void onItemClick(int position) {
                openClassAdapter.setPosition(position);
                openClassAdapter.notifyDataSetChanged();
            }
        });
    }

    public void OpenClassmPlayerOnItemClickLitener() {
        openClassAdapter.setOnItemClickLitener(new CollectionOpenClassAdapter.PlayerOnItemClickLiter() {
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
