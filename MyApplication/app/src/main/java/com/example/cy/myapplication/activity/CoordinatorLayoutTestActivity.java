package com.example.cy.myapplication.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cy.myapplication.R;

import butterknife.ButterKnife;
import butterknife.BindView;

public class CoordinatorLayoutTestActivity extends AppCompatActivity {


    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout_test);
        ButterKnife.bind(this);

        final View view1 = LayoutInflater.from(this).inflate(R.layout.layout_recyclerview, null);
        RecyclerView rcv1 = (RecyclerView) view1.findViewById(R.id.rcv);
        rcv1.setLayoutManager(new LinearLayoutManager(this));
        rcv1.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new Holder(LayoutInflater.from(CoordinatorLayoutTestActivity.this).inflate(R.layout.item_list, null));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((Holder) holder).tvItem.setText(position + "" + position + "" + position + "" + position + "" + position + "");
                ((Holder) holder).tvItem.setTextColor(getResources().getColor(R.color.red));
            }

            @Override
            public int getItemCount() {
                return 12;
            }

            class Holder extends RecyclerView.ViewHolder {
                public TextView tvItem;

                public Holder(View itemView) {
                    super(itemView);
                    tvItem = (TextView) itemView.findViewById(R.id.tv_item);
                }
            }
        });

        final View view2 = LayoutInflater.from(this).inflate(R.layout.layout_recyclerview, null);
        RecyclerView rcv2 = (RecyclerView) view2.findViewById(R.id.rcv);
        rcv2.setLayoutManager(new LinearLayoutManager(this));
        rcv2.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new Holder(LayoutInflater.from(CoordinatorLayoutTestActivity.this).inflate(R.layout.item_list, null));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((Holder) holder).tvItem.setText(position + "" + position + "" + position + "" + position + "" + position + "");
                ((Holder) holder).tvItem.setTextColor(getResources().getColor(R.color.green));
            }

            @Override
            public int getItemCount() {
                return 12;
            }

            class Holder extends RecyclerView.ViewHolder {
                public TextView tvItem;

                public Holder(View itemView) {
                    super(itemView);
                    tvItem = (TextView) itemView.findViewById(R.id.tv_item);
                }
            }
        });


        final View view3 = LayoutInflater.from(this).inflate(R.layout.layout_recyclerview, null);
        RecyclerView rcv3 = (RecyclerView) view3.findViewById(R.id.rcv);
        rcv3.setLayoutManager(new LinearLayoutManager(this));
        rcv3.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new Holder(LayoutInflater.from(CoordinatorLayoutTestActivity.this).inflate(R.layout.item_list, null));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((Holder) holder).tvItem.setText(position + "" + position + "" + position + "" + position + "" + position + "");
                ((Holder) holder).tvItem.setTextColor(getResources().getColor(R.color.blue));
            }

            @Override
            public int getItemCount() {
                return 12;
            }

            class Holder extends RecyclerView.ViewHolder {
                public TextView tvItem;

                public Holder(View itemView) {
                    super(itemView);
                    tvItem = (TextView) itemView.findViewById(R.id.tv_item);
                }
            }
        });


        vp.setAdapter(new PagerAdapter() {

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "11111111111";
                    case 1:
                        return "22222222222";
                    case 2:
                        return "3333333333";
                }
                return "";
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                switch (position) {
                    case 0:
                        container.addView(view1);
                        return view1;
                    case 1:
                        container.addView(view2);
                        return view2;
                    case 2:
                        container.addView(view3);
                        return view3;
                }

                return super.instantiateItem(container, position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        mTabLayout.setupWithViewPager(vp);
    }
}
