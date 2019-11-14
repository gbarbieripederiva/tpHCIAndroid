package ar.edu.itba.barsahome.ui.addDevice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.itba.barsahome.MainActivity;
import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Api;

public class AddDeviceFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_add_device,container,false);
        ((MainActivity)getContext()).setActionBarTitle(AddDeviceFragmentArgs.fromBundle(getArguments()).getRoomName());
        EditText editText=view.findViewById(R.id.fragment_add_device_edittext);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_add_device_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        final AddDeviceAdapter adapter = new AddDeviceAdapter(Api.getInstance(getContext()).getTypes());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }
}
