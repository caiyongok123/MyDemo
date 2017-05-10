package com.example.cy.myapplication.activity;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.databinding.ActivityVitamioBundleBinding;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

import static com.example.cy.myapplication.R.id.vitamio;


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

        if (!LibsChecker.checkVitamioLibs(this))
            return;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_vitamio_bundle);

        //binding.setMode(new ViewModle(Environment.getExternalStorageState()+"/paa.ape"));
        binding.setMode(new ViewModle("http://videohy.tc.qq.com/video.dispatch.tc.qq.com/w0013ncil94.mp4?vkey=0B00F050FFAC8EE04CD8CF67DECE2D0AC7A679F05ED7103314B27052ED6678EA4A2902E68A5F0526E4F0FC24BA784EEDC4FAE09197E3ACDC8D6CB07FBE169ED3AE2979BAF303454664DAF3CCFA15D957DE9C7B88961CC5C3FDFA90009EE4D80EF543B59EBBFF6249B05F083946164B10&ocid=300556204"));
        //binding.vitamio
    }


    public static class ViewModle extends BaseObservable {
        public String path;


        public ViewModle(String path) {
            this.path = path;
        }

        @BindingAdapter("play")
        public static void play(final VideoView vv, String path) {
            vv.setVideoURI(Uri.parse(path));
            //默认的controller
            MediaController controller = new MediaController(vv.getContext());
            vv.setMediaController(controller);
            controller.setAnchorView(vv);
            vv.start();
            //缓冲监听
            vv.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    //percent 当前缓冲百分比
                }
            });
            vv.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    switch (what) {
                        //开始缓冲
                        case MediaPlayer.MEDIA_INFO_BUFFERING_START:
//                            percentTv.setVisibility(View.VISIBLE);
//                            netSpeedTv.setVisibility(View.VISIBLE);
                            mp.pause();
                            return true;
                        //缓冲结束
                        case MediaPlayer.MEDIA_INFO_BUFFERING_END:
//                            percentTv.setVisibility(View.GONE);
//                            netSpeedTv.setVisibility(View.GONE);
                            mp.start();
                            return true;

                    }
                    return false;
                }
            });
        }
    }
}
