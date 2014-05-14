package com.indivisible.shortie.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
public class LinkInputFragment
        extends Fragment
        implements OnClickListener
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private EditText etLongUrl;
    private Button bShorten;
    private OnInputListener inputListener;

    private static final String TAG = "sho:LinkInputFrag";


    ///////////////////////////////////////////////////////
    ////    init
    ///////////////////////////////////////////////////////

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_input, container, false);
        etLongUrl = (EditText) view.findViewById(R.id.etLongUrl);
        bShorten = (Button) view.findViewById(R.id.bShorten);
        bShorten.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            inputListener = (OnInputListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement OnInputListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        inputListener = null;
    }


    ///////////////////////////////////////////////////////
    ////    input
    ///////////////////////////////////////////////////////

    /**
     * Listener for LinkInputFragment submissions.
     * 
     * @author indiv
     */
    public interface OnInputListener
    {

        /**
         * Call on Submit button press to pass EditText's text.
         * 
         * @param urlLong
         */
        public void onShortenSubmit(String urlLong);
    }


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

    //    public String validateUrl()
    //    {
    //        //TODO: Move validation to clipboard listener, allow any manual string. let shortener service decide.
    //        String inputUrl = etLongUrl.getText().toString().trim();
    //        URL url = null;
    //        try
    //        {
    //            url = new URL(inputUrl);
    //        }
    //        catch (MalformedURLException e)
    //        {
    //            Log.w(TAG, "Failed to make URL");
    //            e.printStackTrace();
    //            return null;
    //        }
    //        URI uri = null;
    //        try
    //        {
    //            uri = url.toURI();
    //            return uri.toString();
    //        }
    //        catch (URISyntaxException e)
    //        {
    //            Log.w(TAG, "Failed to make URI");
    //            e.printStackTrace();
    //            return null;
    //        }
    //        //TODO: don't validate, just test connection and create short (two background threads)
    //        //  inform on no connect, fail on no short.
    //    }

}
