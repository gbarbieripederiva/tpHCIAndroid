package ar.edu.itba.barsahome.ui.devices_dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ar.edu.itba.barsahome.R;

public class FridgeDialog extends DialogFragment {
    private TextView fridge_title;
    private Double minFridge, maxFridge, currentFridge;
    private Double minFreezer, maxFreezer, currentFreezer;
    private SeekBar fridge_temp;
    private SeekBar freeze_temp;
    private Spinner fridge_options;
    private TextView fridge_temp_value, frezer_temp_value;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        minFridge = 2.0;
        maxFridge = 8.0;
        currentFridge = 6.0; //TODO
        currentFreezer = -14.0;  //TODO
        minFreezer = -20.0;
        maxFreezer = -8.0;

        View view = inflater.inflate(R.layout.dialog_fridge, container, false);
        fridge_title = (TextView) view.findViewById(R.id.fridge_title);
        fridge_title.setText("Fridge1");

        fridge_options = (Spinner) view.findViewById(R.id.fridge_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.fridge_opt, android.R.layout.simple_spinner_item);
        fridge_options.setAdapter(adapter);

        frezer_temp_value = (TextView) view.findViewById(R.id.value_freezer_temp);
        frezer_temp_value.setText(currentFreezer + "°C");
        freeze_temp = (SeekBar) view.findViewById(R.id.freezer_seekbar);
        freeze_temp.setProgress((int) ((currentFreezer - minFreezer) * 100 /(maxFreezer -minFreezer)));
        freeze_temp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentFreezer = minFreezer + (progress*(maxFreezer - minFreezer) / 100);
                frezer_temp_value.setText(currentFreezer + "°C");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        fridge_temp_value =(TextView) view.findViewById(R.id.value_fridge_temp);
        fridge_temp_value.setText(currentFridge.toString() + "°C");
        fridge_temp = (SeekBar) view.findViewById(R.id.fridge_seekbar);
        fridge_temp.setProgress((int)((currentFridge - minFridge) * 100 / (maxFridge - minFridge)));
        fridge_temp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentFridge = minFridge + ((progress*(maxFridge - minFridge))/100);
                fridge_temp_value.setText(currentFridge.toString() + "°C");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });








        return view;
    }
}
