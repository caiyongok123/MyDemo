package com.example.cy.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.widget.view.RotatingMenuLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 旋转菜单
 */
public class RotatingMenuActivity extends AppCompatActivity {

    @Bind(R.id.rml)
    RotatingMenuLayout rml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotating_menu);
        ButterKnife.bind(this);

        rml.setItemList(new ArrayList(){{add("");add("");add("");add("");add("");add("");}});
    }
}
