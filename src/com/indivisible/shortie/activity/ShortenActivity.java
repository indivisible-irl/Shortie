package com.indivisible.shortie.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
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
import com.indivisible.shortie.fragment.InputDelete;
import com.indivisible.shortie.fragment.InputSearch;
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
        implements OnInputListener, OnLinkPairClickListener, OnSpinnerChangeListener,
        OnBackStackChangedListener
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private ASpinnerFragment spinner;
    private ALinkListFragment list;
    private AInputFragment input;

    private ShortenActivityMode activityMode;
    private boolean firstLoad = true;

    private static final String SPINNER_TAG = "spinner";
    private static final String LIST_TAG = "list";
    private static final String INPUT_TAG = "input";
    private static final String STACK_ROOT = "root";

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
            Log.w(TAG, "SavedInstance state: NULL");
            this.activityMode = ShortenActivityMode.INPUT;
            this.firstLoad = true;
            loadMode();
        }
        else
        {
            Log.w(TAG, "SavedInstance state: NOT NULL");
        }
    }


    ///////////////////////////////////////////////////////
    ////    swap fragments / change modes
    ///////////////////////////////////////////////////////

    private void setMode(ShortenActivityMode mode)
    {
        Log.d(TAG, "Changing mode: " + mode.name());
        this.activityMode = mode;
        this.invalidateOptionsMenu();
        loadMode();
    }

    private void loadMode()
    {
        if (firstLoad)
        {
            firstLoad();
            firstLoad = false;
        }
        else
        {
            subsequentLoad();
        }
    }

    private void firstLoad()
    {
        Log.d(TAG, "fresh load: start loading fragments");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        setFragmentsByMode(fragmentManager, fragmentTransaction);
        fragmentTransaction.addToBackStack(STACK_ROOT);
        fragmentTransaction.commit();
        Log.d(TAG, "fresh load: finish loading fragments");
    }

    private void subsequentLoad()
    {
        Log.d(TAG, "load: start loading fragments");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        setFragmentsByMode(fragmentManager, fragmentTransaction);
        fragmentTransaction.commit();
        Log.d(TAG, "load: finish loading fragments");
    }

    private void setFragmentsByMode(FragmentManager fm, FragmentTransaction ft)
    {
        switch (this.activityMode)
        {
            default:
                Log.e(TAG, "Unhandled mode for InputFragment: " + this.activityMode.name());
                Log.e(TAG, "Using default (INPUT)");
            case INPUT:
                if (fm.findFragmentByTag(SPINNER_TAG) != null)
                {
                    ft.remove(spinner);
                }
                spinner = null;
                list = new LinkListInput();
                input = new InputSubmit();
                break;
            case EDIT:
                if (fm.findFragmentByTag(INPUT_TAG) != null)
                {
                    ft.remove(input);
                }
                spinner = new SpinnerServices();
                list = new LinkListInput();
                input = null;
                break;
            case DELETE:
                spinner = new SpinnerServices();
                list = new LinkListInput();
                input = new InputDelete();
                break;
            case SEARCH:
                spinner = new SpinnerServices();
                list = new LinkListInput();
                input = new InputSearch();
                break;
        }
        if (spinner != null)
        {
            ft.replace(R.id.frSpinner, spinner, SPINNER_TAG);
        }
        if (list != null)
        {
            ft.replace(R.id.frList, list, LIST_TAG);
        }
        if (input != null)
        {
            ft.replace(R.id.frInput, input, INPUT_TAG);
        }
    }

    @Override
    public void onBackStackChanged()
    {
        //TODO: implement onBackStackChanged()
        Log.d(TAG, "BackStack changed");
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
                setMode(ShortenActivityMode.INPUT);
                return true;
            case R.id.action_edit:
                Log.v(TAG, "ActionBar: Edit");
                setMode(ShortenActivityMode.EDIT);
                return true;
            case R.id.action_delete:
                Log.v(TAG, "ActionBar: Delete");
                setMode(ShortenActivityMode.DELETE);
                return true;
            case R.id.action_search:
                Log.v(TAG, "ActionBar: Search");
                setMode(ShortenActivityMode.SEARCH);
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
        shortenLink(this.list, newLinkPair);
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
        list.removeLinkPair(linkPair);
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
