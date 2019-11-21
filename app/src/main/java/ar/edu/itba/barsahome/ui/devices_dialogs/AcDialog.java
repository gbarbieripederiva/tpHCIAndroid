package ar.edu.itba.barsahome.ui.devices_dialogs;

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
import androidx.fragment.app.DialogFragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Api;
import ar.edu.itba.barsahome.api.Params;

public class AcDialog extends DialogFragment {
    private TextView acTitle;
    private TextView acModeText;
    private TextView acSpeedText;
    private TextView acVertText;
    private TextView acHorText;
    private TextView acTempText;
    private TextView acTempValue;
    private Spinner acMode;
    private Spinner acHor;
    private Spinner acVert;
    private Spinner acSpeed;
    private SeekBar acTemp;
    private Switch acSwitch;
    private TextView cancel;
    private TextView accept;


    private Integer temp;
    private String title;
    private String[] acModeArray;
    private String[] acSpeedArray;
    private String[] acVertArray;
    private String[] acHorArray;
    private Double min,max,current;
    private Integer currentMode, currentVert, currentHor, currentSpeed;
    private boolean on;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        title = "AC";
        min = 18.0;
        max = 38.0;
        current = 20.0;
        on = true;

        acModeArray = getResources().getStringArray(R.array.ac_mode);
        acSpeedArray = getResources().getStringArray(R.array.ac_speed);
        acVertArray = getResources().getStringArray(R.array.ac_vertical_angle);
        acHorArray = getResources().getStringArray(R.array.ac_horizontal_angle);

        currentHor = 1;
        currentMode = 2;
        currentSpeed = 0;
        currentVert = 1;




        View view = inflater.inflate(R.layout.dialog_ac, container, false);

        acTitle = (TextView) view.findViewById(R.id.ac_title);
        acTitle.setText(title);

        acSpeedText = (TextView) view.findViewById(R.id.ac_speed_text);
        acSpeedText.setText(getText(R.string.ac_speed_text));

        acVertText = (TextView) view.findViewById(R.id.ac_vertical_text);
        acVertText.setText(getText(R.string.ac_vertical_angle_text));

        acModeText = (TextView) view.findViewById(R.id.ac_mode_text);
        acModeText.setText(getText(R.string.mode_text));

        acHorText = (TextView) view.findViewById(R.id.ac_horizontal_text);
        acHorText.setText(getText(R.string.ac_horizontal_angle_text));

        acTempText = (TextView) view.findViewById(R.id.temp_text);
        acTempText.setText(getText(R.string.temp_text));

        acMode = (Spinner) view.findViewById(R.id.ac_mode_spinner);
        ArrayAdapter<String> modeAdapter =  new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, acModeArray);
        acMode.setAdapter(modeAdapter);
        acMode.setSelection(currentMode);
        acMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentMode = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        acHor = (Spinner) view.findViewById(R.id.ac_hor_spinner);
        ArrayAdapter<String> horAdapter =  new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, acHorArray);
        acHor.setAdapter(horAdapter);
        acHor.setSelection(currentHor);
        acHor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentHor = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        acVert = (Spinner) view.findViewById(R.id.ac_vertical_spinner);
        ArrayAdapter<String> verAdapter =  new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, acVertArray);
        acVert.setAdapter(verAdapter);
        acVert.setSelection(currentVert);
        acVert.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentVert = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        acSpeed = (Spinner) view.findViewById(R.id.ac_speed_spinner);
        ArrayAdapter<String> speedAdapter =  new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, acSpeedArray);
        acSpeed.setAdapter(speedAdapter);
        acSpeed.setSelection(currentSpeed);
        acSpeed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentSpeed = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        acTempValue = (TextView) view.findViewById(R.id.ac_temp_value);
        acTempValue.setText(current.toString() + "°C");


        acTemp = (SeekBar) view.findViewById(R.id.ac_temp_seekbar);

        acTemp.setProgress((int) ((current - min) * 100 /(max -min)));

        acTemp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                current = min + (progress*(max - min) / 100);
                acTempValue.setText(current + "°C");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        acSwitch = (Switch) view.findViewById(R.id.ac_switch);
        acSwitch.setChecked(on);

        acSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                on = isChecked;
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
                    api_turnOn("29534b8fad4d9084");
                }
                else {
                    api_turnOff("29534b8fad4d9084");
                }

                api_changeSpeed("29534b8fad4d9084" , acSpeedArray[currentSpeed]);
                api_changeVertical("29534b8fad4d9084", acVertArray[currentVert]);
                api_changeHorizontal("29534b8fad4d9084" , acHorArray[currentHor]);
                api_setMode("29534b8fad4d9084" , acModeArray[currentMode]);
                api_setTemperature("29534b8fad4d9084" ,current);

                getDialog().dismiss();
            }
        });







        return view;
    }




    private void api_changeSpeed(String devId, String speed){
        Params[] args = new Params[1];
        args[0] = new Params(speed, null,null,null);

        Api.getInstance(getActivity()).setAction(devId, "setFanSpeed", args, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void api_changeVertical(String devId, String vert){
        Params[] args = new Params[1];
        args[0] = new Params(vert, null,null,null);

        Api.getInstance(getActivity()).setAction(devId, "setVerticalSwing", args, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
    private void api_changeHorizontal(String devId, String hor){
        Params[] args = new Params[1];
        args[0] = new Params(hor, null,null,null);

        Api.getInstance(getActivity()).setAction(devId, "setHorizontalSwing", args, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void api_setMode(String devId, String mode){
        Params[] args = new Params[1];
        args[0] = new Params(mode, null,null,null);

        Api.getInstance(getActivity()).setAction(devId, "setMode", args, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void api_turnOff(String devId){
        Api.getInstance(getActivity()).setAction(devId, "turnOff",null, new Response.Listener<Object>() {
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

    private void api_turnOn(String devId){
        Api.getInstance(getActivity()).setAction(devId, "turnOn",null, new Response.Listener<Object>() {
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

    private void api_setTemperature(String devId, Double temp){
        Params[] args = new Params[1];
        args[0] = new Params(null, null,null,temp);

        Api.getInstance(getActivity()).setAction(devId, "setTemperature", args, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
