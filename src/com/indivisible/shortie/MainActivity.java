package com.indivisible.shortie;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{
	
	EditText etUrlIn, etUrlOut;
	Button bClear, bCopy, bGenerate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        etUrlIn = (EditText) findViewById(R.id.et_url_in);
		etUrlOut = (EditText) findViewById(R.id.et_url_out);
		bClear = (Button) findViewById(R.id.b_clear);
		bCopy = (Button) findViewById(R.id.b_copy);
		bGenerate = (Button) findViewById(R.id.b_generate);
		
		bClear.setOnClickListener(this);
		bCopy.setOnClickListener(this);
		bGenerate.setOnClickListener(this);
		
		Log.d("finished", "Main.onCreate()");
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        Log.d("finished", "Main.onCreateOptionsMenu()");
        return true;
    }

	public void onClick(View v) {
		switch (v.getId()){
		case R.id.b_clear:
			actionClear();
			Log.i("button", "Clear");
			break;
		case R.id.b_copy:
			Log.i("button", "Copy");
			break;
		case R.id.b_generate:
			actionGenerate();
			Log.i("button", "Generate");
			break;
		}
		Log.d("finished", "Main.onClick()");
	}
	
	private void actionClear(){
		etUrlIn.setText("");
		etUrlOut.setText("");
	}
	private void actionGenerate(){
		String urlIn = etUrlIn.getText().toString();
		Toast toast = Toast.makeText(getApplicationContext(), urlIn, Toast.LENGTH_SHORT);
		toast.show();
	}
}
