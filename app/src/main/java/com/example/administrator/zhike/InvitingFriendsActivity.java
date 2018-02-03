package com.example.administrator.zhike;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import fragment.InvitingFriends1Fragment;
import fragment.InvitingFriends2Fragment;
import fragment.InvitingFriends3Fragment;

public class InvitingFriendsActivity extends BaseActivity {
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private LinearLayout qrKnowLinearLayout;


    public static void startActivity(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, InvitingFriendsActivity.class));
        }
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.ll_know_qr_code_help:
                showQrCodeHelper();
                break;
            case R.id.black:
                finish();
                break;
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
        return R.layout.activity_inviting_friends;
    }

    @Override
    public void initView(View view) {
        viewPager = $(R.id.vp_horizon);
        qrKnowLinearLayout = $(R.id.ll_know_qr_code_help);
        $(R.id.black).setOnClickListener(this);
    }

    @Override
    public void setListener() {
        qrKnowLinearLayout.setOnClickListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void doBusiness(Context mContext) {
        fragments = new ArrayList<>();
        InvitingFriends1Fragment friends1Fragment = new InvitingFriends1Fragment();
        InvitingFriends2Fragment friends2Fragment = new InvitingFriends2Fragment();
        InvitingFriends3Fragment friends3Fragment = new InvitingFriends3Fragment();
        fragments.add(friends1Fragment);
        fragments.add(friends2Fragment);
        fragments.add(friends3Fragment);
        viewPager.setOffscreenPageLimit(fragments.size() - 1);
        viewPager.setAdapter(new InvitingFriendsAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(1);
    }

    private void showQrCodeHelper() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialog);
        AlertDialog dialog = builder.create();
        View view = LayoutInflater.from(this).inflate(R.layout.view_dialog_inviting_friends, new FrameLayout(this), false);
        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = -1;
            window.setGravity(Gravity.BOTTOM);
            window.setAttributes(params);
            window.setContentView(view);
        }
    }


    class InvitingFriendsAdapter extends FragmentPagerAdapter {
        InvitingFriendsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
