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

    // view fragment
    private ASpinnerFragment spinnerFragmentView;
    private ALinkListFragment listFragmentView;
    private AInputFragment inputFragmentView;
    // edit fragment
    private ASpinnerFragment spinnerFragmentEdit;
    private ALinkListFragment listFragmentEdit;     //ASK: reuse view's list? //ANS: No want edit icons in rows
    // delete fragment
    private ASpinnerFragment spinnerFragmentDelete;
    private ALinkListFragment listFragmentDelete;
    // search fragment
    private ASpinnerFragment spinnerFragmentSearch;
    private ALinkListFragment listFragmentSearch;
    private AInputFragment inputFragmentSearch;

    private ShortenActivityMode activityMode;
    private static final String TAG = "sho:ShortenAct";


    ///////////////////////////////////////////////////////
    ////    init
    ///////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shorten);
        getSupportFragmentManager().addOnBackStackChangedListener(this);

        if (savedInstanceState == null)
        {
            Log.w(TAG, "SAVEDINSTANCESTATE: NULL");
            initFragments();
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
        listFragmentView = new LinkListInput();
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
    private void
            setFragments(ASpinnerFragment spinner, ALinkListFragment list, AInputFragment input)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
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
            {}
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
            {}
        }
        if (spinner != null)
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
            {}
        }
        fragmentTransaction.commit();
    }

    private void loadViewState()
    {
        this.activityMode = ShortenActivityMode.VIEW;
        if (spinnerFragmentView == null)
        {
            spinnerFragmentView = new SpinnerServices();
        }
        if (listFragmentView == null)
        {
            listFragmentView = new LinkListInput();
        }
        if (inputFragmentView == null)
        {
            inputFragmentView = new InputSubmit();
        }
        setFragments(spinnerFragmentView, listFragmentView, inputFragmentView);
    }

    private void loadEditState()
    {
        this.activityMode = ShortenActivityMode.EDIT;
        if (spinnerFragmentEdit == null)
        {
            spinnerFragmentEdit = new SpinnerServices();
        }
        if (listFragmentEdit == null)
        {
            listFragmentEdit = new LinkListInput();     //FIXME: edit icon list
        }
        setFragments(spinnerFragmentEdit, listFragmentEdit, null);
    }

    private void loadDeleteState()
    {
        this.activityMode = ShortenActivityMode.DELETE;
        if (spinnerFragmentDelete == null)
        {
            spinnerFragmentDelete = new SpinnerServices();
        }
        if (listFragmentDelete == null)
        {
            listFragmentDelete = new LinkListInput();   //FIXME: multi select list
        }
        setFragments(spinnerFragmentDelete, listFragmentDelete, null);
    }

    private void loadSearchState()
    {
        this.activityMode = ShortenActivityMode.SEARCH;
        if (spinnerFragmentSearch == null)
        {
            spinnerFragmentSearch = new SpinnerServices();
        }
        if (listFragmentSearch == null)
        {
            listFragmentSearch = new LinkListInput();
        }
        if (inputFragmentSearch == null)
        {
            inputFragmentSearch = new InputSearch();
        }
        setFragments(spinnerFragmentSearch, listFragmentSearch, inputFragmentSearch);
    }


    @Override
    public void onBackStackChanged()
    {
        // TODO Auto-generated method stub
        Log.d(TAG, "BackStack changed");
        if (this.activityMode.equals(ShortenActivityMode.VIEW))
        {
            finish();
        }
        else
        {
            loadViewState();
        }
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
                getMenuInflater().inflate(R.menu.main_input, menu);
                loadViewState();
                return true;
            case EDIT:
                getMenuInflater().inflate(R.menu.main_edit, menu);
                loadEditState();
                return true;
            case DELETE:
                getMenuInflater().inflate(R.menu.main_delete, menu);
                loadDeleteState();
                return true;
            case SEARCH:
                getMenuInflater().inflate(R.menu.main_search, menu);
                loadSearchState();
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

    @Override
    public void onShortenSubmit(String longUrl)
    {
        Log.v(TAG, "Received URL submit: " + longUrl);
        LinkPair newLinkPair = new LinkPair();
        newLinkPair.setCreatedMillis(System.currentTimeMillis());
        newLinkPair.setLongUrl(longUrl);
        shortenLink(listFragmentView, newLinkPair);
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

    private void shortenLink(ALinkListFragment listPairListFragment, LinkPair linkPair)
    {
        //return linkPair;
        ShortenTask shortenTask = new ShortenTask(listPairListFragment, linkPair);
        shortenTask.execute();
    }


}
