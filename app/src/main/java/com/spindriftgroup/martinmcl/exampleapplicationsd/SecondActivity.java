package com.spindriftgroup.martinmcl.exampleapplicationsd;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class SecondActivity extends Activity {

    private String LOG_TAG = "WEAR"; // Our LogCat tag for debugging purposes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

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

    // Define the method to send the notifications with the same name from the Android onClick from the XML Layout
    public void sendNotification(View view) {

        // Common elements for all our notifications
        int notificationId = 002; // id- An identifier for this notification unique within your application.
        String intentExtra = getString(R.string.sampleExtraString); // Extra String to be passed to a intent


        Intent viewIntent2 = new Intent(this, ThirdActivity.class);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent2, 0);

        // Specify the 'big view' content to display the long
        // event description that may not fit the normal content text.
        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();

        NotificationCompat.Builder mBuilder = null;

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // Create the new intent that is gonna receive the information from our action.
        Intent pageIntent2 = new Intent(this, ThirdActivity.class); // Intent pointing to our third activity
        pageIntent2.putExtra("message", intentExtra); // Set the extra message that will open in the next activity
        pageIntent2.putExtra("photo", R.drawable.ic_launcher); // Send the photo to the next activity

        bigStyle.setBigContentTitle(getString(R.string.sampleBigTitle2)); // title for the Big Text
        bigStyle.bigText(getString(R.string.sampleBigText2)); // Message in the Big Text
        mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_mail_notify) // Small icon for our notification
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)) // The PNG picture
                .setContentIntent(viewPendingIntent) // This will be the default OPEN button.
                .setAutoCancel(true)
                .setStyle(bigStyle); // Add the bigStyle

        notificationManager.notify(notificationId, mBuilder.build());
        Log.d(LOG_TAG, getString(R.string.normal_notify));

    }

}
