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
import java.util.Arrays;

import ar.edu.itba.barsahome.BarsaApp;
import ar.edu.itba.barsahome.MainActivity;
import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Api;
import ar.edu.itba.barsahome.api.Device;
import ar.edu.itba.barsahome.api.Routine;

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
    private Double min = 18.0, max = 38.0, current;
    private Integer currentMode, currentVert, currentHor, currentSpeed;
    private boolean on;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {




        title = getArguments().getString("deviceName");
        min = 18.0;
        max = 38.0;
        current = 20.0;
        on = true;

        acModeArray = getResources().getStringArray(R.array.ac_mode);
        acSpeedArray = getResources().getStringArray(R.array.ac_speed);
        acVertArray = getResources().getStringArray(R.array.ac_vertical_angle);
        acHorArray = getResources().getStringArray(R.array.ac_horizontal_angle);


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
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, acModeArray);
        acMode.setAdapter(modeAdapter);
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
        ArrayAdapter<String> horAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, acHorArray);
        acHor.setAdapter(horAdapter);
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
        ArrayAdapter<String> verAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, acVertArray);
        acVert.setAdapter(verAdapter);
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
        ArrayAdapter<String> speedAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, acSpeedArray);
        acSpeed.setAdapter(speedAdapter);
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


        acTemp = (SeekBar) view.findViewById(R.id.ac_temp_seekbar);

        acTemp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                current = min + (progress * (max - min) / 100);
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
                if (on) {
                    api_turnOn(getArguments().getString("deviceId"));
                } else {
                    api_turnOff(getArguments().getString("deviceId"));
                }
                api_setTemperature(getArguments().getString("deviceId"), current);
                api_changeSpeed(getArguments().getString("deviceId"), acSpeedArray[currentSpeed].replace("%", ""));
                api_changeVertical(getArguments().getString("deviceId"), acVertArray[currentVert].replace("°", ""));
                api_changeHorizontal(getArguments().getString("deviceId"), acHorArray[currentHor].replace("°", ""));
                api_setMode(getArguments().getString("deviceId"), acModeArray[currentMode]);

                local_refresh();
                getDialog().dismiss();
            }
        });


        //fetch_refresh();

        fetching();

        return view;
    }


    private void fetching() {
        Api.getInstance(getActivity()).getDeviceState(getArguments().getString("deviceId"), new Response.Listener<Device>() {
            @Override
            public void onResponse(Device response) {
                current = response.getTemperature();
                String aux = response.getMode();

                switch (aux.toLowerCase()) {

                    case "cool":
                    case "frío":
                        currentMode = 0;
                        break;
                    case "heat":
                    case "calor":
                        currentMode = 1;
                        break;
                    case "fan":
                    case "ventilación":
                        currentMode = 2;
                        break;
                    default:
                        currentMode = 0;
                }
                switch (response.getFanSpeed().toLowerCase()) {
                    case "auto":
                        currentSpeed = 0;
                        break;
                    case "25":
                        currentSpeed = 1;
                        break;
                    case "50":
                        currentSpeed = 2;
                        break;
                    case "75":
                        currentSpeed = 3;
                        break;
                    case "100":
                        currentSpeed = 4;
                        break;
                }


                switch (response.getHorizontalSwing().toLowerCase()) {
                    case "auto":
                        currentHor = 0;
                        break;
                    case "-90":
                        currentHor = 1;
                        break;
                    case "-45":
                        currentHor = 2;
                        break;
                    case "0":
                        currentHor = 3;
                        break;
                    case "45":
                        currentHor = 4;
                        break;
                    case "90":
                        currentHor = 5;
                        break;
                }

                switch (response.getVerticalSwing().toLowerCase()) {
                    case "auto":
                        currentVert = 0;
                        break;
                    case "22":
                        currentVert = 1;
                        break;
                    case "45":
                        currentVert = 2;
                        break;
                    case "67":
                        currentVert = 3;
                        break;
                    case "90":
                        currentVert = 4;
                        break;
                }


                switch (response.getStatus()) {
                    case "on":
                        on = true;
                        break;
                    case "off":
                        on = false;
                        break;
                }



                acTemp.setProgress((int) ((current - min) * 100 / (max - min)));
                acTempValue.setText(current.toString() + "°C");
                acMode.setSelection(currentMode);
                acSpeed.setSelection(currentSpeed);
                acSwitch.setChecked(on);
                acVert.setSelection(currentVert);
                acHor.setSelection(currentHor);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }


    private void api_changeSpeed(String devId, String speed) {
        String[] args = new String[1];
        args[0] = speed;

        Api.getInstance(getActivity()).setActionString(devId, "setFanSpeed", args, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void api_changeVertical(String devId, String vert) {
        String[] args = new String[1];
        args[0] = vert;

        Api.getInstance(getActivity()).setActionString(devId, "setVerticalSwing", args, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void api_changeHorizontal(String devId, String hor) {
        String[] args = new String[1];
        args[0] = hor;
        Api.getInstance(getActivity()).setActionString(devId, "setHorizontalSwing", args, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void api_setMode(String devId, String mode) {
        String[] args = new String[1];
        args[0] = mode;

        Api.getInstance(getActivity()).setActionString(devId, "setMode", args, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void api_turnOff(String devId) {
        Api.getInstance(getActivity()).setAction(devId, "turnOff", null, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

    }

    private void api_turnOn(String devId) {
        Api.getInstance(getActivity()).setAction(devId, "turnOn", null, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

    }

    private void api_setTemperature(String devId, Double temp) {
        Double[] args = new Double[1];
        args[0] = temp;
        Api.getInstance(getActivity()).setActionDoub(devId, "setTemperature", args, new Response.Listener<Object>() {
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




