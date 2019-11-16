package ar.edu.itba.barsahome.ui.devices_dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ar.edu.itba.barsahome.R;

public class BlindDialog extends DialogFragment {
    private TextView blind_title;
    private ProgressBar blind_progbar;
    private Switch blind_switch;
    private Integer percentage;
    private TextView blind_percentage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_blinds, container, false);
        blind_title = (TextView) view.findViewById(R.id.blind_text);
        blind_title.setText("BLINDS");

        percentage = 50; //change to api value

        blind_percentage = (TextView) view.findViewById(R.id.blind_percentage);

        blind_percentage.setText("%" + percentage.toString());

        blind_progbar = (ProgressBar) view.findViewById(R.id.blind_prog);
        blind_progbar.setProgress(percentage);




        blind_switch = (Switch) view.findViewById(R.id.blind_switch);


        return view;
    }
}