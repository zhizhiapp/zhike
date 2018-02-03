package com.example.administrator.adapter;


import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.entity.MyhightscoreEntity;
import com.example.administrator.zhike.R;
import com.example.administrator.zhike.view.CircleImageView;

import java.util.ArrayList;

import base.BaseApplication;

public class RankingAdapter extends BaseQuickAdapter<MyhightscoreEntity.list, BaseViewHolder> {
    public RankingAdapter(@Nullable ArrayList<MyhightscoreEntity.list> list) {
        super(R.layout.item_recycler_ranking, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyhightscoreEntity.list item) {
        String name = item.getNickname();
        double rate = item.getMastery_rate();
        String thumb = item.getHeadimgurl();
        int Number = item.getNumber();
        String rateStr = String.format("%.2f", rate);
        if (!TextUtils.isEmpty(name)) {
            helper.setText(R.id.item_recycler_ranking_name, name);
        }

        helper.setText(R.id.item_recycler_ranking_rate, rateStr + "%");

        if (!TextUtils.isEmpty(thumb)) {
            CircleImageView header = helper.getView(R.id.item_recycler_ranking_header);
            Glide.with(BaseApplication.getContext()).load(thumb).into(header);
        }

        CircleImageView header = helper.getView(R.id.item_recycler_ranking_ranking);
        if (Number == 1) {

            header.setImageResource(R.drawable.ranking_yi);
            header.setVisibility(View.VISIBLE);
        } else if (Number == 2) {
            header.setImageResource(R.drawable.ranking_er);
            header.setVisibility(View.VISIBLE);
        } else if (Number == 3) {
            header.setImageResource(R.drawable.ranking_san);
            header.setVisibility(View.VISIBLE);
        } else {
            header.setVisibility(View.GONE);
        }
    }
}