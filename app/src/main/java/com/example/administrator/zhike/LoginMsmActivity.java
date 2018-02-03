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
import android.widget.TextView;

import com.example.administrator.constants.ServiceApi;
import com.example.administrator.entity.LoginEntity;
import com.example.administrator.entity.SmsEntity;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SMSUrils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginMsmActivity extends BaseActivity {
    EditText call, yzm;
    Button mLand;
    TextView mUser;
    static Button mGetYzm;
    TextView mRegister;
    ImageView mReturn;
    ImageView mQQ;
    ImageView mWX;
    ImageView mWB;

    private String yzmStr;
    private String callStr;

    private SharedPreferences sp;
    ServiceApi service;
    private DescendingEncryption encryption;
    int ResultCode = 1;
    static int time = 60;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.login_msm_return://返回
                finish();
                return;
            case R.id.login_msm_getyzm://获取验证码

                getYzm();
                return;
            case R.id.login_msm_register://注册
                startActivityForResult(RegisterActivity.class, ResultCode);//1
                return;
            case R.id.login_msm_land://登陆
                yzmStr = yzm.getText().toString().trim();
                callStr = call.getText().toString().trim();
                Login(callStr, yzmStr);
                return;
            case R.id.login_msm_user://用户名登陆
                startActivity(LoginActivity.class);
                finish();
                return;
            case R.id.login_msm_qq:
                return;
            case R.id.login_msm_wb:
                return;
            case R.id.login_msm_wx:
                return;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {//注册
            Bundle bundle = data.getBundleExtra("register");
            String mobile = bundle.getString("mobile");
            String pwd = bundle.getString("pwd");
            Intent intent = new Intent();
            Bundle bundle1 = new Bundle();
            bundle1.putString("mobile", mobile);
            bundle1.putString("pwd", pwd);
            intent.putExtra("register", bundle1);
            setResult(2, intent);
            finish();


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
        return R.layout.activity_login_sms;
    }

    @Override
    public void initView(View view) {
        call = $(R.id.login_msm_call);
        yzm = $(R.id.login_msm_yzm);
        mLand = $(R.id.login_msm_land);
        mGetYzm = $(R.id.login_msm_getyzm);
        mUser = $(R.id.login_msm_user);
        mRegister = $(R.id.login_msm_register);
        mReturn = $(R.id.login_msm_return);
        mQQ = $(R.id.login_msm_qq);
        mWX = $(R.id.login_msm_wx);
        mWB = $(R.id.login_msm_wb);
    }

    @Override
    public void setListener() {
        mLand.setOnClickListener(this);
        mGetYzm.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mReturn.setOnClickListener(this);
        mQQ.setOnClickListener(this);
        mWX.setOnClickListener(this);
        mWB.setOnClickListener(this);
        mUser.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        sp = getSharedPreferences("xs", MODE_PRIVATE);
        service = RxjavaRetrofitUtils.getApi();
        encryption = new DescendingEncryption();//降序
    }


    public void getYzm() {
        callStr = call.getText().toString().trim();//手机
        if (TextUtils.isEmpty(callStr)) {
            showToast("请输入手机号");
            return;
        } else if (!SMSUrils.judgePhoneNums(callStr, LoginMsmActivity.this)) { //手机号码不对
            return;
        }
        int id = 3;
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
                    mGetYzm.setClickable(false);
                    mGetYzm.setText("重新发送(" + time + ")");
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
                    mGetYzm.setText("重新发送(" + time + ")");
                    return;
                case 2:
                    mGetYzm.setText("获取验证码");
                    mGetYzm.setClickable(true);
                    return;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }


    private void Login(String username, String yzmStr) {
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
        mmp.put("mobile", username);
        mmp.put("code", YZM);
        mmp.put("type", 2);
        String[] arr = new String[]{"timeStamp", "apiKey", "mobile", "code", "type"};
        String md5 = encryption.Descending(mmp, arr);//加密

        Map<String, Object> map = new HashMap<>();
        map.put("apiSign", md5);
        map.put("timeStamp", timeStr);
        map.put("mobile", username);
        map.put("code", YZM);
        map.put("type", 2);


        Call<LoginEntity> call = service.postLogin(map);
        call.enqueue(new Callback<LoginEntity>() {
            @Override
            public void onResponse(Call<LoginEntity> call, Response<LoginEntity> response) {
                Log.i("TAG", "=====" + response.body().getCode());
                Log.i("TAG", response.body().getList().getHeadimgurl() + "");
                if (response.body().getCode().equals("0")) {
                    showToast("密码错误");
                }
                if (response.body().getCode().equals("1")) {
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("nickname", response.body().getList().getNickname());
                    edit.putString("headimgurl", response.body().getList().getHeadimgurl() + "");
                    edit.putString("token", response.body().getList().getToken());
                    edit.putString("mobile", response.body().getList().getMobile());
                    edit.putInt("id", response.body().getList().getId());
                    edit.commit();
                    setResult(3);
                    finish();
                }
                if (response.body().getCode().equals("2")) {
                    showToast("帐号不存在");
                }
            }

            @Override
            public void onFailure(Call<LoginEntity> call, Throwable t) {
                showToast("登录失败");
            }
        });


    }

}
