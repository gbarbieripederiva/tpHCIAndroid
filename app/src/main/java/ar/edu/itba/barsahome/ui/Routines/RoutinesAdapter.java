package ar.edu.itba.barsahome.ui.Routines;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Room;
import ar.edu.itba.barsahome.api.Routine;

public class RoutinesAdapter extends RecyclerView.Adapter<RoutinesAdapter.ViewHolder> {

    private Context context;
    private Routine[] routines;

    public RoutinesAdapter(Context context, Routine[] routines){
        this.context = context;
        this.routines = routines;
    }

    public void changeDataSet(Routine[] routines){
        this.routines = routines;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_routine, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Routine routine = routines[position];

        holder.routButton.setText(routine.getName());
    }

    @Override
    public int getItemCount() {
        return routines.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public Button routButton;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            routButton = (Button) itemView.findViewById(R.id.routine_button);
        }
    }
}
