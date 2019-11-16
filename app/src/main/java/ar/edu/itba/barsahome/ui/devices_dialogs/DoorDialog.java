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

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ar.edu.itba.barsahome.R;

public class DoorDialog extends DialogFragment {

    private TextView door_title;
    private ImageButton door_lock;
    private boolean locked;
    private boolean openned;
    private Button open_close;
    private Switch door_switch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dialog_door, container, false);
        locked = true; //TODO
        openned = true;
        door_title = (TextView) view.findViewById(R.id.door_title);
        door_title.setText("DOOR");

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
                    door_lock.setBackgroundResource(R.drawable.ic_lockdoor);
                }else{
                    door_lock.setBackgroundResource(R.drawable.ic_unlockdoor);
                }

            }
        });







        return view;
    }
}
