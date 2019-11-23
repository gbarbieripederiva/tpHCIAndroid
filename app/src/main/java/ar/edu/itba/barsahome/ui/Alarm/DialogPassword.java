package ar.edu.itba.barsahome.ui.Alarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Api;
import ar.edu.itba.barsahome.ui.Routine.DialogRoutine;

public class DialogPassword extends DialogFragment {
    private String password;
    private Boolean locked;
    private TextView pass_title;
    private EditText pass;
    private Button cancel;
    private Button accept;
    private Spinner alarm_mode_spinner;



    String[] alarm_mode;

    Integer current_alarm_mode;





    public DialogPassword(String password, Boolean locked) {
        this.password = password;
        this.locked = locked;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        alarm_mode = getResources().getStringArray(R.array.alarm_mode);
        current_alarm_mode = 0;

        View view = inflater.inflate(R.layout.dialog_password, container, false);

        pass_title = (TextView) view.findViewById(R.id.unlock_alarm_title);
        pass_title.setText(R.string.unlock_alarm_string);

        pass = (EditText) view.findViewById(R.id.pass_editText);
        pass.setHint(R.string.password);

        alarm_mode_spinner = (Spinner) view.findViewById(R.id.alarm_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, alarm_mode);
        alarm_mode_spinner.setAdapter(adapter);
        alarm_mode_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                current_alarm_mode = position;
            }
        });

        alarm_mode_spinner.setSelection(current_alarm_mode);






        cancel = (Button) view.findViewById(R.id.cancel_btn);
        cancel.setText(getText(R.string.cancel));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        accept = (Button) view.findViewById(R.id.block_btn);
        if(locked){
            accept.setText(getText(R.string.deactivate));
        }
        else {
            accept.setText(getText(R.string.activate));
        }

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(locked == false){
                    api_check_pass_armstay(pass.getText().toString());
                }
                else if(current_alarm_mode == 0){
                    api_check_pass_armAway(pass.getText().toString());
                }
                else {
                    api_check_pass_disarm(pass.getText().toString());
                }

                getDialog().dismiss();

            }

        });




        return view;
    }

    private void api_check_pass_armstay(String string){
        String[] args = new String[1];
        args[0] = string;

        Api.getInstance(getActivity()).setActionString(AlarmFragment.ALARM_ID, "armStay", args, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {
                Boolean success = (Boolean) response;
                if(success){
                    Toast.makeText(getActivity(), getText(R.string.succesful_process), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), getText(R.string.unsuccesful_process), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void api_check_pass_armAway(String string){
        String[] args = new String[1];
        args[0] = string;

        Api.getInstance(getActivity()).setActionString(AlarmFragment.ALARM_ID, "armAway", args, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {
                Boolean success = (Boolean) response;
                if(success){
                    Toast.makeText(getActivity(), getText(R.string.succesful_process), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), getText(R.string.unsuccesful_process), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void api_check_pass_disarm(String string){
        String[] args = new String[1];
        args[0] = string;

        Api.getInstance(getActivity()).setActionString(AlarmFragment.ALARM_ID, "disarm", args, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {
                Boolean success = (Boolean) response;
                if(success){
                    Toast.makeText(getActivity(), getText(R.string.succesful_process), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), getText(R.string.unsuccesful_process), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }



}



