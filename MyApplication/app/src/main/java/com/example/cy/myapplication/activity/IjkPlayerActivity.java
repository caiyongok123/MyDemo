package com.example.cy.myapplication.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.databinding.ActivityIjkPlayerBinding;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * B站IJK播放器播放视频
 */

public class IjkPlayerActivity extends AppCompatActivity {

    ActivityIjkPlayerBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_ijk_player);
        /*try {
            binding.vv.setDataSource("http://125.71.238.129:7086/Files/MVideo/20160118nx.mp4");
            binding.vv.start();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        LinkedHashSet<String> lhs = new LinkedHashSet<>();


    }
}
