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
import com.indivisible.shortie.fragment.AListFragment;
import com.indivisible.shortie.fragment.AListFragment.OnLinkPairClickListener;
import com.indivisible.shortie.fragment.ASpinnerFragment;
import com.indivisible.shortie.fragment.ASpinnerFragment.OnSpinnerChangeListener;
import com.indivisible.shortie.fragment.InputSearch;
import com.indivisible.shortie.fragment.InputSubmit;
import com.indivisible.shortie.fragment.ListFragView;
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

    // view fragment
    private ASpinnerFragment spinnerFragmentView;
    private AListFragment listFragmentView;
    private AInputFragment inputFragmentView;
    // edit fragment
    private ASpinnerFragment spinnerFragmentEdit;
    private AListFragment listFragmentEdit;
    // delete fragment
    private ASpinnerFragment spinnerFragmentDelete;
    private AListFragment listFragmentDelete;
    // search fragment
    private ASpinnerFragment spinnerFragmentSearch;
    private AListFragment listFragmentSearch;
    private AInputFragment inputFragmentSearch;

    private ShortenActivityMode activityMode;
    private static final String TRANSACTION_TAG_ROOT = "root";
    private static final String TRANSACTION_TAG_OTHER = "other";
    private static final String TAG = "sho:ShortenAct";


    ///////////////////////////////////////////////////////
    ////    init
    ///////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shorten);
        //getSupportFragmentManager().addOnBackStackChangedListener(this);

        initFragments();
        if (savedInstanceState == null)
        {
            Log.w(TAG, "SAVEDINSTANCESTATE: NULL");
        }
        else
        {
            Log.w(TAG, "SAVEDINSTANCESTATE: NOT NULL");
        }
    }


    ///////////////////////////////////////////////////////
    ////    swap fragments / change modes
    ///////////////////////////////////////////////////////

    /**
     * Initialise fragments in the default state (View).
     */
    private void initFragments()
    {
        this.activityMode = ShortenActivityMode.VIEW;
        spinnerFragmentView = new SpinnerServices();
        listFragmentView = new ListFragView();
        inputFragmentView = new InputSubmit();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frSpinner, spinnerFragmentView);
        fragmentTransaction.add(R.id.frList, listFragmentView);
        fragmentTransaction.add(R.id.frInput, inputFragmentView);
        fragmentTransaction.commit();
    }

    /**
     * Replace and remove Fragments as required. <br/>
     * To remove (or not use) a fragment pass argument as null.
     * 
     * @param spinner
     * @param list
     * @param input
     */
    private void setFragments(ASpinnerFragment spinner,
                              AListFragment list,
                              AInputFragment input,
                              ShortenActivityMode previousMode)
    {
        // refresh menu
        this.supportInvalidateOptionsMenu();

        // get FragmentManager and pop to root
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate(TRANSACTION_TAG_ROOT,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);

        // start transaction and add to stack
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (previousMode == ShortenActivityMode.VIEW)
        {
            fragmentTransaction.addToBackStack(TRANSACTION_TAG_ROOT);
        }
        else
        {
            fragmentTransaction.addToBackStack(TRANSACTION_TAG_OTHER);
        }


        // replace and remove fragments as needed
        if (spinner != null)
        {
            fragmentTransaction.replace(R.id.frSpinner, spinner);
        }
        else
        {
            try
            {
                fragmentTransaction.remove(getSupportFragmentManager()
                        .findFragmentById(R.id.frSpinner));
            }
            catch (NullPointerException e)
            {
                Log.w(TAG, "SpinnerFragment didn't exist for removal");
            }
        }
        if (list != null)
        {
            fragmentTransaction.replace(R.id.frList, list);
        }
        else
        {
            try
            {
                fragmentTransaction.remove(getSupportFragmentManager()
                        .findFragmentById(R.id.frList));
            }
            catch (NullPointerException e)
            {
                Log.w(TAG, "ListFragment didn't exist for removal");
            }
        }

        if (input != null)
        {
            fragmentTransaction.replace(R.id.frInput, input);
        }
        else
        {
            try
            {
                fragmentTransaction.remove(getSupportFragmentManager()
                        .findFragmentById(R.id.frInput));
            }
            catch (NullPointerException e)
            {
                Log.w(TAG, "InputFragment didn't exist for removal");
            }
        }

        // finish transaction
        fragmentTransaction.commit();
    }

    private void loadViewState()
    {
        ShortenActivityMode prevMode = this.activityMode;
        this.activityMode = ShortenActivityMode.VIEW;
        if (spinnerFragmentView == null)
        {
            spinnerFragmentView = new SpinnerServices();
        }
        if (listFragmentView == null)
        {
            listFragmentView = new ListFragView();
        }
        if (inputFragmentView == null)
        {
            inputFragmentView = new InputSubmit();
        }
        setFragments(spinnerFragmentView, listFragmentView, inputFragmentView, prevMode);
    }

    private void loadEditState()
    {
        ShortenActivityMode prevMode = this.activityMode;
        this.activityMode = ShortenActivityMode.EDIT;
        if (spinnerFragmentEdit == null)
        {
            spinnerFragmentEdit = new SpinnerServices();
        }
        if (listFragmentEdit == null)
        {
            listFragmentEdit = new ListFragView();     //FIXME: edit icon list
        }
        setFragments(spinnerFragmentEdit, listFragmentEdit, null, prevMode);
    }

    private void loadDeleteState()
    {
        ShortenActivityMode prevMode = this.activityMode;
        this.activityMode = ShortenActivityMode.DELETE;
        if (spinnerFragmentDelete == null)
        {
            spinnerFragmentDelete = new SpinnerServices();
        }
        if (listFragmentDelete == null)
        {
            listFragmentDelete = new ListFragView();   //FIXME: multi select list
        }
        setFragments(spinnerFragmentDelete, listFragmentDelete, null, prevMode);
    }

    private void loadSearchState()
    {
        ShortenActivityMode prevMode = this.activityMode;
        this.activityMode = ShortenActivityMode.SEARCH;
        if (spinnerFragmentSearch == null)
        {
            spinnerFragmentSearch = new SpinnerServices();
        }
        if (listFragmentSearch == null)
        {
            listFragmentSearch = new ListFragView();
        }
        if (inputFragmentSearch == null)
        {
            inputFragmentSearch = new InputSearch();
        }
        setFragments(spinnerFragmentSearch, listFragmentSearch, inputFragmentSearch, prevMode);
    }


    ///////////////////////////////////////////////////////
    ////    options
    ///////////////////////////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        switch (this.activityMode)
        {
            case VIEW:
                getMenuInflater().inflate(R.menu.main_view, menu);
                return true;
            case EDIT:
                getMenuInflater().inflate(R.menu.main_edit, menu);
                return true;
            case DELETE:
                getMenuInflater().inflate(R.menu.main_delete, menu);
                return true;
            case SEARCH:
                getMenuInflater().inflate(R.menu.main_search, menu);
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
                loadViewState();
                return true;
            case R.id.action_edit:
                Log.v(TAG, "ActionBar: Edit");
                loadEditState();
                return true;
            case R.id.action_delete:
                Log.v(TAG, "ActionBar: Delete");
                loadDeleteState();
                return true;
            case R.id.action_search:
                Log.v(TAG, "ActionBar: Search");
                loadSearchState();
                return true;
            default:
                Log.e(TAG, "Unhandled ActionBar item click!");
                return super.onOptionsItemSelected(item);
        }
    }


    ///////////////////////////////////////////////////////
    ////    fragment listeners
    ///////////////////////////////////////////////////////

    //-------------------------------//
    // listener input fragments
    //-------------------------------//

    // view

    @Override
    public void onShortenSubmit(String longUrl)
    {
        Log.v(TAG, "Received URL submit: " + longUrl);
        LinkPair newLinkPair = new LinkPair();
        newLinkPair.setCreatedMillis(System.currentTimeMillis());
        newLinkPair.setLongUrl(longUrl);
        shortenLink(listFragmentView, newLinkPair);
    }

    // edit

    //TODO; edit interactions?

    // delete

    @Override
    public void onDeleteConfirm()
    {}

    @Override
    public void onDeleteCancel()
    {}

    // search

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
        listFragmentView.removeLinkPair(linkPair);
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

    private void shortenLink(AListFragment listPairListFragment, LinkPair linkPair)
    {
        //return linkPair;
        ShortenTask shortenTask = new ShortenTask(listPairListFragment, linkPair);
        shortenTask.execute();
    }


}
