package com.example.cosmindogaru.whatdidyouwatch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "LoginActivity";


    private Button loginBtn;
    private Button createAccountBtn;
    private EditText loginPasswordEditText, loginUsernameEditText;
    private Boolean result;

    private static AppCompatActivity instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtn);
        createAccountBtn = findViewById(R.id.createAccountBtn);
        loginUsernameEditText = findViewById(R.id.loginUsernameEditText);
        loginPasswordEditText = findViewById(R.id.loginPasswordEditText);

        loginBtn.setOnClickListener(this);
        createAccountBtn.setOnClickListener(this);

        instance = this;
    }

    public static Context getContext() {
        return instance;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.loginBtn:
                if(!checkPassword()) {
                    Toast.makeText(LoginActivity.this, LoginActivity.this.getString(R.string.loginFailed), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, LoginActivity.this.getString(R.string.loginSuccess), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.createAccountBtn:
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
            default:
        }
    }

    private boolean checkPassword() {
        if(loginPasswordEditText.getText().toString().isEmpty() || loginUsernameEditText.getText().toString().isEmpty()) {
            return false;
        } else {
            result = DatabaseManager.getInstance(LoginActivity.this).checkLogin(loginUsernameEditText.getText().toString(), loginPasswordEditText.getText().toString());
        }
        return result;
    }
}
