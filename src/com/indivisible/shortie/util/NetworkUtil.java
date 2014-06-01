package com.indivisible.shortie.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Convenience util class for testing network connectivity.
 * 
 * @author indiv
 */
public class NetworkUtil
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private static final String TAG = "NetworkUtil";


    ///////////////////////////////////////////////////////
    ////    methods
    ///////////////////////////////////////////////////////

    /**
     * Check with ConnectivityManager if a connection is possible.
     * 
     * @param context
     * @return
     */
    public static boolean canConnect(Context context)
    {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();
        if (info.isAvailable())
        {
            Log.i(TAG, "Can establish connection");
            return true;
        }
        else
        {
            switch (info.getState())
            {
                case CONNECTING:
                    Log.w(TAG, "Device connection is CONNECTING");
                    break;
                case DISCONNECTING:
                    Log.w(TAG, "DEvice connection is DISCONNECTING");
                    break;
                case DISCONNECTED:
                    Log.w(TAG, "Device connection is DISCONNECTED");
                    break;
                case SUSPENDED:
                    Log.w(TAG, "Device connection is SUSPENDED");
                    break;
                case CONNECTED:
                    Log.e(TAG, "Device connection is CONNECTED. WTF?");
                    break;
                case UNKNOWN:
                    Log.e(TAG, "Device connection is UNKNOWN");
                    break;
                default:
                    Log.e(TAG, "Device connection WAS NOT HANDLED");
                    break;
            }
            return false;
        }
    }

}
