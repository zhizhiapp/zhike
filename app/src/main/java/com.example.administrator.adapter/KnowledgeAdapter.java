package com.example.administrator.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.entity.KnowledgeListEntity;
import com.example.administrator.zhike.R;

import java.util.List;

import me.zhouzhuo.zzhorizontalprogressbar.ZzHorizontalProgressBar;

public class KnowledgeAdapter extends BaseQuickAdapter<KnowledgeListEntity.list, BaseViewHolder> {
    String status = "";
    int ponun;
    private OnItemClickLiter mOnItemClickLitener;
    public interface OnItemClickLiter {
        void onItemClick(int position);
    }
    public void setOnItemClickLitener(OnItemClickLiter mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }




    private OnItemClickLiterCollection mOnItemClickLitenerCollection;
    public interface OnItemClickLiterCollection {
        void onItemClick(int id, String status, int trpe);
    }
    public void setOnItemClickLiterCollection(OnItemClickLiterCollection mOnItemClickLitener) {
        this.mOnItemClickLitenerCollection = mOnItemClickLitener;
    }


    public KnowledgeAdapter(@Nullable List<KnowledgeListEntity.list> data) {
        super(R.layout.item_recycler_knowledge, data);
    }

    public void setPosition(int ponun) {
        this.ponun = ponun;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final KnowledgeListEntity.list item) {

        String title = item.getName();
        if (!TextUtils.isEmpty(title)) {
            helper.setText(R.id.item_recycler_knowledge_title, title);
        }
        helper.setText(R.id.item_recycler_knowledge_nun, (helper.getAdapterPosition() + 1) + ".");
        helper.setText(R.id.item_recycler_knowledge_contont, item.getDescription() + "");
        ZzHorizontalProgressBar bar = helper.getView(R.id.item_recycler_knowledge_progress);
        bar.setMax(100);
        bar.setProgress(item.getKnownum());

        final LinearLayout layout = helper.getView(R.id.item_recycler_knowledge_layout);
        final LinearLayout itemLayout = helper.getView(R.id.item_recycler_knowledge_item_layout);
        //        ImageView img = helper.getView(R.id.item_recycler_knowledge_img);
        itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickLitener.onItemClick(helper.getAdapterPosition());
            }
        });


        if (ponun == helper.getAdapterPosition()) {
            layout.setVisibility(View.VISIBLE);
        } else {
            layout.setVisibility(View.GONE);
        }

        final int collstatus = item.getCollstatus();
        final ImageView collection = helper.getView(R.id.item_recycler_knowledge_collection);//收藏
        if (collstatus == 0) {
            collection.setImageResource(R.drawable.ic_cancel_collection);
        } else if (collstatus == 1) {
            collection.setImageResource(R.drawable.home_collection);
        }


        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG","xxxx============"+collstatus);

                if (collstatus == 0) {
                    status = "true";
                } else if (collstatus == 1) {
                    status = "false";
                }
                mOnItemClickLitenerCollection.onItemClick(item.getId(), status, 4);

            }
        });


    }
}