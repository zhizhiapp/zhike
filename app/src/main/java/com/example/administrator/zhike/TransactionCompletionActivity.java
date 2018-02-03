package com.example.administrator.zhike;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TransactionCompletionActivity extends BaseActivity {
    TextView money;
    TextView number;
    Button details;
    Button continu;
    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.transaction_completion_details:
                return;
            case R.id.transaction_completion_continue:
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
        return R.layout.activity_transaction_completion;
    }

    @Override
    public void initView(View view) {
        money = $(R.id.transaction_completion_money);
        number = $(R.id.transaction_completion_number);
        details = $(R.id.transaction_completion_details);
        continu = $(R.id.transaction_completion_continue);
    }

    @Override
    public void setListener() {
        details.setOnClickListener(this);
        continu.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
