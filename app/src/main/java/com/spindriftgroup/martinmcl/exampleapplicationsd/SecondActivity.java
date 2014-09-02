package com.spindriftgroup.martinmcl.exampleapplicationsd;

import android.app.Activity;
import android.app.Notification;
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
import android.widget.EditText;
import android.widget.RadioGroup;

import static android.widget.RadioGroup.OnCheckedChangeListener;


public class SecondActivity extends Activity {

    private EditText mCustomTitle, mCustomMessage; // Edit text boxes for the custom notification
    private RadioGroup mCustomIconGroup, showHideIconGroup; // Radiogroups with the Icon and settings for the custom notification
    private int mCustomIcon; // The variable that will hold the ID of the custom icon to show
    private boolean showIcon = false; // boolean that will tell if wear should show the app icon or not
    private String LOG_TAG = "WEAR"; // Our LogCat tag for debugging purposes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Custom Title and Message for custom Notification
        mCustomTitle = (EditText) findViewById(R.id.notificationTitle);
        mCustomMessage = (EditText) findViewById(R.id.notificationMessage);

        // RadioGroup for the customIcon
        mCustomIconGroup = (RadioGroup) findViewById(R.id.iconGroup);
        mCustomIconGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            // The name of the ICONS will change based on how you named it....
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.icon1:
                        mCustomIcon = R.drawable.ic_mail_notify;
                        break;
                    case R.id.icon2:
                        mCustomIcon = R.drawable.ic_alert_rnd_notify;
                        break;
                    case R.id.icon3:
                        mCustomIcon = R.drawable.ic_world_notify;
                        break;
                }
            }
        });

        // RadioGroup to determine if App Icons should be shown or not.
        showHideIconGroup = (RadioGroup) findViewById(R.id.hideIconGroup);
        showHideIconGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.showIcon:
                        showIcon = true;
                        break;
                    case R.id.hideIcon:
                        showIcon = false;
                        break;
                }
            }
        });
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
        int notificationId = 001; // id- An identifier for this notification unique within your application.
        //String eventTitle = getString(R.string.sampleNotifyText); // Title for the notificatton
        //String eventText = getString(R.string.sampleEventText); // Text for the notification
        String intentExtra = getString(R.string.sampleExtraString); // Extra String to be passed to a intent
        // A large String to be used by the BigStyle
        //String eventDescription = getString(R.string.sampleExtraEventTExt)
        //        + getString(R.string.sampleExtraEventText2);

        Intent viewIntent2 = new Intent(this, ThirdActivity.class);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent2, 0);

        // Specify the 'big view' content to display the long
        // event description that may not fit the normal content text.
        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();

        // We instantiate as null because they will be changing based on which button is pressed
        NotificationCompat.Builder mBuilder = null;
        Notification mNotification = null;

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        switch(view.getId()) {

            case R.id.bigNotificationWithAction:
                // Create the new intent that is gonna receive the information from our action.
                Intent photoIntent2 = new Intent(this, ThirdActivity.class); // Intent pointing to our third activity
                photoIntent2.putExtra("message", intentExtra); // Set the extra message that will open in the next activity
                photoIntent2.putExtra("photo", R.drawable.ic_launcher); // Send the photo to the next activity

                PendingIntent photoPending2 = PendingIntent.getActivity(this, 0, photoIntent2, 0); // set a new pending intent
                bigStyle.setBigContentTitle(getString(R.string.sampleBigTitle2)); // title for the Big Text
                bigStyle.bigText(getString(R.string.sampleBigText2)); // Message in the Big Text
                mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_mail_notify) // Small icon for our notification
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)) // The PNG picture
                        .setContentIntent(viewPendingIntent) // This will be the default OPEN button.
                        //.addAction(R.drawable.ic_mail_notify, "See screen 3", photoPending2) // This is our extra action. With an Extra Icon and pointing to the other PendingIntent
                        .setAutoCancel(true)
                        .setStyle(bigStyle); // Add the bigStyle
                break;

            case R.id.sendCustomNotification:
                // We instantiate the builder again
                mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(mCustomIcon) // This time we set the icon to be whenever icon is selected by the user
                        .setContentTitle(mCustomTitle.getText().toString()) // We set the contentTitle to the text in the EditText
                        .setAutoCancel(true)
                        .setContentText(mCustomMessage.getText().toString()) // We set the contentText to the message set by the user
                        .setContentIntent(viewPendingIntent); // set an intent to receive the Open action.

                mNotification = new NotificationCompat.Builder(this)
                        .setSmallIcon(mCustomIcon)
                        .extend(new NotificationCompat.WearableExtender().setHintHideIcon(!showIcon))
                        .setContentTitle(mCustomTitle.getText().toString())
                        .setContentText(mCustomMessage.getText().toString())
                        .setContentIntent(viewPendingIntent)
                        .build();
                break;
        }

        // This check will allow us to display the normal notification or the Wearable notification if the
        // notification is a CustomNotification
        if(view.getId() != R.id.sendCustomNotification) {
            // Build the notification and issues it with notification manager.
            notificationManager.notify(notificationId, mBuilder.build());
            Log.d(LOG_TAG, getString(R.string.normal_notify));
        } else {
            // Use the Wearable Notification Builder
            notificationManager.notify(notificationId, mNotification);
            Log.d(LOG_TAG, getString(R.string.wear_notify));
        }
    }

}
