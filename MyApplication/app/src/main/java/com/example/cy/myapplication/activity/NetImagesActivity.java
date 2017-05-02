package com.example.cy.myapplication.activity;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.cy.myapplication.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

/**
 * 显示一组网络图片
 */
public class NetImagesActivity extends AppCompatActivity {

    ViewPager vp;
    PagerAdapter adapter;
    ArrayList<String> urls = new ArrayList();

    String SavePath = Environment.getExternalStorageDirectory().getPath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_images);
        vp = (ViewPager) findViewById(R.id.vp);
        adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return urls.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                View item = LayoutInflater.from(NetImagesActivity.this).inflate(R.layout.item_net_image, null);

                ImageView ivItem = (ImageView) item.findViewById(R.id.iv_item);
                final String url = urls.get(position);
                Glide.with(NetImagesActivity.this).load(url).into(ivItem);
                container.addView(item);
                return item;
            }
        };
        vp.setAdapter(adapter);

        urls = (ArrayList<String>) getIntent().getSerializableExtra("urls");
        adapter.notifyDataSetChanged();

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Uri.parse(urls.get(vp.getCurrentItem())) != null) {
                    new GetImageCacheAsyncTask(NetImagesActivity.this).execute(urls.get(vp.getCurrentItem()));
                } else {
                    Toast.makeText(NetImagesActivity.this, "请稍后……", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void saveBitMapAsFile(File file) {
        String imageName = System.currentTimeMillis() + ".png";
        File f = new File(SavePath, imageName);
        if (f.exists()) {
            f.delete();
        }

        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(file);
            outStream = new FileOutputStream(f);
            in = inStream.getChannel();
            out = outStream.getChannel();
            in.transferTo(0, in.size(), out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {inStream.close();}catch (Exception e){}
            try {inStream.close();}catch (Exception e){}
            try {outStream.close();}catch (Exception e){}
            try {out.close();}catch (Exception e){}
        }
        Toast.makeText(NetImagesActivity.this, "已保存为:" + SavePath + imageName, Toast.LENGTH_LONG).show();

    }

    private class GetImageCacheAsyncTask extends AsyncTask<String, Void, File> {
        private final Context context;

        public GetImageCacheAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected File doInBackground(String... params) {

            String imgUrl = params[0];
            try {
                return Glide.with(context)
                        .load(imgUrl)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(File result) {
            if (result == null) {
                Toast.makeText(NetImagesActivity.this, "请稍后……", Toast.LENGTH_LONG).show();
                return;
            }
            saveBitMapAsFile(result);
        }
    }
}
