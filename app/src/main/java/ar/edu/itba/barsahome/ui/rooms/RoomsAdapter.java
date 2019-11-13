package ar.edu.itba.barsahome.ui.rooms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Room;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ViewHolder> {
    private Context context;
    private Room[] rooms;

    public RoomsAdapter(Room[] rooms, Context context) {
        this.rooms=rooms;
        this.context=context;
    }

    public void changeDataSet(Room[] rooms){
        this.rooms=rooms;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_room,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.textview.setText(this.rooms[position].getName());
        holder.button.setImageDrawable(context.getDrawable(R.drawable.ic_home));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoomsFragmentDirections.ActionNavRoomsToNavRoom action =
                        RoomsFragmentDirections.actionNavRoomsToNavRoom(rooms[position].getName(),rooms[position].getId());
                Navigation.findNavController(v).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.rooms.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textview;
        public ImageButton button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.button=(ImageButton)itemView.findViewById(R.id.recyclerview_item_room_button);
            this.textview=(TextView)itemView.findViewById(R.id.recyclerview_item_room_text);
        }
    }
}
