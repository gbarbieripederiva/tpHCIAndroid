package ar.edu.itba.barsahome.ui.rooms;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.lang.reflect.Array;
import java.util.Arrays;

import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Api;
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
        //TODO: resolver que si se tocan dos botones a la vez se rompe todo
        if(position<this.rooms.length){
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
        }else{
            holder.textview.setText("");
            holder.button.setImageDrawable(context.getDrawable(R.drawable.ic_add));
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    //crea un dialog input para poder ingresar el nombre
                    //del nuevo cuarto
                    final EditText input = new EditText(v.getContext());
                    input.setHint(v.getResources().getString(R.string.name));
                    new AlertDialog.Builder(v.getContext())
                            .setTitle(v.getResources().getString(R.string.add_cuarto))
                            .setView(input)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Api.getInstance(v.getContext())
                                            .addRoom(
                                                    new Room(input.getText().toString(), null),
                                                    new Response.Listener<Room>() {
                                                        @Override
                                                        public void onResponse(Room response) {
                                                            rooms=Arrays.copyOf(rooms,rooms.length+1);
                                                            rooms[rooms.length-1]=response;
                                                            changeDataSet(rooms);
                                                        }
                                                    },
                                                    new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            //TODO:handle error
                                                        }
                                                    }
                                            );
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Do nothing.
                        }
                    }).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.rooms.length+1;
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
