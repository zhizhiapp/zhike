package com.example.administrator.adapter;


import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.entity.MyWrongTitleRecordsEntity;
import com.example.administrator.zhike.R;

import java.util.List;

public class MyWrongTitleAdapter extends BaseQuickAdapter<MyWrongTitleRecordsEntity.list.chapterList, BaseViewHolder> {


    private OnItemClickLiter mOnItemClickLitener;

    public interface OnItemClickLiter {
        void onItemClick(int id, String status, int type);
    }

    public void setOnItemClickLitener(OnItemClickLiter mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public MyWrongTitleAdapter(@Nullable List<MyWrongTitleRecordsEntity.list.chapterList> data) {
        super(R.layout.item_recycler_my_wrong_title, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyWrongTitleRecordsEntity.list.chapterList item) {

        String title = item.getName();
        int num = item.getError_num();
        if (!TextUtils.isEmpty(title)) {
            helper.setText(R.id.item_recycler_my_wrong_title_title, title);
        }

        helper.setText(R.id.item_recycler_my_wrong_title_num, num + "");


    }
}
