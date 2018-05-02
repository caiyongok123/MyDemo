package com.example.cy.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.activity.qrscan.QrDecoder;
import com.example.cy.myapplication.util.ToastUtil;
import com.example.cy.myapplication.widget.view.TitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QrCreateActivity extends AppCompatActivity {

    @Bind(R.id.titleBar)
    TitleBar titleBar;
    @Bind(R.id.et)
    EditText et;
    @Bind(R.id.bt_1)
    Button bt1;
    @Bind(R.id.bt_2)
    Button bt2;
    @Bind(R.id.iv_qr)
    ImageView ivQr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_create);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.bt_1, R.id.bt_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_1:
                //QrDecoder.
                break;
            case R.id.bt_2:
                break;
        }
    }
}
