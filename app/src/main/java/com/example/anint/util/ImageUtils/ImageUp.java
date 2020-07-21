package com.example.anint.util.ImageUtils;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.anint.R;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ImageUp extends AppCompatActivity {
    private static final int REQUEST_CHOOSE_IMAGE = 0x01;
    private static final int RC_CHOOSE_PHOTO = 0x02;
    private static final int REQUEST_WRITE_EXTERNAL_PERMISSION_GRANT = 0xff;
    private TextView photoPath;
    private Button post;
    private ImageView photo;
    String image_path;
    private File file;
    Uri uri;
    private final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        post=(Button)findViewById(R.id.choose_image_btn);
//        post.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                prepareToOpenAlbum();//进入相册方法
//            }
//        });
//        //用户头像
//        photo = (ImageView) findViewById(R.id.photo);
    }

    public void prepareToOpenAlbum() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //未授权，申请授权(从相册选择图片需要读取存储卡的权限)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RC_CHOOSE_PHOTO);
        } else {
            //已授权，获取照片
            openAlbum();
        }
    }
    //跳转到相册
    public void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_IMAGE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHOOSE_IMAGE && resultCode == RESULT_OK) {
            //获取图片的uri
            uri= data.getData();
            //图片的绝对路径
            image_path = ImageUtils.getRealPathFromUri(this, uri);
            Bitmap bitmap = BitmapFactory.decodeFile(image_path);
            photo.setImageBitmap(bitmap);
            file = new File(image_path);
            //选取完图片后调用上传方法，将图片路径放入参数中
            sendStudentInfoToServer(file);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void sendStudentInfoToServer(File file) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        // 设置文件以及文件上传类型封装
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);

        // 文件上传的请求体封装
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("img", file.getName(), requestBody)
                .build();

        Request request = new Request.Builder()
                .url("http://212.64.81.216:5000/infer")
                .post(multipartBody)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ImageUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                setContentView(R.layout.activity_main);
//                final TextView hello=(TextView)findViewById(R.id.hello);
//                final ImageView photo=(ImageView)findViewById(R.id.photo);
                final String result = response.body().string();
                Gson gson = new Gson();
                final UpLoadBean upLoadBean = gson.fromJson(result, UpLoadBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (upLoadBean != null) {
                            if (upLoadBean.getCode() == 200) {
//                                hello.setText(upLoadBean.getRes());
                                Glide.with(ImageUp.this).load(upLoadBean.getData().getUrl()).into(photo);
                            } else {
//                                hello.setText(upLoadBean.getRes());
                            }

                        } else {
                            Toast.makeText(ImageUp.this, "失败", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }



}