package com.example.cosmindogaru.whatdidyouwatch;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

class BackgroundDatabaseTask extends AsyncTask<String, Void, Boolean> {
    private static final String TAG = "BackgroundDatabaseTask";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean cursor) {
        super.onPostExecute(cursor);
    }

    @Override
    protected Boolean doInBackground(String... strings) {

        String operation = strings[0];
        String query = strings[1];
        String username = strings[2];


        switch(operation) {
            case "add_info":
                try {
                    String password = strings[3];
                    SQLiteStatement sqLiteStatement = DatabaseManager.getInstance(SignUpActivity.getContext()).getDb().compileStatement(query);
                    sqLiteStatement.bindString(1, username);
                    sqLiteStatement.bindString(2, password);
                    sqLiteStatement.execute();
                    Toast.makeText(SignUpActivity.getContext(), "Account was created successfully!", Toast.LENGTH_LONG).show();
                    return true;
                } catch (SQLiteConstraintException e) {
                    Log.e(TAG, "doInBackground: Insertion failed, the username already exists " + e.getMessage());
                    Toast.makeText(SignUpActivity.getContext(), "The username is already used!", Toast.LENGTH_LONG).show();
                }
                break;
            case "check_login":
                try {
                    String password = strings[3];
                    SQLiteStatement sqLiteStatement = DatabaseManager.getInstance(LoginActivity.getContext()).getDb().compileStatement(query);
                    sqLiteStatement.bindString(1, username);
                    String result = sqLiteStatement.simpleQueryForString();
                    if(password.equals(result)) {
                        LoggedAccount.setLoggedAccount(username);
                        return true;
                    }
                } catch(SQLException e) {
                    Log.e(TAG, "doInBackground: Query for password failed " + e.getMessage());
                }
                break;
            case "add_movie":
                try {
                    String movieName = strings[3];
                    SQLiteStatement sqLiteStatement = DatabaseManager.getInstance(AddMovieActivity.getContext()).getDb().compileStatement(query);
                    sqLiteStatement.bindString(1, username);
                    sqLiteStatement.bindString(2, movieName);
                    Log.d(TAG, "doInBackground: The statement is " + sqLiteStatement.toString());
                    sqLiteStatement.execute();
                    Toast.makeText(AddMovieActivity.getContext(), "Movie successfully added!", Toast.LENGTH_LONG).show();
                    return true;
                } catch(SQLException e) {
                    Log.e(TAG, "doInBackground: Movie insertion failed " + e.getMessage());
                }
            case "get_movie_list":
                try {
                    String MOVIE_TABLE_NAME = strings[3];
//                    SQLiteStatement sqLiteStatement = DatabaseManager.getInstance(ShowMovieActivity.getContext()).getDb().compileStatement(query);
//                    sqLiteStatement.bindString(1, username);
//                    Log.d(TAG, "doInBackground: The statement is " + sqLiteStatement.toString());
//                    Cursor cursor = DatabaseManager.getInstance(ShowMovieActivity.getContext()).getDb().rawQuery(sqLiteStatement.toString(), null);
                    Cursor cursor = DatabaseManager.getInstance(ShowMovieActivity.getContext()).getDb().query(MOVIE_TABLE_NAME, new String[] {"Movie"}, "Account = ?", new String[] {username}, null, null, null, null);
                    Log.d(TAG, "doInBackground: query is " + query + " " + username);
                    Log.d(TAG, "doInBackground: cursor is " + cursor.getCount());
                    List<Movie> movieList = new ArrayList<>();
                    if(cursor.moveToFirst()) {
                        while(!cursor.isAfterLast()) {
                            movieList.add(new Movie(cursor.getString(cursor.getColumnIndex("Movie"))));
                            Log.d(TAG, "doInBackground: cursor is " + cursor.getString(cursor.getColumnIndex("Movie")));
                            cursor.moveToNext();
                        }
                    }
                    Log.d(TAG, "doInBackground: Item count: " + movieList.size());
                    MovieList.setmMovieList(movieList);
                } catch (SQLException e) {
                    Log.e(TAG, "doInBackground: Couldn't get movie list" + e.getMessage());
                }
            default:
        }

        return false;
    }
}
