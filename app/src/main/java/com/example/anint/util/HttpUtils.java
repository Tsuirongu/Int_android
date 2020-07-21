package com.example.anint.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    final int CONNECT_TIMEOUT = 60;
    final int READ_TIMEOUT = 100;
    final int WRITE_TIMEOUT = 60;
    String name;
    String email;
    String pwd;
    int uid;
    String tid;
    String path;
    String method;
    String url;
    public HttpUtils(){}

    public HttpUtils(String url,int uid){
        this.url=url;
        this.uid=uid;
    }

    public HttpUtils(String url,int uid,String tid){
        this.url=url;
        this.uid=uid;
        this.tid=tid;
    }
    public HttpUtils(String url){
        this.url=url;
    }
    public HttpUtils(String url,String username,String pwd){
        this.url=url;
        this.name=username;
        this.pwd=pwd;
    }
    public HttpUtils(String url,String username,String pwd,String email){
        this.url=url;
        this.name=username;
        this.pwd=pwd;
        this.email=email;
    }

    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    final OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
            .build();

    /**
     * 从服务器获取任务数据
     *
     * @param url  URL
     * @param uid 用户标识符，int
     * @return Json字符串
     * Json
     * {
     *     "taskLen":@@int@@,
     *     "tasks":{
     *         [
     *              "tid":@@String@@,
     *              "taskName":@@String@@,
     *              "taskType":@@String@@,
     *              "taskPayback":@@int@@,
     *              "taskImage":@@bitmap/String@@,
     *              "Ouid":@@int@@
     *         ]......
     *     },
     *     "LastTaskTid":@@String@@
     * }
     */
    public String getTaskFromServer(final String url,int uid)throws IOException {
        final String[] res = {""};
        final JSONObject resJs=null;
        new Thread(new Runnable() {
            @Override
            public void run() {
//                JSONObject jsonObject=new JSONObject();

                String json="{\"uid\":"+uid+"}";

//                String json="{\"username\":"+name+",\"pwd\":"+pwd+"}";
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
                        Log.d(">>>>>>>>>>", "run: "+res[0]);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
        return res[0];
    }

    /**
     * 登陆服务器
     *
     * username
     * pwd
     *
     * @return Json字符串
     * Json
     * {
     *     "FirstTid":@@String@@,
     *     "taskLen":@@int@@,
     *     "tasks":{
     *         [
     *              "uid":@@int@@,
     *              "taskPayback":@@int@@,
     *              "taskImage":@@bitmap/String@@,
     *              "Ouid":@@int@@
     *         ]......
     *     },
     *     "LastTaskTid":@@String@@
     * }
     */
    public String loginFromServer()throws IOException {
        final String[] res = {""};
        final JSONObject resJs=null;
        new Thread(new Runnable() {
            @Override
            public void run() {
//                JSONObject jsonObject=new JSONObject();

                String json="{\"username\":"+name+",\"pwd\":"+pwd+"}";

//                String json="{\"username\":"+name+",\"pwd\":"+pwd+"}";
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

                        Log.d(">>>>>>>>>>", "登陆结果： "+res[0]);
                        JSONObject out=new JSONObject(res[0]);//根据返回的结果进行处理。

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
        return res[0];
    }

    /**
     * 注册服务器
     *
     * username
     * pwd
     * email
     *
     * @return Json字符串
     * Json
     * {
     *     "FirstTid":@@String@@,
     *     "taskLen":@@int@@,
     *     "tasks":{
     *         [
     *              "uid":@@int@@,
     *              "taskPayback":@@int@@,
     *              "taskImage":@@bitmap/String@@,
     *              "Ouid":@@int@@
     *         ]......
     *     },
     *     "LastTaskTid":@@String@@
     * }
     */
    public String registerFromServer(String url)throws IOException {
        final String[] res = {""};
        final JSONObject resJs=null;
        new Thread(new Runnable() {
            @Override
            public void run() {
//                JSONObject jsonObject=new JSONObject();

                String json="{\"username\":"+name+",\"pwd\":"+pwd+",\"email\":"+"}";

//                String json="{\"username\":"+name+",\"pwd\":"+pwd+"}";
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
                        Log.d(">>>>>>>>>>", "注册结果： "+res[0]);
                        JSONObject out=new JSONObject(res[0]);//根据返回的结果进行处理。

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
        return res[0];
    }


    /**
     * 从服务器获取任务数据
     *
     * @param url  URL
     * @param uid 用户标识符，int
     * @return Json字符串
     * Json
     * {
     *     "taskLen":@@int@@,
     *     "tasks":{
     *         [
     *              "tid":@@String@@,
     *              "taskName":@@String@@,
     *              "taskType":@@String@@,
     *              "taskPayback":@@int@@,
     *              "taskImage":@@bitmap/String@@,
     *              "Ouid":@@int@@
     *         ]......
     *     },
     *     "LastTaskTid":@@String@@
     * }
     */
    public String getTask(final String url,int uid,String tid)throws IOException {
        final String[] res = {""};
        final JSONObject resJs=null;
        new Thread(new Runnable() {
            @Override
            public void run() {
//                JSONObject jsonObject=new JSONObject();

                String json="{\"uid\":"+uid+",\"tid\":"+tid+"}";

//                String json="{\"username\":"+name+",\"pwd\":"+pwd+"}";
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
                        Log.d(">>>>>>>>>>", "接受任务的响应: "+res[0]);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
        return res[0];
    }

}
