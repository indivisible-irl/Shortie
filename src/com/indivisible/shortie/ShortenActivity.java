package com.indivisible.shortie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShortenActivity
        extends ActionBarActivity
        implements View.OnClickListener
{

    EditText etLongUrl;
    TextView tvResult;
    Button bShorten;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shorten);
        initViews();

        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }
    }

    private void initViews()
    {
        etLongUrl = (EditText) findViewById(R.id.etLongUrl);
        tvResult = (TextView) findViewById(R.id.tvResult);
        bShorten = (Button) findViewById(R.id.bMakeShort);
    }


    //  Options

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.shorten, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment
            extends Fragment
    {

        public PlaceholderFragment()
        {}

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container,
                                 Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_shorten, container, false);
            return rootView;
        }
    }


    //  click handling

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bMakeShort:
                Toast.makeText(this, "Pressed shorten", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Unhandled button press", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
