package com.example.cosmindogaru.whatdidyouwatch;

import java.util.ArrayList;
import java.util.List;

class MovieList {
    private static List<Movie> mMovieList = new ArrayList<>();

    public static List<Movie> getmMovieList() {
        return mMovieList;
    }

    public static void setmMovieList(List<Movie> mMovieList) {
        MovieList.mMovieList = mMovieList;
    }
}
