package com.example.administrator.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.entity.SoluListEntity;
import com.example.administrator.zhike.R;

import java.util.List;

public class WisdomSchemeLinearAdapter extends BaseQuickAdapter<SoluListEntity.list, BaseViewHolder> {

    public WisdomSchemeLinearAdapter(@Nullable List<SoluListEntity.list> data) {
        super(R.layout.item_recycler_wisdom_scheme_linear, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SoluListEntity.list item) {
        String title = item.getName();
        if (!TextUtils.isEmpty(title)) {
            helper.setText(R.id.item_recycler_wisdom_scheme_linear_title, title);
        }

        helper.setText(R.id.item_recycler_wisdom_scheme_linear_nun, (helper.getAdapterPosition() + 1) + ".");//

        final LinearLayout layout = helper.getView(R.id.item_recycler_wisdom_scheme_linear_layout);
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (layout.getVisibility() == View.VISIBLE) {
                    layout.setVisibility(View.GONE);
                } else {
                    layout.setVisibility(View.VISIBLE);
                }
            }
        });


    }


}
