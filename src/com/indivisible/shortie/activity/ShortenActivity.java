package com.indivisible.shortie.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.indivisible.shortie.R;
import com.indivisible.shortie.data.LinkPair;
import com.indivisible.shortie.fragment.LinkInputFragment.OnInputListener;
import com.indivisible.shortie.fragment.LinkPairListFragment;
import com.indivisible.shortie.fragment.LinkPairListFragment.OnLinkPairClickListener;
import com.indivisible.shortie.service.GoogleShortener;
import com.indivisible.shortie.service.ResponseStatus;
import com.indivisible.shortie.service.Shortener;

/**
 * Activity to manually shorten URLs and reuse old ones.
 * 
 * @author indiv
 */
public class ShortenActivity
        extends ActionBarActivity
        implements OnInputListener, OnLinkPairClickListener
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private LinkPairListFragment listFragment;
    private static final String TAG = "sho:ShortenAct";


    ///////////////////////////////////////////////////////
    ////    init
    ///////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shorten);
        listFragment = (LinkPairListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frLinksList);
    }


    ///////////////////////////////////////////////////////
    ////    options
    ///////////////////////////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.shorten, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    ///////////////////////////////////////////////////////
    ////    fragments
    ///////////////////////////////////////////////////////

    // From LinkInputFragment
    @Override
    public void onShortenSubmit(String longUrl)
    {
        Log.d(TAG, "Received URL submit: " + longUrl);
        LinkPair newLinkPair = new LinkPair();
        newLinkPair.setCreatedMillis(System.currentTimeMillis());
        newLinkPair.setLongUrl(longUrl);
        shortenLink(newLinkPair);
    }

    // From LinkPairListFragment
    @Override
    public void onLinkShortClick(LinkPair linkPair)
    {
        Toast.makeText(this,
                       "LinkPair clicked, id: " + linkPair.getId(),
                       Toast.LENGTH_SHORT).show();
        //TODO: paste shortUrl to clipboard
    }

    @Override
    public void onLinkLongClick(LinkPair linkPair)
    {
        Toast.makeText(this,
                       "LinkPair long clicked, id: " + linkPair.getId(),
                       Toast.LENGTH_SHORT).show();
        listFragment.removeLinkPair(linkPair);
    }


    ///////////////////////////////////////////////////////
    ////    link shortening
    ///////////////////////////////////////////////////////

    //TODO: New spinner/preference fragment to select Shortener service (use icons too)
    //TODO: Move to own class for better reuse

    private void shortenLink(LinkPair linkPair)
    {
        //return linkPair;
        ShortenTask shortenTask = new ShortenTask(linkPair, listFragment);
        shortenTask.execute();
    }

    private class ShortenTask
            extends AsyncTask<Void, Void, LinkPair>
    {

        LinkPair linkPair;
        LinkPairListFragment listFragment;

        public ShortenTask(LinkPair linkPair, LinkPairListFragment listFragment)
        {
            this.linkPair = linkPair;
            this.listFragment = listFragment;
        }

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
            listFragment.addLinkPair(linkPair);
        }

        @Override
        protected void onCancelled()
        {
            Log.d(TAG, "Cancelled shortening.");
        }


    }


}
