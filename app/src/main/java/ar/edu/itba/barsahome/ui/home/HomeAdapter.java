package ar.edu.itba.barsahome.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Api;
import ar.edu.itba.barsahome.api.Device;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Device[] devices;

    public HomeAdapter(Device[] devices) {
        this.devices = devices;
    }

    public void changeDataSet(Device[] devices) {
        this.devices = devices;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_device, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.textview.setText(this.devices[position].getName());
        Integer drawable = Api.getInstance(holder.button.getContext())
                .getDeviceTypeIcon(this.devices[position].getType().getName());
        if (drawable != null)
            holder.button.setImageDrawable(holder.button.getContext().getDrawable(drawable));
        else
            holder.button.setImageDrawable(holder.button.getContext().getDrawable(R.drawable.ic_menu_camera));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("deviceId", devices[position].getId());
                DialogFragment dialog = Api
                        .getInstance(v.getContext())
                        .getDeviceTypeDialog(devices[position].getType().getName(), v.getContext());
                dialog.setArguments(args);
                dialog.show(((AppCompatActivity) v.getContext()).getSupportFragmentManager(), "deviceDialog");
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.devices.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textview;
        public ImageButton button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.button = (ImageButton) itemView.findViewById(R.id.recyclerview_item_device_button);
            this.textview = (TextView) itemView.findViewById(R.id.recyclerview_item_device_text);
        }
    }
}
