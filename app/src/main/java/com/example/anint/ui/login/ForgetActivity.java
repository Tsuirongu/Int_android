package com.example.anint.ui.login;
import com.example.anint.R;
import com.example.anint.util.RandomNumber;
import com.example.anint.util.SendEmail;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class ForgetActivity extends AppCompatActivity {
    private EditText etInputEmail,etInputGetNum;

    private long verificationCode=0; //生成的验证码
    private String email; //邮箱
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_forg);
        init();
    }

    private void init() {
        etInputEmail= (EditText) findViewById(R.id.etInputEmail);
        etInputGetNum= (EditText) findViewById(R.id.etInputGetNum);
    }
    public void btClick(View view){
        switch (view.getId()){
            case R.id.btGetNum:
                email=etInputEmail.getText().toString();
                sendVerificationCode(email); //发送验证码
                break;
            case R.id.btSubmit:
                judgeVerificationCode(); //判断输入的验证码是否正确
                break;
        }
    }
    //发送验证码
    private void sendVerificationCode(final String email) {
        try {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                         RandomNumber rn = new RandomNumber();
                        verificationCode = rn.getRandomNumber(6);
                        SendEmail se = new SendEmail(email);
                        se.sendHtmlEmail(verificationCode);//发送html邮件
                        Toast.makeText(ForgetActivity.this,"发送成功",Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //判断输入的验证码是否正确
    private void judgeVerificationCode() {
        if(Integer.parseInt(etInputGetNum.getText().toString())==verificationCode){ //验证码和输入一致
            Toast.makeText(ForgetActivity.this,"验证成功",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(ForgetActivity.this, "验证失败", Toast.LENGTH_LONG).show();
        }
    }
}
