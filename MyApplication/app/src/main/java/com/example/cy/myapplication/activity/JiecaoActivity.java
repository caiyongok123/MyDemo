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
        binding.jc.setUp("http://183.60.145.23/youku/67788EDEBB24081076C5BB3F52/03000201004BDD351CB7B40208AA6C3BE39B90-2391-E335-7C9B-0BAC9FFECD5F.flv?sid=049447051517387bb1ae2_00&sign=f9c14030fde56b9b2adcc0e2bd0ecd25&ctype=87","完美的一天");
        binding.jc.ivThumb.setImageResource(R.drawable.bg);
        binding.jc1.setUp("https://d11.baidupcs.com/file/0fb4e901725b36900aa9f2a87af0a98d?bkt=p3-14000fb4e901725b36900aa9f2a87af0a98d65dc82980000043cfdba&xcode=5421a43b08853d1c4466c3c05771c3039c775c9e7b026fd90b2977702d3e6764&fid=3595802637-250528-1125354830873779&time=1494470197&sign=FDTAXGERLBHS-DCb740ccc5511e5e8fedcff06b081203-WaqlnNCFvlMuJVGZk68IzuYuJUw%3D&to=d11&size=71105978&sta_dx=71105978&sta_cs=2437&sta_ft=avi&sta_ct=7&sta_mt=6&fm2=MH,Yangquan,Netizen-anywhere,,guangdong,ct&newver=1&newfm=1&secfm=1&flow_ver=3&pkey=14000fb4e901725b36900aa9f2a87af0a98d65dc82980000043cfdba&sl=83034191&expires=8h&rt=pr&r=300516065&mlogid=3025320480629515175&vuk=3595802637&vbdid=2045980691&fin=03_%E4%BB%A3%E7%A0%81%E6%A0%B7%E5%BC%8F.avi&fn=03_%E4%BB%A3%E7%A0%81%E6%A0%B7%E5%BC%8F.avi&rtype=1&iv=0&dp-logid=3025320480629515175&dp-callid=0.1.1&hps=1&csl=300&csign=fz%2BSLm4%2BaP2XPAUDRZQqk%2BvpDwA%3D&by=themis","白鸽");
        binding.jc1.ivThumb.setImageResource(R.drawable.bg);
        binding.jc2.setUp("http://125.71.238.129:7086/Files/MVideo/20160118nx.mp4","白玫瑰");
        binding.jc2.ivThumb.setImageResource(R.drawable.bg);
    }

    @Override
    protected void onPause() {
        try {
            JCVideoPlayer.releaseAllVideos();
        }catch (Exception e){}

        super.onPause();
    }
}
