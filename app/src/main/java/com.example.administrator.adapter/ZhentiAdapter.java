package com.example.administrator.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.entity.ZhenTiListEntity;
import com.example.administrator.zhike.R;

import java.util.List;

//节
public class ZhentiAdapter extends BaseQuickAdapter<ZhenTiListEntity.list, BaseViewHolder> {

    String status = "";
    private OnItemClickLiter mOnItemClickLitener;

    public interface OnItemClickLiter {
        void onItemClick(int id);
    }

    public void setOnItemClickLitener(OnItemClickLiter mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    private OnItemClickLiterCollection mOnItemClickLitenerCollection;

    public interface OnItemClickLiterCollection {
        void onItemClick(int id, String status, int type);
    }

    public void setOnItemClickLitenerCollection(OnItemClickLiterCollection mOnItemClickLitener) {
        this.mOnItemClickLitenerCollection = mOnItemClickLitener;
    }


    public ZhentiAdapter(@Nullable List<ZhenTiListEntity.list> data) {
        super(R.layout.item_recycler_zhenti, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ZhenTiListEntity.list item) {
        String title = item.getTitle();
        if (!TextUtils.isEmpty(title)) {
            helper.setText(R.id.item_recycler_zhenti_title, title);
        }

        final int collstatus = item.getCollstatus();
        final ImageView collection = helper.getView(R.id.item_recycler_zhenti_collection);//收藏
        Log.i("TAG", collstatus + "=====");
        if (collstatus == 0) {
            collection.setImageResource(R.drawable.ic_cancel_collection);
        } else if (collstatus == 1) {
            collection.setImageResource(R.drawable.home_collection);
        }


        if (mOnItemClickLitener != null) {
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(item.getId());
                }
            });
        }


        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collstatus == 0) {
                    status = "true";
                } else if (collstatus == 1) {
                    status = "false";
                }
                mOnItemClickLitenerCollection.onItemClick(item.getId(), status, 7);

            }
        });

    }
}