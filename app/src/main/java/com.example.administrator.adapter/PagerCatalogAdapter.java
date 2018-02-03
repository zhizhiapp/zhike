package com.example.administrator.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.entity.VideoCatalogEntity;
import com.example.administrator.utils.FullyLinearLayoutManager;
import com.example.administrator.zhike.R;

import java.util.ArrayList;

public class PagerCatalogAdapter extends RecyclerView.Adapter<PagerCatalogAdapter.MyViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    ArrayList<VideoCatalogEntity.list> list;
    int ponun = 0;
    private OnItemClickLiter mOnItemClickLitener;
    public interface OnItemClickLiter {
        void onItemClick(int position);
    }
    public void setOnItemClickLitener(OnItemClickLiter mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }



    private ItemClick mItemClick;
    public interface ItemClick {
        void onItemClick(String url);
    }
    public void setItemClick(ItemClick mItemClick) {
        this.mItemClick = mItemClick;
    }



    public PagerCatalogAdapter(Context context, ArrayList<VideoCatalogEntity.list> list) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    public void setPosition(int ponun) {
        this.ponun = ponun;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.item_recycler_pager_catalog, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        VideoCatalogEntity.list entity= list.get(position);
        holder.title.setText(entity.getName() + "");

        final PagerCatalogVideoAdapter adapter = new PagerCatalogVideoAdapter(context,entity.getCatalog());

        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(pos);
                }
            });
        }

        if (ponun == position) {
            FullyLinearLayoutManager manger = new FullyLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            holder.recycler.setLayoutManager(manger);
            holder.recycler.setAdapter(adapter);
            holder.recycler.setItemAnimator(new DefaultItemAnimator());
            holder.img.setImageResource(R.drawable.pager_synopsis_shut);
            holder.recycler.setVisibility(View.VISIBLE);
            holder.itemView.setClickable(false);
            adapter.setOnItemClickLitener(new PagerCatalogVideoAdapter.OnItemClickLiter() {
                @Override
                public void onItemClick(String url) {
                    Log.i("TAG", "============" + url);
                    mItemClick.onItemClick(url);
                }
            });
        } else {
            holder.recycler.setVisibility(View.GONE);
            holder.img.setImageResource(R.drawable.pager_synopsis_open);
            holder.itemView.setClickable(true);
        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends ViewHolder {
        TextView title;
        LinearLayout layout;
        ImageView img;
        RecyclerView recycler;

        MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.item_recycler_pager_catalog_title);
            layout = (LinearLayout) view.findViewById(R.id.item_recycler_pager_catalog_layout);
            img = (ImageView) view.findViewById(R.id.item_recycler_pager_catalog_img);
            recycler = (RecyclerView) view.findViewById(R.id.item_recycler_pager_catalog_recycler);
        }
    }
}