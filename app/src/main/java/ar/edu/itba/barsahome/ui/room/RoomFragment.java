package ar.edu.itba.barsahome.ui.room;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import ar.edu.itba.barsahome.MainActivity;
import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Api;
import ar.edu.itba.barsahome.api.Device;

public class RoomFragment extends Fragment {

    private String roomId;
    private Device[] devices;
    private String roomName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.roomId=RoomFragmentArgs.fromBundle(getArguments()).getRoomId();
        this.roomName=RoomFragmentArgs.fromBundle(getArguments()).getRoomName();
        this.devices=new Device[0];
        ((MainActivity)getActivity()).setActionBarTitle(this.roomName);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_room,container,false);
        this.roomId=RoomFragmentArgs.fromBundle(getArguments()).getRoomId();
        this.roomName=RoomFragmentArgs.fromBundle(getArguments()).getRoomName();

        RecyclerView recyclerView=view.findViewById(R.id.fragment_room_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        final RoomAdapter adapter = new RoomAdapter(this.devices,getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Api.getInstance(getContext()).getDevicesInRoom(this.roomId,new Response.Listener<ArrayList<Device>>() {
            @Override
            public void onResponse(ArrayList<Device> response) {
                devices=response.toArray(new Device[response.size()]);
                adapter.changeDataSet(devices);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RoomFragment Api call",error.toString());
            }
        });
        return view;
    }
}
