package com.example.cosmindogaru.whatdidyouwatch;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";

    private static AppCompatActivity instance;

    private EditText signupUsername, signupPassword, signupCheckPassword;
    private Button signupCreateAccountBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupUsername = findViewById(R.id.signupUsername);
        signupPassword = findViewById(R.id.signupPassword);
        signupCheckPassword = findViewById(R.id.signupCheckPassword);
        signupCreateAccountBtn = findViewById(R.id.signupCreateAccountBtn);

        instance = this;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    @Override
    protected void onResume() {
        super.onResume();

        signupCreateAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkUsername() && checkPassword()) {
                    DatabaseManager.getInstance(SignUpActivity.this).insertAccount(signupUsername.getText().toString(), signupPassword.getText().toString());
                }
            }
        });
    }

    private boolean checkUsername() {
        if(signupUsername.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean checkPassword() {
        if(signupPassword.getText().toString().isEmpty() || signupCheckPassword.getText().toString().isEmpty() ||
                !signupPassword.getText().toString().equals(signupCheckPassword.getText().toString())) {
            return false;
        }
        return true;
    }
}
