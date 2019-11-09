package ar.edu.itba.barsahome.ui.rooms;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Api;
import ar.edu.itba.barsahome.api.Room;

public class RoomsFragment extends Fragment {
    private Room rooms[];

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.rooms=new Room[0];
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate view
        View view = inflater.inflate(R.layout.fragment_rooms, container, false);
        //obtiene los elementos
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_rooms_recyclerview);
        final TextView textView = (TextView) view.findViewById(R.id.fragment_rooms_empty);
        //setea el texto a buscando cuartos
        textView.setText(getString(R.string.getting_rooms_from_api));

        /*TODO: ver que hacemos cuando esto este en una tablet y en celulares de distinito tamaño
        * esta es una solucion temporaria
        * */
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        }else{
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        //add the adapter
        final RoomsAdapter adapter = new RoomsAdapter(this.rooms,this.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //si no hay rooms muestra obteniendo sino previzualiza los que ya hay
        if(this.rooms.length>0){
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }else{
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
        //busca los rooms
        Api.getInstance(getActivity()).getRooms(
                new Response.Listener<ArrayList<Room>>() {
                    @Override
                    public void onResponse(ArrayList<Room> response) {
                        rooms=response.toArray(new Room[response.size()]);
                        if(rooms.length>0){
                            adapter.chageDataSet(rooms);
                            recyclerView.setVisibility(View.VISIBLE);
                            textView.setVisibility(View.GONE);
                        }else{
                            textView.setText(getString(R.string.no_rooms));
                            textView.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO: usar un string en vez de este literal
                        /*textView.setText("Hubo un error");
                        textView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);*/
                    }
                }
        );
        return view;
    }
}
