package com.example.cy.myapplication.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.databinding.ActivityMvvmBinding;


public class MvvmActivity extends AppCompatActivity {

    public String text = "11111";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMvvmBinding vdb = DataBindingUtil.setContentView(this,R.layout.activity_mvvm);
        Entity en = new Entity();
        en.name = "狗屎强";
        vdb.setPpa(en);
        en.setName("123");
    }


    public class Entity {
        public String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
