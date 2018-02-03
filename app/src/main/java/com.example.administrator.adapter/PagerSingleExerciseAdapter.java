package com.example.administrator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.zhike.R;

public class PagerSingleExerciseAdapter extends RecyclerView.Adapter<PagerSingleExerciseAdapter.MyViewHolder> {

    private LayoutInflater mInflater;
    private Context context;

    boolean b = true;

    public PagerSingleExerciseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    private OnItemClickLiter mOnItemClickLitener;
    public interface OnItemClickLiter{
        void onItemClick(View view, int position,TextView nun);
    }
    public void setOnItemClickLitener(OnItemClickLiter mOnItemClickLitener){
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.item_recycler_pager_single_exercise, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {






            if (mOnItemClickLitener != null){
                holder.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        if (b){
                        int pos = holder.getLayoutPosition();
                        b = false;
                        mOnItemClickLitener.onItemClick(holder.itemView, pos,holder.nun);
                        }
                    }
                });

        }


    }


    @Override
    public int getItemCount() {
        return 5;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nun;
        TextView answer;

        MyViewHolder(View view) {
            super(view);
            nun = (TextView) view.findViewById(R.id.item_recycler_pager_single_exercise_nun);
            answer = (TextView) view.findViewById(R.id.item_recycler_pager_single_exercise_answer);
        }
    }



}