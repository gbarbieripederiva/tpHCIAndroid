package ar.edu.itba.barsahome.ui.devices_dialogs;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
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

public class FridgeDialog extends DialogFragment {
    private NotificationManagerCompat notManager;

    private TextView fridge_title;
    private SeekBar fridge_temp;
    private SeekBar freeze_temp;
    private Spinner fridge_options;
    private TextView fridge_temp_text, freezer_temp_text, mode_text;
    private TextView fridge_temp_value, frezer_temp_value;
    private TextView cancel;
    private TextView accept;

    private Double minFridge, maxFridge, currentFridge;
    private Double minFreezer, maxFreezer, currentFreezer;
    private String title;
    private String[] fridge_options_array;
    private Integer current_fridge_opt;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        title = "FRIDGE";
        minFridge = 2.0;
        maxFridge = 8.0;
        minFreezer = -20.0;
        maxFreezer = -8.0;
        fridge_options_array = getResources().getStringArray(R.array.fridge_opt);

        notManager = NotificationManagerCompat.from(getActivity());


        View view = inflater.inflate(R.layout.dialog_fridge, container, false);
        fridge_title = (TextView) view.findViewById(R.id.fridge_title);
        fridge_title.setText(title);

        fridge_options = (Spinner) view.findViewById(R.id.fridge_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, fridge_options_array);
        fridge_options.setAdapter(adapter);
        fridge_options.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                current_fridge_opt = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        frezer_temp_value = (TextView) view.findViewById(R.id.value_freezer_temp);

        freeze_temp = (SeekBar) view.findViewById(R.id.freezer_seekbar);
        freeze_temp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentFreezer = minFreezer + (progress*(maxFreezer - minFreezer) / 100);
                frezer_temp_value.setText(currentFreezer + "°C");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        fridge_temp_value =(TextView) view.findViewById(R.id.value_fridge_temp);
        fridge_temp = (SeekBar) view.findViewById(R.id.fridge_seekbar);
        fridge_temp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentFridge = minFridge + ((progress*(maxFridge - minFridge))/100);
                fridge_temp_value.setText(currentFridge.toString() + "°C");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        fridge_temp_text = (TextView) view.findViewById(R.id.fridge_text);
        fridge_temp_text.setText(getText(R.string.fridge_temp_text));

        freezer_temp_text = (TextView) view.findViewById(R.id.freezer_text);
        freezer_temp_text.setText(getText(R.string.freeze_temp_text));

        mode_text = (TextView) view.findViewById(R.id.fridge_mode);
        mode_text.setText(getText(R.string.mode_text));



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

                api_setFreezerTemp(currentFreezer);
                api_setFridgeTemp(currentFridge);
                api_setMode(fridge_options_array[current_fridge_opt]);

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
                switch (response.getMode().toLowerCase()){

                    case "default":
                    case "normal":
                        current_fridge_opt = 0;
                        break;
                    case "party":
                    case "fiesta":
                        current_fridge_opt = 1;
                        break;
                    case "vacation":
                    case "vacaciones":
                        current_fridge_opt = 2;
                        break;
                }

                currentFreezer = response.getFreezerTemperature();

                currentFridge = response.getTemperature();


                fridge_options.setSelection(current_fridge_opt);
                freeze_temp.setProgress((int) ((currentFreezer - minFreezer) * 100 /(maxFreezer -minFreezer)));
                fridge_temp.setProgress((int)((currentFridge - minFridge) * 100 / (maxFridge - minFridge)));
                frezer_temp_value.setText(currentFreezer + "°C");
                fridge_temp_value.setText(currentFridge.toString() + "°C");


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });
    }





    private void api_setFridgeTemp(Double temp){

        Double[] args = new Double[1];
        args[0] = temp;

        Api.getInstance(getActivity()).setActionDoub(getArguments().getString("deviceId"), "setTemperature", args, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    private void api_setFreezerTemp(Double temp){

        Double[] args = new Double[1];
        args[0] = temp;

        Api.getInstance(getActivity()).setActionDoub(getArguments().getString("deviceId"), "setFreezerTemperature", args, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    private void api_setMode(String mode){
        String[] args = new String[1];
        args[0] = mode;

        Api.getInstance(getActivity()).setActionString(getArguments().getString("deviceId"), "setMode", args, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    public void sendDevNot(View view){



        Notification not = new NotificationCompat.Builder(getActivity(), BarsaApp.CHANNEL_1_ID).setSmallIcon(R.drawable.ic_refrigerator)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setPriority(NotificationManager.IMPORTANCE_LOW).build();

        notManager.notify(1, not);




    }



}
