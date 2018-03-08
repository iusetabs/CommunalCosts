package jameskealanthirdyearproject.communalcosts_client_app;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessagingServce";
    private FirebaseAuth firAuth;
    private FirebaseUser userRef;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String notificationTitle = null, notificationBody = null, notificationCreator = null;

        // Check if message contains a notification payload.
        if (remoteMessage.getData() != null) {
            notificationTitle = remoteMessage.getData().get("title");
            notificationBody = remoteMessage.getData().get("body");
            notificationCreator = remoteMessage.getData().get("creator");
            Log.d(TAG, "Notification->" + notificationTitle + ": /n" + notificationBody + "/n sent by " + notificationCreator);
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        sendNotification(notificationTitle, notificationBody, notificationCreator);
    }


    private void sendNotification(String notificationTitle, String notificationBody, String notificationCreator) {
        firAuth = FirebaseAuth.getInstance();
        userRef = firAuth.getCurrentUser();
        if(!(notificationCreator.equals(userRef.getUid()))) {
            Log.d(TAG, notificationCreator + " != " + userRef.getUid());
            Intent intent = new Intent(this, HomeCollectiveView.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setAutoCancel(true)   //Automatically delete the notification
                    .setSmallIcon(R.mipmap.communal_logo) //Notification icon
                    .setContentIntent(pendingIntent)
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationBody)
                    .setSound(defaultSoundUri);


            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        }
        else{
            Log.d(TAG, "Creator shouldn't receive the notification!");
        }
    }
}