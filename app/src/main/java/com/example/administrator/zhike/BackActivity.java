package com.example.administrator.zhike;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.constants.ServiceApi;
import com.example.administrator.entity.SmsEntity;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.PasswordFormat;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SMSUrils;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BackActivity extends BaseActivity {// 找回密码
    ImageView bReturn;
    EditText bCall;
    EditText bYzm;
    EditText bMm;
    EditText bXmm;
    static Button bGetYzm;
    Button bDetermine;

    SharedPreferences sp;
    ServiceApi service;
    String callStr;
    String yzmStr;
    String mmStr;
    String xmmStr;
    static int time = 60;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.back_return:
                finish();
                return;
            case R.id.back_get_yzm://获取验证码
                getYzm();
                return;
            case R.id.back_determine://确定修改
                callStr = bCall.getText().toString().trim();
                yzmStr = bYzm.getText().toString().trim();
                mmStr = bMm.getText().toString().trim();
                xmmStr = bXmm.getText().toString().trim();
                if (TextUtils.isEmpty(callStr)) {
                    showToast("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(mmStr) || TextUtils.isEmpty(xmmStr)) {
                    showToast("请输入密码");
                    return;
                }
                if (!mmStr.equals(xmmStr)) {
                    showToast("密码不相同");
                    return;
                }
                if (mmStr.length() < 8 || mmStr.length() > 16) {
                    showToast("密码长度不能低于8位数,或者大于16位数");
                    return;
                }
                if (TextUtils.isEmpty(yzmStr)) {
                    showToast("请输入验证码");
                    return;
                }
                if (!PasswordFormat.pwdMatch(mmStr)) {
                    showToast("密码必须字母+数字");
                    return;
                }
                Login(callStr, mmStr, yzmStr);

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
        return R.layout.activity_back;
    }

    @Override
    public void initView(View view) {
        bReturn = $(R.id.back_return);
        bCall = $(R.id.back_call);
        bYzm = $(R.id.back_yzm);
        bMm = $(R.id.back_mm);
        bXmm = $(R.id.back_xmm);
        bGetYzm = $(R.id.back_get_yzm);
        bDetermine = $(R.id.back_determine);
    }

    @Override
    public void setListener() {
        bReturn.setOnClickListener(this);
        bGetYzm.setOnClickListener(this);
        bDetermine.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        sp = getSharedPreferences("xs", MODE_PRIVATE);
        service = RxjavaRetrofitUtils.getApi();
    }


    public void getYzm() {
        callStr = bCall.getText().toString().trim();//手机
        if (TextUtils.isEmpty(callStr)) {
            showToast("请输入手机号");
            return;
        } else if (!SMSUrils.judgePhoneNums(callStr, BackActivity.this)) { //手机号码不对
            return;
        }
        int id = 2;
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile", callStr);
        map.put("type", id);
        DescendingEncryption.init(map);

        Call<SmsEntity> cal = service.getYzm(map);
        cal.enqueue(new Callback<SmsEntity>() {
            @Override
            public void onResponse(Call<SmsEntity> call, Response<SmsEntity> response) {
                Log.i("TAG", "" + response.body().getCode());
                Log.i("TAG", "" + response.body().getMsg());
                if (response.body().getCode().equals("0")) {
                    showToast("发送失败");
                    return;
                } else if (response.body().getCode().equals("1")) {
                    showToast("验证码发送成功，请注意查收！");
                    bGetYzm.setClickable(false);
                    bGetYzm.setText("重新发送(" + time + ")");
                    new Thread(new Runnable() { //倒计时
                        @Override
                        public void run() {
                            for (; time > 0; time--) {
                                handler.sendEmptyMessage(1);
                                if (time <= 0) {
                                    break;
                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            handler.sendEmptyMessage(2);
                        }
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<SmsEntity> call, Throwable t) {
                showToast("网络异常");
            }
        });


    }

    public static android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    bGetYzm.setText("重新发送(" + time + ")");
                    return;
                case 2:
                    bGetYzm.setText("获取验证码");
                    bGetYzm.setClickable(true);
                    return;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }


    private void Login(final String mobile, final String pwd, String code) {
        int YZM = 0;
        try {
            YZM = Integer.parseInt(code);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("pwd", pwd);
        map.put("code", YZM);
        DescendingEncryption.init(map);


        Call<ResponseBody> call = service.getpwd(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    String code = object.getString("code");
                    if (code.equals("1")) {//成功
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("mobile", mobile);
                        bundle.putString("pwd", pwd);
                        intent.putExtra("register", bundle);
                        setResult(2, intent);
                        finish();
                        showToast("修改成功");
                    } else if (code.equals("0")) {//失败
                        showToast("找回密码失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showToast("找回密码失败");
            }
        });

    }


}
