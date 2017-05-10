package com.example.cy.myapplication.activity;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.databinding.ActivityVitamioBundleBinding;

import io.vov.vitamio.widget.VideoView;


/**
 * VitamioBundle框架播放视频
 * <p>
 * <p>
 * 集成流程：http://blog.csdn.net/rd_w_csdn/article/details/53466372
 */
public class VitamioBundleActivity extends AppCompatActivity {

    ActivityVitamioBundleBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vitamio_bundle);
        binding.setMode(new ViewModle(""));
        //binding.vitamio
    }


    public static class ViewModle extends BaseObservable {
        public String path;


        public ViewModle(String path) {
            this.path = path;
        }

        @BindingAdapter("play")
        public static void play(VideoView vv, String path) {

        }
    }
}
