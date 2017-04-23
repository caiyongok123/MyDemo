package com.example.cy.myapplication.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.cy.myapplication.R;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
                final SimpleDraweeView simpleDraweeView = (SimpleDraweeView) item.findViewById(R.id.sdv);
                simpleDraweeView.setImageURI(Uri.parse(urls.get(position)));
                findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Uri.parse(urls.get(position)) != null) {
                            saveBitMap(returnBitmap(Uri.parse(urls.get(position))));
                        } else {
                            Toast.makeText(NetImagesActivity.this, "请稍后……", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                container.addView(item);
                return item;
            }
        };
        vp.setAdapter(adapter);

        urls = (ArrayList<String>) getIntent().getSerializableExtra("urls");
        adapter.notifyDataSetChanged();
    }

    void saveBitMap(Bitmap bitmap) {
        String imageName = System.currentTimeMillis() + ".png";
        File f = new File(SavePath, imageName);
        if (f.exists()) {
            f.delete();
        }

        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(NetImagesActivity.this,"已保存为:"+SavePath+imageName,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(NetImagesActivity.this,"图片保存失败",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    private Bitmap returnBitmap(Uri uri) {

        Bitmap bitmap = null;
        FileBinaryResource resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainFileCache().getResource(new SimpleCacheKey(uri.toString()));
        File file = resource.getFile();
        bitmap = BitmapFactory.decodeFile(file.getPath());
        return bitmap;

    }
}
