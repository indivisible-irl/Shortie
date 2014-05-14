package com.indivisible.shortie.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DbOpenHelper
        extends SQLiteOpenHelper
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private static final String DB_NAME = "shortie_links.db";
    private static final int DB_VERSION = 5;

    public static final String TABLE_PAIRS = "link_pairs";
    public static final String COL_KEY = "_id";
    public static final String COL_LONGURL = "url_long";
    public static final String COL_SHORTURL = "url_short";
    public static final String COL_DATETIME = "datetime_created";
    public static final String COL_STATUS = "request_status";
    public static final String[] ALL_COLUMNS = new String[] {
            DbOpenHelper.COL_KEY, DbOpenHelper.COL_DATETIME, DbOpenHelper.COL_LONGURL,
            DbOpenHelper.COL_SHORTURL, DbOpenHelper.COL_STATUS
    };

    private static final String CREATE_TABLE_PAIRS = "create table " + TABLE_PAIRS + "("
            + COL_KEY + " integer primary key autoincrement, " + COL_DATETIME
            + " BIGINT not null, " + COL_SHORTURL + " text not null, " + COL_LONGURL
            + " text not null, " + COL_STATUS + " text not null" + ");";
    private static final String DROP_TABLE_PAIRS = "drop table if exists " + TABLE_PAIRS;

    private static final String TAG = "sho:DbOpenHelper";

    ///////////////////////////////////////////////////////
    ////    init
    ///////////////////////////////////////////////////////

    public DbOpenHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }


    ///////////////////////////////////////////////////////
    ////    overrides
    ///////////////////////////////////////////////////////

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.i(TAG, "Creating new database");
        db.execSQL(CREATE_TABLE_PAIRS);
        Log.i(TAG, "Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i(TAG, String.format("Upgrading database from v{0} to v{1}",
                                 oldVersion,
                                 newVersion));
        Log.w(TAG, "Recreating empty database as still in development stage");
        db.execSQL(DROP_TABLE_PAIRS);
        onCreate(db);
    }


}
