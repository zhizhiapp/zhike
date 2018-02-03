package com.example.administrator.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.constants.MyConstants;
import com.example.administrator.entity.MyCollectionMicroClassEntity;
import com.example.administrator.zhike.R;

import java.util.List;

import base.BaseApplication;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class CollectionMicroClassAdapter extends BaseQuickAdapter<MyCollectionMicroClassEntity.list, BaseViewHolder> {
    int position = 0;

    private OnItemClickLiter mOnItemClickLitener;

    public interface OnItemClickLiter {
        void onItemClick(int position);
    }

    public void setOnItemClickLitener(OnItemClickLiter mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    private PlayerOnItemClickLiter mPlayerOnItemClickLitener;

    public interface PlayerOnItemClickLiter {
        void onItemClick(JCVideoPlayerStandard playerStandard);
    }

    public void setOnItemClickLitener(PlayerOnItemClickLiter mOnItemClickLitener) {
        this.mPlayerOnItemClickLitener = mOnItemClickLitener;
    }


    public CollectionMicroClassAdapter(@Nullable List<MyCollectionMicroClassEntity.list> data) {
        super(R.layout.item_pager_collection_microclass, data);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    protected void convert(final BaseViewHolder helper, MyCollectionMicroClassEntity.list item) {
        String title = item.getTitle();
        if (!TextUtils.isEmpty(title)) {
            helper.setText(R.id.item_pager_collection_microclass_title, title);
        }

        LinearLayout layout = helper.getView(R.id.item_pager_collection_microclass_layout);
        if (mOnItemClickLitener != null) {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = helper.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(pos);
                }
            });
        }


        final JCVideoPlayerStandard playerStandard = helper.getView(R.id.item_pager_collection_microclass_video);
        if (position == helper.getLayoutPosition()) {
            playerStandard.setVisibility(View.VISIBLE);
            boolean setUp = playerStandard.setUp(item.getUrl(), JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
            if (setUp) {
                Glide.with(BaseApplication.getContext()).load(MyConstants.Service_URL + item.getThumb()).into(playerStandard.thumbImageView);
            }
        } else {
            playerStandard.setVisibility(View.GONE);
        }

        //做到这
        if (mPlayerOnItemClickLitener != null) {
            playerStandard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPlayerOnItemClickLitener.onItemClick(playerStandard);
                }
            });
        }

          //长按删除
//        helper.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                return false;
//            }
//        });


    }


}
