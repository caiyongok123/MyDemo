package com.example.cy.myapplication.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
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

    float y = -1;
    float temp;

    @Override
    protected void onResume() {
        super.onResume();
        if (y == -1) {
            y = ivAnim.getY();
            temp = (int) (getResources().getDisplayMetrics().density * 155);
            doAnim();
        }
    }

    int drawableId;

    float oldVal, newVal;

    /**
     * 根据参数播放动画
     */
    private void doAnim() {


        ObjectAnimator oa = ObjectAnimator.ofFloat(ivAnim, "anim", 0.0f, 1.0f, 0.0f)
                .setDuration(1000);
        oa.setRepeatCount(ValueAnimator.INFINITE);//无限循环;
        oa.setInterpolator(new AccelerateDecelerateInterpolator());
        oa.start();

        oa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float cVal = (float) animation.getAnimatedValue();
                if (oldVal < newVal && newVal > cVal) {
                    drawableId = drawableId == R.mipmap.ic_launcher ? R.mipmap.tx : R.mipmap.ic_launcher;
                    ivAnim.setImageResource(drawableId);
                }

                oldVal = newVal;
                newVal = cVal;
                ivAnim.setY(y + cVal * temp);

            }
        });

        ObjectAnimator oa2 = ObjectAnimator.ofFloat(ivAnim, "rotation", 0, 360).setDuration(500);
        oa2.setRepeatCount(ValueAnimator.INFINITE);//无限循环;
        oa2.setInterpolator(new LinearInterpolator());
        oa2.start();


    }
}
