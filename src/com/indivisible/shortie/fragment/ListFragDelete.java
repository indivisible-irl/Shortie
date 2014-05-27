package com.indivisible.shortie.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.indivisible.shortie.data.LinkPair;
import com.indivisible.shortie.data.LinkPairListAdapter;


/**
 * Fragment for displaying and interacting with LinkPairs.
 * 
 * @author indiv
 */
public class ListFragDelete
        extends AListFragment
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private static final String TAG = "sho:LinkListInput";


    ///////////////////////////////////////////////////////
    ////    init
    ///////////////////////////////////////////////////////

    public ListFragDelete()
    {}

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        Log.v(TAG, "Attached LinkListInput fragment");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View
            onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(android.R.layout.list_content, container, false);
        adapter = new LinkPairListAdapter(getActivity(), this.getAllLinkPairs());
        setListAdapter(adapter);
        Log.d(TAG, "Number of list items: " + adapter.getCount());
        return view;
    }


    ///////////////////////////////////////////////////////
    ////    click handling
    ///////////////////////////////////////////////////////


    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        Log.v(TAG, "Short click, pos: " + position);
        LinkPair linkPair = adapter.getItem(position);
        Log.v(TAG, linkPair.getId() + ":" + linkPair.getLongUrl());
        clickListener.onLinkShortClick(linkPair);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
    {
        Log.v(TAG, "Long click, pos: " + position);
        LinkPair linkPair = adapter.getItem(position);
        Log.v(TAG, linkPair.getId() + ":" + linkPair.getLongUrl());
        clickListener.onLinkLongClick(linkPair);
        return true;
    }


}
