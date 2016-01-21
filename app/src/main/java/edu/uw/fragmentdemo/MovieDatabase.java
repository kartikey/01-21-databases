package edu.uw.fragmentdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Class to manage a database to store movie info
 * Ideally this should be made into a Provider
 */
public final class MovieDatabase {

    //empty constructor so cannot be called
    public MovieDatabase(){}

    //defines the schema
    public static abstract class FavoriteEntry implements BaseColumns {
        //_ID = "_id"
        public static final String TABLE_NAME = "entry";
        public static final String COL_TITLE = "title";
        public static final String COL_YEAR = "year";
        public static final String COL_IMDB_ID = "imdbId";
        public static final String COL_POSTER_URL = "posterUrl";

    }

    public static final String TABLE_NAME = "favorites";

    public static final String CREATE_FAVOURITE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            FavoriteEntry._ID + " INTEGER PRIMARY KEY" +","+
            FavoriteEntry.COL_TITLE + " TEXT" +","+
            FavoriteEntry.COL_YEAR + " INTEGER" +","+
            FavoriteEntry.COL_IMDB_ID + " TEXT UNIQUE" +","+
            FavoriteEntry.COL_POSTER_URL + " TEXT" +
                ")";

    public static final String DROP_FAVORITE_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

    public static class Helper extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "movies.db";
        public static final int DATABASE_VERSION = 1;

        public Helper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_FAVOURITE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_FAVORITE_TABLE);
            onCreate(db);
        }
    }

    public static void testDatabase(Context context) {

        Helper helper = new Helper(context);

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues content = new ContentValues();

        content.put(FavoriteEntry.COL_TITLE,"Batman");
        content.put(FavoriteEntry.COL_YEAR,1989);
        content.put(FavoriteEntry.COL_IMDB_ID,"1111");
        content.put(FavoriteEntry.COL_POSTER_URL, "");


        long newRowId = db.insert(TABLE_NAME, null, content );

        Log.v("DATABASE", "" + newRowId);

    }

    public static Cursor queryDatabase(Context context) {

        Helper helper = new Helper(context);

        SQLiteDatabase db = helper.getWritableDatabase();

        String[] cols = {FavoriteEntry._ID,FavoriteEntry.COL_TITLE, FavoriteEntry.COL_YEAR, FavoriteEntry.COL_IMDB_ID,FavoriteEntry.COL_POSTER_URL};
        Cursor results = db.query(TABLE_NAME, cols, null, null, null, null, null, null);

        return results;
    }


}
