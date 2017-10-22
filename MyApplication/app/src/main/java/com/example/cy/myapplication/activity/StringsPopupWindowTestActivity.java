package com.example.cy.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.widget.view.StringsPopupWindow;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StringsPopupWindowTestActivity extends AppCompatActivity {


    @Bind(R.id.bt)
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strings_popup_window_test);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.bt)
    public void onViewClicked() {

        ArrayList<String> strings = new ArrayList<>();
        strings.add("111111111");
        strings.add("222222222");
        strings.add("33333333");


        new StringsPopupWindow(StringsPopupWindowTestActivity.this, strings, R.layout.item_text_50dp_100dp) {
            @Override
            public void onItemClick(View itemView, int position, String itemString) {
                Toast.makeText(StringsPopupWindowTestActivity.this, itemString, Toast.LENGTH_LONG).show();
            }
        }.showAsDropDown(bt);
    }
}
