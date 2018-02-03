package com.example.administrator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.entity.SubjectEntity;
import com.example.administrator.zhike.R;

import java.util.ArrayList;

public class ListSubjectAdapter extends BaseAdapter {
    ArrayList<SubjectEntity.listEntity> list;
    Context context;

    public ListSubjectAdapter(Context context, ArrayList<SubjectEntity.listEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hale hale;
        if (null == convertView) {
            hale = new Hale();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list, null);
            hale.textView = (TextView) convertView.findViewById(R.id.item_list_text);
            convertView.setTag(hale);
        } else {
            hale = (Hale) convertView.getTag();
        }

        SubjectEntity.listEntity entity = list.get(position);
        hale.textView.setText(entity.getName());
        return convertView;
    }

    class Hale {
        TextView textView;
    }
}