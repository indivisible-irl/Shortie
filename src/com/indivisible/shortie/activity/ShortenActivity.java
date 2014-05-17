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
import com.indivisible.shortie.fragment.ALinkListFragment.OnLinkPairClickListener;
import com.indivisible.shortie.fragment.LinkListInput;
import com.indivisible.shortie.fragment.ShortenActivityMode;

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

    private LinkListInput listFragment;
    private ShortenActivityMode activityMode = ShortenActivityMode.INPUT;   //TODO: allow for user default pref
    private static final String TAG = "sho:ShortenAct";


    ///////////////////////////////////////////////////////
    ////    init
    ///////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shorten);
        listFragment = (LinkListInput) getSupportFragmentManager()
                .findFragmentById(R.id.frLinksList);
    }

    private void loadMode()
    {

    }

    private void setMode(ShortenActivityMode mode)
    {
        this.activityMode = mode;
        this.invalidateOptionsMenu();
        loadMode();
    }


    ///////////////////////////////////////////////////////
    ////    options
    ///////////////////////////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        switch (this.activityMode)
        {
            case INPUT:
                getMenuInflater().inflate(R.menu.main_input, menu);
                return true;
            case EDIT:
                getMenuInflater().inflate(R.menu.main_edit, menu);
                return true;
            case DELETE:
                getMenuInflater().inflate(R.menu.main_delete, menu);
                return true;
            case SEARCH:
                getMenuInflater().inflate(R.menu.main_search, menu);
            default:
                Log.e(TAG, "Did not handle Mode: " + this.activityMode.name());
                return false;
        }
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
            case R.id.action_input:
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
    // listener InputFragment
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

    private boolean setSpinnerFragment()
    {
        return false;
    }

    private boolean setListViewFragment()
    {
        return false;
    }

    private boolean setInputFragment()
    {
        return false;
    }


    ///////////////////////////////////////////////////////
    ////    link shortening
    ///////////////////////////////////////////////////////

    //TODO: New spinner/preference fragment to select Shortener service (use icons too)

    private void
            shortenLink(LinkListInput listPairListFragment, LinkPair linkPair)
    {
        //return linkPair;
        ShortenTask shortenTask = new ShortenTask(listPairListFragment, linkPair);
        shortenTask.execute();
    }


}
