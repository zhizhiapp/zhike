package com.example.administrator.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.entity.ExercisesTestItemEntity;
import com.example.administrator.zhike.R;

import java.util.List;

public class ExercisesTestItemAdapter extends BaseQuickAdapter<ExercisesTestItemEntity.list, BaseViewHolder> {


    private OnItemClickLiter mOnItemClickLitener;
    public interface OnItemClickLiter {
        void onItemClick(int id);
    }

    public void setOnItemClickLitener(OnItemClickLiter mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public ExercisesTestItemAdapter(@Nullable List<ExercisesTestItemEntity.list> data) {
        super(R.layout.item_recycler_exercise, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ExercisesTestItemEntity.list item) {


        Log.i("TAG","xxxx=========="+item.getId());

        String s = (helper.getAdapterPosition() + 1) + " . " + item.getName();
        helper.setText(R.id.item_recycler_exercise_title, s);


        if (mOnItemClickLitener != null) {
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(item.getId());
                }
            });
        }


    }
}
