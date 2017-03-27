package com.example.cy.myapplication.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.Tab;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.cy.myapplication.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class TabLayoutTestActivity extends AppCompatActivity {


    @InjectView(R.id.tabLayout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout_test);
        ButterKnife.inject(this);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Toast.makeText(TabLayoutTestActivity.this, "0", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(TabLayoutTestActivity.this, "1", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(TabLayoutTestActivity.this, "2", Toast.LENGTH_LONG).show();
                        break;

                }
            }

            @Override
            public void onTabUnselected(Tab tab) {

            }

            @Override
            public void onTabReselected(Tab tab) {

            }
        });
    }

}
