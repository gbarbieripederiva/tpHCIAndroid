package ar.edu.itba.barsahome.ui.addDevice;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Api;
import ar.edu.itba.barsahome.api.DeviceType;

public class AddDeviceAdapter extends RecyclerView.Adapter<AddDeviceAdapter.ViewHolder> {

    private DeviceType[] deviceTypes;
    private int positionSelected;


    public AddDeviceAdapter(DeviceType[] deviceTypes) {
        this.deviceTypes = deviceTypes;
        this.positionSelected=-1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_device,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //TODO:resolver traduccion
        Integer drawable=
                Api.getInstance(holder.button.getContext()).getDeviceTypeIcon(this.deviceTypes[position].getName());
        holder.button.setImageDrawable(holder.button.getContext().getDrawable(drawable));

        holder.textview.setText(this.deviceTypes[position].getName());
        if(position==this.positionSelected){
            holder.button.setBackgroundColor(holder.button.getContext().getColor(R.color.buttonSelected));
        }else{
            holder.button.setBackgroundColor(holder.button.getContext().getColor(R.color.buttonUnselected));
        }
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionSelected=position;
                notifyDataSetChanged();
            }
        });
    }

    public DeviceType getDeviceType(){
        return positionSelected>=0?deviceTypes[positionSelected]:null;
    }

    @Override
    public int getItemCount() {
        return this.deviceTypes.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textview;
        public ImageButton button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.button=(ImageButton)itemView.findViewById(R.id.recyclerview_item_device_button);
            this.textview=(TextView)itemView.findViewById(R.id.recyclerview_item_device_text);
        }
    }
}
