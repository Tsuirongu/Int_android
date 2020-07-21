package com.example.anint.Task.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.anint.R;
import com.example.anint.util.AlbumUtils.PermissionUtils;
import com.example.anint.util.GlideEngine;
import com.example.anint.util.MainAdapter;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class TaskDetail extends AppCompatActivity {

    private final int PERMISSION_CODE_FIRST = 0x14;//权限请求码
    private             boolean isToast      = true;//是否弹吐司，为了保证for循环只弹一次
//    public static final String  CROP_WIDTH   = "crop_width";
//    public static final String  CROP_HEIGHT  = "crop_Height";
//    public static final String  RATIO_WIDTH  = "ratio_Width";
//    public static final String  RATIO_HEIGHT = "ratio_Height";
//    public static final String  ENABLE_CROP  = "enable_crop";
    private String image_path;
    private int     mCropWidth;
    private int     mCropHeight;
    private int     mRatioWidth;
    private int     mRatioHeight;
    private boolean mCropEnabled;



    private ArrayList<Photo> selectedPhotoList = new ArrayList<>();
    private Bitmap bitmap = null;
    private MainAdapter adapter;

    private ImageButton Iv_image;
    private TextView name;
    private TextView number;
    private TextView intro;
    private Button getTask;

    String url="http://212.64.81.216:5000/gettask";
    private Uri imageUri;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_detail);

//        mCropEnabled = getIntent().getBooleanExtra(ENABLE_CROP, true);
//        mCropWidth = getIntent().getIntExtra(CROP_WIDTH, 200);
//        mCropHeight = getIntent().getIntExtra(CROP_HEIGHT, 200);
//        mRatioWidth = getIntent().getIntExtra(RATIO_WIDTH, 1);
//        mRatioHeight = getIntent().getIntExtra(RATIO_HEIGHT, 1);
//


        init();
        Bundle bundle=this.getIntent().getExtras();
        String taskName=bundle.getString("taskName");

//        final String uid=bundle.getString("uid");
//        final String tid=bundle.getString("tid");
        name.setText(taskName);
        final boolean checkPermissionFirst = PermissionUtils.checkPermissionFirst(this, PERMISSION_CODE_FIRST,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA});

        Iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d("Iv_image", ">>>>>>>>>>>");
                    if (checkPermissionFirst) {
                        //selectPicture();

                        EasyPhotos.createAlbum(TaskDetail.this, true, GlideEngine.getInstance())
                                .setFileProviderAuthority("com.huantansheng.easyphotos.demo.fileprovider")
                                .start(101);


                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });



    }
    final int CONNECT_TIMEOUT = 60;
    final int READ_TIMEOUT = 100;
    final int WRITE_TIMEOUT = 60;


    final OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
            .build();

    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    private String getTask(final String url, final String uid, final String tid)throws IOException {
        final String[] res = {""};
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json="{\"uid\":"+uid+",\"tid\":"+tid+"}";
                RequestBody body=RequestBody.create(JSON,json);
                Request request=new Request.Builder().url(url).post(body).build();
                Response response= null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(response.isSuccessful()){

                    try {
                        res[0] =response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
        return res[0];






    }


    private void init(){

//        Iv_image=(ImageButton) findViewById(R.id.taskImageDetail);
//        name=(TextView)findViewById(R.id.taskNameDetail);
//        number=(TextView)findViewById(R.id.taskNumber);
//        intro=(TextView)findViewById(R.id.taskIntro);
//        getTask=(Button)findViewById(R.id.getTask);

    }





//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//            if (resultCode == RESULT_OK) {
//                if(requestCode==101) {
//                    ArrayList<Photo> photos =
//                            data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
//
//                    selectedPhotoList.clear();
//                    selectedPhotoList.addAll(photos);
//                    imageUri = selectedPhotoList.get(0).uri;
//                    Log.d("Uri>>>>>>>>>>>>>", imageUri.toString());
//                    image_path = ImageUtils.getImagePath(this, imageUri);
//                    Log.d("path>>>>>>>>>>>>>", image_path);
//                    Bitmap bitmap = BitmapFactory.decodeFile(image_path);
//
////                    bitmap = makeBitmap(bitmap);
//                    Iv_image.setImageBitmap(bitmap);
//
//
//                }
//
//
//
//
//            }
//
//    }
//修改图片尺寸
    private Bitmap makeBitmap(Bitmap bitmap){
        return Bitmap.createScaledBitmap(bitmap, 200, 200, true);
    }

//    @Override
//    public void onBackPressed() {
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//            return;
//        }
//        super.onBackPressed();
//    }


}
