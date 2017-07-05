package com.example.cy.myapplication.activity;

import android.app.DialogFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.cy.myapplication.R;

import butterknife.ButterKnife;

public class DialogFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_fragment);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog md = new MyDialog();
                md.show(getFragmentManager(),"");
            }
        });



    }


    public static class MyDialog extends DialogFragment{

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);
            //设置偏向操作
            getDialog().getWindow().setGravity(Gravity.RIGHT|Gravity.BOTTOM);
            getDialog().getWindow().getAttributes().windowAnimations = R.style.CustomDialog;
            View view = inflater.inflate(R.layout.dialog_fragment, container);

            return view;
        }
    }
}
