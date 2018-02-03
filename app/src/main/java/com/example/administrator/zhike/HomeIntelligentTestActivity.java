package com.example.administrator.zhike;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.adapter.AnswerAdapter;
import com.example.administrator.adapter.IntelligentTestAdapter;
import com.example.administrator.entity.IntelligentTestEntity;
import com.example.administrator.utils.CountDown;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SystemUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeIntelligentTestActivity extends BaseActivity implements IntelligentTestAdapter.Listener, CountDown.Listener {
    private ImageView blackImageView;
    private TextView recordTextView;
    private RecyclerView recyclerView;
    private IntelligentTestAdapter adapter;
    private ProgressBar pb;
    private TextView timeTextView;
    private CountDown countDown;


    /**
     * 跳转到该页面
     *
     * @param context
     * @param subjectId
     */
    public static void startActivity(Context context, int subjectId) {
        if (context != null) {
            context.startActivity(new Intent(context, HomeIntelligentTestActivity.class).putExtra("subjectId", subjectId));
        }
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.intelligent_test_black:
                finish();
                break;
            case R.id.intelligent_test_submit:
                TransportingDialog myDialog = new TransportingDialog(HomeIntelligentTestActivity.this, R.style.MyDialog);
                myDialog.setCancelable(true);
                myDialog.show();
                break;
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
        return R.layout.activity_intelligent_test;
    }

    @Override
    public void initView(View view) {
        blackImageView = $(R.id.intelligent_test_black);
        recordTextView = $(R.id.intelligent_test_recorder);
        recyclerView = $(R.id.intelligent_test_subject);//RecyclerView
        pb = $(R.id.pb_time);
        timeTextView = $(R.id.tv_time);
    }

    @Override
    public void setListener() {
        blackImageView.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void doBusiness(Context mContext) {
        new CountDown(10).start();
        getData();
    }

    private void getData() {
        Map<String, Object> httpMap = getHttpMap();
        if (httpMap != null && !httpMap.isEmpty()) {
            Call<ResponseBody> call = RxjavaRetrofitUtils.getApi().getEntranceExam(httpMap, SystemUtils.getHeader(this));
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        List<IntelligentTestEntity> data = IntelligentTestEntity.get(response.body().string());
                        int size = data.size();
                        pb.setMax(size);
                        String s = "0/" + size;
                        recordTextView.setText(s);
                        startTime();
                        updateAdapter(data);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    //网络错误
                }
            });
        }
    }

    private void startTime() {
        //考试时长一小时
        countDown = new CountDown(60 * 60);
        countDown.setListener(this);
        countDown.start();
    }

    private Map<String, Object> getHttpMap() {
        Intent intent = getIntent();
        Map<String, Object> map = new HashMap<>();
        if (intent != null) {
            int subjectId = intent.getIntExtra("subjectId", 2);
            HashMap<String, Object> md5Map = new HashMap<>();
            md5Map.put("apiKey", "3bdb25d93535b66fd13c16379d26f46fgzzzwh");
            md5Map.put("timeStamp", new DescendingEncryption().getTime());
            md5Map.put("subjectid", subjectId);
            md5Map.put("uid", 0);
            md5Map.put("mobSign", SystemUtils.getDeviceId(this));
            String[] arr = new String[]{"timeStamp", "apiKey", "subjectid", "uid", "mobSign"};
            String md5 = new DescendingEncryption().Descending(md5Map, arr);
            map.put("timeStamp", new DescendingEncryption().getTime());
            map.put("apiSign", md5);
            map.put("subjectid", subjectId);
            map.put("uid", 0);
            map.put("mobSign", SystemUtils.getDeviceId(this));
        }
        return map;
    }


    private void updateAdapter(List<IntelligentTestEntity> data) {
        if (adapter == null) {
            View headView = LayoutInflater.from(this).inflate(R.layout.intelligent_test_top, new FrameLayout(this), false);
            View footView = LayoutInflater.from(this).inflate(R.layout.intelligent_test_bottom, new FrameLayout(this), false);
            headView.findViewById(R.id.intelligent_test).setSelected(true);
            adapter = new IntelligentTestAdapter(data, this);
            adapter.addHeaderView(headView);
            adapter.addFooterView(footView);
            footView.findViewById(R.id.intelligent_test_submit).setOnClickListener(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.getItemAnimator().setChangeDuration(0);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setNewData(data);
        }
    }

    @Override
    public void selectOption(int option, int position) {
        if (adapter != null) {
            int select = adapter.getData().get(position - 1).getSelect();
            if (select == -1) {
                pb.setProgress(pb.getProgress() + 1);
                recordTextView.setText(pb.getProgress() + "/" + adapter.getData().size());
            }
            adapter.getData().get(position - 1).setSelect(option);
            adapter.notifyItemChanged(position);
        }
    }

    @Override
    public void onTimeChange(String txt) {
        timeTextView.setText(txt);
    }


    private class TransportingDialog extends Dialog implements View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener {
        Button submit;
        Button conti;
        Context context;
        RecyclerView answer;

        TransportingDialog(Context context, int theme) {
            super(context, theme);
            this.context = context;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.setContentView(R.layout.toast_intelligenttest);
            initView();
            initData();
        }

        private void initData() {
            if (adapter != null) {
                answer.setLayoutManager(new GridLayoutManager(HomeIntelligentTestActivity.this, 5));
                answer.setHasFixedSize(true);
                AnswerAdapter answerAdapter = new AnswerAdapter(adapter.getData());
                answer.setAdapter(answerAdapter);
                answer.setItemAnimator(new DefaultItemAnimator());
                answerAdapter.setOnItemChildClickListener(this);
            }
        }

        private void initView() {
            conti = (Button) findViewById(R.id.toast_conti);
            submit = (Button) findViewById(R.id.toast_submit);
            answer = (RecyclerView) findViewById(R.id.toast_title_answer);
            answer.setNestedScrollingEnabled(false);
            conti.setOnClickListener(this);
            submit.setOnClickListener(this);

        }

        @Override
        public void onClick(View arg0) {
            switch (arg0.getId()) {
                case R.id.toast_conti:
                    this.dismiss();
                    break;
                case R.id.toast_submit:
                    if (countDown != null) {
                        int time = countDown.getTime();
                        int useTime = countDown.getUseTime();
                        ExaminationAnalysisActivity.startActivity(HomeIntelligentTestActivity.this,
                                time, useTime, adapter.getData().size(), adapter.getData());
                    }
                    break;
            }
        }

        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            this.dismiss();
            recyclerView.scrollToPosition(position + 1);
        }
    }
}
