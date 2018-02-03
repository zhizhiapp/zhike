package com.example.administrator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.entity.MyShoppingCartEntity;
import com.example.administrator.zhike.R;

import java.util.ArrayList;

//节
public class MyShoppingCartSettlementAdapter extends RecyclerView.Adapter<MyShoppingCartSettlementAdapter.MyViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    ArrayList<MyShoppingCartEntity> list;
    private OnItemClickLiter mOnItemClickLitener;

    public interface OnItemClickLiter {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLiter mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public MyShoppingCartSettlementAdapter(Context context,ArrayList<MyShoppingCartEntity> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.item_recycler_my_shopping_cart_settlement, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        MyShoppingCartEntity entity = list.get(position);
        holder.money.setText(""+entity.getMoney());




        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;
        TextView money;
        Button collection;

        MyViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.item_recycler_my_shopping_cart_settlement_img);
            title = (TextView) view.findViewById(R.id.item_recycler_my_shopping_cart_settlement_title);
            money = (TextView) view.findViewById(R.id.item_recycler_my_shopping_cart_settlement_money);
            collection = (Button) view.findViewById(R.id.item_recycler_my_shopping_cart_settlement_collection);
        }
    }
}