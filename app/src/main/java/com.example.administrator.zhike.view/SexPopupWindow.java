package com.example.administrator.zhike.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.administrator.zhike.R;

public class SexPopupWindow extends PopupWindow {

	private View mMenuView;
	private LinearLayout linearLayout;
	private Context context;
	public SexPopupWindow(final Activity context,LinearLayout _linearLayout) {//, ImageView _textView
		super(context);
		this.linearLayout = _linearLayout;
		this.context = context;

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mMenuView = inflater.inflate(R.layout.sex_menu_alert, null);

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
