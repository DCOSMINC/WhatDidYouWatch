package com.example.cosmindogaru.whatdidyouwatch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

class FeedAdapter extends ArrayAdapter<Movie> {

    private static final String TAG = "FeedAdapter";

    private final int layoutResource;
    private final LayoutInflater mLayoutInflater;
    List<Movie> mMovieList;


    public FeedAdapter(Context context, int resource, List<Movie> movieList) {
        super(context, resource);
        this.mMovieList = movieList;
        Log.d(TAG, "FeedAdapter: Movie size is " + mMovieList.size());
        this.layoutResource = resource;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mMovieList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;

        if(convertView == null) {
            convertView = mLayoutInflater.inflate(layoutResource, parent, false);

            view = new View(convertView.getContext());
            convertView.setTag(view);
        }

        Movie currentMovie = mMovieList.get(position);

        TextView movieName = convertView.findViewById(R.id.listItem);

        movieName.setText(currentMovie.getName());

        return convertView;
    }
}
