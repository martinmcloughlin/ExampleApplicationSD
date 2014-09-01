package com.spindriftgroup.martinmcl.exampleapplicationsd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView mTextView = (TextView) findViewById(R.id.extraMessage); // TextView to retrieve the message
        ImageView mImageView = (ImageView) findViewById(R.id.extraPhoto); // ImageView to retrieve the picture

        // Get the intent information
        Intent extraIntent = getIntent();

        // Get the intent information based on the names passed by your notificaiton "message" and
        mTextView.setText(extraIntent.getStringExtra("message")); // Retrieve the text and set it to our TextView
        mImageView.setImageResource(extraIntent.getIntExtra("photo", 0)); // The zero is a default value in case the intent extra is empty.
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
