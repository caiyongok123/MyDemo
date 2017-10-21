package com.example.cy.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.widget.view.MyView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {


    @Bind(R.id.mv)
    MyView mv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

    }

}
