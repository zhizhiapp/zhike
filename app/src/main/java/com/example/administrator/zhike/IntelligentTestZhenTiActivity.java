package com.example.administrator.zhike;

import android.app.Dialog;
import android.content.Context;
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
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SystemUtils;
import com.example.administrator.zhike.view.Anticlockwise;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntelligentTestZhenTiActivity extends BaseActivity implements IntelligentTestAdapter.Listener {
    private ImageView blackImageView;
    private TextView recordTextView;
    private RecyclerView recyclerView;
    private IntelligentTestAdapter adapter;
    private ProgressBar pb;
    private Anticlockwise anticlockwise;
    private View inteTest;

    int subjectId;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.intelligent_test_black:
                finish();
                break;
            case R.id.intelligent_test_submit:
                TransportingDialog myDialog = new TransportingDialog(IntelligentTestZhenTiActivity.this, R.style.MyDialog);
                myDialog.setCancelable(true);
                myDialog.show();
                break;
        }
    }

    @Override
    public void initParms(Bundle parms) {
        subjectId = parms.getInt("subjectId");
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
        anticlockwise = $(R.id.alw_time);
    }

    @Override
    public void setListener() {
        blackImageView.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void doBusiness(Context mContext) {
        getData();
    }


    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("subjectid", subjectId);
        map.put("uid", 0);
        map.put("mobSign", SystemUtils.getDeviceId(this));
        DescendingEncryption.init(map);
        Call<ResponseBody> call = RxjavaRetrofitUtils.getApi().getEntranceExam(map, SystemUtils.getHeader(this));
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

    private void startTime() {
        anticlockwise.initTime(60 * 60);
        anticlockwise.start();
    }


    private void updateAdapter(List<IntelligentTestEntity> data) {
        if (adapter == null) {
            View footView = LayoutInflater.from(this).inflate(R.layout.intelligent_test_bottom, new FrameLayout(this), false);
            adapter = new IntelligentTestAdapter(data, this);
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
                answer.setLayoutManager(new GridLayoutManager(IntelligentTestZhenTiActivity.this, 5));
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
                    startActivity(ExaminationAnalysisActivity.class);
                    break;
                default:
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
