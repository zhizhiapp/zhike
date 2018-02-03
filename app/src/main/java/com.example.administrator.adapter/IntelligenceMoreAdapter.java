package com.example.administrator.adapter;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.constants.MyConstants;
import com.example.administrator.entity.IntelligenceMoreEntity;
import com.example.administrator.zhike.IntelligenceActivity;
import com.example.administrator.zhike.R;

import java.util.List;

import base.BaseApplication;

public class IntelligenceMoreAdapter extends BaseQuickAdapter<IntelligenceMoreEntity.list, BaseViewHolder> {
    public IntelligenceMoreAdapter(@Nullable List<IntelligenceMoreEntity.list> data) {
        super(R.layout.item_recycler_home_intelligence, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final IntelligenceMoreEntity.list item) {
        String title = item.getTitle();
        String thumb = item.getThumb();
        String description = item.getDescription();
        if (!TextUtils.isEmpty(title)) {
            helper.setText(R.id.item_recycler_home_intelligence_title, title);
        }

        Log.i("TAG","==="+thumb);
        if (!TextUtils.isEmpty(thumb)) {
            String url = MyConstants.Service_URL + thumb;
            ImageView view = helper.getView(R.id.item_recycler_home_intelligence_logo);
            Glide.with(BaseApplication.getContext()).load(url).into(view);
        }
        if (!TextUtils.isEmpty(description)) {
            helper.setText(R.id.item_recycler_home_intelligence_content, description);
        }


        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(BaseApplication.getContext(),IntelligenceActivity.class);
                intent.putExtra("id",item.getId());
                intent.putExtra("url",item.getThumb());
                BaseApplication.getContext().startActivity(intent);
            }
        });

    }
}
