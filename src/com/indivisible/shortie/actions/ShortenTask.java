package com.indivisible.shortie.actions;

import android.os.AsyncTask;
import android.util.Log;
import com.indivisible.shortie.data.LinkPair;
import com.indivisible.shortie.fragment.ALinkListFragment;
import com.indivisible.shortie.service.GoogleShortener;
import com.indivisible.shortie.service.ResponseStatus;
import com.indivisible.shortie.service.Shortener;


/**
 * Asynchronously shorten a URL
 * 
 * @author indiv
 */
public class ShortenTask
        extends AsyncTask<Void, Void, LinkPair>
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private LinkPair linkPair;
    private ALinkListFragment listPairListFragment;
    private static final String TAG = "sho:ShortTask";


    ///////////////////////////////////////////////////////
    ////    init
    ///////////////////////////////////////////////////////

    public ShortenTask(ALinkListFragment listPairListFragment, LinkPair linkPair)
    {
        this.linkPair = linkPair;
        this.listPairListFragment = listPairListFragment;
    }


    ///////////////////////////////////////////////////////
    ////    task
    ///////////////////////////////////////////////////////

    @Override
    protected void onPreExecute()
    {
        Log.d(TAG, "Starting link shortening...");
    }

    @Override
    protected LinkPair doInBackground(Void... params)
    {
        Shortener shortener = new GoogleShortener();
        return shortener.shortenUrl(this.linkPair);
    }

    @Override
    protected void onPostExecute(LinkPair linkPair)
    {
        Log.i(TAG, "Finished shorten: " + linkPair.getLongUrl());
        if (linkPair.getShortUrl().equals(LinkPair.DEFAULT_URL))
        {
            Log.w(TAG, "Failed: " + ResponseStatus.getString(linkPair.getStatus()));
        }
        else
        {
            Log.i(TAG, "Success: " + linkPair.getShortUrl());
        }
        listPairListFragment.addLinkPair(linkPair);
    }

    @Override
    protected void onCancelled()
    {
        Log.d(TAG, "Cancelled shortening.");
    }


}
