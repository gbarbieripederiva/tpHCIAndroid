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

import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Api;
import ar.edu.itba.barsahome.api.Device;

public class AlarmFragment extends Fragment {

    private TextView title;
    private ImageButton img_button;
    private Button unlock_lock_btn;
    private Button change_pass_btn;

    private Handler fetchHandler = new Handler();

    private String pass;
    private Boolean locked;
    public static final String ALARM_ID = "fewqfeqewq32";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        title = (TextView) view.findViewById(R.id.alarm_title);
        title.setText(getText(R.string.alarm_title));

        img_button = (ImageButton) view.findViewById(R.id.locked_unlocked_alarm);

        unlock_lock_btn = (Button) view.findViewById(R.id.unlock_lock_button);
        unlock_lock_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPassword dialogPassword = new DialogPassword(pass, locked);
                dialogPassword.show(getFragmentManager(), "dialog_pass");

            }
        });


        change_pass_btn = (Button) view.findViewById(R.id.change_pass_button);
        change_pass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogChgPass dialogChgPass = new DialogChgPass();
                dialogChgPass.show(getFragmentManager(), "chg_psw");
            }
        });




        startRepeating(getView());






        return super.onCreateView(inflater, container, savedInstanceState);
    }



    private void fetching(){
        Api.getInstance(getActivity()).getDeviceState(ALARM_ID, new Response.Listener<Device>() {
            @Override
            public void onResponse(Device response) {
                switch (response.getStatus()){
                    case "armed":
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
            fetchHandler.postDelayed(this, 5000);
        }
    };
}
