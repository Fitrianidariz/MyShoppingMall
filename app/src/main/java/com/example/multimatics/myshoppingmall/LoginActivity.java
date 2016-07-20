package com.example.multimatics.myshoppingmall;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity
    implements View.OnClickListener {

    private TextView tvRegister;
    private Button btnLogin;
    private EditText edtUsername, edtPassword;
    private AppPreferences appPreferences;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Login");

        appPreferences = new AppPreferences(LoginActivity.this);

        tvRegister = (TextView)findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(this);

        edtUsername = (EditText)findViewById(R.id.edt_username);
        edtPassword = (EditText)findViewById(R.id.edt_password);

        btnLogin = (Button)findViewById(R.id.btn_login);
        btnLogin.setOnClickListener (this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        boolean isLogin = false;
        switch (view.getId()){
            case R.id.tv_register:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                break;

            case R.id.btn_login:

                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }else{
                    appPreferences.setUsername(username);
                    intent = new Intent(LoginActivity.this, HomeActivity.class);
                    isLogin = true;
                }
                break;

        }
        if (intent != null){
            startActivity(intent);
            if(isLogin){
                finish();
            }
        }
    }
}
