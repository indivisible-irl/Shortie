package com.indivisible.shortie.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.indivisible.shortie.R;
import com.indivisible.shortie.actions.ShortenTask;
import com.indivisible.shortie.data.LinkPair;
import com.indivisible.shortie.fragment.AInputFragment;
import com.indivisible.shortie.fragment.AInputFragment.OnInputListener;
import com.indivisible.shortie.fragment.ALinkListFragment;
import com.indivisible.shortie.fragment.ALinkListFragment.OnLinkPairClickListener;
import com.indivisible.shortie.fragment.ASpinnerFragment;
import com.indivisible.shortie.fragment.ASpinnerFragment.OnSpinnerChangeListener;
import com.indivisible.shortie.fragment.InputSubmit;
import com.indivisible.shortie.fragment.LinkListInput;
import com.indivisible.shortie.fragment.ShortenActivityMode;
import com.indivisible.shortie.fragment.SpinnerServices;

/**
 * Activity to manually shorten URLs and reuse old ones.
 * 
 * @author indiv
 */
public class ShortenActivity
        extends ActionBarActivity
        implements OnInputListener, OnLinkPairClickListener, OnSpinnerChangeListener
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private ASpinnerFragment spinnerFragment;
    private ALinkListFragment listFragment;
    private AInputFragment inputFragment;
    //TODO: allow for user default pref
    private ShortenActivityMode activityMode = ShortenActivityMode.INPUT;
    private static final String TAG = "sho:ShortenAct";


    ///////////////////////////////////////////////////////
    ////    init
    ///////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shorten);
        if (savedInstanceState == null)
        {
            loadMode();
        }
    }

    private void loadMode()
    {
        Log.d(TAG, "load: start loading fragments");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Log.d(TAG, "load: start queuing fragments");
        setSpinnerFragment(fragmentTransaction);
        setListViewFragment(fragmentTransaction);
        setInputFragment(fragmentTransaction);
        Log.d(TAG, "load: finish queuing fragments, commit...");

        fragmentTransaction.commit();
        Log.d(TAG, "load: finish loading fragments");
    }

    private void setMode(ShortenActivityMode mode)
    {
        this.activityMode = mode;
        this.invalidateOptionsMenu();
        loadMode();
    }


    ///////////////////////////////////////////////////////
    ////    swap fragments / change modes
    ///////////////////////////////////////////////////////

    private void setSpinnerFragment(FragmentTransaction fragmentTransaction)
    {
        spinnerFragment = new SpinnerServices();
        fragmentTransaction.replace(R.id.frSpinner, spinnerFragment);
    }

    private void setListViewFragment(FragmentTransaction fragmentTransaction)
    {
        listFragment = new LinkListInput();
        fragmentTransaction.replace(R.id.frList, listFragment);
    }

    private void setInputFragment(FragmentTransaction fragmentTransaction)
    {
        inputFragment = new InputSubmit();
        fragmentTransaction.replace(R.id.frInput, inputFragment);
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
                setMode(ShortenActivityMode.INPUT);
                return true;
            case EDIT:
                getMenuInflater().inflate(R.menu.main_edit, menu);
                setMode(ShortenActivityMode.EDIT);
                return true;
            case DELETE:
                getMenuInflater().inflate(R.menu.main_delete, menu);
                setMode(ShortenActivityMode.DELETE);
                return true;
            case SEARCH:
                getMenuInflater().inflate(R.menu.main_search, menu);
                setMode(ShortenActivityMode.SEARCH);
                return true;
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
    // listener input fragments
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

    @Override
    public void onDeleteConfirm()
    {}

    @Override
    public void onDeleteCancel()
    {}

    @Override
    public void onSearchTermChanged(String searchTerm)
    {}

    //-------------------------------//
    // listener list fragments
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

    //-------------------------------//
    // listener spinner fragments
    //-------------------------------//

    @Override
    public void onSpinnerChange(String selectedItem)
    {
        Log.v(TAG, "New spinner item selected: " + selectedItem);
    }


    ///////////////////////////////////////////////////////
    ////    link shortening
    ///////////////////////////////////////////////////////

    //TODO: New spinner/preference fragment to select Shortener service (use icons too)

    private void shortenLink(ALinkListFragment listPairListFragment, LinkPair linkPair)
    {
        //return linkPair;
        ShortenTask shortenTask = new ShortenTask(listPairListFragment, linkPair);
        shortenTask.execute();
    }


}
