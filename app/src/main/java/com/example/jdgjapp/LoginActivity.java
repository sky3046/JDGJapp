package com.example.jdgjapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private EditText useridEdit;
    private EditText passwordEdit;
    private Button login;
    private Button forgetpw;
    private CheckBox rememberPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        useridEdit = (EditText) findViewById(R.id.userid);
        passwordEdit = (EditText) findViewById(R.id.password);
        rememberPass = (CheckBox) findViewById(R.id.remember_pw);
        forgetpw = (Button) findViewById(R.id.btn_forget_pw);
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid = useridEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString().trim();

                sendRequestWithOkHttp(userid,password);

                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }



    //发送请求获取个人信息
    private void sendRequestWithOkHttp(final String id, final String pw) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("name",id)
                            .add("password",pw)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://106.14.145.208:8080/KQ/LogJudgeServlet")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseDate = response.body().string();

                    //解析数据
                    parseJSON(responseDate);

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this,response,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseJSON(String jsonData){
        Gson gson = new Gson();
        List<User> user_List = gson.fromJson(jsonData, new TypeToken<List<User>>(){}.getType());
        for (User user : user_List){
            Log.d(TAG, "usr_id is " + user.getUsr_id());
            Log.d(TAG, "usr_name is " + user.getUsr_name());
            Log.d(TAG, "usr_password is" + user.getUsr_paswprd());
            Log.d(TAG, "usr_addr is" + user.getUsr_addr());
            Log.d(TAG, "usr_phone is" + user.getUsr_phone());
            Log.d(TAG, "usr_birth is" + user.getUsr_brith());
            Log.d(TAG, "usr_deptId is" + user.getUsr_deptId());
            Log.d(TAG, "usr_bossId is" + user.getUsr_bossId());
        }
    }

}
