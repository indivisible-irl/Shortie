package com.indivisible.shortie.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.indivisible.shortie.R;
import com.indivisible.shortie.service.GoogleShortener;
import com.indivisible.shortie.service.Shortener;

/**
 * Activity to manage the shortening of links.
 * 
 * @author indiv
 */
public class ShortenActivity
        extends ActionBarActivity
        implements OnClickListener
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    //private ListView lvPreviousLinks;
    private EditText etLongUrl;
    private TextView tvResult;
    private Button bShorten;

    private String TAG = "ShortenAct";


    ///////////////////////////////////////////////////////
    ////    init
    ///////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shorten);
        initViews();
    }

    private void initViews()
    {
        etLongUrl = (EditText) findViewById(R.id.etLongUrl);
        tvResult = (TextView) findViewById(R.id.tvResult);
        bShorten = (Button) findViewById(R.id.bMakeShort);
        bShorten.setOnClickListener(this);
    }


    ///////////////////////////////////////////////////////
    ////    click handling
    ///////////////////////////////////////////////////////

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bMakeShort:
                Log.v(TAG, "ButtonPress: Shorten");
                shortenLink();
                break;
            default:
                Log.w(TAG, "Unhandled button press");
                break;
        }
    }


    ///////////////////////////////////////////////////////
    ////    methods
    ///////////////////////////////////////////////////////

    private void shortenLink()
    {
        String longUrl = etLongUrl.getText().toString();
        new ShortenTask().execute(longUrl);
    }


    ///////////////////////////////////////////////////////
    ////    shorten task
    ///////////////////////////////////////////////////////

    private class ShortenTask
            extends AsyncTask<String, Void, String>
    {

        @Override
        protected void onPreExecute()
        {
            Log.d(TAG, "Starting link shortening...");
        }

        @Override
        protected String doInBackground(String... params)
        {
            Shortener shortener = new GoogleShortener();
            return shortener.requestUrl(params[0]);
        }

        @Override
        protected void onPostExecute(String result)
        {
            tvResult.setText(result);
        }

        @Override
        protected void onCancelled()
        {
            Log.d(TAG, "Cancelled shortening.");
        }


    }
}
