package com.example.administrator.adapter;


import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.entity.IntelligentTestEntity;
import com.example.administrator.zhike.R;

import java.util.List;

public class AnswerAdapter extends BaseQuickAdapter<IntelligentTestEntity, BaseViewHolder> {

    public AnswerAdapter(@Nullable List<IntelligentTestEntity> data) {
        super(R.layout.item_recycler_answer, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IntelligentTestEntity item) {
        int select = item.getSelect();
        helper.setBackgroundRes(R.id.FrameLayout, select == -1 ? R.drawable.ic_toast_un : R.drawable.ic_option_on);
        helper.setText(R.id.item_recycler_answer_nun, String.valueOf(helper.getAdapterPosition() + 1));
        helper.addOnClickListener(R.id.FrameLayout);
    }
}