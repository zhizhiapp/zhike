package com.example.administrator.zhike;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.administrator.adapter.IntelligentAnalysisAdapter;
import com.example.administrator.adapter.ListSubjectAdapter;
import com.example.administrator.adapter.MyPagerAdapter;
import com.example.administrator.adapter.WisdomSchemeGridLoveAdapter;
import com.example.administrator.adapter.WisdomSchemeLinearAdapter;
import com.example.administrator.constants.ServiceApi;
import com.example.administrator.entity.CollectionEntity;
import com.example.administrator.entity.CorrectRateEntity;
import com.example.administrator.entity.SoluListEntity;
import com.example.administrator.entity.SoluVideoEntity;
import com.example.administrator.entity.SolutionEntity;
import com.example.administrator.entity.StudyAnalysisEntity;
import com.example.administrator.entity.SubjectEntity;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.FullyLinearLayoutManager;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SystemUtils;
import com.example.administrator.zhike.view.CircleImageView;
import com.example.administrator.zhike.view.GridSpacingItemDecoration;
import com.example.administrator.zhike.view.LinearLayoutColorDecoration;
import com.example.administrator.zhike.view.LinearLayoutItemDecoration;
import com.example.administrator.zhike.view.MicroClassPow;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.BaseApplication;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.administrator.constants.MyList.subjectId;

public class SchemeAnalysisActivity extends BaseActivity {


    ImageView eSaReturn;
    LinearLayout eSaChoice;
    TextView eSaChoiceText;
    TextView eSaChoiceTitle;
    ImageView eSaChoiceImg;
    Button inteLligent;
    Button wisdomScheme;
    String title;

    MicroClassPow pow;
    ViewPager pager;
    List<View> listViews;
    View view1, view2;

    TextView wTitle;
    TextView wContont;

    int startnum = 1;
    int page = 1;
    int subjectid = 0;

    WisdomSchemeLinearAdapter wAdapter;
    WisdomSchemeGridLoveAdapter gAdapter;
    RecyclerView wRecyRecom;
    RecyclerView wRecyLove;

    TextView maximum;
    RecyclerView recyclerView;
    SharedPreferences sp;


    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.scheme_analysis_return://返回
                finish();
                return;
            case R.id.scheme_analysis_choice://课程选择
                initChoiceSubjects();

                return;
            case R.id.wisdom_scheme_butt_recommended_courses://推荐课程  换一组

                startnum++;//2
                if (startnum > 5) {
                    startnum = 1;
                }
                int num = startnum * 5 - 5;
                Log.i("TAG", "换一组num===" + num);
                getSoluList(subjectid, num);

                return;
            case R.id.wisdom_scheme_butt_love://推荐视频  换一组
                page++;
                if (page > 5) {
                    page = 1;
                }
                getSoluVideo(subjectId, page);

