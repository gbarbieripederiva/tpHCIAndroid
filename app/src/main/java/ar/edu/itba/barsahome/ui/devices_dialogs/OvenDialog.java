package ar.edu.itba.barsahome.ui.devices_dialogs;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
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
import ar.edu.itba.barsahome.MainActivity;
import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Api;
import ar.edu.itba.barsahome.api.Device;

public class OvenDialog extends DialogFragment {


    private SeekBar temp;
    private TextView grillModeText;
    private TextView ovenTitle;
    private TextView convModeText;
    private TextView heatSourceText;
    private TextView tempText;
    private TextView tempValue;
    private Spinner convMode;
    private Spinner heatSource;
    private Spinner grillMode;
    private Switch ovenSwitch;
    private TextView cancel;
    private TextView accept;

    private String title;
    private Double max,min, current;
    private String[] heatSourceArray, grillModeArray, conModeArray;
    private boolean on;
    private Integer currentHeatSource, currentGrillMode, currentConMode;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){


        max = 230.0;
        min = 90.0;




        title = getArguments().getString("deviceName");

        heatSourceArray = getResources().getStringArray(R.array.heat_source);
        grillModeArray = getResources().getStringArray(R.array.grill_mode);
        conModeArray = getResources().getStringArray(R.array.conv_mode);


        View view = inflater.inflate(R.layout.dialog_oven, container, false);

        ovenTitle = (TextView) view.findViewById(R.id.oven_title);
        ovenTitle.setText(title);

        grillModeText = (TextView) view.findViewById(R.id.oven_grill_mode_text);
        grillModeText.setText(getText(R.string.oven_grill_mode_text));

        convModeText = (TextView) view.findViewById(R.id.oven_convection_mode_text);
        convModeText.setText(getText(R.string.oven_conv_mode_text));

        heatSourceText = (TextView) view.findViewById(R.id.oven_source_text);
        heatSourceText.setText(getText(R.string.oven_heat_source_text));

        tempText = (TextView) view.findViewById(R.id.oven_temperature_text);
        tempText.setText(getText(R.string.temp_text));


        convMode = (Spinner) view.findViewById(R.id.oven_convection_mode_spinner);
        ArrayAdapter<String> convAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, conModeArray);
        convMode.setAdapter(convAdapter);

        convMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentConMode = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        grillMode = (Spinner) view.findViewById(R.id.oven_grill_mode_spinner);
        ArrayAdapter<String> grillAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, grillModeArray);
        grillMode.setAdapter(grillAdapter);

        grillMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentGrillMode = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        heatSource = (Spinner) view.findViewById(R.id.oven_source_spinner);
        final ArrayAdapter<String> sourceAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, heatSourceArray);
        heatSource.setAdapter(sourceAdapter);

        heatSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentHeatSource = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ovenSwitch = (Switch) view.findViewById(R.id.oven_switch);


        ovenSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                on = isChecked;
            }
        });

        tempValue = (TextView) view.findViewById(R.id.temp_value);


        temp = (SeekBar) view.findViewById(R.id.oven_temperature_seekbar);



        temp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                current = min + (progress*(max - min) / 100);
                tempValue.setText(current + "°C");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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

                api_setConvection(conModeArray[currentConMode]);
                api_setGrill(grillModeArray[currentGrillMode]);
                api_setHeat(heatSourceArray[currentHeatSource]);
                api_setTemperature(current);




                local_refresh();
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
                switch (response.getConvection().toLowerCase()){
                    case "apagado":
                    case "off":
                        currentConMode = 0;
                        break;
                    case "económico":
                    case "eco":
                        currentConMode = 1;
                        break;

                    case "convencional":
                    case "normal":
                        currentConMode = 2;
                        break;

                    default:
                        currentConMode = 0;

                }

                switch (response.getGrill().toLowerCase()){
                    case "apagado":
                    case "off":
                        currentGrillMode = 0;
                        break;
                    case "económico":
                    case "eco":
                        currentGrillMode = 1;
                        break;
                    case "completo":
                    case "large":
                        currentGrillMode = 2;
                        break;
                }

                switch (response.getHeat().toLowerCase()){
                    case "convencional":
                    case "conventional":
                        currentHeatSource = 0;
                        break;
                    case "abajo":
                    case "bottom":
                        currentHeatSource = 1;
                        break;
                    case "arriba":
                    case "top":
                        currentHeatSource = 2;
                        break;
                }

                switch (response.getStatus()){
                    case "on":
                        on = true;
                        break;
                    case "off":
                        on = false;
                        break;
                }

                current = response.getTemperature();


                ovenSwitch.setChecked(on);
                grillMode.setSelection(currentGrillMode);
                convMode.setSelection(currentConMode);
                heatSource.setSelection(currentHeatSource);
                tempValue.setText(current.toString() + "°C");
                temp.setProgress((int) ((current - min) * 100 /(max -min)));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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


    private void api_setTemperature(Double temp){
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

    private void api_setHeat(String source){
        String[] args = new String[1];
        args[0] = source;

        Api.getInstance(getActivity()).setActionString(getArguments().getString("deviceId"), "setHeat", args,new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void api_setGrill(String grill){
        String[] args = new String[1];
        args[0] = grill;

                Api.getInstance(getActivity()).setActionString(getArguments().getString("deviceId"), "setGrill", args,new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void api_setConvection(String conv){
        String[] args = new String[1];
        args[0] = conv;

                Api.getInstance(getActivity()).setActionString(getArguments().getString("deviceId"), "setConvection", args,new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }


    private void local_refresh(){
        Api.getInstance(getActivity()).getDevices(new Response.Listener<ArrayList<Device>>() {
            @Override
            public void onResponse(ArrayList<Device> response) {
                MainActivity.localDevices = new ArrayList<>(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }




}

