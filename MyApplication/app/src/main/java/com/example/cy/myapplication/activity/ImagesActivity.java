package com.example.cy.myapplication.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cy.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImagesActivity extends AppCompatActivity {

    @Bind(R.id.rv)
    RecyclerView rv;
    MyAdapter adapter;

    int size = 0;


    /**
     * 系统相册所有图片
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        ButterKnife.bind(this);
        initView();
        initData();
        size = getWindowManager().getDefaultDisplay().getWidth() / 3;
    }

    static class ImageModel {
        String id;
        String path;

        public ImageModel(String id, String path) {
            this.id = id;
            this.path = path;
        }
    }

    public static List<ImageModel> getImages(Context context) {
        List<ImageModel> list = new ArrayList<ImageModel>();
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA,};
        String sortOrder = MediaStore.Images.Media.DATE_ADDED + " desc";
        Cursor cursor = contentResolver.query(uri, projection, null, null, sortOrder);
        int iId = cursor.getColumnIndex(MediaStore.Images.Media._ID);
        int iPath = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String id = cursor.getString(iId);
            String path = cursor.getString(iPath);
            ImageModel imageModel = new ImageModel(id, path);
            list.add(imageModel);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    private void initView() {
        rv.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new MyAdapter();
        rv.setAdapter(adapter);
    }

    private void initData() {
        adapter.imgs = getImages(this);
        adapter.notifyDataSetChanged();
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {

        List<ImageModel> imgs = new ArrayList();

        @Override
        public MyAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            ImageView imageView = (ImageView) LayoutInflater.from(ImagesActivity.this).inflate(R.layout.item_image, parent, false);
            return new Holder(imageView);
        }

        @Override
        public void onBindViewHolder(Holder holder, final int position) {
            holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    adapter.imgs.remove(position);
                    adapter.notifyItemRemoved(position);
                    if (position != imgs.size()) { // 如果移除的是最后一个，忽略
                        notifyItemRangeChanged(position, imgs.size());
                    }
                    return false;
                }
            });
            Glide.with(ImagesActivity.this).load(imgs.get(position).path).into(holder.imageView);

        }

        @Override
        public int getItemCount() {
            return imgs.size();
        }

        public class Holder extends RecyclerView.ViewHolder {

            public ImageView imageView;

            public Holder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView;
                imageView.setMinimumHeight(size);
            }
        }
    }

}
