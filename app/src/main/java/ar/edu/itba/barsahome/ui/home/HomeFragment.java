package ar.edu.itba.barsahome.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Api;
import ar.edu.itba.barsahome.api.Device;

public class HomeFragment extends Fragment {

    private Device[] devices;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.devices=new Device[0];
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_room,container,false);

        final RecyclerView recyclerView=view.findViewById(R.id.fragment_room_recyclerview);
        final TextView textView = view.findViewById(R.id.fragment_room_textview);
        textView.setText(R.string.getting_favourite_devices);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        final HomeAdapter adapter = new HomeAdapter(this.devices);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if(devices.length<1){
            textView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

        Api.getInstance(getContext()).getDevices(new Response.Listener<ArrayList<Device>>() {
            @Override
            public void onResponse(ArrayList<Device> response) {
                List<Device> favs=new ArrayList<>();
                for(Device d : response){
                    if(d.getMeta()!=null
                            &&d.getMeta().getFav()!=null
                            &&d.getMeta().getFav().equals("true")){
                        favs.add(d);
                    }
                }
                devices=favs.toArray(new Device[favs.size()]);
                adapter.changeDataSet(devices);
                if(devices.length<1){
                    textView.setText(R.string.empty_dispositivos_favoritos);
                    textView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }else{
                    textView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
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