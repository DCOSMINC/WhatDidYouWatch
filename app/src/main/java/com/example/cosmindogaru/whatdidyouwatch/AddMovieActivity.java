package com.example.cosmindogaru.whatdidyouwatch;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddMovieActivity extends AppCompatActivity {

    private static final String TAG = "AddMovieActivity";

    public static final String ACCOUNT_STRING = "account_info";

    private EditText movieNameText;
    private Button addMovieBtn;

    public static AppCompatActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        movieNameText = findViewById(R.id.movieNameText);
        addMovieBtn = findViewById(R.id.addMovieBtn);

        Log.d(TAG, "onCreate: Account is " + LoggedAccount.getLoggedAccount());

        instance = this;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!movieNameText.getText().toString().isEmpty()) {
                    DatabaseManager.getInstance(AddMovieActivity.this).addMovie(LoggedAccount.getLoggedAccount(), movieNameText.getText().toString());
                }
            }
        });
    }
}
