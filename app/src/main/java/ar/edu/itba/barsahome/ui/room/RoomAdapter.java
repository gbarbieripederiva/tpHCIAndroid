package ar.edu.itba.barsahome.ui.room;

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
import ar.edu.itba.barsahome.api.Device;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private Context context;
    private Device[] devices;

    public RoomAdapter(Device[] devices, Context context) {
        this.devices=devices;
        this.context=context;
    }

    public void changeDataSet(Device[] devices){
        this.devices=devices;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_device,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.textview.setText(this.devices[position].getName());
        holder.button.setImageDrawable(context.getDrawable(R.drawable.ic_home));
    }

    @Override
    public int getItemCount() {
        return this.devices.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textview;
        public ImageButton button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.button=(ImageButton)itemView.findViewById(R.id.recyclerview_item_device_button);
            this.textview=(TextView)itemView.findViewById(R.id.recyclerview_item_device_text);
        }
    }
}
