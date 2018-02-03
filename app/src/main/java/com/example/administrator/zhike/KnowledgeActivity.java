package com.example.administrator.zhike;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.adapter.KnowledgeAdapter;
import com.example.administrator.constants.MyList;
import com.example.administrator.constants.ServiceApi;
import com.example.administrator.entity.CollectionEntity;
import com.example.administrator.entity.KnowledgeListEntity;
import com.example.administrator.entity.SubjectEntity;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.FullyLinearLayoutManager;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SystemUtils;
import com.example.administrator.zhike.view.LinearLayoutColorDecoration;
import com.example.administrator.zhike.view.PickerScrollViewKnowledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import base.BaseApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KnowledgeActivity extends BaseActivity { //知识库
    ImageView eReturn;
    LinearLayout eChoice;
    TextView eChoiceText;
    ImageView eChoiceImg;
    LinearLayout eChapter;
    LinearLayout eSection;
    RecyclerView eSectionRecycler;
    KnowledgeAdapter knowledgeAdapter;
    Spinner spinner;
    int subjectId = 2;
    int cId;
    ArrayList<KnowledgeListEntity.list> data;
    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.knowledge_return://返回
                finish();
                return;
            case R.id.knowledge_choice://课程选择
                spinner.performClick();
                return;
            case R.id.knowledge_chapter://章
                if (data.size()>0){
                    kWindow();
                }

                return;
            case R.id.knowledge_section://节
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
        return R.layout.activity_knowledge;
    }

    @Override
    public void initView(View view) {
        eReturn = $(R.id.knowledge_return);
        eChoice = $(R.id.knowledge_choice);
        eChoiceText = $(R.id.knowledge_choice_text);
        eChoiceImg = $(R.id.knowledge_choice_img);
        eChapter = $(R.id.knowledge_chapter);
        eSection = $(R.id.knowledge_section);
        eSectionRecycler = $(R.id.knowledge_section_recycler);
        spinner = $(R.id.knowledge_spinner);
    }

    @Override
    public void setListener() {
        eReturn.setOnClickListener(this);
        eChoice.setOnClickListener(this);
        eChapter.setOnClickListener(this);
        eSection.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        initChoiceSubjects();
        getknowledgeList(MyList.subjectId);
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
                            view = LayoutInflater.from(KnowledgeActivity.this).inflate(R.layout.item_list, viewGroup, false);
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
                                getknowledgeList(id);

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

    //知识库列表
    public void getknowledgeList(int subjectid) {
        this.subjectId = subjectid;
        Map<String, Object> map = new HashMap<>();
        map.put("subjectid", subjectid);
        DescendingEncryption.init(map);
        ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<KnowledgeListEntity> call = api.getknowledgeList(map, SystemUtils.getHeader(KnowledgeActivity.this));
        call.enqueue(new Callback<KnowledgeListEntity>() {
            @Override
            public void onResponse(Call<KnowledgeListEntity> call, Response<KnowledgeListEntity> response) {
                data = response.body().getList();
                if (knowledgeAdapter == null) {
                    FullyLinearLayoutManager manager = new FullyLinearLayoutManager(KnowledgeActivity.this, LinearLayoutManager.VERTICAL, false);
                    eSectionRecycler.setLayoutManager(manager);
                    eSectionRecycler.addItemDecoration(new LinearLayoutColorDecoration(LinearLayoutManager.VERTICAL, getResources().getDimensionPixelSize(R.dimen.x15), getResources().getColor(R.color.bj), getResources().getDimensionPixelSize(R.dimen.x15)));
                    knowledgeAdapter = new KnowledgeAdapter(data);
                    eSectionRecycler.setAdapter(knowledgeAdapter);
                    eSectionRecycler.setItemAnimator(new DefaultItemAnimator());
                } else {
                    knowledgeAdapter.setNewData(data);
                }
                knowledgeAdapterOnItemClickLiterCollection();
                mOnItemClickLitener();
            }

            @Override
            public void onFailure(Call<KnowledgeListEntity> call, Throwable t) {
                showToast("网络异常");
            }
        });
    }

    //显示隐藏
    public void mOnItemClickLitener() {
        knowledgeAdapter.setOnItemClickLitener(new KnowledgeAdapter.OnItemClickLiter() {
            @Override
            public void onItemClick(int position) {
                knowledgeAdapter.setPosition(position);
                knowledgeAdapter.notifyDataSetChanged();
            }
        });
    }

    //点击收藏
    public void knowledgeAdapterOnItemClickLiterCollection() {
        knowledgeAdapter.setOnItemClickLiterCollection(new KnowledgeAdapter.OnItemClickLiterCollection() {
            @Override
            public void onItemClick(int id, String status, int type) {
                Log.i("TAG", id + "" + status + "" + type);
                Collection(id, status, type);

            }
        });
    }

    //    //收藏真题
    public void Collection(int id, String status, final int type) {
        Log.i("TAG", "========" + subjectId);
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
                    getknowledgeList(subjectId);
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


    public void kWindow() {
        final AlertDialog dialog = new AlertDialog.Builder(KnowledgeActivity.this, R.style.MyDialog).create();
        dialog.setCancelable(true);
        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = -1;
            window.setGravity(Gravity.BOTTOM);
            window.setAttributes(attributes);
            View view = LayoutInflater.from(KnowledgeActivity.this).inflate(R.layout.view_whell_knowledge, new FrameLayout(KnowledgeActivity.this), false);
            PickerScrollViewKnowledge pickerScrollView = (PickerScrollViewKnowledge) view.findViewById(R.id.psv_wheel);
            pickerScrollView.setData(data); //
            pickerScrollView.setSelected(0);
            window.setContentView(view);
            pickerScrollView.setOnSelectListener(new PickerScrollViewKnowledge.onSelectListener() {
                @Override
                public void onSelect(KnowledgeListEntity.list pickers) {
                    cId = pickers.getId();
                }
            });

            view.findViewById(R.id.bt_complete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    getlist(cId);
                    dialog.dismiss();
                }

            });


        }
    }


}
