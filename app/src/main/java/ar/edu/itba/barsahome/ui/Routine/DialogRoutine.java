package ar.edu.itba.barsahome.ui.Routine;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Routine;

public class DialogRoutine extends DialogFragment {
    private Routine routine;
    private TextView title;
    private Button cancelButton;
    private Button acceptButton;
    private RecyclerView recyclerView;
    private Context context;

    private Routine.Actions[] actions;
    private ArrayList<String> params;


    public DialogRoutine (Routine routine, Context context){
        this.routine = routine;
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_routine, container, false);

        title = (TextView) view.findViewById(R.id.routine_title);
        title.setText(routine.getName());




        recyclerView = (RecyclerView) view.findViewById(R.id.routine_recycler_view);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 15));
        }else{
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        }


        final RoutineRecyclerViewAdapter adapter = new RoutineRecyclerViewAdapter(routine.getActions(), context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        //adapter.changeDataSet(routine.getActions());
        recyclerView.setVisibility(View.VISIBLE);





        return view;
    }
}
