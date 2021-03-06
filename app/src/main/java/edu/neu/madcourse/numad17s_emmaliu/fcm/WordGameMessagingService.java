package edu.neu.madcourse.numad17s_emmaliu.fcm;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.content.Context;
import android.view.View;
import android.widget.Button;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

//import edu.neu.madcourse.lecture6.firebasedemo.MainActivity;
//import edu.neu.madcourse.lecture6.firebasedemo.R;

import edu.neu.madcourse.numad17s_emmaliu.R;
import edu.neu.madcourse.numad17s_emmaliu.wordGame.GameStatus;
import edu.neu.madcourse.numad17s_emmaliu.wordGame.LeaderboardActivity;
import edu.neu.madcourse.numad17s_emmaliu.wordGame.NotificationDialog;


public class WordGameMessagingService extends FirebaseMessagingService {
    private static final String TAG = WordGameMessagingService.class.getSimpleName();
    private static String chatTitle = "";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, " on MessageReceived get called");
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.d(TAG, "Message Notification Title: " + remoteMessage.getNotification().getTitle());


            if (remoteMessage.getNotification().getTitle() == null) {
                if (GameStatus.getIsInGame()) {
                    sendNotification(remoteMessage.getNotification().getBody());
                    Log.d(TAG, "send background");
                } else {
                    Log.d(TAG, " should be NotificationDialog");
                    Intent intent = new Intent(WordGameMessagingService.this, NotificationDialog.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            } else {
                chatTitle = remoteMessage.getNotification().getTitle();
                sendNotification(remoteMessage.getNotification().getBody());
            }

            // Note: We happen to be just getting the body of the notification and displaying it.
            // We could also get the title and other info and do different things.

        }

    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {

        Intent intent = new Intent(this, LeaderboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(chatTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        chatTitle = "";

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
