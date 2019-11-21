package ar.edu.itba.barsahome.ui.devices_dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Api;

public class DoorDialog extends DialogFragment {

    private TextView door_title;
    private ImageButton door_lock;
    private Button open_close;
    private Switch door_switch;
    private TextView cancel;
    private TextView accept;

    private boolean locked;
    private boolean openned;
    private String title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        title = "DOOR";
        locked = true; //TODO
        openned = true;

        View view = inflater.inflate(R.layout.dialog_door, container, false);

        door_title = (TextView) view.findViewById(R.id.door_title);
        door_title.setText(title);

        door_switch = (Switch) view.findViewById(R.id.door_switch);
        if(openned){
            door_switch.setChecked(true);
        }
        else{
            door_switch.setChecked(false);
        }
        door_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                openned = isChecked;
            }
        });

        door_lock = (ImageButton) view.findViewById(R.id.lock_unlock);
        if(locked){
            if(openned){
                door_switch.setChecked(false);
            }
            door_lock.setBackgroundResource(R.drawable.ic_lockdoor);
        }else{
            door_lock.setBackgroundResource(R.drawable.ic_unlockdoor);
        }

        open_close = (Button) view.findViewById(R.id.open_close);

        open_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locked = !locked;
                if(locked){
                    if(openned){
                        door_switch.setChecked(false);
                    }
                    door_switch.setClickable(false);
                    door_lock.setBackgroundResource(R.drawable.ic_lockdoor);
                }else{
                    door_switch.setClickable(true);
                    door_lock.setBackgroundResource(R.drawable.ic_unlockdoor);
                }

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

                if(openned){
                    api_open(getArguments().getString("deviceId"));
                }
                else{
                    api_close(getArguments().getString("deviceId"));
                }

                if(locked){
                    api_lock(getArguments().getString("deviceId"));
                }
                else {
                    api_unlock(getArguments().getString("deviceId"));
                }

                getDialog().dismiss();
            }
        });








        return view;
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

    private void api_unlock(String devId){
        Api.getInstance(getActivity()).setAction(devId, "unlock", null, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void api_lock(String devId){
        Api.getInstance(getActivity()).setAction(devId, "lock", null, new Response.Listener<Object>() {
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
