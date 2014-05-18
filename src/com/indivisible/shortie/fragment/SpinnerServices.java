package com.indivisible.shortie.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import com.indivisible.shortie.R;


public class SpinnerServices
        extends ASpinnerFragment
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private static final String TAG = "sho:SpinnerServices";


    ///////////////////////////////////////////////////////
    ////    init
    ///////////////////////////////////////////////////////

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_spinner, container, false);
        spinner = (Spinner) view.findViewById(R.id.spServices);
        return view;
    }

}
