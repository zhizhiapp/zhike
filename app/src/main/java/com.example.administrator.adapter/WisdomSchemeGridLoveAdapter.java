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
import com.example.administrator.entity.SoluVideoEntity;
import com.example.administrator.zhike.R;

import java.util.List;

import base.BaseApplication;

public class WisdomSchemeGridLoveAdapter extends BaseQuickAdapter<SoluVideoEntity.list, BaseViewHolder> {
    String status;
    private OnItemClickLiter mOnItemClickLitener;

    public interface OnItemClickLiter {
        void onItemClick(int id, String status, int type);
    }

    public void setOnItemClickLitener(OnItemClickLiter mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public WisdomSchemeGridLoveAdapter(@Nullable List<SoluVideoEntity.list> data) {
        super(R.layout.item_recycler_wisdom_scheme_grid_love, data);
    }



    private shoppingcartOnItemClickLiter shoppingcartOnItemClickLitener;

    public interface shoppingcartOnItemClickLiter {
        void onItemClick(int id, String status);
    }

    public void setOnItemClickLitener(shoppingcartOnItemClickLiter mOnItemClickLitener) {
        this.shoppingcartOnItemClickLitener = mOnItemClickLitener;
    }





    @Override
    protected void convert(BaseViewHolder helper, final SoluVideoEntity.list item) {
        String title = item.getTitle();
        helper.setText(R.id.item_recycler_wisdom_scheme_grid_love_title, title);


        String thumb = item.getThumb();
        if (!TextUtils.isEmpty(thumb)) {
            ImageView header = helper.getView(R.id.item_recycler_wisdom_scheme_grid_love_logo);
            Glide.with(BaseApplication.getContext()).load(MyConstants.Service_URL + thumb).into(header);
        }

        final int collstatus = item.getCollstatus();
        final ImageView collection = helper.getView(R.id.item_recycler_wisdom_scheme_grid_love_collection);//收藏
        if (collstatus == 0) {
            collection.setImageResource(R.drawable.ic_cancel_collection);
        } else if (collstatus == 1) {
            collection.setImageResource(R.drawable.home_collection);
        }

        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collstatus == 0) {
                    status = "true";
                } else if (collstatus == 1) {
                    status = "false";
                }
                mOnItemClickLitener.onItemClick(item.getId(), status, 3);

            }
        });






        Log.i("TAG","status==="+item.getCartstatus());
        final int cartstatus = item.getCartstatus();
        final ImageView shoppingcart = helper.getView(R.id.item_recycler_wisdom_scheme_grid_love_shoppingcart);//购物车
        if (cartstatus == 0) {
            shoppingcart.setImageResource(R.drawable.more_curriculum_vehicle_no);
        } else if (cartstatus == 1) {
            shoppingcart.setImageResource(R.drawable.more_curriculum_vehicle_yes);
        }

        shoppingcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartstatus == 0) {
                    status = "true";
                } else if (cartstatus == 1) {
                    status = "false";
                }
                shoppingcartOnItemClickLitener.onItemClick(item.getId(), status);

            }
        });



    }


}