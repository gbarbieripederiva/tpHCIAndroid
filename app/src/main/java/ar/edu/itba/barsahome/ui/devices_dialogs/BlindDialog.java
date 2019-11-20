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

import ar.edu.itba.barsahome.R;

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

        openning = true;
        title = "BLINDS";
        percentage = 50; //change to api value


        View view = inflater.inflate(R.layout.dialog_blinds, container, false);
        blind_title = (TextView) view.findViewById(R.id.blind_text);
        blind_title.setText(title);



        blind_percentage = (TextView) view.findViewById(R.id.blind_percentage);

        blind_percentage.setText("%" + percentage.toString());

        blind_progbar = (ProgressBar) view.findViewById(R.id.blind_prog);
        blind_progbar.setProgress(percentage);




        blind_switch = (Switch) view.findViewById(R.id.blind_switch);
        blind_switch.setChecked(openning);

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

                getDialog().dismiss();
            }
        });



        return view;
    }
}