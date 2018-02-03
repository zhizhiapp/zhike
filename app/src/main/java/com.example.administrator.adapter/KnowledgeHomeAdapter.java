package com.example.administrator.adapter;


import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.entity.HomeKnowledgeEntity;
import com.example.administrator.zhike.R;

import java.util.List;

public class KnowledgeHomeAdapter extends BaseQuickAdapter<HomeKnowledgeEntity.list, BaseViewHolder> {
    private OnItemClickLiter mOnItemClickLitener;

    public interface OnItemClickLiter {
        void onItemClick(int id, String status, int type);
    }

    public void setOnItemClickLitener(OnItemClickLiter mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    public KnowledgeHomeAdapter(@Nullable List<HomeKnowledgeEntity.list> data) {
        super(R.layout.item_recycler_home_knowledge, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final HomeKnowledgeEntity.list item) {
        String description = item.getDescription();
        if (!TextUtils.isEmpty(description)) {
            helper.setText(R.id.item_recycler_home_knowledge_title, description);
        }


    }
}
