package aws_push_notification.group4.amazonmobilepush;

//import android.app.Notification;
//import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
//import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
//import android.os.Build;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class CustomFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = CustomFirebaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i(TAG, "onMessageReceived::" + remoteMessage.getData().toString());

        Map<String, String> data = remoteMessage.getData();
        String title = "AmazonMobilePush";//data.get("title");
        String message = data.get("default");

        try{

            PowerManager.WakeLock wakelock = ((PowerManager) this.getSystemService(Context.POWER_SERVICE)).newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.FULL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE,"disableLock");
            wakelock.acquire();
            sendNotification(title, message);
            wakelock.release();

        }catch (Exception e) {
            sendNotification(title, message);
            Log.i(TAG, "Exception::" + e.toString());
        }
    }

    private void sendNotification(String title, String message) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                //.setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
