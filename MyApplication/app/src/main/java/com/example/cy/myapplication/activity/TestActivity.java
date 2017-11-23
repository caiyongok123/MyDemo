package com.example.cy.myapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.cy.common.base.BaseActivity;
import com.example.cy.myapplication.R;

import java.util.ArrayList;

public class TestActivity extends BaseActivity {

    ListFragment lf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        lf = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.lf);
        lf.setListAdapter(new ArrayAdapter<String>(TestActivity.this, android.R.layout.simple_list_item_1, new ArrayList<String>() {{
            add("1111111111");
            add("1111111111");
            add("1111111111");
            add("1111111111");
            add("1111111111");
            add("1111111111");
        }}));
        lf.setEmptyText("dddddd");


        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    class Bv extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    @Override
    public void onBackClick() {
        Toast.makeText(mContext, "Back", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRightClick() {
        Toast.makeText(mContext, "Right", Toast.LENGTH_LONG).show();
    }
}
