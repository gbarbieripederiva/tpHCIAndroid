package ar.edu.itba.barsahome.ui.devices_dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ar.edu.itba.barsahome.R;

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
        current = 130.0;
        on = true;

        currentConMode = 0;
        currentHeatSource = 1;
        currentGrillMode = 2;


        title = "OVEN";

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
        convMode.setSelection(currentConMode);

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
        grillMode.setSelection(currentGrillMode);

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
        heatSource.setSelection(currentHeatSource);

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
        if(on){
            ovenSwitch.setChecked(true);
        }
        else{
            ovenSwitch.setChecked(false);
        }

        ovenSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                on = isChecked;
            }
        });

        tempValue = (TextView) view.findViewById(R.id.temp_value);
        tempValue.setText(current.toString() + "°C");

        temp = (SeekBar) view.findViewById(R.id.oven_temperature_seekbar);

        temp.setProgress((int) ((current - min) * 100 /(max -min)));

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

                getDialog().dismiss();
            }
        });









        return view;
    }
}