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
import com.example.administrator.entity.RegisterEntity;
import com.example.administrator.entity.SmsEntity;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.PasswordFormat;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SMSUrils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {
    private SharedPreferences sp;
    ServiceApi service;
    ImageView rReturn;
    EditText rCall;
    EditText rYzm;
    static Button rGetYzm;
    EditText rPassword;
    EditText rOkPassword;
    Button rOk;
    String callStr;
    String passwordStr;
    String okPasswordStr;
    String yzmStr;
    static int time = 60;
    private DescendingEncryption encryption;
    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.register_return://返回
                finish();
                break;
            case R.id.register_getyzm://获取验证码
                getYzm();
                break;
            case R.id.register_ok://完成注册
                Register();
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
        return R.layout.activity_register;
    }

    @Override
    public void initView(View view) {
        rReturn = $(R.id.register_return);
        rCall = $(R.id.register_call);
        rYzm = $(R.id.register_yzm);
        rGetYzm = $(R.id.register_getyzm);
        rPassword = $(R.id.register_password);
        rOkPassword = $(R.id.register_ok_password);
        rOk = $(R.id.register_ok);
    }

    @Override
    public void setListener() {
        rReturn.setOnClickListener(this);
        rGetYzm.setOnClickListener(this);
        rOk.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        encryption = new DescendingEncryption();
        sp = getSharedPreferences("xs", MODE_PRIVATE);
    }

    public void getYzm() {
        callStr = rCall.getText().toString().trim();//手机
        if (TextUtils.isEmpty(callStr)) {
            showToast("请输入手机号");
            return;
        } else if (!SMSUrils.judgePhoneNums(callStr, RegisterActivity.this)) { //手机号码不对
            return;
        }
        int id = 1;
        int timeStr = encryption.getTime();
        HashMap<String, Object> mmp = new HashMap<>();
        mmp.put("apiKey", "3bdb25d93535b66fd13c16379d26f46fgzzzwh");
        mmp.put("timeStamp", timeStr);
        mmp.put("mobile", callStr);
        mmp.put("type", id);

        String[] arr = new String[]{"timeStamp", "apiKey", "mobile", "type"};
        String md5 = encryption.Descending(mmp, arr);//加密


        Map<String, Object> map = new HashMap<>();
        map.put("apiSign", md5);
        map.put("timeStamp", timeStr);
        map.put("mobile", callStr);
        map.put("type", id);

        service = RxjavaRetrofitUtils.getApi();
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
                    rGetYzm.setClickable(false);
                    rGetYzm.setText("重新发送(" + time + ")");
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
                    rGetYzm.setText("重新发送(" + time + ")");
                    return;
                case 2:
                    rGetYzm.setText("获取验证码");
                    rGetYzm.setClickable(true);
                    return;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }



    private void Register() {
        callStr = rCall.getText().toString().trim();//手机
        passwordStr = rPassword.getText().toString().trim();//密码
        okPasswordStr = rOkPassword.getText().toString().trim();//q确认密码
        yzmStr = rYzm.getText().toString().trim();//验证码
        if (TextUtils.isEmpty(callStr)) {
            showToast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(passwordStr) || TextUtils.isEmpty(okPasswordStr)) {
            showToast("请输入密码");
            return;
        }
        if (!passwordStr.equals(okPasswordStr)) {
            showToast("密码不相同");
            return;
        }
        if (passwordStr.length() < 8 || passwordStr.length() > 16) {
            showToast("密码长度不能低于8位数,或者大于16位数");
            return;
        }
        if (TextUtils.isEmpty(yzmStr)) {
            showToast("请输入验证码");
            return;
        }
        if (!PasswordFormat.pwdMatch(passwordStr)) {
            showToast("密码必须字母+数字");
            return;
        }
        //18565021283
        //18394665812

        int YZM = 0;
        try {
            YZM = Integer.parseInt(yzmStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        int timeStr = encryption.getTime();
        HashMap<String, Object> mmp = new HashMap<>();
        mmp.put("apiKey", "3bdb25d93535b66fd13c16379d26f46fgzzzwh");
        mmp.put("timeStamp", timeStr);
        mmp.put("mobile", callStr);
        mmp.put("pwd", passwordStr);
        mmp.put("code", YZM);
        String[] arr = new String[]{"timeStamp", "apiKey", "mobile", "pwd", "code"};
        String md5 = encryption.Descending(mmp, arr);//加密

        Map<String, Object> map = new HashMap<>();
        map.put("apiSign", md5);
        map.put("timeStamp", timeStr);
        map.put("mobile", callStr);
        map.put("pwd", passwordStr);
        map.put("code", YZM);


        Call<RegisterEntity> call = service.postRegister(map);
        call.enqueue(new Callback<RegisterEntity>() {
            @Override
            public void onResponse(Call<RegisterEntity> call, Response<RegisterEntity> response) {
                Log.i("TAG", "" + response.body().getCode());
                Log.i("TAG", "" + response.body().getMsg());
                if (response.body().getCode().equals("0")) {
                    showToast("注册失败,验证码错误");
                } else if (response.body().getCode().equals("1")) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("mobile",callStr);
                    bundle.putString("pwd",passwordStr);
                    intent.putExtra("register",bundle);
                    setResult(2,intent);
                    finish();
                } else if (response.body().getCode().equals("2")) {
                    showToast("号码已注册！");
                }
            }

            @Override
            public void onFailure(Call<RegisterEntity> call, Throwable t) {
                t.printStackTrace();
                showToast("注册失败");
            }
        });
    }





}