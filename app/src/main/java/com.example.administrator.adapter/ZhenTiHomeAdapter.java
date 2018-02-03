package com.example.administrator.adapter;


import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.entity.HomeZhenTiEntity;
import com.example.administrator.zhike.R;

import java.util.ArrayList;

public class ZhenTiHomeAdapter extends BaseQuickAdapter<HomeZhenTiEntity.list, BaseViewHolder> {
    private OnItemClickLiter mOnItemClickLitener;

    public interface OnItemClickLiter {
        void onItemClick(int id, String status, int type);
    }

    public void setOnItemClickLitener(OnItemClickLiter mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public ZhenTiHomeAdapter(@Nullable ArrayList<HomeZhenTiEntity.list> data) {
        super(R.layout.item_recycler_home_zhenti, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final HomeZhenTiEntity.list item) {
        String title = item.getTitle();
        if (!TextUtils.isEmpty(title)) {
            helper.setText(R.id.item_recycler_home_zhenti_title, title);
        }



    }

}
