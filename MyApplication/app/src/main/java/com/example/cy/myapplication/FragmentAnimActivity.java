package com.example.cy.myapplication;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cy.myapplication.fragment.MyFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * fragment的进入退出动画
 *
 * 不使用v4包的情况下(min API >=11)所对应的动画类型是Property Animation。
 * 即动画资源文件需要放在res\anim目录下，且根标签是<set>, <objectAnimator>, or <valueAnimator>三者之一。
 */
public class FragmentAnimActivity extends AppCompatActivity {

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_anim);
        ButterKnife.inject(this);

        fragment = new MyFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.setCustomAnimations(
                R.anim.anim_enter,
                R.anim.anim_exit,
                R.anim.anim_pop_enter,
                R.anim.anim_pop_exit
        );
        ft .add(R.id.fl_fragment,fragment);

        ft.commit();

    }

    @OnClick({R.id.bt1, R.id.bt2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                fragment = new MyFragment();
                getFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.anim_enter,
                        R.anim.anim_exit
                        ).replace(R.id.fl_fragment,fragment).commit();
                break;
            case R.id.bt2:
                getFragmentManager().beginTransaction().hide(fragment).commit();
                break;
        }
    }





}
