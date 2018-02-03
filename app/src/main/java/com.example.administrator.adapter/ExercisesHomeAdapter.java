package com.example.administrator.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.entity.HomeExercisesEntity;
import com.example.administrator.zhike.R;

import java.util.List;

public class ExercisesHomeAdapter extends BaseQuickAdapter<HomeExercisesEntity.list, BaseViewHolder> {
    private OnItemClickLiter mOnItemClickLitener;

    public interface OnItemClickLiter {
        void onItemClick(int id);
    }

    public void setOnItemClickLitener(OnItemClickLiter mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    public ExercisesHomeAdapter(@Nullable List<HomeExercisesEntity.list> data) {
        super(R.layout.item_recycler_home_exercises, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final HomeExercisesEntity.list item) {
        String name = item.getName();
        if (!TextUtils.isEmpty(name)) {
            helper.setText(R.id.item_recycler_home_exercises_title, name);
        }



    }

}
