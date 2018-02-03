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
import com.example.administrator.entity.VideoEntity;
import com.example.administrator.zhike.R;

import java.util.List;

import base.BaseApplication;

public class CurriculumAdapter extends BaseQuickAdapter<VideoEntity.VideoList, BaseViewHolder> {
    String status = "";
    private OnItemClickLiter mOnItemClickLitener;

    public interface OnItemClickLiter {
        void onItemClick(VideoEntity.VideoList entity);
    }

    public void setOnItemClickLitener(OnItemClickLiter mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    private OnItemClickLiterCollstatus mOnItemClickLitenerCollstatus;

    public interface OnItemClickLiterCollstatus {
        void onItemClick(int id, String status, int type);
    }

    public void setOnItemClickLitener(OnItemClickLiterCollstatus mOnItemClickLitener) {
        this.mOnItemClickLitenerCollstatus = mOnItemClickLitener;
    }

    private shoppingcartOnItemClickLiter shoppingcartOnItemClickLitener;

    public interface shoppingcartOnItemClickLiter {
        void onItemClick(int id, String status);
    }

    public void setOnItemClickLitener(shoppingcartOnItemClickLiter mOnItemClickLitener) {
        this.shoppingcartOnItemClickLitener = mOnItemClickLitener;
    }

    public CurriculumAdapter(@Nullable List<VideoEntity.VideoList> data) {
        super(R.layout.item_recycler_curriculum, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final VideoEntity.VideoList item) {
        String title = item.getTitle();
        helper.setText(R.id.item_recycler_curriculum_title, title);

        String thumb = item.getThumb();
        if (!TextUtils.isEmpty(thumb)) {
            ImageView header = helper.getView(R.id.item_recycler_curriculum_img);
            Glide.with(BaseApplication.getContext()).load(MyConstants.Service_URL + item.getThumb()).into(header);
        }
        if (mOnItemClickLitener != null) {
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(item);

                }
            });
        }


        final int collstatus = item.getCollstatus();
        Log.i("TAG",collstatus+"");
        ImageView collection = helper.getView(R.id.item_recycler_curriculum_collection);
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
                mOnItemClickLitenerCollstatus.onItemClick(item.getId(), status, 3);

            }
        });

        final int cartstatus = item.getCartstatus();
        final ImageView shoppingcart = helper.getView(R.id.item_recycler_curriculum_vehicle);//购物车
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