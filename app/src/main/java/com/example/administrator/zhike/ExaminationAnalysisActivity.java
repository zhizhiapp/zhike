package com.example.administrator.zhike;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.entity.AnswerSubmitEntity;
import com.example.administrator.entity.IntelligentTestEntity;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SystemUtils;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.BaseApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExaminationAnalysisActivity extends BaseActivity {
    private TextView timeTextView;
    private TextView subjectNumberTextView;
    private TextView perTextView;
    private CircularProgressBar progressBar;
    private TextView branchTextView;


    public static void startActivity(Context context,
                                     int time,
                                     int useTime,
                                     int subjectNumber,
                                     List<IntelligentTestEntity> data) {
        if (context != null && data != null && !data.isEmpty()) {
            Intent intent = new Intent(context, ExaminationAnalysisActivity.class);
            intent.putExtra("subject_number", subjectNumber);
            intent.putExtra("time", time);
            intent.putExtra("use_time", useTime);
            intent.putExtra("data", (Serializable) data);
            context.startActivity(intent);
        }
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.black:
                finish();
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
        return R.layout.activity_examination_analysis;
    }

    @Override
    public void initView(View view) {
        findViewById(R.id.black).setOnClickListener(this);
      /*  timeTextView = (TextView) findViewById(R.id.tv_time);
        subjectNumberTextView = (TextView) findViewById(R.id.tv_subject_number);
        perTextView = (TextView) findViewById(R.id.tv_per);
        progressBar = (CircularProgressBar) findViewById(R.id.cpb_achievement);
        branchTextView = (TextView) findViewById(R.id.tv_branch);
       */
    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(Context mContext) {
        Intent intent = getIntent();
        if (intent != null) {
            int useTime = intent.getIntExtra("use_time", 0);
            List<IntelligentTestEntity> data = (List<IntelligentTestEntity>) intent.getSerializableExtra("data");
            String subjectId = data.get(0).getSubjectId();
            String paperId = data.get(0).getPaperId();
            Log.d("zaibuzouwoj", "第一个 : " + paperId);
          /*  int time = intent.getIntExtra("time", 0);
            int useTime = intent.getIntExtra("use_time", 0);
            int subjectNumber = intent.getIntExtra("subject_number", 0);
            String timeTxt = new SimpleDateFormat("mm:ss", Locale.CHINA).format(new Date(useTime * 1000));
            List<IntelligentTestEntity> data = (List<IntelligentTestEntity>) intent.getSerializableExtra("data");
            String subjectId = data.get(0).getSubjectId();
            String paperId = data.get(0).getPaperId();
            //答对几题
            int correct = 0;
            for (IntelligentTestEntity item : data) {
                if (item.isAnswer()) {
                    correct++;
                }
            }
            //百分比
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);
            String result = numberFormat.format((float) correct / (float) subjectNumber * 100) + "%";
            subjectNumberTextView.setText(correct + "/" + subjectNumber + "道");
            timeTextView.setText(timeTxt);
            perTextView.setText(result);
            branchTextView.setText(String.valueOf(correct * 4));
            progressBar.setProgress(correct * 4);*/
            submitExam(subjectId, paperId, useTime, 100, Arrays.toString(getAnswer(data)));
            // getAnalysisMap(subjectId, "1161");
            //  analysis(getAnalysisMap(subjectId, "1161"));
        }
    }

    private String[] getAnswer(List<IntelligentTestEntity> data) {
        if (data != null && !data.isEmpty()) {
            String[] strings = new String[data.size()];
            int a = 0;
            for (IntelligentTestEntity entity : data) {
                String answer = entity.getAnswer();
                switch (answer) {
                    case "A":
                        answer = "0";
                        break;
                    case "B":
                        answer = "1";
                        break;
                    case "C":
                        answer = "2";
                        break;
                    case "D":
                        answer = "3";
                        break;
                    case "E":
                        answer = "4";
                        break;
                }
                boolean b = entity.isAnswer();
                String id = entity.getId();
                String[] item = {id, answer, String.valueOf(b ? 0 : 1)};
                String s = Arrays.toString(item);
                strings[a] = s;
                a++;
            }
            return strings;
        }
        return null;
    }

    private Map<String,Object> getSubmitMap(String subjectId, String paperId, int doTime, int score, String answer) {
        HashMap<String, Object> md5Map = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        md5Map.put("apiKey", BaseApplication.API_KEY);
        md5Map.put("timeStamp", new DescendingEncryption().getTime());
        md5Map.put("subjectid", subjectId);
        md5Map.put("mobSign", SystemUtils.getDeviceId(this));
        md5Map.put("paperid", paperId);
        md5Map.put("times", doTime);
        md5Map.put("score", score);
        md5Map.put("answer", answer);
        String[] arr = new String[]{"timeStamp", "apiKey", "subjectid", "mobSign", "paperid", "times", "score", "answer"};
        String md5 = new DescendingEncryption().Descending(md5Map, arr);
        map.put("timeStamp", new DescendingEncryption().getTime());
        map.put("apiSign", md5);
        map.put("subjectid", subjectId);
        map.put("mobSign", SystemUtils.getDeviceId(this));
        map.put("paperid", paperId);
        map.put("times", doTime);
        map.put("score", score);
        map.put("answer", answer);
        return map;
    }

    private Map getAnalysisMap(String subjectId, String paperId) {
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> md5Map = new HashMap<>();
        md5Map.put("apiKey", "3bdb25d93535b66fd13c16379d26f46fgzzzwh");
        md5Map.put("timeStamp", new DescendingEncryption().getTime());
        md5Map.put("subjectid", subjectId);
        md5Map.put("mobSign", SystemUtils.getDeviceId(this));
        md5Map.put("paperid", paperId);
        String[] arr = new String[]{"timeStamp", "apiKey", "subjectid", "mobSign", "paperid"};
        String md5 = new DescendingEncryption().Descending(md5Map, arr);
        map.put("timeStamp", new DescendingEncryption().getTime());
        map.put("apiSign", md5);
        map.put("subjectid", subjectId);
        map.put("mobSign", SystemUtils.getDeviceId(this));
        map.put("paperid", paperId);
        return map;
    }


    private void submitExam(final String subjectId, final String paperId, int doTime, int score, String answer) {
        Map<String, Object> submitMap = getSubmitMap(subjectId, paperId, doTime, score, answer);
        Call<AnswerSubmitEntity> call = RxjavaRetrofitUtils.getApi().submitExam(submitMap, SystemUtils.getHeader(this));
        call.enqueue(new Callback<AnswerSubmitEntity>() {
            @Override
            public void onResponse(Call<AnswerSubmitEntity> call, Response<AnswerSubmitEntity> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals("1")) {

                    }
                }
            }

            @Override
            public void onFailure(Call<AnswerSubmitEntity> call, Throwable t) {

            }
        });
    }

    private void analysis(Map map) {
        Call call = RxjavaRetrofitUtils.getApi().getAnswerAnalysis(map, SystemUtils.getHeader(this));
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }
}
