package com.example.anint.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.ConversationActions;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.anint.R;
import com.example.anint.util.HttpUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity{
    private Button Bt_regi;
    private EditText Et_register;
    private EditText Et_email;
    private EditText Et_password;
    private EditText Et_password2;

    String url="http://212.64.81.216:5000/register";
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

        setContentView( R.layout.activity_register);

        Et_register=(EditText)findViewById(R.id.registerName);
        Et_password=(EditText)findViewById(R.id.registerPassword);
        Et_password2=(EditText)findViewById(R.id.registerPassword2);
        Et_email=(EditText)findViewById(R.id.registerEmail);
        Bt_regi=(Button)findViewById(R.id.registerButton);
        Toast.makeText(RegisterActivity.this,"正在注册页面",Toast.LENGTH_SHORT).show();

        Bt_regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String pwd1=Et_password.getText().toString();
                    String pwd2=Et_password2.getText().toString();
                    if(pwd1.equals(pwd2)){
                        String username=Et_register.getText().toString();
                        String email=Et_email.getText().toString();
//                        HttpUtils hu=new HttpUtils();
//                        hu.HttpPutUp("http://212.64.81.216:5000/register",username,email,pwd1);
                        HttpUtils th=new HttpUtils(url,username,email,pwd1);
//                        th.init(username,email,pwd1,"regi");
//                        String res=th.getRes();
                        String res=th.registerFromServer(url);
                        JSONObject jsonObject=new JSONObject();

                        if(jsonObject.getString("result").equals("0")){
                            Toast.makeText(RegisterActivity.this,"OK",Toast.LENGTH_SHORT).show();
                        }else if(jsonObject.getString("result").equals("1")){
                            Toast.makeText(RegisterActivity.this,"已经注册",Toast.LENGTH_SHORT).show();
                        }else if(jsonObject.getString("result").equals("2")){
                            Toast.makeText(RegisterActivity.this,"其他错误",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(RegisterActivity.this,"密码不一致",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
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


//    private String registerNameToServer(final String url, final String name, final String email, final String pwd) throws IOException, InterruptedException {
//        final String[] res = {""};
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                String json="{\"username\":\""+name+"\",\"email\":\""+email+"\",\"pwd\":\""+pwd+"\"}";
//                Log.d("JSON", ">>>>>>>>>>>>"+json);
//                RequestBody body=RequestBody.create(JSON,json);
//                Request request=new Request.Builder().url(url).post(body).build();
//                Response response= null;
//                try {
//                    response = client.newCall(request).execute();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if(response.isSuccessful()){
//
//                    try {
//                        assert response.body() != null;
//                        res[0] =response.body().string();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        }).start();
//
//        return res[0];
//
//        }
}
