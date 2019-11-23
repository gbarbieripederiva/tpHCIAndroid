package ar.edu.itba.barsahome;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import ar.edu.itba.barsahome.api.Api;
import ar.edu.itba.barsahome.api.Device;

public class DevWorkManager extends Worker {


    private NotificationManagerCompat notManager;
    public ArrayList<Device> apiCopy;

    public DevWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        notManager = NotificationManagerCompat.from(getApplicationContext());
    }

    @NonNull
    @Override
    public Result doWork() {

        Api.getInstance(getApplicationContext()).getDevices(new Response.Listener<ArrayList<Device>>() {
            @Override
            public void onResponse(ArrayList<Device> response) {
                apiCopy = new ArrayList<>(response);
                if(!(MainActivity.localDevices.contains(response))){
                    sendDevNot();
                    MainActivity.localDevices = new ArrayList<>(response);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        return Result.SUCCESS;
    }
    public void sendDevNot(){



        Notification not = new NotificationCompat.Builder(getApplicationContext(), BarsaApp.CHANNEL_1_ID).setSmallIcon(R.drawable.ic_door)
                .setContentTitle("Cambio en un dispositivo")
                .setContentText("Ha habido un cambio en alguno de sus dispositivos")
                .setPriority(NotificationManager.IMPORTANCE_HIGH).build();

        notManager.notify(1, not);




    }
}
