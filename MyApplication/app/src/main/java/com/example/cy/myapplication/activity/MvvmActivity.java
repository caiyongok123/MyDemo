package com.example.cy.myapplication.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.databinding.ActivityMvvmBinding;


public class MvvmActivity extends AppCompatActivity {

    public String text = "11111";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMvvmBinding vdb = DataBindingUtil.setContentView(this,R.layout.activity_mvvm);
        Entity en = new Entity();
        //en.name = "狗屎强";
        vdb.setPpa(en);
        //en.name="123";
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
}
