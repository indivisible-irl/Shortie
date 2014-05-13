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
import android.widget.Toast;
import com.indivisible.shortie.data.LinkDataSource;
import com.indivisible.shortie.data.LinkPair;
import com.indivisible.shortie.data.LinkPairListAdapter;


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

    private static final String TAG = "LinksListFrag";


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
        //this.getListView().setStackFromBottom(true);
        adapter = new LinkPairListAdapter(getActivity(), this.getAllLinkPairs());
        setListAdapter(adapter);
        return view;
    }

    public interface OnLinkPairClickListener
    {

        public void onLinkShortClick(LinkPair linkPair);

        public void onLinkLongClick(LinkPair linkPair);

    }


    ///////////////////////////////////////////////////////
    ////    click handling
    ///////////////////////////////////////////////////////

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        Toast.makeText(getActivity(),
                       "List item clicked, pos: " + position,
                       Toast.LENGTH_SHORT).show();
        clickListener.onLinkShortClick(adapter.getItem(position));
    }

    @Override
    public boolean
            onItemLongClick(AdapterView<?> parent, View view, int position, long id)
    {
        Toast.makeText(getActivity(),
                       "List item long clicked, pos: " + position,
                       Toast.LENGTH_SHORT).show();
        clickListener.onLinkLongClick(adapter.getItem(position));
        return true;
    }

    ///////////////////////////////////////////////////////
    ////    link pair handling
    ///////////////////////////////////////////////////////

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

    public void addLinkPair(LinkPair linkPair)
    {
        try
        {
            linkSource.openWritable();
            LinkPair newLinkPair = linkSource.createLinkPair(linkPair.getCreatedMillis(),
                                                             linkPair.getShortUrl(),
                                                             linkPair.getLongUrl());
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

    public boolean removeLinkPair(LinkPair rmLinkPair)
    {
        //TODO: test if removing from list will update adapter
        try
        {
            linkSource.openWritable();
            boolean rmSuccess = linkSource.deleteLinkPair(rmLinkPair.getId());
            if (rmSuccess) adapter.remove(rmLinkPair);
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

}
