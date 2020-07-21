package com.example.anint.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;


import com.example.anint.MainActivity;
import com.example.anint.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;

    private EditText et_username;
    private EditText et_password;
    private CheckBox cb_isSave;
    private Button Bt_login;
    private Button Bt_regi;
    private Button Bt_forg;
    boolean loginsignal=false;
    private TextView tv_logo;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载界面

        setContentView( R.layout.activity_login);
        et_username=(EditText)findViewById(R.id.username);
        et_password=(EditText)findViewById(R.id.password);
        cb_isSave=(CheckBox)findViewById(R.id.checkBox);
        Bt_login=(Button)findViewById(R.id.login);
        Bt_forg=(Button)findViewById(R.id.forg);
        Bt_regi=(Button)findViewById(R.id.regi);
        Bt_login.setOnClickListener(new loginLisen());
        Bt_regi.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                try{
                    Toast.makeText(LoginActivity.this,"正在注册页面",Toast.LENGTH_SHORT);
                    Intent register=new Intent(LoginActivity.this,RegisterActivity.class);
                    startActivity(register);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        Bt_forg.setOnClickListener(new forgListener());


        sp=getSharedPreferences("info",0);

        sp.getBoolean("isChecked",false);
        boolean isSave=sp.getBoolean("isChecked",false);
        if(isSave){
            String username=sp.getString("username","");
            String pwd=sp.getString("pwd","");
            et_username.setText(username);
            et_password.setText(pwd);
            cb_isSave.setChecked(true);
        }

    }




    private class loginLisen implements View.OnClickListener{

        private String url="http://212.64.81.216:5000/login";
        @Override
        public void onClick(View v) {
            String pwd=et_password.getText().toString().trim();
            String username=et_username.getText().toString().trim();
            if(TextUtils.isEmpty(username)||TextUtils.isEmpty(pwd)){
                Toast.makeText(LoginActivity.this,"username can not be empty",Toast.LENGTH_SHORT).show();

            }else {

                SharedPreferences.Editor editor=sp.edit();
                boolean checked=cb_isSave.isChecked();
                if(checked){
                    editor.putString("username",username);
                    editor.putString("pwd",pwd);
                }
                editor.putBoolean("isChecked",checked);
                editor.apply();
                try {
                    String res=getCheckFromServer(url,username,pwd);
                    JSONObject resJs=new JSONObject(res);
                    if (resJs.getString("code").equals("0")) {
                        Toast.makeText(LoginActivity.this,"未注册或链接失败",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                        Intent main=new Intent(LoginActivity.this, MainActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("uid",resJs.getString("uid"));
                        main.putExtras(bundle);
                        startActivity(main);

                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }


        }
    }


    private class forgListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Toast.makeText(LoginActivity.this,"暂未开发",Toast.LENGTH_SHORT).show();
            Intent f = new Intent(LoginActivity.this,ForgetActivity.class);
            startActivity(f);
        }
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
    private String getCheckFromServer(final String url, final String name, final String pwd)throws IOException{
        final String[] res = {""};
        final JSONObject resJs=null;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json="{\"username\":"+name+",\"pwd\":"+pwd+"}";
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

                        loginsignal=true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
        return res[0];
    }


}
