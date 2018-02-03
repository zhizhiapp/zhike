package com.example.administrator.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.entity.MyCollectionExerciseEntity;
import com.example.administrator.zhike.R;

import java.util.List;

public class CollectionExerciseAdapter extends BaseQuickAdapter<MyCollectionExerciseEntity.list, BaseViewHolder> {
    public CollectionExerciseAdapter( @Nullable List<MyCollectionExerciseEntity.list> data) {
        super(R.layout.item_pager_collection_exercise, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCollectionExerciseEntity.list item) {

        String title = item.getName();
        if (!TextUtils.isEmpty(title)) {
            helper.setText(R.id.item_pager_collection_exercise_title, title);
        }

    }
}
