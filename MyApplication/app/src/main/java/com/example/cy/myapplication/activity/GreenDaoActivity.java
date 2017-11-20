package com.example.cy.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cy.common.MyApplication;
import com.example.cy.myapplication.R;
import com.example.cy.myapplication.entity.User;
import com.google.gson.Gson;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * GreenDao数据库操作
 */
public class GreenDaoActivity extends AppCompatActivity {

    @Bind(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1://增
                try {
                    User user = new User();
                    user.setId(new Long("121"));
                    user.setUsername(""+System.currentTimeMillis());
                    user.setNickname("王大王");

                    long l = MyApplication.myApplication.daoSession.getUserDao().insert(user);
                    Toast.makeText(this,""+l,Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(this,"插入失败",Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.button2:
                try {
                    MyApplication.myApplication.daoSession.getUserDao().deleteByKey(new Long("121"));
                    Toast.makeText(this,"删除成功",Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(this,"删除失败",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button3:
                try {
                    User user = new User();
                    user.setId(new Long("121"));
                    user.setUsername(""+System.currentTimeMillis());
                    user.setNickname("王大王改");
                    MyApplication.myApplication.daoSession.getUserDao().updateInTx(user);
                    Toast.makeText(this,"修改成功",Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(this,"修改失败",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button4:
                try {
                    List<User> list = MyApplication.myApplication.daoSession.getUserDao().queryBuilder().build().list();
                    tv.setText(new Gson().toJson(list));
                    Toast.makeText(this,"查询成功",Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(this,"查询失败",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
