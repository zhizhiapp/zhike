package com.example.administrator.zhike;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyLodingMicroClassActivity extends Fragment {//微课的下载
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_loding_micro_class, container, false);
//        recyclerView = (RecyclerView) view.findViewById(R.id.my_loding_micro_class_recycler);
//        recyclerView.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动
//        MyLodingMicroClass();





        return view;
    }

//    public void MyLodingMicroClass() {
//        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.addItemDecoration(new LinearLayoutItemDecoration(LinearLayoutManager.VERTICAL, getResources().getDimensionPixelSize(R.dimen.x20)));//设置颜色  和 分割线
////        mExercisesAdapter = new ExercisesHomeAdapter(getActivity(), eList);
////        mExercRecycler.setAdapter(mExercisesAdapter);
////        mExercRecycler.setItemAnimator(new DefaultItemAnimator());
//    }







}
