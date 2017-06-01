package com.example.cy.myapplication.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.cy.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 图片上下回弹跳的动画
 */
public class AnimalUpAndDownActivity extends AppCompatActivity {

    @Bind(R.id.iv_anim)
    ImageView ivAnim;
    @Bind(R.id.rl_anim)
    RelativeLayout rlAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_up_and_down);
        ButterKnife.bind(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        doAnim();
    }

    /**
     * 根据参数播放动画
     */
    private void doAnim() {
        int temp = (int) (getResources().getDisplayMetrics().density*155);
        ObjectAnimator oa = ObjectAnimator.ofFloat(ivAnim,"anim",1.0f,0.0f);
    }
}
