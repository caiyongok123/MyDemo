package com.example.cy.myapplication.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.leon.vuforia.ImageTargetRenderer;
import com.leon.vuforia.ImageTargets;
import com.leon.vuforia.SampleApplicationSession;
import com.vuforia.ImageTarget;

/**
 * Created by joson on 2017/4/19.
 */

public class ArActivity extends ImageTargets {

    SampleApplicationSession vuforiaAppSession;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vuforiaAppSession = new SampleApplicationSession(this);
        ImageTargetRenderer irt = new ImageTargetRenderer(this,vuforiaAppSession);

    }
}
