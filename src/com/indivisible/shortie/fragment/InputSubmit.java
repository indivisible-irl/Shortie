package com.indivisible.shortie.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.indivisible.shortie.R;


/**
 * Fragment containing an EditText and Button for submitting/editing URLs
 * manually.
 * 
 * @author indiv
 */
public class InputSubmit
        extends AInputFragment
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private EditText etLongUrl;
    private Button bShorten;
    private String LONG_URL = "long_url";

    private static final String TAG = "sho:LinkInputFrag";


    ///////////////////////////////////////////////////////
    ////    init
    ///////////////////////////////////////////////////////

    @Override
    public View
            onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_input_submit, container, false);
        etLongUrl = (EditText) view.findViewById(R.id.etLongUrl);
        bShorten = (Button) view.findViewById(R.id.bShorten);
        bShorten.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null)
        {
            etLongUrl.setText(savedInstanceState.getString(LONG_URL, ""));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString(LONG_URL, etLongUrl.getText().toString());
    }


    ///////////////////////////////////////////////////////
    ////    input
    ///////////////////////////////////////////////////////

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bShorten:
                String longUrl = etLongUrl.getText().toString();
                Log.v(TAG, "bShorten: " + longUrl);
                inputListener.onShortenSubmit(longUrl);
                break;
            default:
                break;
        }
    }

}
