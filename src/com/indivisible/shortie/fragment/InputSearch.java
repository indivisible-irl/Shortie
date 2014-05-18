package com.indivisible.shortie.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import com.indivisible.shortie.R;


/**
 * Fragment containing an EditText and Button for searching through past links
 * 
 * @author indiv
 */
public class InputSearch
        extends AInputFragment
        implements TextWatcher
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private EditText etSearchTerm;
    private ImageButton bSearch;
    private static final String TAG = "sho:InputSearch";


    ///////////////////////////////////////////////////////
    ////    init
    ///////////////////////////////////////////////////////

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_input_search, container, false);
        etSearchTerm = (EditText) view.findViewById(R.id.etSearchTerm);
        bSearch = (ImageButton) view.findViewById(R.id.bSearch);
        etSearchTerm.addTextChangedListener(this);
        bSearch.setOnClickListener(this);
        return view;
    }

    ///////////////////////////////////////////////////////
    ////    input
    ///////////////////////////////////////////////////////

    @Override
    public void onClick(View v)
    {
        Log.v(TAG, "Button: Search");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {}

    @Override
    public void afterTextChanged(Editable s)
    {
        Log.v(TAG, "searchTerm: " + s.toString());
        inputListener.onSearchTermChanged(s.toString());
    }

}
