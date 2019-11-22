package ar.edu.itba.barsahome.ui;

import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import ar.edu.itba.barsahome.BarsaApp;
import ar.edu.itba.barsahome.R;

public class Notification {

    private Context context;
    private NotificationManagerCompat notManager;

    public Notification(Context context){
        this.context = context;
        notManager = NotificationManagerCompat.from(context);
    }


    public void sendDevNot(){

/*

        Notification not = new NotificationCompat.Builder(context, BarsaApp.CHANNEL_1_ID).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getResources().getString(R.string.notification_title))
                .setContentText(context.getResources().getString(R.string.notification_text))
                .setPriority(NotificationManager.IMPORTANCE_LOW).build();

 */


    }


}
