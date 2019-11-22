package ar.edu.itba.barsahome.ui.devices_dialogs;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import ar.edu.itba.barsahome.BarsaApp;
import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Api;
import ar.edu.itba.barsahome.api.Device;
import ar.edu.itba.barsahome.api.Routine;

public class BlindDialog extends DialogFragment {
    private NotificationManagerCompat notManager;
    private TextView blind_title;
    private ProgressBar blind_progbar;
    private Switch blind_switch;
    private TextView blind_percentage;
    private TextView cancel;
    private TextView accept;

    private Handler fetchHandler = new Handler();



    private String title;
    private Integer percentage;
    private boolean openning;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        notManager = NotificationManagerCompat.from(getActivity());

        startRepeating(getView());

        title = "BLINDS";


        View view = inflater.inflate(R.layout.dialog_blinds, container, false);
        blind_title = (TextView) view.findViewById(R.id.blind_text);
        blind_title.setText(title);



        blind_percentage = (TextView) view.findViewById(R.id.blind_percentage);



        blind_progbar = (ProgressBar) view.findViewById(R.id.blind_prog);




        blind_switch = (Switch) view.findViewById(R.id.blind_switch);

        blind_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                openning = isChecked;
            }
        });


        cancel = (TextView) view.findViewById(R.id.cancel);
        cancel.setText(getText(R.string.cancel));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopReapeating(getView());
                getDialog().dismiss();
            }
        });



        accept = (TextView) view.findViewById(R.id.accept);
        accept.setText(getText(R.string.accept));
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopReapeating(getView());
                Toast.makeText(getActivity(), getText(R.string.accept_message), Toast.LENGTH_SHORT).show();




                if(openning){
                    api_open(getArguments().getString("deviceId"));
                }
                else {
                    api_close(getArguments().getString("deviceId"));
                }
                sendDevNot(getView());
                getDialog().dismiss();
            }
        });

        fetching();


        return view;
    }


    private void fetching(){
        Api.getInstance(getActivity()).getDeviceState(getArguments().getString("deviceId"), new Response.Listener<Device>() {
            @Override
            public void onResponse(Device response) {
                switch (response.getStatus().toLowerCase()){
                    case "opened":
                        openning = true;
                        break;
                    case "closing":
                        openning = false;
                        break;

                }
                percentage = response.getLevel();

                blind_switch.setChecked(openning);
                blind_percentage.setText(percentage.toString() + "%" );
                blind_progbar.setProgress(percentage);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }


    private void api_open(String devId){
        Api.getInstance(getActivity()).setAction(devId, "open", null, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void api_close(String devId){
        Api.getInstance(getActivity()).setAction(devId, "close", null, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    public void startRepeating(View view){
        fetchRun.run();
    }

    public void stopReapeating(View view){
        fetchHandler.removeCallbacks(fetchRun);
    }

    public void fetchPerc(){
        Api.getInstance(getActivity()).getDeviceState(getArguments().getString("deviceId"), new Response.Listener<Device>() {
            @Override
            public void onResponse(Device response) {

                percentage = response.getLevel();

                blind_percentage.setText(percentage.toString() + "%" );
                blind_progbar.setProgress(percentage);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private Runnable fetchRun = new Runnable() {
        @Override
        public void run() {
            fetchPerc();
            fetchHandler.postDelayed(this, 1000);
        }
    };



    public void sendDevNot(View view){



        Notification not = new NotificationCompat.Builder(getActivity(), BarsaApp.CHANNEL_1_ID).setSmallIcon(R.drawable.ic_blinds)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setPriority(NotificationManager.IMPORTANCE_LOW).build();

        notManager.notify(1, not);




    }
}