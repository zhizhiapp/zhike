package com.example.administrator.zhike;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.adapter.MyShoppingCartSettlementAdapter;
import com.example.administrator.adapter.MyShoppingCartSettlementCouponAdapter;
import com.example.administrator.entity.CouponEntity;
import com.example.administrator.entity.MyShoppingCartEntity;
import com.example.administrator.utils.FullyLinearLayoutManager;
import com.example.administrator.zhike.view.LinearLayoutItemDecoration;

import java.util.ArrayList;

public class MyShoppingCartSettlementActivity extends BaseActivity {
    ArrayList<MyShoppingCartEntity> list;
    ImageView MySettlementReturn;
    RecyclerView recycler;
    RecyclerView recyclerCoupon;
    ScrollView scrollView;
    MyShoppingCartSettlementAdapter adapter;
    LinearLayout wx;
    ImageView wxImg;
    TextView wxText;

    LinearLayout zfb;
    ImageView zfbImg;
    TextView zfbText;

    TextView num;
    TextView money;
    TextView moneyDiscount;
    TextView moneyActual;
    Button submit;
    int i = 1;//标记的作用
    double totalPrice;
    double Total;
    MyShoppingCartSettlementCouponAdapter couponAdapter;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.my_shopping_cart_settlement_return:
                finish();
                return;
            case R.id.my_shopping_cart_settlement_wx://微信
                i = 1;
                wx.setBackgroundResource(R.drawable.my_shopping_cart_settlement_return_recycler_coupon_yes);
                wxImg.setImageResource(R.drawable.my_shopping_cart_settlement_return_recycler_coupon_wx_yes);
                wxText.setTextColor(getResources().getColor(R.color.heiS));

                zfb.setBackgroundResource(R.drawable.my_shopping_cart_settlement_return_recycler_coupon_no);
                zfbImg.setImageResource(R.drawable.my_shopping_cart_settlement_return_recycler_coupon_zfb_no);
                zfbText.setTextColor(getResources().getColor(R.color.Coupon));

                return;
            case R.id.my_shopping_cart_settlement_zfb://支付宝
                i = 2;
                wx.setBackgroundResource(R.drawable.my_shopping_cart_settlement_return_recycler_coupon_no);
                wxImg.setImageResource(R.drawable.my_shopping_cart_settlement_return_recycler_coupon_wx_no);
                wxText.setTextColor(getResources().getColor(R.color.Coupon));

                zfb.setBackgroundResource(R.drawable.my_shopping_cart_settlement_return_recycler_coupon_yes);
                zfbImg.setImageResource(R.drawable.my_shopping_cart_settlement_return_recycler_coupon_zfb_yes);
                zfbText.setTextColor(getResources().getColor(R.color.heiS));

                return;
            case R.id.my_shopping_cart_settlement_submit://提交
//                Total //共计
                if (i == 1) {//微信

                } else if (i == 2) {//支付宝

                }

                return;
        }
    }

    @Override
    public void initParms(Bundle parms) {
        list = (ArrayList<MyShoppingCartEntity>) parms.getSerializable("putList");
        totalPrice = parms.getDouble("money");
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_my_shopping_cart_settlement;
    }

    @Override
    public void initView(View view) {
        MySettlementReturn = $(R.id.my_shopping_cart_settlement_return);
        recycler = $(R.id.my_shopping_cart_settlement_recycler);
        recyclerCoupon = $(R.id.my_shopping_cart_settlement_recycler_coupon);
        scrollView = $(R.id.my_shopping_cart_settlement_scroll);

        wx = $(R.id.my_shopping_cart_settlement_wx);
        wxImg = $(R.id.my_shopping_cart_settlement_wx_img);
        wxText = $(R.id.my_shopping_cart_settlement_wx_text);
        zfb = $(R.id.my_shopping_cart_settlement_zfb);
        zfbImg = $(R.id.my_shopping_cart_settlement_zfb_img);
        zfbText = $(R.id.my_shopping_cart_settlement_zfb_text);
        num = $(R.id.my_shopping_cart_settlement_num);
        money = $(R.id.my_shopping_cart_settlement_money);
        moneyDiscount = $(R.id.my_shopping_cart_settlement_money_discount);
        moneyActual = $(R.id.my_shopping_cart_settlement_money_actual);
        submit = $(R.id.my_shopping_cart_settlement_submit);


    }

    @Override
    public void setListener() {
        MySettlementReturn.setOnClickListener(this);
        recycler.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动
        recyclerCoupon.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动
        scrollView.setVerticalScrollBarEnabled(false);
        wx.setOnClickListener(this);
        zfb.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(MyShoppingCartSettlementActivity.this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
        recycler.addItemDecoration(new LinearLayoutItemDecoration(LinearLayoutManager.VERTICAL, getResources().getDimensionPixelSize(R.dimen.x22)));//设置颜色  和 分割线
        adapter = new MyShoppingCartSettlementAdapter(MyShoppingCartSettlementActivity.this, list);
        recycler.setAdapter(adapter);
        recycler.setItemAnimator(new DefaultItemAnimator());

        ArrayList<CouponEntity> mList = new ArrayList<>();
//        CouponEntity entity = new CouponEntity();
//        entity.setMoney(1000);
//        entity.setMoneyTitle(5);
//        entity.setStarTime("2018-1-15");
//        entity.setEndTime("2018-10-15");
//        mList.add(entity);
//
//        CouponEntity entity1 = new CouponEntity();
//        entity1.setMoney(5000);
//        entity1.setMoneyTitle(5);
//        entity1.setStarTime("2018-5-15");
//        entity1.setEndTime("2018-7-15");
//        mList.add(entity1);
//
//        CouponEntity entity2 = new CouponEntity();
//        entity2.setMoney(6000);
//        entity2.setMoneyTitle(5);
//        entity2.setStarTime("2018-1-10");
//        entity2.setEndTime("2018-10-1");
//        mList.add(entity2);


        //优惠券
        FullyLinearLayoutManager man = new FullyLinearLayoutManager(MyShoppingCartSettlementActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerCoupon.setLayoutManager(man);
        recyclerCoupon.addItemDecoration(new LinearLayoutItemDecoration(LinearLayoutManager.HORIZONTAL, getResources().getDimensionPixelSize(R.dimen.x16)));//设置颜色  和 分割线
        couponAdapter = new MyShoppingCartSettlementCouponAdapter(MyShoppingCartSettlementActivity.this, totalPrice, mList);
        recyclerCoupon.setAdapter(couponAdapter);
        recyclerCoupon.setItemAnimator(new DefaultItemAnimator());

        num.setText("您购买了" + list.size() + "个课程");//个数
        money.setText("商品金额:￥" + totalPrice + "");//金额
        moneyDiscount.setText("已优惠金额:￥" + 0);//优惠金额
        moneyActual.setText("实付金额合计:￥" + totalPrice);//实际

        initEvent();
    }

    private void initEvent() {
        couponAdapter.setOnItemClickLitener(new MyShoppingCartSettlementCouponAdapter.OnItemClickLiter() {
            @Override
            public void onItemClick(int couponMoney, int position, double MoneyTitle) {
                couponAdapter.checked(position);
                couponAdapter.notifyDataSetChanged();
                moneyDiscount.setText("已优惠金额:￥" + MoneyTitle);//优惠金额
                Total = totalPrice - MoneyTitle;
                moneyActual.setText("实付金额合计:￥" + Total);//优惠金额

                Log.i("TAG", "优惠" + couponMoney);
                Log.i("TAG", "值" + position);
            }
        });
    }


}
