package com.indivisible.shortie.fragment;

import java.util.List;
import android.app.Activity;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import com.indivisible.shortie.data.LinkDataSource;
import com.indivisible.shortie.data.LinkPair;
import com.indivisible.shortie.data.LinkPairListAdapter;


/**
 * Fragment for displaying and interacting with LinkPairs.
 * 
 * @author indiv
 */
public class LinkPairListFragment
        extends ListFragment
        implements OnItemLongClickListener
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private LinkDataSource linkSource;
    private LinkPairListAdapter adapter;
    private OnLinkPairClickListener clickListener;

    private static final String TAG = "sho:LinkListFrag";


    ///////////////////////////////////////////////////////
    ////    init
    ///////////////////////////////////////////////////////

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
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(android.R.layout.list_content, container, false);
        adapter = new LinkPairListAdapter(getActivity(), this.getAllLinkPairs());
        setListAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        //TODO: Fill from bottom and ensure correct ordering
        super.onActivityCreated(savedInstanceState);
        getListView().setLongClickable(true);
        getListView().setOnItemLongClickListener(this);
        getListView().setStackFromBottom(true);
    }


    ///////////////////////////////////////////////////////
    ////    click handling
    ///////////////////////////////////////////////////////

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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        Log.v(TAG, "Short click, pos: " + position);
        LinkPair linkPair = adapter.getItem(position);
        Log.v(TAG, linkPair.getId() + ":" + linkPair.getLongUrl());
        clickListener.onLinkShortClick(linkPair);
    }

    @Override
    public boolean
            onItemLongClick(AdapterView<?> parent, View view, int position, long id)
    {
        Log.v(TAG, "Long click, pos: " + position);
        LinkPair linkPair = adapter.getItem(position);
        Log.v(TAG, linkPair.getId() + ":" + linkPair.getLongUrl());
        clickListener.onLinkLongClick(linkPair);
        return true;
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
    private List<LinkPair> getAllLinkPairs()
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
