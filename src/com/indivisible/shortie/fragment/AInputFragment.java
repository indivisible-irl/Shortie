package com.indivisible.shortie.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View.OnClickListener;


/**
 * Parent class for InputFragments
 * 
 * @author indiv
 */
public abstract class AInputFragment
        extends Fragment
        implements OnClickListener
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    protected OnInputListener inputListener;
    private static final String TAG = "sho:AInputFrag";


    ///////////////////////////////////////////////////////
    ////    init
    ///////////////////////////////////////////////////////

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
            Log.e(TAG, "Parent Activity deosn't implement 'OnInputListener'");
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
    ////    interface
    ///////////////////////////////////////////////////////

    /**
     * Listener for LinkInputFragment submissions.
     * 
     * @author indiv
     */
    public interface OnInputListener
    {

        //-------------------------------------//
        //  Submit
        //-------------------------------------//

        public void onShortenSubmit(String urlLong);

        //-------------------------------------//
        //  Delete
        //-------------------------------------//

        public void onDeleteConfirm();

        public void onDeleteCancel();

        //-------------------------------------//
        //  Search
        //-------------------------------------//

        public void onSearchTermChanged(String searchTerm);

    }
}
