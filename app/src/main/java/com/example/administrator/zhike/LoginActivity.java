package com.example.administrator.zhike;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.constants.ServiceApi;
import com.example.administrator.entity.LoginEntity;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.RxjavaRetrofitUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    EditText call, password;
    Button mLand;
    TextView mForget;
    TextView mSms;
    TextView mRegister;
    ImageView mReturn;
    ImageView mQQ;
    ImageView mWX;
    ImageView mWB;

    private String passwordStr;
    private String callStr;

    private SharedPreferences sp;
    ServiceApi service;
    private DescendingEncryption encryption;
    int ResultCode = 1;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.login_land://登陆
                callStr = call.getText().toString().trim();
                passwordStr = password.getText().toString().trim();
                Login(callStr, passwordStr);

                return;
            case R.id.login_return://返回
                finish();
                return;
            case R.id.login_forget://忘记密码
                startActivityForResult(BackActivity.class, ResultCode);//1
                return;
            case R.id.login_sms://短信登陆
                startActivityForResult(LoginMsmActivity.class, ResultCode);//1
                return;
            case R.id.login_register://立即注册
                startActivityForResult(RegisterActivity.class, ResultCode);//1
                return;
            case R.id.login_qq://QQ
                return;
            case R.id.login_wx://WX
                return;
            case R.id.login_wb://WB

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
        return R.layout.activity_login;
    }

    @Override
    public void initView(View view) {
        call = $(R.id.login_call);
        password = $(R.id.login_password);
        mLand = $(R.id.login_land);
        mForget = $(R.id.login_forget);
        mSms = $(R.id.login_sms);
        mRegister = $(R.id.login_register);
        mReturn = $(R.id.login_return);
        mQQ = $(R.id.login_qq);
        mWX = $(R.id.login_wx);
        mWB = $(R.id.login_wb);
    }

    @Override
    public void setListener() {
        mLand.setOnClickListener(this);
        mForget.setOnClickListener(this);
        mSms.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mReturn.setOnClickListener(this);
        mQQ.setOnClickListener(this);
        mWX.setOnClickListener(this);
        mWB.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        sp = getSharedPreferences("xs", MODE_PRIVATE);
        service = RxjavaRetrofitUtils.getApi();
        encryption = new DescendingEncryption();//降序
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {//注册
            Bundle bundle = data.getBundleExtra("register");
            String mobile = bundle.getString("mobile");
            String pwd = bundle.getString("pwd");

            Log.i("TAG", mobile + "");
            Log.i("TAG", pwd + "");
            Login(mobile, pwd);
        }
        if (requestCode == 1 && resultCode == 3) {//短信登陆
            setResult(20);
            finish();
        }
    }


    private void Login(String username, String password) {
        int timeStr = encryption.getTime();
        HashMap<String, Object> mmp = new HashMap<>();
        mmp.put("apiKey", "3bdb25d93535b66fd13c16379d26f46fgzzzwh");
        mmp.put("timeStamp", timeStr);
        mmp.put("mobile", username);
        mmp.put("pwd", password);
        mmp.put("type", 1);
        String[] arr = new String[]{"timeStamp", "apiKey", "mobile", "pwd", "type"};
        String md5 = encryption.Descending(mmp, arr);//加密

        Map<String, Object> map = new HashMap<>();
        map.put("apiSign", md5);
        map.put("timeStamp", timeStr);
        map.put("mobile", username);
        map.put("pwd", password);
        map.put("type", 1);

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
                    Log.i("TAG",response.body().getList().getToken()+"");
                    edit.putString("token", response.body().getList().getToken());
                    edit.putString("mobile", response.body().getList().getMobile());
                    edit.putInt("id", response.body().getList().getId());
                    edit.commit();
                    setResult(20);
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
