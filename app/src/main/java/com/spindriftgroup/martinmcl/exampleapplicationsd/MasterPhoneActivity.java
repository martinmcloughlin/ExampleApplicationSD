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


public class MasterPhoneActivity extends Activity {

    private EditText mCustomTitle, mCustomMessage; // Edit text boxes for the custom notification
    private RadioGroup mCustomIconGroup, showHideIconGroup; // Radiogroups with the Icon and settings for the custom notification
    private int mCustomIcon; // The variable that will hold the ID of the custom icon to show
    private boolean showIcon = false; // boolean that will tell if wear should show the app icon or not
    private String LOG_TAG = "WEAR"; // Our LogCat tag for debugging purposes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_phone);

        // Custom Title and Message for custom Notification
        mCustomTitle = (EditText) findViewById(R.id.notificationTitle);
        mCustomMessage = (EditText) findViewById(R.id.notificationMessage);

        // RadioGroup for the customIcon
        mCustomIconGroup = (RadioGroup) findViewById(R.id.iconGroup);
        mCustomIconGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
        showHideIconGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
        getMenuInflater().inflate(R.menu.master_phone, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Define the method to send the notifications with the same name from the Android onClick from the XML Layout
    // Define the method to send the notifications with the same name from the Android onClick from the XML Layout
    public void sendNotification(View view) {

        // Common elements for all our notifications
        int notificationId = 001; // id- An identifier for this notification unique within your application.
        String eventTitle = "Sample Notification"; // Title for the notificaiton
        String eventText = "Text for the notification."; // Text for the notificaiton
        String intentExtra = "This is an extra String!"; // Extra String to be passed to a intent
        // A large String to be used by the BigStyle
        String eventDescription = "This is supposed to  be a content that will not fit the normal content screen"
                + " usually a bigger text, by example a long text message or email.";

        // Build intent for notification content - This will take us to our MainActivity
        Intent viewIntent = new Intent(this, MasterPhoneActivity.class);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, 0);

        // Specify the 'big view' content to display the long
        // event description that may not fit the normal content text.
        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();

        // We instantiate as null because they will be changing based on which button is pressed
        NotificationCompat.Builder mBuilder = null;
        Notification mNotification = null;

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        switch(view.getId()) {
            case R.id.simpleNotification:
                mBuilder = new NotificationCompat.Builder(this) // Instantiate the builder with our context.
                        .setSmallIcon(R.drawable.ic_mail_notify) // set the icon
                        .setContentTitle(eventTitle) // set the title
                        .setContentText(eventText) // set the text
                        .setAutoCancel(true)  // This flag makes the notification disappear when the user clicks on it!
                        .setContentIntent(viewPendingIntent); // and finally the intent to be used
                break;

            case R.id.bigNotification:
                bigStyle.bigText(eventDescription); // bigText will override setContentText
                bigStyle.setBigContentTitle("Override Title"); // bigContentTitle Override the contentTitle
                mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_alert_rnd_notify)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                        .setContentTitle(eventTitle) // This is unnecessary for the big notification if you use bigText
                        .setContentText(eventText) // Unnecessary if setBigContentTitle is Overriden
                        .setContentIntent(viewPendingIntent)
                        .setAutoCancel(true)
                        .setStyle(bigStyle);
                break;

            case R.id.bigNotificationWithAction:
                // Create the new intent that is gonna receive the information from our action.
                Intent photoIntent = new Intent(this, SecondActivity.class); // Intent pointing to our second activity
                photoIntent.putExtra("message", intentExtra); // Set the extra message that will open in the next activity
                photoIntent.putExtra("photo", R.drawable.ic_launcher); // Send the photo to the next activity

                PendingIntent photoPending = PendingIntent.getActivity(this, 0, photoIntent, 0); // set a new pending intent
                bigStyle.setBigContentTitle("Mr. Flowers"); // title for the Big Text
                bigStyle.bigText("Check out this picture!! :D"); // Message in the Big Text
                mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_world_notify) // Small icon for our notification
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)) // The PNG picture
                        .setContentIntent(viewPendingIntent) // This will be the default OPEN button.
                        .addAction(R.drawable.ic_photo_view, "See Photo", photoPending) // This is our extra action. With an Extra Icon and pointing to the other PendingIntent
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

                // This is an example of the NEW WearableNotification SDK.
                // The WearableNotification has special functionality for wearable devices
                // By example the setHintHideIcon hides the APP ICON from the notification.
                mNotification = new Notification.Builder(this)
                        //.setHintHideIcon(!showIcon) // This will determine if we should show or not the Icon of the app
                        .build();
                break;
        }

        // This check will allow us to display the normal notification or the Wearable notification if the
        // notification is a CustomNotification
        if(view.getId() != R.id.sendCustomNotification) {
            // Build the notification and issues it with notification manager.
            notificationManager.notify(notificationId, mBuilder.build());
            Log.d(LOG_TAG, "Normal Notification");
        } else {
            // Use the Wearable Notification Builder
            notificationManager.notify(notificationId, mNotification);
            Log.d(LOG_TAG, "Wear Notification");
        }
    }

}
