package com.example.administrator.zhike;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.adapter.IntelligenceAdapter;
import com.example.administrator.constants.MyConstants;
import com.example.administrator.constants.ServiceApi;
import com.example.administrator.entity.IntelligenceInfoEntity;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.FullyLinearLayoutManager;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SystemUtils;
import com.example.administrator.zhike.view.LinearLayoutItemDecoration;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntelligenceActivity extends BaseActivity {

    ImageView iReturn;
    TextView iTitle;
    ImageView iShare;
    ScrollView iScroll;
    ImageView iImg;
    TextView iRelativeTitle;
    TextView iRelativeContont;
    TextView iContont;
    ImageView iZan;
    ImageView iCollection;
    RecyclerView recycler;
    int id;
    String url;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.intelligence_return://返回
                finish();
                return;
            case R.id.intelligence_share://分享
                return;
        }

    }

    @Override
    public void initParms(Bundle parms) {
        id = parms.getInt("id");
        url = MyConstants.Service_URL + parms.getString("url");
        Log.i("TAG", url);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_intelligence;
    }

    @Override
    public void initView(View view) {
        iReturn = $(R.id.intelligence_return);
        iTitle = $(R.id.intelligence_title);
        iShare = $(R.id.intelligence_share);
        iScroll = $(R.id.intelligence_scroll);
        iImg = $(R.id.intelligence_img);
        iRelativeTitle = $(R.id.intelligence_relative_title);
        iRelativeContont = $(R.id.intelligence_relative_contont);
        iContont = $(R.id.intelligence_contont);
        iZan = $(R.id.intelligence_zan);
        iCollection = $(R.id.intelligence_collection);
        recycler = $(R.id.intelligence_recycler);
    }

    @Override
    public void setListener() {
        iReturn.setOnClickListener(this);
        iShare.setOnClickListener(this);
        iScroll.setVerticalScrollBarEnabled(false);
        recycler.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动
    }

    @Override
    public void doBusiness(Context mContext) {
        Intelligence();
        getIntelligenceInfo(id);
    }


    //微课
    public void getIntelligenceInfo(int nid) {
        Map<String, Object> map = new HashMap<>();
        map.put("nid", nid);
        DescendingEncryption.init(map);
        final ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<IntelligenceInfoEntity> call = api.getIntelligenceInfo(map, SystemUtils.getHeader(IntelligenceActivity.this));
        call.enqueue(new Callback<IntelligenceInfoEntity>() {
            @Override
            public void onResponse(Call<IntelligenceInfoEntity> call, Response<IntelligenceInfoEntity> response) {
                iTitle.setText(response.body().getList().getTitle() + "");
                iRelativeTitle.setText(response.body().getList().getKeywords() + "");
                iRelativeContont.setText(response.body().getList().getDescription() + "");
                Glide.with(IntelligenceActivity.this).load(url).into(iImg);
                iContont.setText(Html.fromHtml(response.body().getList().getContent(), imgGetter, null));
            }

            @Override
            public void onFailure(Call<IntelligenceInfoEntity> call, Throwable t) {
            }
        });
    }

    Html.ImageGetter imgGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            URL url = null;
            try {
                url = new URL(MyConstants.Service_URL + source);
                drawable = Drawable.createFromStream(url.openStream(), "");
            } catch (Exception e) {
                return null;
            }
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            return drawable;
        }
    };


    public void Intelligence() {
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(IntelligenceActivity.this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
        recycler.addItemDecoration(new LinearLayoutItemDecoration(LinearLayoutManager.VERTICAL, getResources().getDimensionPixelSize(R.dimen.x30)));//设置颜色  和 分割线
        IntelligenceAdapter mIntelliAdapter = new IntelligenceAdapter(IntelligenceActivity.this);
        recycler.setAdapter(mIntelliAdapter);
        recycler.setItemAnimator(new DefaultItemAnimator());
    }
}
