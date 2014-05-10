package com.indivisible.shortie;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShortenActivity
        extends ActionBarActivity
        implements OnClickListener
{

    private EditText etLongUrl;
    private TextView tvResult;
    private Button bShorten;

    private String TAG = "ShortenAct";


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


    //  click handling

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bMakeShort:
                Toast.makeText(this, "Pressed shorten", Toast.LENGTH_SHORT).show();
                setTestResult();
                break;
            default:
                Toast.makeText(this, "Unhandled button press", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private void setTestResult()
    {
        String url = etLongUrl.getText().toString();
        tvResult.setText(url);
    }

}
