package com.example.cy.myapplication.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cy.myapplication.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;

import android.provider.MediaStore.Images.Thumbnails;

public class ImagesActivity extends AppCompatActivity {

    @InjectView(R.id.rv)
    RecyclerView rv;
    MyAdapter adapter;

    ContentResolver cr;

    // 缩略图列表
    HashMap<String, String> thumbnailList = new HashMap<String, String>();
    // 专辑列表
    List<HashMap<String, String>> albumList = new ArrayList<HashMap<String, String>>();


    /**
     * 系统相册所有图片
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        ButterKnife.inject(this);

        initView();
        initData();
    }

    private void initView() {
        rv.setLayoutManager(new GridLayoutManager(this,4));
        adapter = new MyAdapter();
        rv.setAdapter(adapter);
    }

    private void initData(){
        cr = getContentResolver();
        getThumbnail();
        Set<Map.Entry<String,String>> entrys = thumbnailList.entrySet();
        Iterator it = entrys.iterator();
        while (it.hasNext()){
            Map.Entry<String,String> entry = (Map.Entry<String, String>) it.next();
            Log.e("xxxxxxxxxxx",entry.getKey()+"=============="+entry.getValue());
            adapter.uris.add(entry.getValue());
        }
        adapter.notifyDataSetChanged();
    }





    class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder>{

        List<String> uris = new ArrayList();

        @Override
        public MyAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) LayoutInflater.from(ImagesActivity.this).inflate(R.layout.item_image,parent,false);
            return new Holder(simpleDraweeView);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.sdv.setImageURI(Uri.parse(uris.get(position)));

        }

        @Override
        public int getItemCount() {
            return uris.size();
        }

        public class Holder extends RecyclerView.ViewHolder{

            public SimpleDraweeView sdv;

            public Holder(View itemView) {
                super(itemView);
                sdv = (SimpleDraweeView) itemView;
            }
        }
    }





    /**
     * 得到缩略图，这里主要得到的是图片的ID值
     */
    private void getThumbnail() {
        String[] projection = { Thumbnails._ID, Thumbnails.IMAGE_ID,
                Thumbnails.DATA};
        Cursor cursor1 = Thumbnails.queryMiniThumbnails(cr, Thumbnails.EXTERNAL_CONTENT_URI,
                Thumbnails.MINI_KIND, projection);
        getThumbnailColumnData(cursor1);
        cursor1.close();
    }

    /**
     * 从数据库中得到缩略图
     * @param cur
     */
    private void getThumbnailColumnData(Cursor cur) {
        if (cur.moveToFirst()) {
            int image_id;
            String image_path;
            int image_idColumn = cur.getColumnIndex(Thumbnails.IMAGE_ID);
            int dataColumn = cur.getColumnIndex(Thumbnails.DATA);
            do {
                image_id = cur.getInt(image_idColumn);
                image_path = cur.getString(dataColumn);
                thumbnailList.put("" + image_id, image_path);
            } while (cur.moveToNext());
        }
    }


    public static String getImageAbsolutePath(Activity context, Uri imageUri) {
            if (context == null || imageUri == null)  
                return null;  
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
                    if (isExternalStorageDocument(imageUri)) {  
                            String docId = DocumentsContract.getDocumentId(imageUri);  
                            String[] split = docId.split(":");  
                            String type = split[0];  
                            if ("primary".equalsIgnoreCase(type)) {  
                                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                                }  
                        } else if (isDownloadsDocument(imageUri)) {  
                            String id = DocumentsContract.getDocumentId(imageUri);  
                            Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                            return getDataColumn(context, contentUri, null, null);  
                        } else if (isMediaDocument(imageUri)) {  
                            String docId = DocumentsContract.getDocumentId(imageUri);  
                            String[] split = docId.split(":");  
                            String type = split[0];  
                            Uri contentUri = null;  
                            if ("image".equals(type)) {  
                                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;  
                                } else if ("video".equals(type)) {  
                                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;  
                                } else if ("audio".equals(type)) {  
                                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;  
                                }  
                            String selection = MediaStore.Images.Media._ID + "=?";  
                            String[] selectionArgs = new String[] { split[1] };  
                            return getDataColumn(context, contentUri, selection, selectionArgs);  
                        }  
                } // MediaStore (and general)  
            else if ("content".equalsIgnoreCase(imageUri.getScheme())) {  
                    // Return the remote address  
                    if (isGooglePhotosUri(imageUri))  
                        return imageUri.getLastPathSegment();  
                    return getDataColumn(context, imageUri, null, null);  
                }  
            // File  
            else if ("file".equalsIgnoreCase(imageUri.getScheme())) {  
                    return imageUri.getPath();  
                }  
            return null;  
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
            Cursor cursor = null;  
            String column = MediaStore.Images.Media.DATA;  
            String[] projection = { column };  
            try {  
                    cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);  
                    if (cursor != null && cursor.moveToFirst()) {  
                            int index = cursor.getColumnIndexOrThrow(column);  
                            return cursor.getString(index);  
                        }  
                } finally {  
                    if (cursor != null)  
                        cursor.close();  
                }  
            return null;  
    }  
              
            /** 
              * @param uri The Uri to check. 
              * @return Whether the Uri authority is ExternalStorageProvider. 
              */  
    public static boolean isExternalStorageDocument(Uri uri) {  
            return "com.android.externalstorage.documents".equals(uri.getAuthority());  
    }  
              
            /** 
              * @param uri The Uri to check. 
              * @return Whether the Uri authority is DownloadsProvider. 
              */  
    public static boolean isDownloadsDocument(Uri uri) {  
            return "com.android.providers.downloads.documents".equals(uri.getAuthority());  
    }  
              
            /** 
              * @param uri The Uri to check. 
              * @return Whether the Uri authority is MediaProvider. 
              */  
    public static boolean isMediaDocument(Uri uri) {  
            return "com.android.providers.media.documents".equals(uri.getAuthority());  
    }  
              
            /** 
              * @param uri The Uri to check. 
              * @return Whether the Uri authority is Google Photos. 
              */  
    public static boolean isGooglePhotosUri(Uri uri) {  
            return "com.google.android.apps.photos.content".equals(uri.getAuthority());  
    }
}
