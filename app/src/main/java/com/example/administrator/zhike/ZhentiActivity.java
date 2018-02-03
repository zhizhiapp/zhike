package com.example.administrator.zhike;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.adapter.ZhentiAdapter;
import com.example.administrator.constants.MyList;
import com.example.administrator.constants.ServiceApi;
import com.example.administrator.entity.CollectionEntity;
import com.example.administrator.entity.SubjectEntity;
import com.example.administrator.entity.ZhenTiListEntity;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.FullyLinearLayoutManager;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SystemUtils;
import com.example.administrator.zhike.view.LinearLayoutItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import base.BaseApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ZhentiActivity extends BaseActivity {


    ImageView eReturn;
    LinearLayout eChoice;
    TextView eChoiceText;
    ImageView eChoiceImg;
    RecyclerView recyclerView;
    ZhentiAdapter adapter;
    Spinner spinner;
    int subjectId = 2;
    Intent intent;
    Bundle bundle;

    @Override
    public void widgetClick(View v) {

        switch (v.getId()) {
            case R.id.zhenti_return://返回
                finish();
                return;
            case R.id.zhenti_choice://课程选择
                spinner.performClick();
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
        return R.layout.activity_zhenti;
    }

    @Override
    public void initView(View view) {
        eReturn = $(R.id.zhenti_return);
        eChoice = $(R.id.zhenti_choice);
        eChoiceText = $(R.id.zhenti_choice_text);
        eChoiceImg = $(R.id.zhenti_choice_img);
        recyclerView = $(R.id.zhenti_curriculum);
        spinner = $(R.id.zhenti_spinner);


    }

    @Override
    public void setListener() {
        eReturn.setOnClickListener(this);
        eChoice.setOnClickListener(this);
        recyclerView.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动
    }

    @Override
    public void doBusiness(Context mContext) {

        initChoiceSubjects();
        getZhenTiList(MyList.subjectId);
    }


    //科目选择数据
    private void initChoiceSubjects() {
        BaseApplication.getChoiceSubjects(new BaseApplication.SubjectListener() {
            @Override
            public void onData(final SubjectEntity entity) {
                spinner.setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return entity.getList().size();
                    }

                    @Override
                    public Object getItem(int i) {
                        return entity.getList().get(i);
                    }

                    @Override
                    public long getItemId(int i) {
                        return i;
                    }

                    @Override
                    public View getView(int i, View view, ViewGroup viewGroup) {
                        ViewHolder viewHolder;
                        if (view == null) {
                            view = LayoutInflater.from(ZhentiActivity.this).inflate(R.layout.item_list, viewGroup, false);
                            viewHolder = new ViewHolder(view);
                            view.setTag(viewHolder);
                        } else {
                            viewHolder = (ViewHolder) view.getTag();
                        }
                        final String s = entity.getList().get(i).getName();
                        final int id = entity.getList().get(i).getId();
                        final int projectid = entity.getList().get(i).getProjectid();
                        viewHolder.textView.setText(s);
                        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                eChoiceText.setText(s);
                                Log.i("TAG", id + "=====" + s);
                                MyList.name = s;
                                getZhenTiList(id);

                                //手动隐藏spinner
                                spinner.setVisibility(View.GONE);
                                spinner.setVisibility(View.VISIBLE);
                            }
                        });
                        return view;
                    }

                    class ViewHolder {
                        private TextView textView;

                        public ViewHolder(View view) {
                            textView = (TextView) view.findViewById(R.id.item_list_text);
                        }
                    }

                });
            }
        });
    }

    //真题列表
    public void getZhenTiList(int subjectid) {
        this.subjectId = subjectid;
        Map<String, Object> map = new HashMap<>();
        map.put("subjectid", subjectid);
        DescendingEncryption.init(map);
        ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<ZhenTiListEntity> call = api.getZhenTiList(map, SystemUtils.getHeader(ZhentiActivity.this));
        call.enqueue(new Callback<ZhenTiListEntity>() {
            @Override
            public void onResponse(Call<ZhenTiListEntity> call, Response<ZhenTiListEntity> response) {
                ArrayList<ZhenTiListEntity.list> data = response.body().getList();
                Log.i("TAG", data.size() + "xxxx");
                if (adapter == null) {
                    FullyLinearLayoutManager manager = new FullyLinearLayoutManager(ZhentiActivity.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.addItemDecoration(new LinearLayoutItemDecoration(LinearLayoutManager.VERTICAL, 1));//设置颜色  和 分割线
                    adapter = new ZhentiAdapter(data);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                } else {
                    adapter.setNewData(data);
                }
                ZhenTiAdapterOnItemClickLiterCollection();
                ZhenTiAdapterOnItemClickLiter();
            }

            @Override
            public void onFailure(Call<ZhenTiListEntity> call, Throwable t) {
                showToast("网络异常");
            }
        });
    }


    public void ZhenTiAdapterOnItemClickLiterCollection() {//收藏
        adapter.setOnItemClickLitenerCollection(new ZhentiAdapter.OnItemClickLiterCollection() {
            @Override
            public void onItemClick(int id, String status, int type) {
                Log.i("TAG", id + "" + status + "" + type);
                Collection(id, status, type);

            }
        });
    }

    public void ZhenTiAdapterOnItemClickLiter() {//点击
        adapter.setOnItemClickLitener(new ZhentiAdapter.OnItemClickLiter() {
            @Override
            public void onItemClick(int id) {
                if (intent == null) {
                    intent = new Intent();
                }
                if (bundle == null) {
                    bundle = new Bundle();
                }
                bundle.putInt("subjectId", MyList.subjectId);
                startActivity(IntelligentTestZhenTiActivity.class, bundle);
            }
        });
    }

    //收藏真题
    public void Collection(int id, String status, final int type) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("otherid", id);
        map.put("status", status);
        DescendingEncryption.init(map);
        final ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<CollectionEntity> call = api.Collection(map, SystemUtils.getHeader(BaseApplication.getContext()));
        call.enqueue(new Callback<CollectionEntity>() {
            @Override
            public void onResponse(Call<CollectionEntity> call, Response<CollectionEntity> response) {
                if (response.body().getCode().equals("1")) {
                    Log.i("TAG", "subjectId===" + subjectId);
                    getZhenTiList(subjectId);
                } else if (response.body().getCode().equals("0")) {
                    Toast.makeText(BaseApplication.getContext(), "操作失败", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CollectionEntity> call, Throwable t) {
                Toast.makeText(BaseApplication.getContext(), "网络异常", Toast.LENGTH_LONG).show();
            }
        });
    }

}
