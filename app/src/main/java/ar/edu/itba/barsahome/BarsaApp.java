package ar.edu.itba.barsahome;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class BarsaApp extends Application {
    public static final String CHANNEL_1_ID = "channel1";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();

    }

    private void createNotificationChannels(){
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1_ID, "DEVICE CHANNEL", NotificationManager.IMPORTANCE_LOW);
            channel1.setDescription((String) getText(R.string.notification));

            manager.createNotificationChannel(channel1);
        }


    }
}
