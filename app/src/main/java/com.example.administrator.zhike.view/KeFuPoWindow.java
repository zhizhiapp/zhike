package com.example.administrator.zhike.view;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.administrator.zhike.R;

public class KeFuPoWindow extends PopupWindow {

    private Button quxiao;
    private View mMenuView;
    private LinearLayout linearLayout;
    private Context context;

    public KeFuPoWindow(final Activity context, LinearLayout _linearLayout) {//, ImageView _textView
        super(context);
        this.linearLayout = _linearLayout;
        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMenuView = inflater.inflate(R.layout.pu_window_artificial, null);

        quxiao = (Button) mMenuView.findViewById(R.id.pu_window_kefu_qx);
        Button boda = (Button) mMenuView.findViewById(R.id.kefu_boda);
        // 取消按钮
        quxiao.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // 销毁弹出框
                dismiss();
            }
        });

        // 取消按钮
        boda.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // 销毁弹出框

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:110"));//第一个参数是id   第二个参数是uri
                context.startActivity(intent);

            }
        });

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
