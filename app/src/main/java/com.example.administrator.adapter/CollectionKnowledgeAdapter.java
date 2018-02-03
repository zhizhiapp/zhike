package com.example.administrator.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.entity.MyCollectionKnowledgeEntity;
import com.example.administrator.zhike.R;

import java.util.List;

public class CollectionKnowledgeAdapter extends BaseQuickAdapter<MyCollectionKnowledgeEntity.list, BaseViewHolder> {
    public CollectionKnowledgeAdapter( @Nullable List<MyCollectionKnowledgeEntity.list> data) {
        super(R.layout.item_pager_collection_knowledge, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCollectionKnowledgeEntity.list item) {
        String title = item.getName();
        if (!TextUtils.isEmpty(title)) {
            helper.setText(R.id.item_pager_collection_knowledge_title, title);
        }
    }
}