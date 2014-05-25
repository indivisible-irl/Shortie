package com.indivisible.shortie.fragment;

import java.util.List;
import android.app.Activity;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import com.indivisible.shortie.data.LinkDataSource;
import com.indivisible.shortie.data.LinkPair;
import com.indivisible.shortie.data.LinkPairListAdapter;


/**
 * Parent class for ListFragments displaying and managing LinkPairs
 * 
 * @author indiv
 */
public abstract class ALinkListFragment
        extends ListFragment
        implements OnItemLongClickListener      // OnItemClick is native
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    protected LinkDataSource linkSource;
    protected OnLinkPairClickListener clickListener;
    protected LinkPairListAdapter adapter;

    private static final String TAG = "sho:ALinkListFrag";


    ///////////////////////////////////////////////////////
    ////    init
    ///////////////////////////////////////////////////////

    /**
     * Parent class for ListFragments displaying and managing LinkPairs
     */
    protected ALinkListFragment()
    {}

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            clickListener = (OnLinkPairClickListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement OnLinkPairClickListener");
        }
        linkSource = new LinkDataSource(getActivity());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getListView().setLongClickable(true);
        getListView().setOnItemLongClickListener(this);
        //ASK: Always stack from bottom?
        getListView().setStackFromBottom(true);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        adapter.notifyDataSetInvalidated();
    }

    ///////////////////////////////////////////////////////
    ////    listener
    ///////////////////////////////////////////////////////

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Listener for list item short and long clicks.
     * 
     * @author indiv
     */
    public interface OnLinkPairClickListener
    {

        /**
         * Called when User short clicks provided LinkPair entry.
         * 
         * @param linkPair
         */
        public void onLinkShortClick(LinkPair linkPair);

        /**
         * Called when User long clicks provided LinkPair entry.
         * 
         * @param linkPair
         */
        public void onLinkLongClick(LinkPair linkPair);
    }


    ///////////////////////////////////////////////////////
    ////    link pair handling
    ///////////////////////////////////////////////////////

    /**
     * Get all LinkPairs from the database. <br/>
     * TODO: Limit the number of entries returned.
     * 
     * @return
     */
    public List<LinkPair> getAllLinkPairs()
    {
        try
        {
            linkSource.openReadable();
            return linkSource.getAllLinkPairs();
        }
        catch (SQLException e)
        {
            Log.e(TAG, "Error reading all LinkPairs from db");
            return null;
        }
        finally
        {
            linkSource.close();
        }
    }

    /**
     * Add a new LinkPair to the database and List Adapter.
     * 
     * @param linkPair
     */
    public void addLinkPair(LinkPair linkPair)
    {
        try
        {
            linkSource.openWritable();
            LinkPair newLinkPair = linkSource.createLinkPair(linkPair.getCreatedMillis(),
                    linkPair.getShortUrl(),
                    linkPair.getLongUrl(),
                    linkPair.getStatus());
            adapter.add(newLinkPair);
            adapter.notifyDataSetChanged();
        }
        catch (SQLException e)
        {
            Log.e(TAG, "Error adding new LinkPair to db: " + linkPair.getLongUrl());
        }
        finally
        {
            linkSource.close();
        }
    }

    /**
     * Remove a LinkPair form the database and List Adapter.
     * 
     * @param rmLinkPair
     * @return
     */
    public boolean removeLinkPair(LinkPair rmLinkPair)
    {
        try
        {
            linkSource.openWritable();
            boolean rmSuccess = linkSource.deleteLinkPair(rmLinkPair.getId());
            if (rmSuccess)
            {
                adapter.remove(rmLinkPair);
                adapter.notifyDataSetChanged();
            }
            return rmSuccess;
        }
        catch (SQLException e)
        {
            return false;
        }
        finally
        {
            linkSource.close();
        }
    }

    //TODO: public boolean updateLinkPair(LinkPair linkPair)
    //      must have an id set, else return false;

}
