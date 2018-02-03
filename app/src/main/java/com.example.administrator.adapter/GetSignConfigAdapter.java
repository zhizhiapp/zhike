package com.example.administrator.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.constants.MyConstants;
import com.example.administrator.entity.GetSignConfigEntity;
import com.example.administrator.zhike.R;

import java.util.List;

import base.BaseApplication;

public class GetSignConfigAdapter extends BaseQuickAdapter<GetSignConfigEntity.list, BaseViewHolder> {
    private OnItemClickLiter mOnItemClickLitener;

    public interface OnItemClickLiter {
        void onItemClick(int position);
    }

    public void setOnItemClickLitener(OnItemClickLiter mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    public GetSignConfigAdapter(@Nullable List<GetSignConfigEntity.list> data) {
        super(R.layout.item_recycler_pager_sign_rise, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GetSignConfigEntity.list item) {
        String name = item.getName();
        if (!TextUtils.isEmpty(name)) {
            helper.setText(R.id.item_recycler_pager_sign_rise_title, name);
        }
        int Num = item.getNumber();
        helper.setText(R.id.item_recycler_pager_sign_rise_num, Num+"");

        String contont = item.getDescription();
        if (!TextUtils.isEmpty(contont)) {
            helper.setText(R.id.item_recycler_pager_sign_rise_contont, contont);
        }

        ImageView imageView = helper.getView(R.id.item_recycler_pager_sign_rise_img);
        String thumb = item.getThumb();
        Log.i("TAG",thumb+"");
        if (!TextUtils.isEmpty(thumb)) {
            Glide.with(BaseApplication.getContext()).load(MyConstants.Service_URL + item.getThumb()).into(imageView);
        }


        if (mOnItemClickLitener!=null){
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = helper.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(pos);
                }
            });
        }



    }
}