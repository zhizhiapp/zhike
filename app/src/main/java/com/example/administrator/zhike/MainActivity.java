package com.example.administrator.zhike;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends BaseActivity {
    private FragmentManager fm;
    private FragmentTransaction transaction;
    private LinearLayout mHomeLayout, mCurriculumLayout, mForumLayout, mMyLayout, mTeacherLayout;
    private ImageView mHomeImg, mCurriculumImg, mForumImg, mMyImg;
    private TextView mHomeText, mCurriculumText, mForumText, mMyText, mTeacherText;
    HomePageActivity pageActivity;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.main_homepage_layout://首页

                Fment(R.id.item_framen, new HomePageActivity(), "ONE");
                setImg(mHomeImg, R.drawable.main_home_yes);
                setImg(mCurriculumImg, R.drawable.main_curriculum_no);
                setImg(mForumImg, R.drawable.main_forum_no);
                setImg(mMyImg, R.drawable.main_my_no);

                setColoor(mHomeText, R.color.danlvS);
                setColoor(mCurriculumText, R.color.huiS);
                setColoor(mForumText, R.color.huiS);
                setColoor(mMyText, R.color.huiS);
                setColoor(mTeacherText, R.color.huiS);
                mHomeLayout.setClickable(false);
                mCurriculumLayout.setClickable(true);
                mForumLayout.setClickable(true);
                mMyLayout.setClickable(true);
                return;
            case R.id.main_curriculum_layout://课程
                Fment(R.id.item_framen, new CurriculumActivity(), "Two");
                setImg(mHomeImg, R.drawable.main_home_no);
                setImg(mCurriculumImg, R.drawable.main_curriculum_yes);
                setImg(mForumImg, R.drawable.main_forum_no);
                setImg(mMyImg, R.drawable.main_my_no);

                setColoor(mHomeText, R.color.huiS);
                setColoor(mCurriculumText, R.color.danlvS);
                setColoor(mForumText, R.color.huiS);
                setColoor(mMyText, R.color.huiS);
                setColoor(mTeacherText, R.color.huiS);

                mHomeLayout.setClickable(true);
                mCurriculumLayout.setClickable(false);
                mForumLayout.setClickable(true);
                mMyLayout.setClickable(true);
                return;
            case R.id.main_forum_layout://论坛
                Fment(R.id.item_framen, new ForumActivity(), "Three");
                setImg(mHomeImg, R.drawable.main_home_no);
                setImg(mCurriculumImg, R.drawable.main_curriculum_no);
                setImg(mForumImg, R.drawable.main_forum_yes);
                setImg(mMyImg, R.drawable.main_my_no);

                setColoor(mHomeText, R.color.huiS);
                setColoor(mCurriculumText, R.color.huiS);
                setColoor(mForumText, R.color.danlvS);
                setColoor(mMyText, R.color.huiS);
                setColoor(mTeacherText, R.color.huiS);

                mHomeLayout.setClickable(true);
                mCurriculumLayout.setClickable(true);
                mForumLayout.setClickable(false);
                mMyLayout.setClickable(true);
                return;
            case R.id.main_my_layout://我的
                Fment(R.id.item_framen, new MyActivity(), "Four");
                setImg(mHomeImg, R.drawable.main_home_no);
                setImg(mCurriculumImg, R.drawable.main_curriculum_no);
                setImg(mForumImg, R.drawable.main_forum_no);
                setImg(mMyImg, R.drawable.main_my_yes);

                setColoor(mHomeText, R.color.huiS);
                setColoor(mCurriculumText, R.color.huiS);
                setColoor(mForumText, R.color.huiS);
                setColoor(mMyText, R.color.danlvS);
                setColoor(mTeacherText, R.color.huiS);

                mHomeLayout.setClickable(true);
                mCurriculumLayout.setClickable(true);
                mForumLayout.setClickable(true);
                mMyLayout.setClickable(false);
                return;
            case R.id.main_teacher_layout://小雄老师
                Fment(R.id.item_framen, new TeacherActivity(), "Five");
                setImg(mHomeImg, R.mipmap.icon);
                setImg(mCurriculumImg, R.mipmap.icon);
                setImg(mForumImg, R.mipmap.icon);
                setImg(mMyImg, R.mipmap.icon);

                setColoor(mHomeText, R.color.huiS);
                setColoor(mCurriculumText, R.color.huiS);
                setColoor(mForumText, R.color.huiS);
                setColoor(mMyText, R.color.huiS);
                setColoor(mTeacherText, R.color.danlvS);
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
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        mHomeLayout = $(R.id.main_homepage_layout);
        mCurriculumLayout = $(R.id.main_curriculum_layout);
        mForumLayout = $(R.id.main_forum_layout);
        mMyLayout = $(R.id.main_my_layout);
        mTeacherLayout = $(R.id.main_teacher_layout);

        mHomeImg = $(R.id.main_homepage_img);
        mCurriculumImg = $(R.id.main_curriculum_img);
        mForumImg = $(R.id.main_forum_img);
        mMyImg = $(R.id.main_my_img);

        mHomeText = $(R.id.main_homepage_text);
        mCurriculumText = $(R.id.main_curriculum_text);
        mForumText = $(R.id.main_forum_text);
        mMyText = $(R.id.main_my_text);
        mTeacherText = $(R.id.main_teacher_text);
    }

    @Override
    public void setListener() {
        mHomeLayout.setOnClickListener(this);
        mCurriculumLayout.setOnClickListener(this);
        mForumLayout.setOnClickListener(this);
        mMyLayout.setOnClickListener(this);
        mTeacherLayout.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        transaction.add(R.id.item_framen, new HomePageActivity(), "ONE");
        transaction.commit();

    }

    public void mSwitch() {
        Fment(R.id.item_framen, new CurriculumActivity(), "Two");
        setImg(mHomeImg, R.drawable.main_home_no);
        setImg(mCurriculumImg, R.drawable.main_curriculum_yes);
        setImg(mForumImg, R.drawable.main_forum_no);
        setImg(mMyImg, R.drawable.main_my_no);

        setColoor(mHomeText, R.color.huiS);
        setColoor(mCurriculumText, R.color.danlvS);
        setColoor(mForumText, R.color.huiS);
        setColoor(mMyText, R.color.huiS);
        setColoor(mTeacherText, R.color.huiS);

        mHomeLayout.setClickable(true);
        mCurriculumLayout.setClickable(false);
        mForumLayout.setClickable(true);
        mMyLayout.setClickable(true);
    }


    private void Fment(int view, Fragment fragment, String str) {
        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        transaction.replace(view, fragment, str);
        transaction.commit();
    }
}