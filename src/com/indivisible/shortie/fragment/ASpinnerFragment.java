package com.indivisible.shortie.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Spinner;
import com.indivisible.shortie.service.GoogleShortener;
import com.indivisible.shortie.service.Shortener;


public abstract class ASpinnerFragment
        extends Fragment
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    protected Spinner spinner;
    protected Shortener[] shortenerServices = new Shortener[] {
            new GoogleShortener(), new GoogleShortener()
    };
    private static final String TAG = "sho:ASpinnerFrag";


    ///////////////////////////////////////////////////////
    ////    init
    ///////////////////////////////////////////////////////

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        // no need for a listener
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

}
