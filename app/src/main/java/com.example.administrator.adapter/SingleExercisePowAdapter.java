package com.example.administrator.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.zhike.R;

public class SingleExercisePowAdapter extends RecyclerView.Adapter<SingleExercisePowAdapter.MyViewHolder> {
    private LayoutInflater mInflater;
    private Context context;

    public SingleExercisePowAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.item_recycler_single_exercise_pow, parent, false));
        return holder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 95;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.item_recycler_single_exercise_pow_title);
        }
    }
}