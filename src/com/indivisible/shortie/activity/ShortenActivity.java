package com.indivisible.shortie.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.indivisible.shortie.R;
import com.indivisible.shortie.R.id;
import com.indivisible.shortie.R.layout;
import com.indivisible.shortie.R.menu;
import com.indivisible.shortie.data.LinkPair;
import com.indivisible.shortie.fragment.LinkInputFragment.OnInputListener;
import com.indivisible.shortie.fragment.LinkPairListFragment;
import com.indivisible.shortie.fragment.LinkPairListFragment.OnLinkPairClickListener;

public class ShortenActivity
        extends ActionBarActivity
        implements OnInputListener, OnLinkPairClickListener
{

    ///////////////////////////////////////////////////////
    ////    data
    ///////////////////////////////////////////////////////

    private LinkPairListFragment listFragment;
    private static final String TAG = "ShortenActivity";


    ///////////////////////////////////////////////////////
    ////    init
    ///////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shorten);
        listFragment = (LinkPairListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frLinksList);
    }


    ///////////////////////////////////////////////////////
    ////    fragments
    ///////////////////////////////////////////////////////

    // From LinkInputFragment
    @Override
    public void onShortenSubmit(String longUrl)
    {
        //Toast.makeText(this, "Valid URL: " + longUrl, Toast.LENGTH_SHORT).show();
        long now = System.currentTimeMillis();
        //Toast.makeText(this, "now: " + now, Toast.LENGTH_SHORT).show();
        LinkPair newLinkPair = new LinkPair();
        newLinkPair.setCreatedMillis(now);
        newLinkPair.setLongUrl(longUrl);
        newLinkPair.setShortUrl("TEMP SHORT");
        listFragment.addLinkPair(newLinkPair);
    }

    // From LinkPairListFragment
    @Override
    public void onLinkShortClick(LinkPair linkPair)
    {
        Toast.makeText(this,
                       "LinkPair clicked, id: " + linkPair.getId(),
                       Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLinkLongClick(LinkPair linkPair)
    {
        Toast.makeText(this,
                       "LinkPair long clicked, id: " + linkPair.getId(),
                       Toast.LENGTH_SHORT).show();
    }


    ///////////////////////////////////////////////////////
    ////    options
    ///////////////////////////////////////////////////////

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


}
