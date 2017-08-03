package com.example.cy.myapplication.activity.filepath;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cy.myapplication.R;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilePathActivity extends AppCompatActivity {

    String path;
    ArrayList<String> list = new ArrayList<>();
    @Bind(R.id.rcv)
    RecyclerView rcv;
    @Bind(R.id.tv_path)
    TextView tvPath;

    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_path);
        ButterKnife.bind(this);

        path = Environment.getExternalStorageDirectory().getPath();

        rcv.setLayoutManager(new LinearLayoutManager(this));

        rcv.setAdapter(adapter = new RecyclerView.Adapter<ItemViewHolder>() {
            @Override
            public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ItemViewHolder(LayoutInflater.from(FilePathActivity.this).inflate(R.layout.item_file, null));
            }

            @Override
            public void onBindViewHolder(ItemViewHolder holder, final int position) {
                File file = new File(list.get(position));
                if (file.isDirectory()) {
                    holder.ivItem.setImageResource(R.mipmap.iv_directory);
                } else {
                    holder.ivItem.setImageResource(R.mipmap.iv_file);
                }
                holder.tvItem.setText(file.getName());
                holder.vItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        choosePath(list.get(position));
                    }
                });

            }

            @Override
            public int getItemCount() {
                return list.size();
            }
        });

        choosePath(path);
    }


    void choosePath(String path) {

        File file = new File(path);
        if (file.isFile()) {
            Toast.makeText(this, "文件路径:" + path, Toast.LENGTH_LONG).show();
            //finish();
        } else {
            this.path = path;
            tvPath.setText(path);
            list.clear();
            ArrayList<String> dList = new ArrayList<>();
            ArrayList<String> fList = new ArrayList<>();
            String[] files = file.list();
            if (files!=null){
                for (int i = 0; i < files.length; i++) {
                    File file1 = new File(path+"/"+files[i]);
                    if (file1.isDirectory()) {
                        dList.add(path+"/"+file1.getName());
                    } else {
                        fList.add(path+"/"+file1.getName());
                    }
                }
            }
            list.addAll(dList);
            list.addAll(fList);
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.bt_back)
    public void onViewClicked() {
        String pathTemp="";
        if (path.lastIndexOf('/')>0 ){
            pathTemp=path.substring(0,path.lastIndexOf('/'));
        }
        File file = new File(pathTemp);
        if (!file.exists() || file.list()==null || file.list().length==0){
            Toast.makeText(this,"无上级目录",Toast.LENGTH_LONG).show();
        }else {
            choosePath(pathTemp);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView ivItem;
        TextView tvItem;
        View vItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ivItem = (ImageView) itemView.findViewById(R.id.iv_item);
            tvItem = (TextView) itemView.findViewById(R.id.tv_item);
            vItem =itemView;
        }
    }


}
