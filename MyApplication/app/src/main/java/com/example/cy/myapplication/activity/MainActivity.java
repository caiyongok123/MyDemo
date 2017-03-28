package com.example.cy.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.cy.myapplication.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.button,R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,R.id.button7})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                startActivity(new Intent(this, TabLayoutTestActivity.class));
                break;
            case R.id.button1:
                startActivity(new Intent(this, CoordinatorLayoutTestActivity.class));
                break;
            case R.id.button2:
                startActivity(new Intent(this, GifActivity.class));
                break;
            case R.id.button3:
                startActivity(new Intent(this, FragmentAnimActivity.class));
                break;
            case R.id.button4:
                startActivity(new Intent(this,AesActivity.class));
                break;
            case R.id.button5:
                startActivity(new Intent(this,ThreadActivity.class));
                break;
            case R.id.button6:
                startActivity(new Intent(this,ImagesActivity.class));
                break;
            case R.id.button7:
                startActivity(new Intent(this,SystemDownLoadActivity.class));
                break;
        }
    }

}
