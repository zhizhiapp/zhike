package com.example.administrator.zhike;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

import com.example.administrator.adapter.ExercisesTestItemAdapter;
import com.example.administrator.constants.MyList;
import com.example.administrator.constants.ServiceApi;
import com.example.administrator.entity.ExercisesTestItemEntity;
import com.example.administrator.entity.SubjectEntity;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SystemUtils;
import com.example.administrator.zhike.view.PickerScrollViewExercises;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import base.BaseApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExercisesActivity extends BaseActivity { //习题
    ImageView eReturn;
    LinearLayout eChoice;
    TextView eChoiceText;
    ImageView eChoiceImg;
    RecyclerView eSectionRecycler;

    ArrayList<ExercisesTestItemEntity.list> data;
    ArrayList<ExercisesTestItemEntity.list> data2;
    ArrayList<ExercisesTestItemEntity.list> data3;
    private Spinner spinner;
    private ExercisesTestItemAdapter adapter;
    int subjectId = 2;//科目id
    int PractChapt = 0;//章的id

    int code = 0;


    LinearLayout PractChaptLayout;
    LinearLayout PractSectionLayout;
    LinearLayout Layout;


    //数学  逻辑
    //
    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.exercises_return://返回
                finish();
                break;
            case R.id.exercises_choice://课程选择
                spinner.performClick();
                break;
            case R.id.fl_chapter_1:
                if (data != null) {
                    if (data.size() > 0) {
                        code = 0;
                        showWheel(data);
                    }
                }

                break;
            case R.id.fl_chapter_2:
                if (data2 != null) {
                    if (data2.size() > 0) {
                        code = 1;
                        showWheel(data2);
                    }
                }


                break;
        }

    }

    private void showWheel(ArrayList<ExercisesTestItemEntity.list> data) {
        final AlertDialog dialog = new AlertDialog.Builder(ExercisesActivity.this, R.style.MyDialog).create();
        dialog.setCancelable(true);
        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = -1;
            window.setGravity(Gravity.BOTTOM);
            window.setAttributes(attributes);
            View view = LayoutInflater.from(this).inflate(R.layout.view_whell, new FrameLayout(this), false);
            PickerScrollViewExercises pickerScrollView = (PickerScrollViewExercises) view.findViewById(R.id.psv_wheel);
            pickerScrollView.setData(data);
            if (data.size() > 0) {
                PractChapt = data.get(0).getId();
                Log.i("TAG", PractChapt + "------------------");
            }
            pickerScrollView.setSelected(0);
            window.setContentView(view);
            pickerScrollView.setOnSelectListener(new PickerScrollViewExercises.onSelectListener() {
                @Override
                public void onSelect(ExercisesTestItemEntity.list pickers) {
                    PractChapt = pickers.getId();
                    Log.i("TAG", PractChapt + "=====PractChapt");
                }
            });

            view.findViewById(R.id.bt_complete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("TAG", PractChapt + "====PractChapt");

                    if (code == 0) {//章的点击
                        Log.i("TAG", code + "=====章的点击");
                        getPractChapt(PractChapt);
                    } else if (code == 1) {//节的点击
                        Log.i("TAG", code + "===节的点击");
                        getPractSection(PractChapt);
                    }

                    dialog.dismiss();
                }
            });
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
        return R.layout.activity_exercises;
    }

    @Override
    public void initView(View view) {
        eReturn = $(R.id.exercises_return);
        eChoice = $(R.id.exercises_choice);
        eChoiceText = $(R.id.exercises_choice_text);
        eChoiceImg = $(R.id.exercises_choice_img);
        eSectionRecycler = $(R.id.exercises_section_recycler);
        spinner = $(R.id.snr_menu);
        PractChaptLayout = $(R.id.fl_chapter_1);
        PractSectionLayout = $(R.id.fl_chapter_2);
        Layout = $(R.id.fl_chapter_layout);
    }

    @Override
    public void setListener() {
        eReturn.setOnClickListener(this);
        eChoice.setOnClickListener(this);
        PractChaptLayout.setOnClickListener(this);
        PractSectionLayout.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        initChoiceSubjects();
        if (MyList.subjectId == 2 || MyList.subjectId == 64) {
            Layout.setVisibility(View.VISIBLE);
        } else {
            Layout.setVisibility(View.GONE);

        }
        getExercisesList(MyList.subjectId);
    }


    //所有章
    public void getExercisesList(int subjectid) {
        Map<String, Object> map = new HashMap<>();
        map.put("subjectid", subjectid);
        DescendingEncryption.init(map);
        Log.i("TAG", map + "");
        ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<ExercisesTestItemEntity> call = api.getExercisesList(map, SystemUtils.getHeader(ExercisesActivity.this));
        call.enqueue(new Callback<ExercisesTestItemEntity>() {
            @Override
            public void onResponse(Call<ExercisesTestItemEntity> call, Response<ExercisesTestItemEntity> response) {
                data = response.body().getList();
                if (adapter == null) {
                    adapter = new ExercisesTestItemAdapter(data);
                    eSectionRecycler.setLayoutManager(new LinearLayoutManager(ExercisesActivity.this));
                    eSectionRecycler.setHasFixedSize(true);
                    eSectionRecycler.setAdapter(adapter);
                } else {
                    adapter.setNewData(data);
                }
                getExercisesListOnItemClick();
            }

            @Override
            public void onFailure(Call<ExercisesTestItemEntity> call, Throwable t) {
                //网络错误
            }
        });
    }

    //章--》节
    public void getPractChapt(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        DescendingEncryption.init(map);
        Log.i("TAG", map + "");
        ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<ExercisesTestItemEntity> call = api.getPractChapt(map, SystemUtils.getHeader(ExercisesActivity.this));
        call.enqueue(new Callback<ExercisesTestItemEntity>() {
            @Override
            public void onResponse(Call<ExercisesTestItemEntity> call, Response<ExercisesTestItemEntity> response) {
                data2 = response.body().getList();
                if (adapter == null) {
                    adapter = new ExercisesTestItemAdapter(data2);
                    eSectionRecycler.setLayoutManager(new LinearLayoutManager(ExercisesActivity.this));
                    eSectionRecycler.setHasFixedSize(true);
                    eSectionRecycler.setAdapter(adapter);
                } else {
                    adapter.setNewData(data2);
                }
                getPractChaptOnItemClick();
            }

            @Override
            public void onFailure(Call<ExercisesTestItemEntity> call, Throwable t) {
                //网络错误
            }
        });
    }

    // 节--》 下面
    public void getPractSection(final int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        DescendingEncryption.init(map);
        Log.i("TAG", map + "");
        ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<ExercisesTestItemEntity> call = api.getPractSection(map, SystemUtils.getHeader(ExercisesActivity.this));
        call.enqueue(new Callback<ExercisesTestItemEntity>() {
            @Override
            public void onResponse(Call<ExercisesTestItemEntity> call, Response<ExercisesTestItemEntity> response) {
                data3 = response.body().getList();
                Log.i("TAG", "没id=======" + id);
                if (data3.size() > 0) {
                    if (adapter == null) {
                        adapter = new ExercisesTestItemAdapter(data3);
                        eSectionRecycler.setLayoutManager(new LinearLayoutManager(ExercisesActivity.this));
                        eSectionRecycler.setHasFixedSize(true);
                        eSectionRecycler.setAdapter(adapter);
                    } else {
                        adapter.setNewData(data3);
                    }
                    getPractSectionOnItemClick();
                } else {
                    Log.i("TAG", "没数据id=======" + id);
                    startActivity(xxxxxxxxxxxxxxxxxxxxxx.class);
                }
            }

            @Override
            public void onFailure(Call<ExercisesTestItemEntity> call, Throwable t) {
                //网络错误
            }
        });
    }

    //章的点击
    public void getExercisesListOnItemClick() {
        adapter.setOnItemClickLitener(new ExercisesTestItemAdapter.OnItemClickLiter() {
            @Override
            public void onItemClick(int id) {
                Log.i("TAG", "章的点击" + id);
                getPractChapt(id);
            }
        });
    }

    //节的点击
    public void getPractChaptOnItemClick() {
        adapter.setOnItemClickLitener(new ExercisesTestItemAdapter.OnItemClickLiter() {
            @Override
            public void onItemClick(int id) {
                Log.i("TAG", "节的点击" + id);
                getPractSection(id);

            }
        });
    }

    //节下面的点击
    public void getPractSectionOnItemClick() {
        adapter.setOnItemClickLitener(new ExercisesTestItemAdapter.OnItemClickLiter() {
            @Override
            public void onItemClick(int id) {
                Log.i("TAG", "节下面的点击" + id);
            }
        });
    }

    //科目列表
    private void initChoiceSubjects() {
        BaseApplication.getChoiceSubjects(new BaseApplication.SubjectListener() {
            @Override
            public void onData(final SubjectEntity entity) {
                if (entity.getList() != null && entity.getList().size() > 0) {
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
                                view = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.item_list, viewGroup, false);
                                viewHolder = new ViewHolder(view);
                                view.setTag(viewHolder);
                            } else {
                                viewHolder = (ViewHolder) view.getTag();
                            }
                            final String s = entity.getList().get(i).getName();
                            final int id = entity.getList().get(i).getId();
                            viewHolder.textView.setText(s);
                            viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    eChoiceText.setText(s);
                                    //手动隐藏spinner
                                    spinner.setVisibility(View.GONE);
                                    spinner.setVisibility(View.VISIBLE);
                                    data = null;
                                    data2 = null;
                                    if (s.equals("数学") || s.equals("逻辑")) {
                                        Layout.setVisibility(View.VISIBLE);
                                    } else if (s.equals("英语") || s.equals("写作")) {
                                        Layout.setVisibility(View.GONE);

                                    }
                                    getExercisesList(id);
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
            }
        });
    }


}
