package com.example.cy.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.cy.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class JPushTestActivity extends AppCompatActivity {

    @Bind(R.id.bt1)
    Button bt1;
    @Bind(R.id.bt2)
    Button bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpush_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt1, R.id.bt2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                JPushInterface.setAlias(this,1,"PPA1");
                break;
            case R.id.bt2:
                JPushInterface.setAlias(this,1,"PPA2");
                break;
            case R.id.bt3:
                //激光推送的删除别名方法无效，这他妈的就是个装饰啊，清除别名只能用JPushInterface.setAlias(this,1,"");这样来覆盖掉
                JPushInterface.deleteAlias(this,1);
                break;
        }
    }
}
