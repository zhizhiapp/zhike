package com.example.administrator.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.entity.MyCollectionZhenTiEntity;
import com.example.administrator.zhike.R;

import java.util.List;

public class CollectionZhenTiAdapter extends BaseQuickAdapter<MyCollectionZhenTiEntity.list, BaseViewHolder> {
    public CollectionZhenTiAdapter( @Nullable List<MyCollectionZhenTiEntity.list> data) {
        super(R.layout.item_pager_collection_zhenti, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCollectionZhenTiEntity.list item) {

        String title = item.getTitle();
        if (!TextUtils.isEmpty(title)) {
            helper.setText(R.id.item_pager_collection_zhenti_title, title);
        }

    }
}
