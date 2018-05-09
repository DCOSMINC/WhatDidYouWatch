package com.example.cosmindogaru.whatdidyouwatch;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

public class ShowMovieActivity extends AppCompatActivity{

    private static final String TAG = "ShowMovieActivity";

    private ListView showMovieListView;

    private static AppCompatActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie);

        showMovieListView = findViewById(R.id.showMovieListView);

        instance = this;

    }

    @Override
    protected void onResume() {
        super.onResume();

        DatabaseManager.getInstance(ShowMovieActivity.getContext()).getMovieList(LoggedAccount.getLoggedAccount());

        Log.d(TAG, "onResume: Item count : " + MovieList.getmMovieList().size());
        if(MovieList.getmMovieList().size() > 0) {
            FeedAdapter feedAdapter = new FeedAdapter(this, R.layout.list_item, MovieList.getmMovieList());
            showMovieListView.setAdapter(feedAdapter);
        }
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}
