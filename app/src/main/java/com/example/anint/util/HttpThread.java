package com.example.anint.util;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpThread extends Thread {
    final int CONNECT_TIMEOUT = 60;
    final int READ_TIMEOUT = 100;
    final int WRITE_TIMEOUT = 60;
    final OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
            .build();

    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    String json="";
    String url="http://212.64.81.216:5000/";
    String res="";


    HttpThread(String json,String url){
        this.json=json;
        this.url=this.url+url;
    }

    @Override

    public void run() {

        Log.d("JSON", ">>>>>>>>>>>>"+json);
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
                assert response.body() != null;
                res =response.body().string();
                Log.d("Reeeesss", ">>>>>>>>> "+res);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}

