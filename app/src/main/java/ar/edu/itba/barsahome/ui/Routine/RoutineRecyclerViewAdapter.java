package ar.edu.itba.barsahome.ui.Routine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Routine;

public class RoutineRecyclerViewAdapter extends RecyclerView.Adapter<RoutineRecyclerViewAdapter.ViewHolder> {

    Routine.Actions[] actions;
    Context context;

    public RoutineRecyclerViewAdapter(Routine.Actions[] actions, Context context) {
        this.actions = actions;
        this.context = context;
    }

    public void changeDataSet(Routine.Actions[] actions){
        this.actions = actions;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycled_view_item_routine, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.action.setText(actions[position].getActionName());
        if(actions[position].getParams() == "[]"){
        holder.params.setText("params : " + actions[position].getParams().toString());}
        else{
            holder.params.setText("params : null");
        }
    }

    @Override
    public int getItemCount() {
        return actions.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView action;
        TextView params;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            action = (TextView) itemView.findViewById(R.id.action_name);
            params = (TextView) itemView.findViewById(R.id.params);

        }
    }

}
