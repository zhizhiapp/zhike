package com.example.administrator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.zhike.R;

public class PagerRelevantVideoAdapter extends RecyclerView.Adapter<PagerRelevantVideoAdapter.MyViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    private OnItemClickLiter mOnItemClickLitener;

    public interface OnItemClickLiter {
        void onItemClick(String url);
    }

    public void setOnItemClickLitener(OnItemClickLiter mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public PagerRelevantVideoAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.item_recycler_pager_relevant_video, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final String url = "http://7xrlrd.com1.z0.glb.clouddn.com/%E8%89%BA%E6%83%B3%E5%A4%A9%E5%BC%80%E5%89%AA%E8%BE%91%E5%90%8E.mp4";
        holder.title.setText("视频名字");
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(url);
                }
            });
        }
        Log.i("TAG","222");
    }

    @Override
    public int getItemCount() {
        return 5;
    }


    class MyViewHolder extends ViewHolder {
        TextView title;

        MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.item_recycler_pager_relevant_video_title);
        }
    }
}