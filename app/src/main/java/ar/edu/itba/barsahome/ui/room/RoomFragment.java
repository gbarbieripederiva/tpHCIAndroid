package ar.edu.itba.barsahome.ui.room;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.snackbar.Snackbar;

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

        setHasOptionsMenu(true);

        RecyclerView recyclerView=view.findViewById(R.id.fragment_room_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        final RoomAdapter adapter = new RoomAdapter(this.devices,this.roomName,this.roomId);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.room_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.action_delete){
           Api.getInstance(getContext())
                   .deleteRoom(this.roomId, new Response.Listener<Boolean>() {
                       @Override
                       public void onResponse(Boolean response) {
                           Navigation.findNavController(getView()).navigateUp();
                       }
                   }, new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {
                           Snackbar.make(getView(),R.string.error_deleting_room,Snackbar.LENGTH_LONG).show();
                       }
                   });
        }
        return true;
    }
}
