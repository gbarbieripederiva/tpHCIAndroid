package ar.edu.itba.barsahome.ui.Alarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Api;

public class DialogChgPass extends DialogFragment {
    private TextView title;
    private EditText oldpsw;
    private EditText newpsw;
    private EditText rptnewpsw;
    private Button cancel;
    private Button accept;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_chgpass, container, false);


        title = (TextView) view.findViewById(R.id.chg_pass_title);
        title.setText(getText(R.string.chg_pass_title));

        oldpsw = (EditText) view.findViewById(R.id.old_psw);
        oldpsw.setHint(R.string.old_psw);

        newpsw = (EditText) view.findViewById(R.id.new_psw);
        newpsw.setHint(R.string.new_psw);

        rptnewpsw = (EditText) view.findViewById(R.id.rpt_new_psw);
        rptnewpsw.setHint(R.string.rpt_new_psw);



        cancel = (Button) view.findViewById(R.id.cancel_btn);
        cancel.setText(getText(R.string.cancel));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        accept = (Button) view.findViewById(R.id.acept_btn);
        accept.setText(getText(R.string.accept));
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(newpsw.getText().toString().matches(rptnewpsw.getText().toString()))){
                    Toast.makeText(getContext(), getText(R.string.psw_nomatch), Toast.LENGTH_SHORT).show();
                }
                else {
                    api_chg_pass(oldpsw.getText().toString(), newpsw.getText().toString(), v);
                }


                getDialog().dismiss();

            }
        });





        return view;
    }



    private void api_chg_pass(String oldpsw, String newpsw, final View v){
        String[] pswds = new String[2];
        pswds[0] = oldpsw;
        pswds[1] = newpsw;

        Api.getInstance(getActivity()).setActionStringBool(AlarmFragment.ALARM_ID, "changeSecurityCode", pswds, new Response.Listener<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                Boolean success = (Boolean) response;
                if(success){
                    Toast.makeText(v.getContext(), "Cambio de contraseña exitoso", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(v.getContext(), "No coincide contraseña", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
