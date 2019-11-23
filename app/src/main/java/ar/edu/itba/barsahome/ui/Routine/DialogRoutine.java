package ar.edu.itba.barsahome.ui.Routine;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Api;
import ar.edu.itba.barsahome.api.Routine;

import static android.content.ContentValues.TAG;

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
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_routine, container, false);

        title = (TextView) view.findViewById(R.id.routine_title);
        title.setText(routine.getName());




        recyclerView = (RecyclerView) view.findViewById(R.id.routine_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final RoutineRecyclerViewAdapter adapter = new RoutineRecyclerViewAdapter(routine.getActions(), context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        //adapter.changeDataSet(routine.getActions());
        recyclerView.setVisibility(View.VISIBLE);

        cancelButton = (Button) view.findViewById(R.id.cancel_routine_button);
        cancelButton.setText(getText(R.string.cancel));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        acceptButton = (Button) view.findViewById(R.id.execute_routine_button);
        acceptButton.setText(getText(R.string.execute));
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                api_execute_routine();

                Toast.makeText(getContext(), getText(R.string.executing), Toast.LENGTH_SHORT).show();

                getDialog().dismiss();
            }
        });






        return view;
    }


    private void api_execute_routine(){
        Api.getInstance(getContext()).execRoutine(routine.getId(), new Response.Listener<Object[]>() {
            @Override
            public void onResponse(Object[] response) {
                System.out.println("hola");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ",error );
            }
        });
    }

}
