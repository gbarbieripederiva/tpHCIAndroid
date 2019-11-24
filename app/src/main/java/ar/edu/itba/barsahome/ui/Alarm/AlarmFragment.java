package ar.edu.itba.barsahome.ui.Alarm;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Api;
import ar.edu.itba.barsahome.api.Device;
import ar.edu.itba.barsahome.api.DeviceMeta;
import ar.edu.itba.barsahome.api.DeviceType;

public class AlarmFragment extends Fragment {

    private TextView title;
    private ImageButton img_button;
    private Button unlock_lock_btn;
    private Button change_pass_btn;

    private Handler fetchHandler = new Handler();

    private String pass;
    private Boolean locked;
    public static String ALARM_ID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Api.getInstance(getContext()).getDevices(new Response.Listener<ArrayList<Device>>() {
            @Override
            public void onResponse(ArrayList<Device> response) {
                for(Device d:response){
                    if(d.getType()!=null&&d.getType().getName().equals("alarm")){
                        ALARM_ID=d.getId();
                    }
                }
                if(ALARM_ID==null){
                    Api.getInstance(getContext()).getDeviceTypes(new Response.Listener<ArrayList<DeviceType>>() {
                        @Override
                        public void onResponse(ArrayList<DeviceType> response) {
                            String alarmTypeId = null;
                            for(DeviceType types:response){
                                if(types.getName().equals("alarm")){
                                    alarmTypeId=types.getId();
                                }
                            }
                            Api
                                    .getInstance(getContext())
                                    .addDevice(new Device(
                                            "alarm",
                                            new DeviceType(alarmTypeId),
                                            new DeviceMeta("1234")
                                    ), new Response.Listener<Device>() {
                                        @Override
                                        public void onResponse(Device response) {
                                            ALARM_ID=response.getId();
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            //TODO:handle error with creating alarm
                                        }
                                    });
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //TODO:handle error with getting types
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO:handle error with getting alarm
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        title = (TextView) view.findViewById(R.id.alarm_title);
        title.setText(getText(R.string.alarm_title));

        img_button = (ImageButton) view.findViewById(R.id.locked_unlocked_alarm);

        unlock_lock_btn = (Button) view.findViewById(R.id.unlock_lock_button);
        unlock_lock_btn.setText(R.string.unlock_alarm_string);
        unlock_lock_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPassword dialogPassword = new DialogPassword(pass, locked);
                dialogPassword.show(getFragmentManager(), "dialog_pass");

            }
        });


        change_pass_btn = (Button) view.findViewById(R.id.change_pass_button);
        change_pass_btn.setText(R.string.chg_pass_title);
        change_pass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogChgPass dialogChgPass = new DialogChgPass();
                dialogChgPass.show(getFragmentManager(), "chg_psw");
            }
        });




        startRepeating(getView());






        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopReapeating(getView());
    }


    private void fetching(){
        Api.getInstance(getActivity()).getDeviceState(ALARM_ID, new Response.Listener<Device>() {
            @Override
            public void onResponse(Device response) {
                switch (response.getStatus()){
                    case "armedAway":
                        locked = true;
                        break;

                    case "armedStay":
                        locked = true;
                        break;
                    case "disarmed":
                        locked = false;
                        break;
                }

                if(locked){
                    img_button.setBackgroundResource(R.drawable.ic_lockdoor);
                }
                else {
                    img_button.setBackgroundResource(R.drawable.ic_unlockdoor);
                }







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



    private Runnable fetchRun = new Runnable() {
        @Override
        public void run() {
            fetching();
            fetchHandler.postDelayed(this, 2000);
        }
    };
}
