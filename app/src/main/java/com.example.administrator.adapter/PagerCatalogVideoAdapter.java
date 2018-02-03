package com.example.administrator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.entity.VideoCatalogEntity;
import com.example.administrator.zhike.R;

import java.util.ArrayList;

public class PagerCatalogVideoAdapter extends RecyclerView.Adapter<PagerCatalogVideoAdapter.MyViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    private OnItemClickLiter mOnItemClickLitener;
    ArrayList<VideoCatalogEntity.list.catalog> list;

    public interface OnItemClickLiter {
        void onItemClick(String url);
    }

    public void setOnItemClickLitener(OnItemClickLiter mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public PagerCatalogVideoAdapter(Context context, ArrayList<VideoCatalogEntity.list.catalog> list) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.item_recycler_pager_catalog_video, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        VideoCatalogEntity.list.catalog entity = list.get(position);
        final String url = entity.getUrl() + "";
        holder.title.setText(entity.getTitle() + "");
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(url);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends ViewHolder {
        TextView title;

        MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.item_recycler_pager_catalog_video_title);
        }
    }
}