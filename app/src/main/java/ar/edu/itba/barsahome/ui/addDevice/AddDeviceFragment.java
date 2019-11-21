package ar.edu.itba.barsahome.ui.addDevice;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.snackbar.Snackbar;

import ar.edu.itba.barsahome.MainActivity;
import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.api.Api;
import ar.edu.itba.barsahome.api.Device;
import ar.edu.itba.barsahome.api.DeviceType;

public class AddDeviceFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_add_device,container,false);

        ((MainActivity)getContext()).setActionBarTitle(AddDeviceFragmentArgs.fromBundle(getArguments()).getRoomName());

        final EditText editText=view.findViewById(R.id.fragment_add_device_edittext);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_add_device_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        final AddDeviceAdapter adapter = new AddDeviceAdapter(Api.getInstance(getContext()).getTypes());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Button acceptButton=view.findViewById(R.id.fragment_add_device_button);

        final String roomId=AddDeviceFragmentArgs.fromBundle(getArguments()).getRoomId();
        //TODO:maybe cambiar el checkeo de aca por filters en el editable de edittext
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //primero checkeo que el nombre sea como debe ser
                //despues que haya un dispositivo seleccionado
                //si tod o es correcto entonces agrego el dispositivo
                String name=editText.getText().toString();
                DeviceType deviceType=adapter.getDeviceType();
                if(
                        name.matches("[0-9A-Za-z _][0-9A-Za-z _][0-9A-Za-z _][0-9A-Za-z _]*")
                        && name.length()<60
                ){
                    //checkeo que haya algo seleccionado
                    if(deviceType!=null){
                        //agregar a la api
                        //TODO
                        Api.getInstance(v.getContext())
                                .addDevice(
                                        new Device(name, new DeviceType(deviceType.getId()), null),
                                        new Response.Listener<Device>() {
                                            @Override
                                            public void onResponse(Device response) {
                                                String deviceId=response.getId();
                                                Api.getInstance(view.getContext())
                                                        .addDeviceToRoom(roomId, deviceId, new Response.Listener<Boolean>() {
                                                            @Override
                                                            public void onResponse(Boolean response) {
                                                                if(response){
                                                                    Snackbar.make(view,R.string.success_creating_device,Snackbar.LENGTH_SHORT);
                                                                    Navigation.findNavController(view).navigateUp();
                                                                }else{
                                                                    Snackbar.make(view,R.string.error_creating_device,Snackbar.LENGTH_LONG);
                                                                    Navigation.findNavController(view).navigateUp();
                                                                }
                                                            }
                                                        }, new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {
                                                                Snackbar.make(view,R.string.error_creating_device,Snackbar.LENGTH_LONG);
                                                                Navigation.findNavController(view).navigateUp();
                                                            }
                                                        });
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Snackbar.make(view,R.string.error_creating_device,Snackbar.LENGTH_LONG).show();
                                                Navigation.findNavController(view).navigateUp();
                                            }
                                        }
                                );
                    }else{
                        //handle no hay nada seleccionado
                        Snackbar.make(view,R.string.no_device_to_add_selected,Snackbar.LENGTH_LONG).show();
                    }
                }else{
                    //handle el nombre no es correcto
                    Snackbar.make(view,R.string.wrong_name_format,Snackbar.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }
}
