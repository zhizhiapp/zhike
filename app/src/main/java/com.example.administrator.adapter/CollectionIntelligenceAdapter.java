package com.example.administrator.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.entity.MyCollectionIntelligenceEntity;
import com.example.administrator.zhike.R;

import java.util.List;

public class CollectionIntelligenceAdapter extends BaseQuickAdapter<MyCollectionIntelligenceEntity.list, BaseViewHolder> {
    public CollectionIntelligenceAdapter( @Nullable List<MyCollectionIntelligenceEntity.list> data) {
        super(R.layout.item_pager_collection_intelligence, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCollectionIntelligenceEntity.list item) {
        String title = item.getTitle();
        if (!TextUtils.isEmpty(title)) {
            helper.setText(R.id.item_pager_collection_intelligence_title, title);
        }
    }
}