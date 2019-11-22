package ar.edu.itba.barsahome.ui.devices_dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Api;
import ar.edu.itba.barsahome.api.Device;

public class BlindDialog extends DialogFragment {
    private TextView blind_title;
    private ProgressBar blind_progbar;
    private Switch blind_switch;
    private TextView blind_percentage;
    private TextView cancel;
    private TextView accept;



    private String title;
    private Integer percentage;
    private boolean openning;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

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
                getDialog().dismiss();
            }
        });



        accept = (TextView) view.findViewById(R.id.accept);
        accept.setText(getText(R.string.accept));
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), getText(R.string.accept_message), Toast.LENGTH_SHORT).show();

                if(openning){
                    api_open(getArguments().getString("deviceId"));
                }
                else {
                    api_close(getArguments().getString("deviceId"));
                }

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
}