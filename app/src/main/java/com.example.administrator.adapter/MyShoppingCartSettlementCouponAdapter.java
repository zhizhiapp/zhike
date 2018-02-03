package com.example.administrator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.entity.CouponEntity;
import com.example.administrator.zhike.R;

import java.util.ArrayList;

//节
public class MyShoppingCartSettlementCouponAdapter extends RecyclerView.Adapter<MyShoppingCartSettlementCouponAdapter.MyViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    int myValue = -1;
    ArrayList<CouponEntity> mList;
    double totalPrice;
    private OnItemClickLiter mOnItemClickLitener;

    public interface OnItemClickLiter {
        void onItemClick(int couponMoney, int position, double MoneyTitle);
    }

    public void setOnItemClickLitener(OnItemClickLiter mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public MyShoppingCartSettlementCouponAdapter(Context context, double totalPrice, ArrayList<CouponEntity> mList) {
        mInflater = LayoutInflater.from(context);
        this.totalPrice = totalPrice;
        this.context = context;
        this.mList = mList;
    }


    public void checked(int myValue) {
        this.myValue = myValue;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.item_recycler_my_shopping_cart_settlement_coupon, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

//        final CouponEntity entity = mList.get(position);
//        holder.couponMoney.setText("￥" + entity.getMoney());
//        holder.couponMoneyTitle.setText("满" + entity.getMoneyTitle() + "元使用");
//        holder.couponMoneyTime.setText(entity.getStarTime() + "-" + entity.getEndTime());
//        if (mOnItemClickLitener != null) {
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (totalPrice > entity.getMoneyTitle()) {
//                        mOnItemClickLitener.onItemClick(entity.getMoney(), position, entity.getMoneyTitle());
//                    } else {
//                        Toast.makeText(context, "不满足优惠券条件", Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
//        }
        if (myValue == position) {
            holder.layout.setBackgroundResource(R.drawable.item_coupon_yyh);
        } else {
            holder.layout.setBackgroundResource(R.drawable.coupon_ljsy);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView couponMoney;
        TextView couponMoneyTitle;
        TextView couponMoneyTime;

        MyViewHolder(View view) {
            super(view);
            layout = (LinearLayout) view.findViewById(R.id.coupon_layout_bj);
            couponMoney = (TextView) view.findViewById(R.id.coupon_money);
            couponMoneyTitle = (TextView) view.findViewById(R.id.coupon_money_title);
            couponMoneyTime = (TextView) view.findViewById(R.id.coupon_money_time);
        }
    }
}