package com.example.cosmindogaru.whatdidyouwatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addMovie, showMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addMovie = findViewById(R.id.addMovie);
        showMovieList = findViewById(R.id.showMovieList);

        addMovie.setOnClickListener(this);
        showMovieList.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch(view.getId()) {
            case R.id.addMovie:
                intent = new Intent(MainActivity.this, AddMovieActivity.class);
                break;
            case R.id.showMovieList:
                intent = new Intent(MainActivity.this, ShowMovieActivity.class);
                break;
            default:
        }
        if(intent != null) {
            startActivity(intent);
        }
    }
}
