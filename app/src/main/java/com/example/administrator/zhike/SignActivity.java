package com.example.administrator.zhike;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.adapter.GetSignConfigAdapter;
import com.example.administrator.adapter.MyPagerAdapter;
import com.example.administrator.adapter.WisdomSchemeGridLoveAdapter;
import com.example.administrator.constants.MyList;
import com.example.administrator.constants.ServiceApi;
import com.example.administrator.entity.AddIntegralEntity;
import com.example.administrator.entity.CollectionEntity;
import com.example.administrator.entity.GetSignConfigEntity;
import com.example.administrator.entity.GetSignEntity;
import com.example.administrator.entity.SoluVideoEntity;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SystemUtils;
import com.example.administrator.zhike.view.GridSpacingItemDecoration;
import com.example.administrator.zhike.view.LinearLayoutColorDecoration;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.BaseApplication;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignActivity extends BaseActivity {
    ImageView sReturn;
    ImageView sSign;
    ScrollView scrollView;
    TextView share;
    TextView sDay;
    TextView sIntegral;
    LinearLayout sRise;
    LinearLayout sDrop;
    ViewPager pager;
    List<View> listViews;
    View view1, view2;
    RecyclerView recycler;
    ServiceApi api;
    RecyclerView riseRecycler;
    GetSignConfigAdapter getSignConfigAdapter;
    WisdomSchemeGridLoveAdapter gAdapter;
    int page = 1;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.sign_return:
                finish();
                return;
            case R.id.sign_sign://签到
                addSign();
                return;
            case R.id.sign_share://分享
                return;
            case R.id.sign_rise://赚积分
                pager.setCurrentItem(0);
                return;
            case R.id.sign_drop://花积分
                pager.setCurrentItem(1);
                return;
            case R.id.pager_sign_drop_love://换一组
                page++;
                if (page > 5) {
                    page = 1;
                }
                getSoluVideo(MyList.subjectId, page);
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
        return R.layout.activity_sign;
    }

    @Override
    public void initView(View view) {
        sReturn = $(R.id.sign_return);
        sSign = $(R.id.sign_sign);
        scrollView = $(R.id.sign_scroll);
        share = $(R.id.sign_share);
        sDay = $(R.id.sign_day);
        sIntegral = $(R.id.sign_integral);
        sRise = $(R.id.sign_rise);
        sDrop = $(R.id.sign_drop);
        pager = $(R.id.sign_pager);

    }

    @Override
    public void setListener() {
        sReturn.setOnClickListener(this);
        sSign.setOnClickListener(this);
        share.setOnClickListener(this);
        sRise.setOnClickListener(this);
        sDrop.setOnClickListener(this);
        scrollView.setVerticalScrollBarEnabled(false);

    }

    @Override
    public void doBusiness(Context mContext) {
        if (api == null) {
            api = RxjavaRetrofitUtils.getApi();
        }
        getSign();
        index();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == 3) {
            Log.i("TAG", "登记完成");
            getSign();
        }
    }

    private void index() {
        listViews = new ArrayList<>();
        LayoutInflater mInflater = getLayoutInflater();
        view1 = mInflater.inflate(R.layout.pager_sign_rise, null);
        riseRecycler = (RecyclerView) view1.findViewById(R.id.pager_sign_rise_recycler);
        riseRecycler.setNestedScrollingEnabled(false);
        getSignConfig();
        listViews.add(view1);

        view2 = mInflater.inflate(R.layout.pager_sign_drop, null);
        recycler = (RecyclerView) view2.findViewById(R.id.pager_sign_drop_recycler);
        recycler.setNestedScrollingEnabled(false);


        Button button = (Button) view2.findViewById(R.id.pager_sign_drop_love);
        button.setOnClickListener(this);
        getSoluVideo(MyList.subjectId, page);
        listViews.add(view2);


        MyPagerAdapter adapte = new MyPagerAdapter(listViews);
        pager.setAdapter(adapte);
        pager.setCurrentItem(0);
        pager.setOnPageChangeListener(new MyOnPageChangeListener());

    }


    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    sRise.setBackgroundColor(getResources().getColor(R.color.baiS));
                    sDrop.setBackgroundColor(getResources().getColor(R.color.sign));
                    break;
                case 1:
                    sRise.setBackgroundColor(getResources().getColor(R.color.sign));
                    sDrop.setBackgroundColor(getResources().getColor(R.color.baiS));

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

    //签到
    public void addSign() {
        Call<ResponseBody> call = api.addSign(DescendingEncryption.getDefault(), SystemUtils.getHeader(SignActivity.this));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject resultJson = new JSONObject(response.body().string());
                    String status = resultJson.getString("code");
                    if (status.equals("-1")) {
                        showToast("完善个人资料,才能签到");
                    } else if (status.equals("0")) {
                        showToast("签到失败");
                    } else if (status.equals("1")) {
                        showToast("签到成功");
                        sSign.setImageResource(R.drawable.sign_logo_yes);
                        sSign.setClickable(false);
                        addIntegral(1, 2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showToast("网络异常");
            }
        });
    }

    //赚积分
    public void addIntegral(int type, int integNum) {
        Map<String, Object> map = new HashMap();
        map.put("type", type);
        map.put("integNum", integNum);
        DescendingEncryption.init(map);
        Call<AddIntegralEntity> call = api.addIntegral(map, SystemUtils.getHeader(SignActivity.this));
        call.enqueue(new Callback<AddIntegralEntity>() {
            @Override
            public void onResponse(Call<AddIntegralEntity> call, Response<AddIntegralEntity> response) {
                sIntegral.setText("当前积分：" + response.body().getList().getTotal_integral());
                sDay.setText("第 " + response.body().getList().getSignNumber() + " 天");
            }

            @Override
            public void onFailure(Call<AddIntegralEntity> call, Throwable t) {
                showToast("网络异常");
            }
        });
    }

    //用户的签到和积分消息
    public void getSign() {
        Call<GetSignEntity> call = api.getSign(DescendingEncryption.getDefault(), SystemUtils.getHeader(SignActivity.this));
        call.enqueue(new Callback<GetSignEntity>() {
            @Override
            public void onResponse(Call<GetSignEntity> call, Response<GetSignEntity> response) {
                String code = response.body().getCode();
                if (code.equals("0")) {
                } else if (code.equals("1")) {
                    sIntegral.setText("当前积分：" + response.body().getList().getTotal_integral());
                    sDay.setText("第 " + response.body().getList().getSignNumber() + " 天");
                    int signStatus = response.body().getList().getSignStatus();
                    int iscomplete = response.body().getList().getIscomplete();
                    if (signStatus == 0) {//0：未签到；1：已签到
                        sSign.setImageResource(R.drawable.sign_logo);
                    } else {
                        sSign.setImageResource(R.drawable.sign_logo_yes);
                        sSign.setClickable(false);
                    }

                }
            }

            @Override
            public void onFailure(Call<GetSignEntity> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    //获取积分配置信息接口
    public void getSignConfig() {
        Call<GetSignConfigEntity> call = api.getSignConfig(DescendingEncryption.getDefault(), SystemUtils.getHeader(SignActivity.this));
        call.enqueue(new Callback<GetSignConfigEntity>() {
            @Override
            public void onResponse(Call<GetSignConfigEntity> call, Response<GetSignConfigEntity> response) {
                ArrayList<GetSignConfigEntity.list> data = response.body().getList();
                if (getSignConfigAdapter == null) {
                    getSignConfigAdapter = new GetSignConfigAdapter(data);
                    riseRecycler.setLayoutManager(new LinearLayoutManager(SignActivity.this));
                    riseRecycler.addItemDecoration(new LinearLayoutColorDecoration(LinearLayoutManager.VERTICAL, getResources().getDimensionPixelSize(R.dimen.x40), getResources().getColor(R.color.baiS), getResources().getDimensionPixelSize(R.dimen.x40)));
                    riseRecycler.setItemAnimator(new DefaultItemAnimator());
                    riseRecycler.setAdapter(getSignConfigAdapter);
                } else {
                    getSignConfigAdapter.setNewData(data);
                }
                getSignConfigAdapterOnItemClick();
            }

            @Override
            public void onFailure(Call<GetSignConfigEntity> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getSignConfigAdapterOnItemClick() {
        getSignConfigAdapter.setOnItemClickLitener(new GetSignConfigAdapter.OnItemClickLiter() {
            @Override
            public void onItemClick(int position) {
                if (position == 2) {//完善资料
                    startActivityForResult(MyInformationActivity.class, 2);
                } else if (position == 3) {//推荐好友
                    //                    startActivityForResult(MyInformationActivity.class, 2);
                    Log.i("TAG", "推荐好友");
                }
            }
        });
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
        Call<SoluVideoEntity> call = api.getSoluVideo(map, SystemUtils.getHeader(SignActivity.this));
        call.enqueue(new Callback<SoluVideoEntity>() {
            @Override
            public void onResponse(Call<SoluVideoEntity> call, Response<SoluVideoEntity> response) {
                ArrayList<SoluVideoEntity.list> data = response.body().getList();
                if (gAdapter == null) {
                    gAdapter = new WisdomSchemeGridLoveAdapter(data);
                    recycler.setLayoutManager(new GridLayoutManager(SignActivity.this, 2));
                    recycler.addItemDecoration(new GridSpacingItemDecoration(2, getResources().getDimensionPixelSize(R.dimen.x20), false));//设置颜色  和 分割线
                    recycler.setItemAnimator(new DefaultItemAnimator());
                    recycler.setAdapter(gAdapter);
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
                    getSoluVideo(MyList.subjectId, page);
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
        Call<ResponseBody> call = api.addCart(map, SystemUtils.getHeader(SignActivity.this));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    String  code = object.getString("code");
                    Log.i("TAG","code===="+code);
                    if (code.equals("1")){
                        getSoluVideo(MyList.subjectId, page);
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
        Call<ResponseBody> call = api.delCart(map, SystemUtils.getHeader(SignActivity.this));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("TAG","id==="+response.code());
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    String  code = object.getString("code");
                    Log.i("TAG","code===="+code);
                    if (code.equals("1")){
                        getSoluVideo(MyList.subjectId, page);
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
