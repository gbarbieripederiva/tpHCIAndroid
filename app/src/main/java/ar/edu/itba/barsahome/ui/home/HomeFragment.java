package ar.edu.itba.barsahome.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import ar.edu.itba.barsahome.MainActivity;
import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Api;
import ar.edu.itba.barsahome.api.Routine;
import ar.edu.itba.barsahome.ui.devices_dialogs.AcDialog;
import ar.edu.itba.barsahome.ui.devices_dialogs.BlindDialog;
import ar.edu.itba.barsahome.ui.devices_dialogs.DoorDialog;
import ar.edu.itba.barsahome.ui.devices_dialogs.FridgeDialog;
import ar.edu.itba.barsahome.ui.devices_dialogs.LampDialog;
import ar.edu.itba.barsahome.ui.devices_dialogs.OvenDialog;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button lamp_but;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dispositivos_favoritos, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        lamp_but = (Button) root.findViewById(R.id.lamp_button);
        lamp_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AcDialog dialog = new AcDialog();
                dialog.show(getFragmentManager(), "Lamp");
            }
        });


        return root;
    }
}