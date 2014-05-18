package com.indivisible.shortie.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.indivisible.shortie.R;


/**
 * InputFragment to display Cancel and Delete buttons.
 * 
 * @author indiv
 */
public class InputDelete
        extends AInputFragment
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private Button bCancel;
    private Button bDelete;
    private static final String TAG = "sho:InputDelete";


    ///////////////////////////////////////////////////////
    ////    init
    ///////////////////////////////////////////////////////

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_input_delete, container, false);
        bCancel = (Button) view.findViewById(R.id.bCancel);
        bDelete = (Button) view.findViewById(R.id.bDelete);
        bCancel.setOnClickListener(this);
        bDelete.setOnClickListener(this);
        return view;
    }


    ///////////////////////////////////////////////////////
    ////    input
    ///////////////////////////////////////////////////////

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bCancel:
                Log.v(TAG, "Button: Cancel");
                break;
            case R.id.bDelete:
                Log.v(TAG, "Button: Delete");
                break;
            default:
                Log.e(TAG, "Unhandled click");
                break;
        }
    }

}
