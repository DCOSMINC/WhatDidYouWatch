package com.example.cosmindogaru.whatdidyouwatch;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DatabaseManager extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseManager";

    private BackgroundDatabaseTask backgroundDatabaseTask;

    public static final int DB_VERSION = 3;
    public static final String DB_NAME = "Account.db";
    //public static final String CONNECTION_STRING = "C:\\Users\\Cosmin\\Desktop\\" + DB_NAME;
    public static final String TABLE_NAME = "Accounts";
    public static final String MOVIE_TABLE_NAME = "Movies";
    public static final String MOVIE_COLUMN_NAME = "Movie";
    public static final String MOVIE_COLUMN_ACCOUNT_NAME = "Account";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_PASSWORD = "Password";

    private SQLiteDatabase db;

    Boolean returnValue;

    private static DatabaseManager instance;

    public static DatabaseManager getInstance(Context context) {
        if(instance == null) {
            instance = new DatabaseManager(context.getApplicationContext());
        }
        return instance;
    }

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_NAME + " VARCHAR(30) PRIMARY KEY NOT NULL,"
            + COLUMN_PASSWORD + " VARCHAR(30) NOT NULL)";

    public static final String CREATE_MOVIE_TABLE = "CREATE TABLE " + MOVIE_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +  MOVIE_COLUMN_ACCOUNT_NAME + " VARCHAR(30) NOT NULL," +
            MOVIE_COLUMN_NAME + " VARCHAR(50) NOT NULL)";

    public static final String INSERT_ACCOUNT = "INSERT INTO " + TABLE_NAME + "(" + COLUMN_NAME + ", " + COLUMN_PASSWORD + ") VALUES(?,?)";
    public static final String CHECK_IF_ACCOUNT_EXISTS = "SELECT " + COLUMN_PASSWORD + " FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = ?";

    public static final String INSERT_MOVIE = "INSERT INTO " + MOVIE_TABLE_NAME + "(" + MOVIE_COLUMN_ACCOUNT_NAME + ", " + MOVIE_COLUMN_NAME + ") VALUES(?,?)";

    public static final String GET_MOVIE_LIST = "SELECT " + MOVIE_COLUMN_NAME + " FROM " + MOVIE_TABLE_NAME + " WHERE " + MOVIE_COLUMN_ACCOUNT_NAME + " = ?";

    private DatabaseManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_MOVIE_TABLE);
        db = sqLiteDatabase;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE_NAME);
        this.onCreate(sqLiteDatabase);
    }

    public void insertAccount(String name, String password) {
        db = this.getWritableDatabase();
        backgroundDatabaseTask = new BackgroundDatabaseTask();
        backgroundDatabaseTask.doInBackground("add_info", INSERT_ACCOUNT , name, password);
        this.closeDB();
    }

    public Boolean checkLogin(String name, String password) {
        db = this.getReadableDatabase();
        backgroundDatabaseTask = new BackgroundDatabaseTask();
        returnValue =  backgroundDatabaseTask.doInBackground("check_login", CHECK_IF_ACCOUNT_EXISTS, name, password);
        this.closeDB();
        return returnValue;
    }

    public void addMovie(String loggedAccountUsername, String movieName) {
        db = this.getWritableDatabase();
        backgroundDatabaseTask = new BackgroundDatabaseTask();
        backgroundDatabaseTask.doInBackground("add_movie", INSERT_MOVIE, loggedAccountUsername, movieName);
        this.closeDB();
    }

    public void getMovieList(String loggedAccountUsername) {
        Log.d(TAG, "getMovieList: Starts");
        db = this.getReadableDatabase();
        backgroundDatabaseTask = new BackgroundDatabaseTask();
        backgroundDatabaseTask.doInBackground("get_movie_list", GET_MOVIE_LIST, loggedAccountUsername, MOVIE_TABLE_NAME);
        this.closeDB();
        Log.d(TAG, "getMovieList: Ends");
    }

    private void closeDB() {
        if(db != null && db.isOpen()) {
            db.close();
        }
    }

}
