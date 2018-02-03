package com.example.administrator.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.zhike.R;

import java.util.List;

public class HistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private OnItemClickLiter mOnItemClickLitener;

    public interface OnItemClickLiter {
        void onItemClick(String contont);
    }

    public void setOnItemClickLitener(OnItemClickLiter mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public HistoryAdapter(@Nullable List<String> data) {
        super(R.layout.item_recycler_history, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final String item) {
        if (!TextUtils.isEmpty(item)) {
            helper.setText(R.id.item_recycler_history, item);
        }


        if (mOnItemClickLitener != null) {
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(item);
                }
            });
        }


    }
}