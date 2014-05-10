package com.indivisible.shortie.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.indivisible.shortie.R;


public class LinkInputFragment
        extends Fragment
{

    private EditText etLongUrl;
    private Button bShorten;

    private static final String TAG = "LinkInputFrag";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_input, container, false);
    }


}
