package com.example.administrator.zhike;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.adapter.MyPagerAdapter;
import com.example.administrator.adapter.PagerSingleExerciseAdapter;
import com.example.administrator.utils.FullyLinearLayoutManager;
import com.example.administrator.zhike.view.LinearLayoutItemDecoration;
import com.example.administrator.zhike.view.SingleExercisePow;

import java.util.ArrayList;
import java.util.List;

public class SingleExerciseActivity extends BaseActivity {//单题练习


    ViewPager sPager;
    ImageView sReturn;
    List<View> list;
    LinearLayout pagerSingleExerciseMore;


    private LinearLayout layoutShade;
    private SingleExercisePow menuWindow;
    private View menuWindowView;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.single_exercise_return:
                finish();
                return;
            case R.id.single_exercise_more://弹框
                Popup();
                menuWindow.showAsDropDown(findViewById(R.id._single_exercise), Gravity.BOTTOM, 0, 0);
                layoutShade.setVisibility(View.VISIBLE);
                return;
        }
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_single_exercise;
    }

    @Override
    public void initView(View view) {
        sPager = $(R.id.single_exercise_pager);
        sReturn = $(R.id.single_exercise_return);
        pagerSingleExerciseMore = $(R.id.single_exercise_more);
        layoutShade = $(R.id.single_exercise_layout);


    }

    @Override
    public void setListener() {
        sReturn.setOnClickListener(this);
        pagerSingleExerciseMore.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void doBusiness(Context mContext) {
        list = new ArrayList<>();
        ArrayList<String> str = new ArrayList<>();
        for (int i = 0; i < 96; i++) {
            str.add("我好帅");
            list.add(getiew(SingleExerciseActivity.this, str, i));
        }
        MyPagerAdapter adapte = new MyPagerAdapter(list);
        sPager.setAdapter(adapte);
        sPager.setCurrentItem(0);
        sPager.setOnPageChangeListener(new MyOnPageChangeListener());


    }

    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {//当前页数
            switch (arg0) {
                case 0:
                    break;
                case 1:

                    break;
                case 2:
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }



    static boolean b;
    //给viewpager添加布局
    public static View getiew(final Context context, ArrayList<String> str, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.pager_single_exercise, null);
        TextView textView = (TextView) view.findViewById(R.id.pager_single_exercise_title);
        final RecyclerView recy = (RecyclerView) view.findViewById(R.id.pager_single_exercise_title_recycler);
        ScrollView scroll = (ScrollView) view.findViewById(R.id.pager_single_exercise_scroll);
        final LinearLayout layout = (LinearLayout) view.findViewById(R.id.pager_single_exercise_layout);

        recy.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动
        scroll.setVerticalScrollBarEnabled(false);
        textView.setText((i + 1) + str.get(i));
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recy.setLayoutManager(manager);
        recy.addItemDecoration(new LinearLayoutItemDecoration(LinearLayoutManager.VERTICAL, context.getResources().getDimensionPixelSize(R.dimen.x22)));//设置颜色  和 分割线
        final PagerSingleExerciseAdapter mIntelliAdapter = new PagerSingleExerciseAdapter(context);
        recy.setAdapter(mIntelliAdapter);
        recy.setItemAnimator(new DefaultItemAnimator());
        mIntelliAdapter.setOnItemClickLitener(new PagerSingleExerciseAdapter.OnItemClickLiter() {
            @Override
            public void onItemClick(View view, int position, TextView nun) {
                layout.setVisibility(View.VISIBLE);
                if (b) {
                    nun.setBackgroundResource(R.drawable.intelligenttest_yes_problem);
                } else {
                    nun.setBackgroundResource(R.drawable.intelligenttest_x_problem);
                    nun.setTextColor(context.getResources().getColor(R.color.hongS));
                }
            }
        });
        return view;
    }


    //弹窗
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void Popup() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        menuWindow = new SingleExercisePow(SingleExerciseActivity.this, layoutShade);// 实例化SelectPicPopupWindow
        menuWindowView = menuWindow.getContentView();//拿到布局
        LinearLayout layout = (LinearLayout) menuWindowView.findViewById(R.id.toast_single_exercise_layout);

        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        layout.measure(w, h);
        int hhh = layout.getMeasuredHeight();
        menuWindow.setWidth(width);// 设置菜单的宽  (int) (d.getWidth() * 0.8)
        menuWindow.setHeight(hhh);// 设置菜单的高  200
    }

}
