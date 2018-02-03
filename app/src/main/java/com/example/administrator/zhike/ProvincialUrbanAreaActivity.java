package com.example.administrator.zhike;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ProvincialUrbanAreaActivity extends BaseActivity {
     ListView listSheng, listShi;
     ShengAdapter shengAdapter;
     int resultCode = 102;//返回码
    //全局的jsonObject
     JSONObject jsonObject;//把全国的省市区的信息以json的格式保存，解析完成后赋值为null
     String[] allProv;//所有的省
     String provinceName;//省的名字
     String cityName;//市省的名字
    //省市区的集合
     Map<String, String[]> cityMap = new HashMap();//key:省p---value:市n  value是一个集合
     ImageView imgSheng, imgShi;
     ShiAdapter shiAdapter;//市的适配器
     String[] cities;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.sheng_fh:
                finish();
                break;
            case R.id.shi_fh:
                imgSheng.setVisibility(View.VISIBLE);
                imgShi.setVisibility(View.GONE);
                cities.clone();
                listShi.setVisibility(View.GONE);
                listSheng.setVisibility(View.VISIBLE);
                break;
            default:
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
        return R.layout.activity_sheng;
    }

    @Override
    public void initView(View view) {
        imgSheng=$(R.id.sheng_fh);
        imgShi=$(R.id.shi_fh);
        listSheng=$(R.id.list_sheng);
        listShi=$(R.id.list_shi);
    }

    @Override
    public void setListener() {
        listSheng.setOnItemClickListener(new OnItemSheng());
        listShi.setOnItemClickListener(new OnItemShi());

        imgSheng.setOnClickListener(this);
        imgShi.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        initJsonData();//初始化json数据
        initDatas();//初始化省市区数据
        shengAdapter = new ShengAdapter();
        listSheng.setAdapter(shengAdapter);
    }



    /**
     * 从assert文件夹中获取json数据
     */
    private void initJsonData() {
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = getAssets().open("city.json");//打开json数据
            byte[] by = new byte[is.available()];//转字节
            int len = -1;
            while ((len = is.read(by)) != -1) {
                sb.append(new String(by, 0, len, "UTF-8"));//根据字节长度设置编码
            }
            is.close();//关闭流
            jsonObject = new JSONObject(sb.toString());//为json赋值
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //初始化省市区数据
    private void initDatas() {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("citylist");//获取整个json数据
            allProv = new String[jsonArray.length()];//封装数据
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonP = jsonArray.getJSONObject(i);//jsonArray转jsonObject
                String provStr = jsonP.getString("p");//获取所有的省
                allProv[i] = provStr;//封装所有的省
                JSONArray jsonCity = null;
                try {
                    jsonCity = jsonP.getJSONArray("c");//在所有的省中取出所有的市，转jsonArray
                } catch (Exception e) {
                    continue;
                }
                //所有的市
                String[] allCity = new String[jsonCity.length()];//所有市的长度
                for (int c = 0; c < jsonCity.length(); c++) {
                    JSONObject jsonCy = jsonCity.getJSONObject(c);//转jsonObject
                    String cityStr = jsonCy.getString("n");//取出所有的市
                    allCity[c] = cityStr;//封装市集合
                }
                cityMap.put(provStr, allCity);//某个省取出所有的市,
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonObject = null;//清空所有的数据
    }
    class OnItemSheng implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            provinceName = (String) arg0.getItemAtPosition(arg2);
            cities = cityMap.get(provinceName);//拿到所点击的省 里的  市
            if (cities != null) {
                listSheng.setVisibility(View.GONE);
                listShi.setVisibility(View.VISIBLE);
                imgSheng.setVisibility(View.GONE);
                imgShi.setVisibility(View.VISIBLE);
                shiAdapter = new ShiAdapter(cities);
                listShi.setAdapter(shiAdapter);
            }


        }

    }
    class OnItemShi implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            cityName = (String) arg0.getItemAtPosition(arg2);
            Intent intent = new Intent();
            intent.putExtra("province", provinceName);
            intent.putExtra("city", cityName);
            setResult(resultCode, intent);
            finish();
        }

    }
    private class ShengAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return allProv.length;
        }

        @Override
        public Object getItem(int arg0) {
            return allProv[arg0];
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            Hale hale;
            if (arg1 == null) {
                hale = new Hale();
                arg1 = LayoutInflater.from(ProvincialUrbanAreaActivity.this).inflate(R.layout.item_list_sheng, null);
                hale.textView = (TextView) arg1.findViewById(R.id.item_list_sheng);
                arg1.setTag(hale);
            } else {
                hale = (Hale) arg1.getTag();
            }
            hale.textView.setText(allProv[arg0]);
            return arg1;
        }

        class Hale {
            TextView textView;
        }
    }
    private class ShiAdapter extends BaseAdapter {
        String cities[];

        public ShiAdapter(String cities[]) {
            this.cities = cities;
        }

        @Override
        public int getCount() {
            return cities.length;
        }

        @Override
        public Object getItem(int arg0) {
            return cities[arg0];
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            Hale hale;
            if (arg1 == null) {
                hale = new Hale();
                arg1 = LayoutInflater.from(ProvincialUrbanAreaActivity.this).inflate(R.layout.item_list_shi, null);
                hale.textView = (TextView) arg1.findViewById(R.id.item_list_shi);
                arg1.setTag(hale);
            } else {
                hale = (Hale) arg1.getTag();
            }


            hale.textView.setText(cities[arg0]);
            return arg1;
        }

        class Hale {
            TextView textView;
        }
    }


}