                return;
            case R.id.scheme_analysis_analysis://智能分析
                inteLligent.setBackgroundColor(getResources().getColor(R.color.baiS));
                wisdomScheme.setBackgroundColor(getResources().getColor(R.color.zhS));
                eSaChoiceTitle.setText("智能分析");
                inteLligent.setClickable(false);
                wisdomScheme.setClickable(true);
                pager.setCurrentItem(0);
                return;
            case R.id.scheme_analysis_programme://智慧方案
                inteLligent.setBackgroundColor(getResources().getColor(R.color.zhS));
                wisdomScheme.setBackgroundColor(getResources().getColor(R.color.baiS));
                eSaChoiceTitle.setText("智慧方案");
                inteLligent.setClickable(true);
                wisdomScheme.setClickable(false);
                pager.setCurrentItem(1);
                return;
        }
    }

    @Override
    public void initParms(Bundle parms) {
        String ExaminationAnalysis = parms.getString("HomePageActivity");
        if (ExaminationAnalysis.equals("Intelligent")) {
            title = parms.getString("title");
        } else if (ExaminationAnalysis.equals("WisdomScheme")) {
            title = parms.getString("title");
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_scheme_analysis;
    }


    @Override
    public void initView(View view) {
        eSaReturn = $(R.id.scheme_analysis_return);
        eSaChoice = $(R.id.scheme_analysis_choice);
        eSaChoiceText = $(R.id.scheme_analysis_text);
        eSaChoiceImg = $(R.id.scheme_analysis_img);
        inteLligent = $(R.id.scheme_analysis_analysis);
        wisdomScheme = $(R.id.scheme_analysis_programme);
        eSaChoiceTitle = $(R.id.scheme_analysis_title);
        pager = $(R.id.scheme_analysis_pager);
    }

    @Override
    public void setListener() {
        eSaReturn.setOnClickListener(this);
        eSaChoice.setOnClickListener(this);
        inteLligent.setOnClickListener(this);
        wisdomScheme.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        eSaChoiceTitle.setText(title);
        sp = getSharedPreferences("xs", MODE_PRIVATE);
        index();
    }


    private void index() {
        listViews = new ArrayList<>();
        LayoutInflater mInflater = getLayoutInflater();

        view1 = mInflater.inflate(R.layout.activity_intelligent_analysis, null);//智能分析
        ScrollView scrollView = (ScrollView) view1.findViewById(R.id.intelligent_analysis_scrollview);
        scrollView.setVerticalScrollBarEnabled(false);

        CircleImageView mHeader = (CircleImageView) view1.findViewById(R.id.intelligent_analysis_scrollview_header);
        Glide.with(this).load(sp.getString("headimgurl", "")).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(mHeader);


        maximum = (TextView) view1.findViewById(R.id.intelligent_analysis_scrollview_maximum);
        TextView name = (TextView) view1.findViewById(R.id.intelligent_analysis_scrollview_name);
        name.setText(sp.getString("nickname", ""));
        recyclerView = (RecyclerView) view1.findViewById(R.id.intelligent_analysis_recycler);


        chart = (LineChartView) view1.findViewById(R.id.lcv);
        chart.setViewportCalculationEnabled(false);
        resetViewport();

        master = (LineChartView) view1.findViewById(R.id.master);
        master.setViewportCalculationEnabled(false);
        resetViewportmaster();


        listViews.add(view1);


        //////////////////////////////////////////////////////////////////////////
        view2 = mInflater.inflate(R.layout.activity_wisdom_scheme, null);//方案
        wTitle = (TextView) view2.findViewById(R.id.wisdom_scheme_title);
        wContont = (TextView) view2.findViewById(R.id.wisdom_scheme_contont);
        Button wRecommend = (Button) view2.findViewById(R.id.wisdom_scheme_butt_recommended_courses);
        Button wLove = (Button) view2.findViewById(R.id.wisdom_scheme_butt_love);
        wRecyRecom = (RecyclerView) view2.findViewById(R.id.wisdom_scheme_recycler_recommended_courses);
        wRecyRecom.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动

        wRecyLove = (RecyclerView) view2.findViewById(R.id.wisdom_scheme_recycler_love);
        wRecyLove.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动

        ScrollView scroll = (ScrollView) view2.findViewById(R.id.wisdom_scheme_scroll);
        scroll.setVerticalScrollBarEnabled(false);

        wRecommend.setOnClickListener(this);
        wLove.setOnClickListener(this);

        int num = startnum * 5 - 5;
        indexData(subjectId, num, page);
        listViews.add(view2);

        MyPagerAdapter adapte = new MyPagerAdapter(listViews);
        pager.setAdapter(adapte);
        if (title.equals("智能分析")) {
            pager.setCurrentItem(0);
            inteLligent.setBackgroundColor(getResources().getColor(R.color.baiS));
            wisdomScheme.setBackgroundColor(getResources().getColor(R.color.zhS));
        } else {
            pager.setCurrentItem(1);
            inteLligent.setBackgroundColor(getResources().getColor(R.color.zhS));
            wisdomScheme.setBackgroundColor(getResources().getColor(R.color.baiS));
        }

        pager.setOnPageChangeListener(new MyOnPageChangeListener());


    }


    //页卡切换监听
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    inteLligent.setBackgroundColor(getResources().getColor(R.color.baiS));
                    wisdomScheme.setBackgroundColor(getResources().getColor(R.color.zhS));
                    eSaChoiceTitle.setText("智能分析");
                    inteLligent.setClickable(false);
                    wisdomScheme.setClickable(true);
                    break;
                case 1:
                    inteLligent.setBackgroundColor(getResources().getColor(R.color.zhS));
                    wisdomScheme.setBackgroundColor(getResources().getColor(R.color.baiS));
                    eSaChoiceTitle.setText("智慧方案");
                    inteLligent.setClickable(true);
                    wisdomScheme.setClickable(false);

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

    public void getSolution(int subjectid) {
        Map<String, Object> map = new HashMap<>();
        map.put("subjectid", subjectid);
        DescendingEncryption.init(map);
        ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<SolutionEntity> call = api.getSolution(map, SystemUtils.getHeader(SchemeAnalysisActivity.this));
        call.enqueue(new Callback<SolutionEntity>() {
            @Override
            public void onResponse(Call<SolutionEntity> call, Response<SolutionEntity> response) {
                wTitle.setText("您当前的学科掌握率为" + response.body().getList().getMastery() + "%" + "，跟上次相比" + response.body().getList().getTrendcont());
                wContont.setText(response.body().getList().getVerse() + "");
            }

            @Override
            public void onFailure(Call<SolutionEntity> call, Throwable t) {
                showToast("网络异常");
            }
        });

    }

    public void getSoluList(int subjectid, int startnum) {
        Map<String, Object> map = new HashMap<>();
        map.put("subjectid", subjectid);
        map.put("startnum", startnum);
        DescendingEncryption.init(map);
        ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<SoluListEntity> call = api.getSoluList(map, SystemUtils.getHeader(SchemeAnalysisActivity.this));
        call.enqueue(new Callback<SoluListEntity>() {
            @Override
            public void onResponse(Call<SoluListEntity> call, Response<SoluListEntity> response) {
                ArrayList<SoluListEntity.list> data = response.body().getList();
                if (wAdapter == null) {
                    wAdapter = new WisdomSchemeLinearAdapter(data);
                    FullyLinearLayoutManager manager = new FullyLinearLayoutManager(SchemeAnalysisActivity.this, LinearLayoutManager.VERTICAL, false);
                    wRecyRecom.setLayoutManager(manager);
                    wRecyRecom.addItemDecoration(new LinearLayoutColorDecoration(LinearLayoutManager.VERTICAL, getResources().getDimensionPixelSize(R.dimen.x15), getResources().getColor(R.color.baiS), getResources().getDimensionPixelSize(R.dimen.x15)));//设置颜色  和 分割线
                    wRecyRecom.setItemAnimator(new DefaultItemAnimator());
                    wRecyRecom.setAdapter(wAdapter);
                } else {
                    wAdapter.setNewData(data);
                }
            }

            @Override
            public void onFailure(Call<SoluListEntity> call, Throwable t) {
            }
        });

    }

    //获取科目选择数据
    private void initChoiceSubjects() {
        BaseApplication.getChoiceSubjects(new BaseApplication.SubjectListener() {
            @Override
            public void onData(final SubjectEntity entity) {
                pow = new MicroClassPow(SchemeAnalysisActivity.this, eSaChoiceImg);// 实例化SelectPicPopupWindow
                View menuWindowView = pow.getContentView();
                ListView listView = (ListView) menuWindowView.findViewById(R.id.home_list);
                ListSubjectAdapter adapter = new ListSubjectAdapter(SchemeAnalysisActivity.this, entity.getList());
                listView.setAdapter(adapter);
                pow.showAsDropDown(findViewById(R.id.scheme_analysis_text), -getResources().getDimensionPixelSize(R.dimen.x10), 0);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        SubjectEntity.listEntity entity1 = entity.getList().get(position);
                        Log.i("TAG", entity1.getName());
                        startnum = 1;
                        page = 1;
                        int num = startnum * 5 - 5;
                        Log.i("TAG", "选择科目num===" + num);
                        indexData(entity1.getId(), num, page);
                    }
                });
            }
        });
    }

    public void indexData(int subjectid, int startnum, int page) {
        this.subjectid = subjectid;
        LearningSituation(subjectid);
        getSolution(subjectid);
        getSoluList(subjectid, startnum);
        getSoluVideo(subjectid, page);
    }


    //视频
    public void getSoluVideo(int id, int page) {
        Log.i("TAG", "page==========" + page);
        Map<String, Object> map = new HashMap<>();
        map.put("subjectid", id);
        map.put("page", page);
        map.put("perNum", 4);
        DescendingEncryption.init(map);
        final ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<SoluVideoEntity> call = api.getSoluVideo(map, SystemUtils.getHeader(SchemeAnalysisActivity.this));
        call.enqueue(new Callback<SoluVideoEntity>() {
            @Override
            public void onResponse(Call<SoluVideoEntity> call, Response<SoluVideoEntity> response) {
                ArrayList<SoluVideoEntity.list> data = response.body().getList();
                if (gAdapter == null) {
                    gAdapter = new WisdomSchemeGridLoveAdapter(data);
                    wRecyLove.setLayoutManager(new GridLayoutManager(SchemeAnalysisActivity.this, 2));
                    wRecyLove.addItemDecoration(new GridSpacingItemDecoration(2,getResources().getDimensionPixelSize(R.dimen.x20),false));//设置颜色  和 分割线
                    wRecyLove.setNestedScrollingEnabled(false);
                    wRecyLove.setItemAnimator(new DefaultItemAnimator());
                    wRecyLove.setAdapter(gAdapter);
                } else {
                    gAdapter.setNewData(data);
                }
                MicroAdapterOnItemClickLiter();
                shoppingcartOnItemClickLitener();
            }

            @Override
            public void onFailure(Call<SoluVideoEntity> call, Throwable t) {
            }
        });
    }


    //智能分析
    public void LearningSituation(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("subjectid", id);
        DescendingEncryption.init(map);
        Log.i("TAG", "===" + map);
        final ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<StudyAnalysisEntity> call = api.studyAnalysis(map, SystemUtils.getHeader(SchemeAnalysisActivity.this));
        call.enqueue(new Callback<StudyAnalysisEntity>() {
            @Override
            public void onResponse(Call<StudyAnalysisEntity> call, Response<StudyAnalysisEntity> response) {
                Log.i("TAG", "===" + response.body().getMsg());
                Log.i("TAG", "===" + response.body().getCode());
                String maximumStr = "";
                for (int i = 0; i < response.body().getList().getRecomm().getMaximum().size(); i++) {
                    maximumStr += response.body().getList().getRecomm().getMaximum().get(i).getChapter_name() + "、";
                }
                String minimumStr = "";
                for (int i = 0; i < response.body().getList().getRecomm().getMinimum().size(); i++) {
                    minimumStr += response.body().getList().getRecomm().getMinimum().get(i).getChapter_name() + "、";
                }

                String contont = "小雄老师发现你在 " + maximumStr + " 掌握的不错哦，希望你再接再厉！但是" + minimumStr + "还存在不足，错误率较高，需要引起重视好好复习错题哦！小雄老师以为你量身打造专属学习方案，可以点击右上角“智慧方案”查看哦！";
                SpannableStringBuilder spannable = new SpannableStringBuilder(contont);
                spannable.setSpan(new ForegroundColorSpan(Color.BLACK), 8, 9 + maximumStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new ForegroundColorSpan(Color.BLACK), 9 + maximumStr.length() + 18, 9 + maximumStr.length() + 18 + minimumStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                maximum.setText(spannable);


                //章节正确率
                String name[] = response.body().getList().getChapter_accuracys().getChapter_name();
                int accuracy[] = response.body().getList().getChapter_accuracys().getChapter_accuracy();
                FullyLinearLayoutManager manager = new FullyLinearLayoutManager(SchemeAnalysisActivity.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(manager);
                recyclerView.addItemDecoration(new LinearLayoutItemDecoration(LinearLayoutManager.VERTICAL, getResources().getDimensionPixelSize(R.dimen.x10)));//设置颜色  和 分割线

                List<CorrectRateEntity> arrayList = new ArrayList<>();
                for (int i = 0; i < accuracy.length; i++) {
                    CorrectRateEntity rateEntity = new CorrectRateEntity();
                    rateEntity.setName(name[i]);
                    rateEntity.setNum(accuracy[i]);
                    arrayList.add(rateEntity);
                }

                IntelligentAnalysisAdapter adapter = new IntelligentAnalysisAdapter(arrayList);
                recyclerView.setAdapter(adapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());


                float LastWeekAccuracy[] = response.body().getList().getMemdata_accuracy().getLast_week_accuracy();
                float ThisWeekAccuracy[] = response.body().getList().getMemdata_accuracy().getThis_week_accuracy();
                generateData(LastWeekAccuracy, ThisWeekAccuracy);


                float LastWeekSolution[] = response.body().getList().getSubject_solution().getLast_week_solution();
                float ThisWeekSolution[] = response.body().getList().getSubject_solution().getThis_week_solution();
                masterData(LastWeekSolution, ThisWeekSolution);


            }

            @Override
            public void onFailure(Call<StudyAnalysisEntity> call, Throwable t) {
                Log.i("TAG", "网络异常");
            }
        });
    }


    public void MicroAdapterOnItemClickLiter() {
        gAdapter.setOnItemClickLitener(new WisdomSchemeGridLoveAdapter.OnItemClickLiter() {
            @Override
            public void onItemClick(int id, String status, int type) {
                Collection(id, status, type);
            }
        });
    }


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
                    getSoluVideo(subjectid, page);
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


    //////////////////////////////////////////////////////////////////
    private LineChartView chart;
    private LineChartView master;
    boolean hasAxes = true;

    //设置折线间隔位置
    private void resetViewport() {
        final Viewport v = new Viewport(chart.getMaximumViewport());
        v.bottom = 0;
        v.top = 100;
        v.left = 0;
        v.right = 7 - 1;
        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);
    }

    //设置折线间隔位置
    private void resetViewportmaster() {
        final Viewport v = new Viewport(master.getMaximumViewport());
        v.bottom = 0;
        v.top = 100;
        v.left = 0;
        v.right = 7 - 1;
        master.setMaximumViewport(v);
        master.setCurrentViewport(v);
    }

    float ints[] = {0, 25, 50, 75, 100};

    private void generateData(float LastWeekAccuracy[], float ThisWeekAccuracy[]) {
        float Line[][] = {LastWeekAccuracy, ThisWeekAccuracy};
        ArrayList<AxisValue> axisValuesX = new ArrayList<>();//定义X轴刻度值的数据集合
        ArrayList<AxisValue> axisValuesY = new ArrayList<>();//定义Y轴刻度值的数据集合
        String week[] = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
        String Proportion[] = {"0%", "25%", "50%", "75%", "100%"};
        //折线数量
        int lineNumber = 2;
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < lineNumber; ++i) {
            List<PointValue> values = new ArrayList<>();
            for (int j = 0; j < 7; ++j) {  //setLabel
                if (j < 5) {
                    axisValuesY.add(new AxisValue(j).setValue(ints[j]).setLabel(Proportion[j]));
                }
                axisValuesX.add(new AxisValue(j).setLabel(week[j]));
                values.add(new PointValue(j, Line[i][j]));//数据
            }


            int color[] = {getResources().getColor(R.color.homeText), getResources().getColor(R.color.myloding)};
            Line line = new Line(values);
            line.setColor(color[i]);//线的颜色
            //图形样式
            line.setShape(ValueShape.CIRCLE);
            line.setCubic(true);//线是否弯曲
            line.setFilled(false);//折线覆盖区域是否填充 背景颜色
            line.setHasLabelsOnlyForSelected(false);//隐藏数据，触摸可以显示
            line.setHasLines(true);//是否显示折线
            //          line.setHasLabels(true);//是否显示点信息
            line.setHasPoints(false);//是否显示点
            //节点颜色
            //line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            lines.add(line);
        }

        LineChartData data = new LineChartData(lines);
        if (hasAxes) {
            Axis axisX = new Axis();
            axisX.setValues(axisValuesX);
            // Y轴属性
            Axis axisY = new Axis().setHasLines(true);
            axisY.setValues(axisValuesY);
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);

        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }


        //// 设置反向覆盖区域颜色
        data.setBaseValue(Float.NEGATIVE_INFINITY);
        //是否可以缩放
        chart.setZoomEnabled(false);
        chart.setLineChartData(data);

    }


    private void masterData(float LastWeekSolution[], float ThisWeekSolution[]) {
        float xxx[][] = {LastWeekSolution, ThisWeekSolution};
        ArrayList<AxisValue> axisValuesX = new ArrayList<>();//定义X轴刻度值的数据集合
        ArrayList<AxisValue> axisValuesY = new ArrayList<>();//定义Y轴刻度值的数据集合
        String week[] = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
        String Proportion[] = {"0%", "25%", "50%", "75%", "100%"};
        //折线数量
        int lineNumber = 2;
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < lineNumber; ++i) {
            List<PointValue> values = new ArrayList<>();
            for (int j = 0; j < 7; ++j) {  //setLabel
                if (j < 5) {
                    axisValuesY.add(new AxisValue(j).setValue(ints[j]).setLabel(Proportion[j]));
                }
                axisValuesX.add(new AxisValue(j).setLabel(week[j]));
                values.add(new PointValue(j, xxx[i][j]));//数据
            }

            int color[] = {getResources().getColor(R.color.homeText), getResources().getColor(R.color.myloding)};
            Line line = new Line(values);
            line.setColor(color[i]);//线的颜色
            //图形样式
            line.setShape(ValueShape.CIRCLE);
            line.setCubic(true);//线是否弯曲
            line.setFilled(false);//折线覆盖区域是否填充 背景颜色
            line.setHasLabelsOnlyForSelected(false);//隐藏数据，触摸可以显示
            line.setHasLines(true);//是否显示折线
            //          line.setHasLabels(true);//是否显示点信息
            line.setHasPoints(false);//是否显示点
            //节点颜色
            //line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            lines.add(line);
        }

        LineChartData data = new LineChartData(lines);
        if (hasAxes) {
            Axis axisX = new Axis();
            axisX.setValues(axisValuesX);
            // Y轴属性
            Axis axisY = new Axis().setHasLines(true);
            axisY.setValues(axisValuesY);
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);

        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }


        //// 设置反向覆盖区域颜色
        data.setBaseValue(Float.NEGATIVE_INFINITY);
        //是否可以缩放
        master.setZoomEnabled(false);
        master.setLineChartData(data);

    }





    public void shoppingcartOnItemClickLitener() {
        gAdapter.setOnItemClickLitener(new WisdomSchemeGridLoveAdapter.shoppingcartOnItemClickLiter() {
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
        Call<ResponseBody> call = api.addCart(map, SystemUtils.getHeader(SchemeAnalysisActivity.this));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    String  code = object.getString("code");
                    Log.i("TAG","code===="+code);
                    if (code.equals("1")){
                        getSoluVideo(subjectid, page);
                    }else {
                        showToast("该视频已在购物车，无需再次添加！");
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
        Call<ResponseBody> call = api.delCart(map, SystemUtils.getHeader(SchemeAnalysisActivity.this));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("TAG","id==="+response.code());
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    String  code = object.getString("code");
                    Log.i("TAG","code===="+code);
                    if (code.equals("1")){
                        getSoluVideo(subjectid, page);
                    }else {
                        showToast("删除失败或其它错误信息");
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
}
