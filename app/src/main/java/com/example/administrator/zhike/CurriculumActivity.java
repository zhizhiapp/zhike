package com.example.administrator.zhike;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
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

import com.example.administrator.adapter.CurriculumAdapter;
import com.example.administrator.constants.MyList;
import com.example.administrator.constants.ServiceApi;
import com.example.administrator.entity.ChapterEntity;
import com.example.administrator.entity.CollectionEntity;
import com.example.administrator.entity.SubjectEntity;
import com.example.administrator.entity.VideoEntity;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.FullyLinearLayoutManager;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SystemUtils;
import com.example.administrator.zhike.view.LinearLayoutColorDecoration;
import com.example.administrator.zhike.view.PickerScrollViewCurriculum;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import base.BaseApplication;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.administrator.constants.MyList.subjectId;

public class CurriculumActivity extends Fragment implements View.OnClickListener {//课程

    ImageView mChoiceImg;// 课程选择
    TextView mTitle;
    LinearLayout mChoice;
    TextView mChoiceText;
    RecyclerView mCurriculum;
    CurriculumAdapter curriculumAdapter;
    LinearLayout layout;
    ArrayList<ChapterEntity.list> chapterList;
    private Spinner spinner;
    View fragmentView;
    int cId;
    int chapid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.activity_microclass, container, false);
            initView(fragmentView);
        }
        return fragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (fragmentView != null) {
            ViewGroup viewGroup = (ViewGroup) fragmentView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(fragmentView);
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initChoiceSubjects();
        getchapter(subjectId);
    }


    public void initView(View view) {
        mChoiceImg = (ImageView) view.findViewById(R.id.microclass_choice_img);
        mTitle = (TextView) view.findViewById(R.id.microclass_title);
        mChoice = (LinearLayout) view.findViewById(R.id.microclass_choice);
        layout = (LinearLayout) view.findViewById(R.id.microclass_layout_chapter);
        mChoiceText = (TextView) view.findViewById(R.id.microclass_choice_text);
        mCurriculum = (RecyclerView) view.findViewById(R.id.microclass_curriculum);//节
        spinner = (Spinner) view.findViewById(R.id.microclass_choice_spinner);
        mCurriculum.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动
        mChoice.setOnClickListener(this);
        layout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.microclass_choice://科目选择
                spinner.performClick();
                return;
            case R.id.microclass_layout_chapter://章节
                if (chapterList.size() > 0) {
                    cWindow();
                }

                return;
        }
    }

    //章
    public void CurriculumItemClick() {
        curriculumAdapter.setOnItemClickLitener(new CurriculumAdapter.OnItemClickLiter() {
            @Override
            public void onItemClick(VideoEntity.VideoList entity) {
                Log.i("TAG", "" + entity.getId());
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("play", entity);
                intent.setClass(getActivity(), MicrovideoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

                //                                getcheckVideo(entity); // 权限


            }
        });
    }

    public void CurriculumItemClickCollstatus() {
        curriculumAdapter.setOnItemClickLitener(new CurriculumAdapter.OnItemClickLiterCollstatus() {
            @Override
            public void onItemClick(int id, String status, int type) {
                Collection(id,status,type);
            }
        });
    }


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
                    getlist(chapid);//章节id
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



     //获取主页科目选择数据
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
                            view = LayoutInflater.from(getActivity()).inflate(R.layout.item_list, viewGroup, false);
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
                                mChoiceText.setText(s);
                                Log.i("TAG", id + "=====" + s);
                                MyList.name = s;
                                getchapter(id);

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


    public void getchapter(final int subjectid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectid", subjectid);
        DescendingEncryption.init(map);
        final ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<ChapterEntity> call = api.getchapter(map, SystemUtils.getHeader(getActivity()));
        call.enqueue(new Callback<ChapterEntity>() {
            @Override
            public void onResponse(Call<ChapterEntity> call, Response<ChapterEntity> response) {
                chapterList = response.body().getList(); //拿到章
                if (chapterList.size() > 0) {
                    chapid = chapterList.get(0).getId();
                    getlist(chapterList.get(0).getId());
                } else {
                    ArrayList<VideoEntity.VideoList> data = new ArrayList<>();
                    if (curriculumAdapter == null) {
                        curriculumAdapter = new CurriculumAdapter(data);
                        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        mCurriculum.setLayoutManager(manager);
                        mCurriculum.addItemDecoration(new LinearLayoutColorDecoration(LinearLayoutManager.VERTICAL, getResources().getDimensionPixelSize(R.dimen.x2), getResources().getColor(R.color.bj), 1));
                        mCurriculum.setAdapter(curriculumAdapter);
                        mCurriculum.setItemAnimator(new DefaultItemAnimator());
                    } else {
                        curriculumAdapter.setNewData(data);
                    }
                }
            }

            @Override
            public void onFailure(Call<ChapterEntity> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void getlist(final int chapid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("chapid", chapid);
        DescendingEncryption.init(map);
        final ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<VideoEntity> call = api.getlist(map, SystemUtils.getHeader(getActivity()));
        call.enqueue(new Callback<VideoEntity>() {
            @Override
            public void onResponse(Call<VideoEntity> call, Response<VideoEntity> response) {
                ArrayList<VideoEntity.VideoList> data = response.body().getList();
                if (curriculumAdapter == null) {
                    curriculumAdapter = new CurriculumAdapter(data);
                    FullyLinearLayoutManager manager = new FullyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    mCurriculum.setLayoutManager(manager);
                    mCurriculum.addItemDecoration(new LinearLayoutColorDecoration(LinearLayoutManager.VERTICAL, getResources().getDimensionPixelSize(R.dimen.x2), getResources().getColor(R.color.bj), 1));
                    mCurriculum.setAdapter(curriculumAdapter);
                    mCurriculum.setItemAnimator(new DefaultItemAnimator());
                } else {
                    curriculumAdapter.setNewData(data);
                }
                CurriculumItemClick();
                CurriculumItemClickCollstatus();
                shoppingcartOnItemClickLitener();
            }

            @Override
            public void onFailure(Call<VideoEntity> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void cWindow() {
        final AlertDialog dialog = new AlertDialog.Builder(getActivity(), R.style.MyDialog).create();
        dialog.setCancelable(true);
        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = -1;
            window.setGravity(Gravity.BOTTOM);
            window.setAttributes(attributes);
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_whell_curriculum, new FrameLayout(getActivity()), false);
            PickerScrollViewCurriculum pickerScrollView = (PickerScrollViewCurriculum) view.findViewById(R.id.psv_wheel);
            pickerScrollView.setData(chapterList);
            pickerScrollView.setSelected(0);
            cId = chapterList.get(0).getId();
            window.setContentView(view);
            pickerScrollView.setOnSelectListener(new PickerScrollViewCurriculum.onSelectListener() {
                @Override
                public void onSelect(ChapterEntity.list pickers) {
                    cId = pickers.getId();
                }
            });

            view.findViewById(R.id.bt_complete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chapid = cId;
                    getlist(chapid);
                    dialog.dismiss();
                }

            });


        }
    }


    //观看视频权限
    public void getcheckVideo(final VideoEntity.VideoList entity) {
        Map<String, Object> map = new HashMap<>();
        map.put("video_id", entity.getId());
        DescendingEncryption.init(map);
        final ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<ResponseBody> call = api.getcheckVideo(map, SystemUtils.getHeader(getActivity()));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    String code = object.getString("code");
                    if (code.equals("-1")) {//'无权限观看该视频'
                        Toast.makeText(getActivity(), "无权限观看该视频", Toast.LENGTH_LONG).show();
                    } else if (code.equals("0")) {//'请先登陆
                        Toast.makeText(getActivity(), "请先登陆", Toast.LENGTH_LONG).show();
                    } else if (code.equals("1")) {//'有权限观看该视频',
                        Toast.makeText(getActivity(), "有权限观看该视频", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.putExtra("play", entity);
                        intent.setClass(getActivity(), MicrovideoActivity.class);
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_LONG).show();

            }
        });
    }


    public void shoppingcartOnItemClickLitener() {
        curriculumAdapter.setOnItemClickLitener(new CurriculumAdapter.shoppingcartOnItemClickLiter() {
            @Override
            public void onItemClick(int id, String status) {
                if (status.equals("true")) {
                    addCart(id);
                } else if (status.equals("false")) {
                    delCart(id);
                }
            }
        });
    }




    //添加购物车接口
    public void addCart(int vid) {
        Map<String, Object> map = new HashMap<>();
        map.put("vid", vid);
        DescendingEncryption.init(map);
        final ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<ResponseBody> call = api.addCart(map, SystemUtils.getHeader(getActivity()));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    String  code = object.getString("code");
                    Log.i("TAG","code===="+code);
                    if (code.equals("1")){
                        getlist(chapid);//章节id
                    }else {
                        Toast.makeText(getActivity(),"该视频已在购物车，无需再次添加！",Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("TAG","id===xxx");

            }
        });
    }
    //删除购物车接口
    public void delCart(int cid) {
        Map<String, Object> map = new HashMap<>();
        map.put("vid", cid);
        DescendingEncryption.init(map);
        final ServiceApi api = RxjavaRetrofitUtils.getApi();
        Call<ResponseBody> call = api.delCart(map, SystemUtils.getHeader(getActivity()));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("TAG","id==="+response.code());
                Log.i("TAG","chapid===="+chapid);
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    String  code = object.getString("code");
                    Log.i("TAG","code===="+code);
                    if (code.equals("1")){
                        getlist(chapid);//章节id
                    }else {
                        Toast.makeText(getActivity(),"删除失败或其它错误信息",Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("TAG","id===xxx");
            }
        });
    }

















}









