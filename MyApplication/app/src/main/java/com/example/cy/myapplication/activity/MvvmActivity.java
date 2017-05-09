package com.example.cy.myapplication.activity;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.databinding.ActivityMvvmBinding;
import com.example.cy.myapplication.databinding.ItemMvvmBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MvvmActivity extends AppCompatActivity {

    public String text = "11111";
    public MyAdapter adapter = new MyAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMvvmBinding vdb = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);
        Entity en = new Entity();
        vdb.setPpa(en);

        vdb.lv.setAdapter(adapter);


        adapter.dataList.add(new Item("111111111"));
        adapter.dataList.add(new Item("22222222"));
        adapter.dataList.add(new Item("333333333"));
        adapter.dataList.add(new Item("44444444444"));
        adapter.notifyDataSetChanged();
    }


    public class Entity {
        public boolean isAdult = true;
        public String name;
        public String name2 = "222222222222222222";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class MyAdapter extends BaseAdapter {

        ArrayList<Item> dataList = new ArrayList();

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemMvvmBinding bd = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_mvvm, parent, false);
            Item item = dataList.get(position);
            bd.setItem(item);
            return bd.getRoot();
        }
    }

    public static class Item extends BaseObservable{

        public String text;

        public Item( String text) {
            this.text = text;
        }

        @BindingAdapter("ccccc")
        public static void setText(TextView tv, Item item) {
            tv.setText(item.text+"哈哈哈哈");
        }
    }
}
