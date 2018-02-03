package com.example.administrator.adapter;


import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.entity.CorrectRateEntity;
import com.example.administrator.zhike.R;

import java.util.List;

import me.zhouzhuo.zzhorizontalprogressbar.ZzHorizontalProgressBar;

public class IntelligentAnalysisAdapter extends BaseQuickAdapter<CorrectRateEntity,BaseViewHolder>{

    public IntelligentAnalysisAdapter(@Nullable List<CorrectRateEntity> data) {
        super(R.layout.item_recycler_intelligent_analysis, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CorrectRateEntity item) {
        String title = item.getName();
        int num = item.getNum();
        if (!TextUtils.isEmpty(title)) {
            helper.setText(R.id.item_recycler_intelligent_analysis_title, title);
        }

        if (!TextUtils.isEmpty(title)) {
            helper.setText(R.id.item_recycler_intelligent_analysis_bfb, num+"%");
        }

        ZzHorizontalProgressBar bar = helper.getView(R.id.item_recycler_intelligent_analysis_progress);
        bar.setMax(100);
        bar.setProgress(num);



    }
}