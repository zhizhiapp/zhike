package com.example.administrator.zhike;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.delete.SwipeItemLayout;
import com.example.administrator.delete.SwipeListView;
import com.example.administrator.entity.MyShoppingCartEntity;
import com.example.administrator.zhike.wxapi.CheckInterface;

import java.util.ArrayList;


public class MyShoppingCartActivity extends BaseActivity implements OnClickListener, CheckInterface {
    SwipeListView listView;
    ArrayList<MyShoppingCartEntity> list;
    ArrayList<MyShoppingCartEntity> putList;
    MyAdapter adapter;
    LinearLayout qx;
    TextView money;
    ImageView myReturn;
    Button mySettlement;
    CheckBox ckAll;
    double totalPrice;
    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.quanxuan:
                if (list.size() != 0) {
                    if (ckAll.isChecked()) {//true
                        ckAll.setChecked(false);
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setChoosed(false);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        ckAll.setChecked(true);
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setChoosed(true);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
                statistics();

                return;
            case R.id.my_shopping_cart_return://返回
                finish();
                return;
            case R.id.my_shopping_cart_settlement://结算
                putList = new ArrayList<>();
                putList.clear();
                for (int i = 0; i < list.size(); i++) {
                    MyShoppingCartEntity shoppingCartBean = list.get(i);
                    if (shoppingCartBean.isChoosed()) {
                        putList.add(shoppingCartBean);
                    }
                }
                if (putList.size()!= 0){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("putList", putList);
                    bundle.putDouble("money", totalPrice);
                    startActivity(MyShoppingCartSettlementActivity.class, bundle);
                }else {
                    showToast("请选择你的订单");
                }
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
        return R.layout.activity_my_shopping_cart;

    }

    @Override
    public void initView(View view) {
        listView = $(R.id.my_shopping_cart_listview);
        money = $(R.id.my_shopping_cart_money);
        qx = $(R.id.quanxuan);
        myReturn = $(R.id.my_shopping_cart_return);
        mySettlement = $(R.id.my_shopping_cart_settlement);
        ckAll = $(R.id.my_shopping_cart_checkbox);
    }

    @Override
    public void setListener() {
        listView.setVerticalScrollBarEnabled(false);
        listView.setFastScrollEnabled(false);
        qx.setOnClickListener(this);
        myReturn.setOnClickListener(this);
        mySettlement.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        list = new ArrayList<>();
        MyShoppingCartEntity entity = new MyShoppingCartEntity();
        entity.setMoney(15);
        entity.setTitle("你");
        list.add(entity);

        MyShoppingCartEntity entity1 = new MyShoppingCartEntity();
        entity1.setMoney(20);
        entity1.setTitle("我");
        list.add(entity1);

        MyShoppingCartEntity entity3 = new MyShoppingCartEntity();
        entity3.setMoney(40);
        entity3.setTitle("她");
        list.add(entity3);


        MyShoppingCartEntity entity4 = new MyShoppingCartEntity();
        entity4.setMoney(100);
        entity4.setTitle("老王");
        list.add(entity4);

        MyShoppingCartEntity entity5 = new MyShoppingCartEntity();
        entity5.setMoney(200);
        entity5.setTitle("答题");
        list.add(entity5);


        adapter = new MyAdapter(list);
        listView.setAdapter(adapter);
        adapter.setCheckInterface(this);
        listView.setOnItemClickListener(new OnItemClick());

    }


    class OnItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }


    private class MyAdapter extends BaseAdapter {
        private CheckInterface checkInterface;

        public void setCheckInterface(CheckInterface checkInterface) {
            this.checkInterface = checkInterface;
        }

        ArrayList<MyShoppingCartEntity> list;

        public MyAdapter(ArrayList<MyShoppingCartEntity> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int arg0) {
            return list.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(final int position, View contentView, final ViewGroup arg2) {
            final Halp help;
            if (contentView == null) {
                help = new Halp();
                View view01 = LayoutInflater.from(MyShoppingCartActivity.this).inflate(R.layout.item_list_my_shopping_cart, null);
                View view02 = LayoutInflater.from(MyShoppingCartActivity.this).inflate(R.layout.btn_delete, null);
                help.btn = (Button) view02.findViewById(R.id.btn_delete);

                help.selected = (CheckBox) view01.findViewById(R.id.item_list_my_shopping_cart_selected);
                help.img = (ImageView) view01.findViewById(R.id.item_list_my_shopping_cart_img);
                help.title = (TextView) view01.findViewById(R.id.item_list_my_shopping_cart_title);
                help.money = (TextView) view01.findViewById(R.id.item_list_my_shopping_cart_money);
                help.collection = (Button) view01.findViewById(R.id.item_list_my_shopping_cart_collection);

                contentView = new SwipeItemLayout(view01, view02, null, null);
                contentView.setTag(help);
            } else {
                help = (Halp) contentView.getTag();
            }

            final MyShoppingCartEntity entity = list.get(position);
            boolean choosed = entity.isChoosed();
            if (choosed) {
                help.selected.setChecked(true);
            } else {
                help.selected.setChecked(false);
            }


            help.btn.setOnClickListener(new View.OnClickListener() {//删除
                @Override
                public void onClick(View arg0) {
                    checkInterface.childDelete(position);//删除 目前只是从item中移除
                }
            });

            help.selected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    entity.setChoosed(((CheckBox) v).isChecked());
                    checkInterface.checkGroup(position, ((CheckBox) v).isChecked());//向外暴露接口
                }
            });
            return contentView;
        }

        private class Halp {
            Button btn;
            CheckBox selected;
            ImageView img;
            TextView title;
            TextView money;
            Button collection;
        }
    }


    @Override
    public void CheckInterface(CheckInterface checkInterface) {

    }

    @Override
    public void checkGroup(int position, boolean isChecked) {
        list.get(position).setChoosed(isChecked);
        if (isAllCheck())
            ckAll.setChecked(true);
        else
            ckAll.setChecked(false);
        adapter.notifyDataSetChanged();
        statistics();
    }

    @Override
    public void childDelete(int position) {
        list.remove(position);
        adapter.notifyDataSetChanged();
        statistics();
    }

    /**
     * 遍历list集合
     *
     * @return
     */
    private boolean isAllCheck() {
        for (MyShoppingCartEntity group : list) {
            if (!group.isChoosed())
                return false;
        }
        return true;
    }

    /**
     * 统计操作
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作
     * 3.给底部的textView进行数据填充
     */
    public void statistics() {
        totalPrice = 0.00;
        for (int i = 0; i < list.size(); i++) {
            MyShoppingCartEntity shoppingCartBean = list.get(i);
            if (shoppingCartBean.isChoosed()) {
                totalPrice += shoppingCartBean.getMoney();
            }
        }
        money.setText("￥" + totalPrice);
    }


}
