package com.indivisible.shortie.data;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.indivisible.shortie.service.ResponseStatus;

/**
 * Bridge between database and LinkPair objects.
 * 
 * @author indiv
 */
public class LinkDataSource
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private Context context;
    private SQLiteDatabase db = null;
    private DbOpenHelper dbHelper = null;

    private static final String TAG = "sho:LinkDataSrc";


    ///////////////////////////////////////////////////////
    ////    init
    ///////////////////////////////////////////////////////

    public LinkDataSource(Context context)
    {
        this.context = context;
        dbHelper = new DbOpenHelper(this.context);
    }


    ///////////////////////////////////////////////////////
    ////    open & close database handles
    ///////////////////////////////////////////////////////

    /**
     * Open read-only database handle
     * 
     * @throws SQLException
     */
    public void openReadable()
        throws SQLException
    {
        db = dbHelper.getReadableDatabase();
    }

    /**
     * Open a readable and writable database handle
     * 
     * @throws SQLException
     */
    public void openWritable()
        throws SQLException
    {
        db = dbHelper.getWritableDatabase();
    }

    /**
     * Close the database handle if open.
     */
    public void close()
    {
        if (dbHelper != null)
        {
            dbHelper.close();
        }
    }


    ///////////////////////////////////////////////////////
    ////    CRUD
    ///////////////////////////////////////////////////////

    //  create

    /**
     * Create and save a new LinkPair.
     * 
     * @param createMillis
     * @param shortUrl
     * @param longUrl
     * @return
     */
    public LinkPair createLinkPair(long createdMillis,
                                   String shortUrl,
                                   String longUrl,
                                   ResponseStatus status)
    {
        ContentValues values = new ContentValues();
        values.put(DbOpenHelper.COL_DATETIME, createdMillis);
        values.put(DbOpenHelper.COL_LONGURL, longUrl);
        values.put(DbOpenHelper.COL_SHORTURL, shortUrl);
        values.put(DbOpenHelper.COL_STATUS, status.name());
        long pairId = db.insert(DbOpenHelper.TABLE_PAIRS, null, values);
        LinkPair newLinkPair = getLinkPairById(pairId);
        Log.v(TAG, "Added new LinkPair, id: " + pairId);
        Log.v(TAG, "longUrl: " + newLinkPair.getLongUrl());
        return newLinkPair;
    }


    //  read

    /**
     * Read a single LinkPair from the database.
     * 
     * @param id
     * @return
     */
    public LinkPair getLinkPairById(long id)
    {
        Cursor cursor = null;
        try
        {
            cursor = db.query(DbOpenHelper.TABLE_PAIRS,
                    DbOpenHelper.ALL_COLUMNS,
                    DbOpenHelper.COL_KEY + " = " + id,
                    null,
                    null,
                    null,
                    null);
            if (cursor.getCount() == 1 && cursor.moveToFirst())
            {
                LinkPair linkPair = CursorToLinkPair(cursor);
                return linkPair;
            }
            else
            {
                return null;
            }
        }
        finally
        {
            if (cursor != null) cursor.close();
        }
    }

    /**
     * Retrieve all LinkPairs from the database.
     * 
     * @return
     */
    public List<LinkPair> getAllLinkPairs()
    {
        List<LinkPair> linkPairs = new ArrayList<LinkPair>();
        Cursor cursor = null;
        try
        {
            cursor = db.query(DbOpenHelper.TABLE_PAIRS,
                    DbOpenHelper.ALL_COLUMNS,
                    null,
                    null,
                    null,
                    null,
                    DbOpenHelper.COL_DATETIME);
            if (cursor.moveToFirst())
            {
                while (!cursor.isAfterLast())
                {
                    linkPairs.add(CursorToLinkPair(cursor));
                    cursor.moveToNext();
                }
            }
            return linkPairs;
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }
    }

    // delete

    /**
     * Delete a single LinkPair from the database.
     * 
     * @param id
     * @return
     */
    public boolean deleteLinkPair(long id)
    {
        db.beginTransaction();
        try
        {
            int rowsAffected = db.delete(DbOpenHelper.TABLE_PAIRS, DbOpenHelper.COL_KEY + " = "
                    + id, null);
            if (rowsAffected == 1)
            {
                db.setTransactionSuccessful();
                return true;
            }
            return false;
        }
        finally
        {
            db.endTransaction();
        }
    }

    //TODO: UpdateLinkPair()


    ///////////////////////////////////////////////////////
    ////    util methods
    ///////////////////////////////////////////////////////

    /**
     * Parse a cursor for a LinkPair object.
     * 
     * @param cursor
     * @return
     */
    private static LinkPair CursorToLinkPair(Cursor cursor)
    {
        LinkPair linkPair = new LinkPair(cursor.getLong(0),      // id
                cursor.getLong(1),      // created
                cursor.getString(2),    // long
                cursor.getString(3),    // short
                ResponseStatus.getStatus(cursor.getString(4))   // status
        );
        return linkPair;
    }

}
