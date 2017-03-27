package com.example.cy.myapplication.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.cy.myapplication.R;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class GifActivity extends AppCompatActivity {

    @InjectView(R.id.sdv)
    SimpleDraweeView sdv;
    @InjectView(R.id.imageView)
    ImageView imageView;

    /**
     * 加载GIF动画图
     *
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        ButterKnife.inject(this);
    }


    @OnClick({R.id.button1, R.id.button2})
    public void onClick(View view) {
        switch (view.getId()) {
            /**
             * Fresco和Glide一起循环播放
             */
            case R.id.button1:
                Glide.with(this).load(R.drawable.gif3).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
                Uri uri = UriUtil.getUriForResourceId(R.drawable.gif3);
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(true)
                        .build();
                sdv.setController(controller);


                break;
            /**
             * Glide播放一次，停在最后一帧
             */
            case R.id.button2:
                Glide.with(this).load(R.drawable.gif2).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(new GlideDrawableImageViewTarget(imageView, 1));
                break;
        }
    }
}
