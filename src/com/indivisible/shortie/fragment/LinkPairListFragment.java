package com.indivisible.shortie.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class LinkPairListFragment
        extends ListFragment
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private ListView lvLinkPairs;
    private static final String TAG = "LinksListFrag";


    ///////////////////////////////////////////////////////
    ////    init
    ///////////////////////////////////////////////////////

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(android.R.layout.list_content, container, false);
    }


    ///////////////////////////////////////////////////////
    ////    click handling
    ///////////////////////////////////////////////////////


}
