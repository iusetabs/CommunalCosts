package jameskealanthirdyearproject.communalcosts_client_app;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class MyService extends Service {

    private final IBinder myBinder = new MyLocalBinder();
    private NotificationCompat.Builder notification; //for the notification
    private static final int uniqueID = 45612; //needs to be global to be accessed in my method

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public class MyLocalBinder extends Binder {
        MyService getService(){
            return MyService.this;
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        notification = new NotificationCompat.Builder(this);
        setNotify(notification, uniqueID); //calls the turn on notification method
        return START_NOT_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
        stopNotify(uniqueID); //method to stop the notification
    }

    public void setNotify(NotificationCompat.Builder b, int u){ //for creating the notificaiton
        b.setSmallIcon(R.mipmap.ic_launcher);
        b.setTicker("This is the ticker");
        b.setWhen(System.currentTimeMillis());
        b.setContentTitle("Collective Title");
        b.setContentText("This it the context");

        Intent i = new Intent(this, HomeCollectiveView.class);
        PendingIntent p = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        b.setContentIntent(p);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(u, b.build());
    }

    public void stopNotify(int i){ //stops notification by passing the unique ID
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(i);
    }

}
