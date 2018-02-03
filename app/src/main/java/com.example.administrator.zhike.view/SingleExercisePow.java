package com.example.administrator.zhike.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.administrator.adapter.SingleExercisePowAdapter;
import com.example.administrator.utils.FullyGridLayoutManager;
import com.example.administrator.zhike.R;

public class SingleExercisePow extends PopupWindow {

    private View mMenuView;
    private LinearLayout linearLayout;
    private LinearLayout pow;
    private Context context;
    RecyclerView recycler;

    public SingleExercisePow(final Context context, LinearLayout _linearLayout) {//, ImageView _textView
        super(context);
        this.linearLayout = _linearLayout;
        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.toast_single_exercise, null);
        pow = (LinearLayout) mMenuView.findViewById(R.id.toast_single_exercise_pow);
        pow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        recycler = (RecyclerView) mMenuView.findViewById(R.id.toast_single_exercise_recycler);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(context, 7);
        recycler.setLayoutManager(manager);
        recycler.addItemDecoration(new GridSpacingItemDecoration(7, context.getResources().getDimensionPixelSize(R.dimen.x30), false));//设置颜色  和 分割线  屏幕适配
        final SingleExercisePowAdapter mIntelliAdapter = new SingleExercisePowAdapter(context);
        recycler.setAdapter(mIntelliAdapter);
        recycler.setItemAnimator(new DefaultItemAnimator());


        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);

        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        // this.setAnimationStyle(R.style.AnimBottom);
        this.setAnimationStyle(R.style.main_menu);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xffe1e1e1);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }

    @Override
    public void dismiss() {
        linearLayout.setVisibility(View.GONE);
        super.dismiss();
    }


}
