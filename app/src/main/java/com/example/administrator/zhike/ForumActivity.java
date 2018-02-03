package com.example.administrator.zhike;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.utils.PieData;
import com.example.administrator.utils.PieUtils;
import com.example.administrator.zhike.view.MenuChart;

import java.util.ArrayList;

public class ForumActivity extends Fragment {

    private ArrayList<PieData> mPieDatas = new ArrayList<>();
    // 颜色表
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_forum, container, false);
        button = (Button) view.findViewById(R.id.btn_click);
        initData();//设置数据
        final MenuChart menuChart = (MenuChart) view.findViewById(R.id.menuChart);
        menuChart.setPieData(mPieDatas);//添加数据
        menuChart.setStartAngle(180);  //设置起始角度
        menuChart.setPieShowAngle(180);//设置总共角度
        menuChart.setCenterBitmap(R.mipmap.ic_launcher, PieUtils.dp2px(getActivity(), 60), PieUtils.dp2px(getActivity(), 60));
        menuChart.setTouchCenterTextSize(30);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuChart.getVisibility() == View.GONE) {
                    menuChart.showStartAnim();
                } else {
                    menuChart.showEndAnim();
                }
            }
        });
        return view;
    }

    //数据
    private void initData() {
        PieData pieData = new PieData();
        pieData.setName("1");//下一层
        pieData.setWeight(1);
        pieData.setName_label("测评");//外面一层
        pieData.setLabelColor(0xffff0000);
//        pieData.setDrawableId(R.mipmap.ic_launcher);
        mPieDatas.add(pieData);

        PieData pieData2 = new PieData();
        pieData2.setName("2");
        pieData2.setWeight(1);
        pieData2.setName_label("分析");
//        pieData2.setDrawableId(R.mipmap.ic_launcher);
        pieData2.setLabelColor(0xffff0000);
        mPieDatas.add(pieData2);

        PieData pieData5 = new PieData();
        pieData5.setName("3");
        pieData5.setWeight(1);
        pieData5.setName_label("方案");
//        pieData5.setDrawableId(R.mipmap.ic_launcher);
        pieData5.setLabelColor(0xffff0000);
        mPieDatas.add(pieData5);

        PieData pieData4 = new PieData();
        pieData4.setName("4");
        pieData4.setWeight(1);
        pieData4.setName_label("试听");
//        pieData4.setDrawableId(R.mipmap.ic_launcher);
        pieData4.setLabelColor(0xffff0000);
        mPieDatas.add(pieData4);
    }
}








