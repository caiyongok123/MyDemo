package com.example.cy.myapplication.activity;

import android.databinding.DataBindingUtil;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.databinding.ActivityJiecaoBinding;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * 节操播放器的使用
 */
public class JiecaoActivity extends AppCompatActivity {

    ActivityJiecaoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_jiecao);
        binding.jc.setUp("http://videohy.tc.qq.com/video.dispatch.tc.qq.com/w0013ncil94.mp4?vkey=0B00F050FFAC8EE04CD8CF67DECE2D0AC7A679F05ED7103314B27052ED6678EA4A2902E68A5F0526E4F0FC24BA784EEDC4FAE09197E3ACDC8D6CB07FBE169ED3AE2979BAF303454664DAF3CCFA15D957DE9C7B88961CC5C3FDFA90009EE4D80EF543B59EBBFF6249B05F083946164B10&ocid=300556204","完美的一天");
        binding.jc.ivThumb.setImageResource(R.drawable.bg);
        binding.jc1.setUp("http://video.dispatch.tc.qq.com/g03420wgjgq.mp4?vkey=80618A9435D543BA411F0CD3043468AEF1DD712D7DC4152B5A63FF973980FF9815F65E11DB6759AA5E23B1DB4AE2ABC4DDE3FFBAC3E07A8BEC35BD44269B069EDBBED59A8EDFEBBD7BBA676FA330CBB427AF491D2DF54AF5E492FE0793F0389B37F345CEB85F5D2C9595596F8653F69A","白鸽");
        binding.jc1.ivThumb.setImageResource(R.drawable.bg);
        binding.jc2.setUp("http://videohy.tc.qq.com/video.dispatch.tc.qq.com/q00139kfw54.mp4?vkey=D801C4F1880302260775583AB70443C0CFAD4311C7780B46594BBDDBC3CF986A7534BBD336E3F5FB17F009BE8305C0B1E72237285E577AB458E10B6055DDA1B346BD43DC12CF753433A893D8D342D24D3DCB7F6264098156C123D3FC9B59D4BA4F1A27AD12607FF905DFE27BC5578916&ocid=349446060","白玫瑰");
        binding.jc2.ivThumb.setImageResource(R.drawable.bg);
    }

    @Override
    protected void onPause() {
        JCVideoPlayer.releaseAllVideos();
        super.onPause();
    }
}
