package com.indivisible.shortie.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.indivisible.shortie.R;
import com.indivisible.shortie.actions.ShortenTask;
import com.indivisible.shortie.data.LinkPair;
import com.indivisible.shortie.fragment.LinkInputFragment.OnInputListener;
import com.indivisible.shortie.fragment.LinkPairListFragment;
import com.indivisible.shortie.fragment.LinkPairListFragment.OnLinkPairClickListener;

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
        getMenuInflater().inflate(R.menu.main_normal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case R.id.action_prefs:
                Log.v(TAG, "ActionBar: Prefs");
                return true;
            case R.id.action_normal:
                Log.v(TAG, "ActionBar: Normal");
                return true;
            case R.id.action_edit:
                Log.v(TAG, "ActionBar: Edit");
                return true;
            case R.id.action_delete:
                Log.v(TAG, "ActionBar: Delete");
                return true;
            case R.id.action_search:
                Log.v(TAG, "ActionBar: Search");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    ///////////////////////////////////////////////////////
    ////    fragment listeners
    ///////////////////////////////////////////////////////

    //-------------------------------//
    // listener LinkInputFragment
    //-------------------------------//

    @Override
    public void onShortenSubmit(String longUrl)
    {
        Log.d(TAG, "Received URL submit: " + longUrl);
        LinkPair newLinkPair = new LinkPair();
        newLinkPair.setCreatedMillis(System.currentTimeMillis());
        newLinkPair.setLongUrl(longUrl);
        shortenLink(this.listFragment, newLinkPair);
    }

    //-------------------------------//
    // listener LinkPairListFragment
    //-------------------------------//

    @Override
    public void onLinkShortClick(LinkPair linkPair)
    {
        Log.d(TAG, "LinkPair clicked, id: " + linkPair.getId());
        //TODO: paste shortUrl to clipboard
    }

    @Override
    public void onLinkLongClick(LinkPair linkPair)
    {
        Log.d(TAG, "LinkPair long clicked, id: " + linkPair.getId());
        listFragment.removeLinkPair(linkPair);
    }


    ///////////////////////////////////////////////////////
    ////    swap fragments / change modes
    ///////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////
    ////    link shortening
    ///////////////////////////////////////////////////////

    //TODO: New spinner/preference fragment to select Shortener service (use icons too)

    private void
            shortenLink(LinkPairListFragment listPairListFragment, LinkPair linkPair)
    {
        //return linkPair;
        ShortenTask shortenTask = new ShortenTask(listPairListFragment, linkPair);
        shortenTask.execute();
    }


}
