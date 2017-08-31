package com.example.cy.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.widget.view.ItemsViewGroup;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemsViewGroupTestActivity extends AppCompatActivity {

    @Bind(R.id.ivg)
    ItemsViewGroup ivg;
    @Bind(R.id.ivg2)
    ItemsViewGroup ivg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_view_group_test);
        ButterKnife.bind(this);





    }


    public void setData(View view){
        final ArrayList<String> titles = new ArrayList<>();
        titles.add("啊发顺丰    qw");
        titles.add("啊发asdasd顺丰");
        titles.add("啊发顺a丰");
        titles.add("啊asd发顺丰");
        titles.add("11啊发顺丰");
        titles.add("22啊发顺丰");
        titles.add("22啊asdasd发顺丰");
        titles.add("22啊发s顺丰");
        titles.add("22啊发asdas顺丰");
        ivg.setMarginHDp(5);
        ivg.setMarginWDp(10);
        ivg.setData(titles,R.layout.item_button);

        ivg2.setMarginHDp(5);
        ivg2.setMarginWDp(10);
        ivg2.setData(titles,R.layout.item_button);

        ivg.setOnItemClickListener(new ItemsViewGroup.OnItemClickListener() {
            @Override
            public void onItemClick(TextView view, int position) {
                Toast.makeText(ItemsViewGroupTestActivity.this,titles.get(position),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setData2(View view){
        final ArrayList<String> titles = new ArrayList<>();
        titles.add("啊发顺丰    qw");
        titles.add("啊发asdasd顺丰");
        titles.add("啊发顺a丰");
        titles.add("啊asd发顺丰");
        titles.add("11啊发顺丰");
        titles.add("22啊发顺丰");
        titles.add("22啊asdasd发顺丰");
        titles.add("22啊发s顺丰");
        titles.add("22啊发asdas顺丰");
        ivg2.setMarginHDp(5);
        ivg2.setMarginWDp(10);
        ivg2.setData(titles,R.layout.item_button);
        ivg2.setOnItemClickListener(new ItemsViewGroup.OnItemClickListener() {
            @Override
            public void onItemClick(TextView view, int position) {
                Toast.makeText(ItemsViewGroupTestActivity.this,titles.get(position),Toast.LENGTH_LONG).show();
            }
        });
    }
}
