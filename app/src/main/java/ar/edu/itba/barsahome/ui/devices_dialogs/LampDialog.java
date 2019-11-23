package ar.edu.itba.barsahome.ui.devices_dialogs;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import ar.edu.itba.barsahome.BarsaApp;
import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Api;
import ar.edu.itba.barsahome.api.Device;
import yuku.ambilwarna.AmbilWarnaDialog;

public class LampDialog extends DialogFragment {
    private static final String TAG = "LampDialog";
    private NotificationManagerCompat notManager;


    private SeekBar lamp_seek_bar;
    private Switch lamp_switch;
    private TextView lamp_intensity_text;
    private Button show_color_button;
    private TextView lamp_title;
    private TextView cancel;
    private TextView accept;

    private String title;
    private boolean on;
    private Integer currentColor;
    private Integer intensity;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        title = "LAMP";
        on = true;

        notManager = NotificationManagerCompat.from(getActivity());



        View view = inflater.inflate(R.layout.dialog_lamp, container, false);

        lamp_switch = (Switch) view.findViewById(R.id.lamp_switch);
        lamp_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                on = isChecked;
            }
        });


        lamp_seek_bar = (SeekBar) view.findViewById(R.id.lamp_seekbar);
        lamp_seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                intensity = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        lamp_intensity_text = (TextView) view.findViewById(R.id.intensity_text);
        lamp_intensity_text.setText(getText(R.string.light_intensity_text));

        show_color_button = (Button) view.findViewById(R.id.select_color_button);
        show_color_button.setText(getText(R.string.show_color));
        show_color_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmbilWarnaDialog colordialog = new AmbilWarnaDialog(getActivity(), currentColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        currentColor = color;
                    }
                });
                colordialog.show();
            }
        });

        lamp_title = (TextView) view.findViewById(R.id.lamp_title);
        lamp_title.setText(title);


        cancel = (TextView) view.findViewById(R.id.cancel);
        cancel.setText(getText(R.string.cancel));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });



        accept = (TextView) view.findViewById(R.id.accept);
        accept.setText(getText(R.string.accept));
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), getText(R.string.accept_message), Toast.LENGTH_SHORT).show();

                if(on){
                    api_turnOn();
                }
                else {
                    api_turnOff();
                }

                api_setBrightness(intensity);
                api_setColor(Integer.toHexString(currentColor).substring(0, 6));

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

                currentColor = Integer.parseInt(response.getColor().replace("#", ""), 16);
                System.out.println(currentColor);
                intensity = response.getBrightness();
                switch (response.getStatus().toLowerCase()){
                    case "off":
                        on = false;
                        break;
                    case "on":
                        on = true;
                        break;
                }

                lamp_switch.setChecked(on);
                lamp_seek_bar.setProgress(intensity);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }


    private void api_setBrightness(Integer brightness){

        Integer[] args = new Integer[1];
        args[0] = brightness;

        Api.getInstance(getActivity()).setActionInt(getArguments().getString("deviceId"), "setBrightness",args, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getActivity(), getText(R.string.error_api), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void api_setColor(String color){
        String[] args = new String[1];
        args[0] = color;
        Api.getInstance(getActivity()).setActionString(getArguments().getString("deviceId"), "setColor",args, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getActivity(), getText(R.string.error_api), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void api_turnOff(){
        Api.getInstance(getActivity()).setAction(getArguments().getString("deviceId"), "turnOff",null, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getActivity(), getText(R.string.error_api), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void api_turnOn(){
        Api.getInstance(getActivity()).setAction(getArguments().getString("deviceId"), "turnOn",null, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getActivity(), getText(R.string.error_api), Toast.LENGTH_SHORT).show();
            }
        });

    }



    public void sendDevNot(View view){



        Notification not = new NotificationCompat.Builder(getActivity(), BarsaApp.CHANNEL_1_ID).setSmallIcon(R.drawable.ic_lamp)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setPriority(NotificationManager.IMPORTANCE_LOW).build();

        notManager.notify(1, not);




    }




}
