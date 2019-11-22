package ar.edu.itba.barsahome.ui.Routines;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Api;
import ar.edu.itba.barsahome.api.Room;
import ar.edu.itba.barsahome.api.Routine;
import ar.edu.itba.barsahome.ui.rooms.RoomsAdapter;

public class RoutinesFragment extends Fragment {
    private Routine[] routines;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.routines = new Routine[0];
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate view
        View view = inflater.inflate(R.layout.fragment_routines, container, false);
        //obtiene los elementos
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_routines_recyclerview);
        final TextView textView = (TextView) view.findViewById(R.id.fragment_routines_empty);
        //setea el texto a buscando cuartos
        textView.setText(getString(R.string.getting_routines_from_api));

        /*TODO: ver que hacemos cuando esto este en una tablet y en celulares de distinito tamaño
         * esta es una solucion temporaria
         * */
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        }else{
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        //add the adapter
        final RoutinesAdapter adapter = new RoutinesAdapter(this.getContext(), this.routines);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //si no hay rooms muestra obteniendo sino previzualiza los que ya hay
        recyclerView.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        //busca los rooms
        Api.getInstance(getActivity()).getRoutines(
                new Response.Listener<ArrayList<Routine>>() {
                    @Override
                    public void onResponse(ArrayList<Routine> response) {
                        routines =response.toArray(new Routine[response.size()]);
                        adapter.changeDataSet(routines);
                        recyclerView.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO: usar un string en vez de este literal
                        /*textView.setText("Hubo un error");
                        textView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);*/
                        Log.e("RoutinesFragment Api call",error.toString());
                    }
                }
        );
        return view;
    }


}
