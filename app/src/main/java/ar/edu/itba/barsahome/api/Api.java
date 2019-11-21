package ar.edu.itba.barsahome.api;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.util.Pair;

import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import ar.edu.itba.barsahome.BuildConfig;
import ar.edu.itba.barsahome.R;
import ar.edu.itba.barsahome.ui.devices_dialogs.AcDialog;
import ar.edu.itba.barsahome.ui.devices_dialogs.BlindDialog;
import ar.edu.itba.barsahome.ui.devices_dialogs.DoorDialog;
import ar.edu.itba.barsahome.ui.devices_dialogs.ErrorOpeningDeviceDialog;
import ar.edu.itba.barsahome.ui.devices_dialogs.FridgeDialog;
import ar.edu.itba.barsahome.ui.devices_dialogs.LampDialog;
import ar.edu.itba.barsahome.ui.devices_dialogs.OvenDialog;


public class Api {
    private static Api instance;
    private static RequestQueue requestQueue;
    private static Map<String, DeviceType> typeId=new HashMap<>();
    // Use IP 10.0.2.2 instead of 127.0.0.1 when running Android emulator in the
    // same computer that runs the API.
    private final String URL="http://"+BuildConfig.api_ip_port+"/api/";

    private Api(Context context) {
        //TODO:podriamos instanciar los devicetypes sin nada y cuando retorna la api agregarle datos
        this.requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        getDeviceTypes(
                new Response.Listener<ArrayList<DeviceType>>() {
                    @Override
                    public void onResponse(ArrayList<DeviceType> response) {
                        for(DeviceType d:response){
                            switch (d.getName()){
                                case "blinds":
                                    typeId.put(d.getName(),new DeviceType(d.getId(),d.getName(),R.drawable.ic_blinds, BlindDialog.class));
                                    break;
                                case "lamp":
                                    typeId.put(d.getName(),new DeviceType(d.getId(),d.getName(),R.drawable.ic_lamp, LampDialog.class));
                                    break;
                                case "oven":
                                    typeId.put(d.getName(),new DeviceType(d.getId(),d.getName(),R.drawable.ic_oven, OvenDialog.class));
                                    break;
                                case "ac":
                                    typeId.put(d.getName(),new DeviceType(d.getId(),d.getName(),R.drawable.ic_ac, AcDialog.class));
                                    break;
                                case "door":
                                    typeId.put(d.getName(),new DeviceType(d.getId(),d.getName(),R.drawable.ic_door,DoorDialog.class));
                                    break;
                                case "refrigerator":
                                    typeId.put(d.getName(),new DeviceType(d.getId(),d.getName(),R.drawable.ic_refrigerator, FridgeDialog.class));
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO: better error handling in init api
                        Log.e("API Initiation",error.toString());
                    }
                }
        );
    }

    public static synchronized Api getInstance(Context context) {
        if (instance == null) {
            instance = new Api(context);
        }
        return instance;
    }

    public String addRoom(Room room, Response.Listener<Room> listener, Response.ErrorListener errorListener) {
        String url = URL + "rooms";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        GsonRequest<Room, Room> request =
                new GsonRequest<>(Request.Method.POST, url, room, "result", new TypeToken<Room>(){}, headers, listener, errorListener);
        String uuid = UUID.randomUUID().toString();
        request.setTag(uuid);
        requestQueue.add(request);

        return uuid;
    }

    public String modifyRoom(Room room, Response.Listener<Boolean> listener, Response.ErrorListener errorListener) {
        String url = URL + "rooms/" + room.getId();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        GsonRequest<Room, Boolean> request =
                new GsonRequest<>(Request.Method.PUT, url, room, "result", new TypeToken<Boolean>(){}, headers, listener, errorListener);
        String uuid = UUID.randomUUID().toString();
        request.setTag(uuid);
        requestQueue.add(request);

        return uuid;
    }

    public String deleteRoom(String id, Response.Listener<Boolean> listener, Response.ErrorListener errorListener) {
        String url = URL + "rooms/" + id;
        GsonRequest<Object, Boolean> request =
                new GsonRequest<>(Request.Method.DELETE, url, null, "result", new TypeToken<Boolean>(){}, null, listener, errorListener);
        String uuid = UUID.randomUUID().toString();
        request.setTag(uuid);
        requestQueue.add(request);

        return uuid;
    }

    public String getRoom(String id, Response.Listener<Room> listener, Response.ErrorListener errorListener) {
        String url = URL + "rooms/" + id;
        GsonRequest<Object, Room> request =
                new GsonRequest<>(Request.Method.GET, url, null, "result", new TypeToken<Room>(){}, null, listener, errorListener);
        String uuid = UUID.randomUUID().toString();
        request.setTag(uuid);
        requestQueue.add(request);

        return uuid;
    }

    public String getRooms(Response.Listener<ArrayList<Room>> listener, Response.ErrorListener errorListener) {
        String url = URL + "rooms/";
        GsonRequest<Object, ArrayList<Room>> request =
                new GsonRequest<>(Request.Method.GET, url, null, "result", new TypeToken<ArrayList<Room>>(){}, null, listener, errorListener);
        String uuid = UUID.randomUUID().toString();
        request.setTag(uuid);
        requestQueue.add(request);
        return uuid;
    }

    public String getDeviceTypes(Response.Listener<ArrayList<DeviceType>> listener, Response.ErrorListener errorListener) {
        String url = URL + "devicetypes/";
        GsonRequest<Object, ArrayList<DeviceType>> request =
                new GsonRequest<>(Request.Method.GET, url, null, "result", new TypeToken<ArrayList<DeviceType>>(){}, null, listener, errorListener);
        String uuid = UUID.randomUUID().toString();
        request.setTag(uuid);
        requestQueue.add(request);
        return uuid;
    }

    public String getTypeId(String type){
        return typeId.get(type).getId();
    }

    public Set<String> getTypesNames(){;
        return typeId.keySet();
    }

    public DeviceType[] getTypes(){
        Collection<DeviceType> col=typeId.values();
        return col.toArray(new DeviceType[typeId.values().size()]);
    }

    public Integer getDeviceTypeIcon(String type){
        DeviceType deviceType = typeId.get(type);
        return deviceType==null?R.drawable.ic_unknown_device:deviceType.getImg();
    }

    public DialogFragment getDeviceTypeDialog(String type,Context context){
        DeviceType deviceType=typeId.get(type);
        DialogFragment dialog;
        try {
            dialog=(DialogFragment) deviceType.getDialog().newInstance();
        }catch (Exception e){
            dialog=new ErrorOpeningDeviceDialog();
        }
        return dialog;
    }

    public String getDevices(Response.Listener<ArrayList<Device>> listener, Response.ErrorListener errorListener) {
        String url = URL + "devices/";
        GsonRequest<Object, ArrayList<Device>> request =
                new GsonRequest<>(Request.Method.GET, url, null, "devices", new TypeToken<ArrayList<Device>>(){}, null, listener, errorListener);
        String uuid = UUID.randomUUID().toString();
        request.setTag(uuid);
        requestQueue.add(request);
        return uuid;
    }

    public String getDevicesInRoom(String roomId,Response.Listener<ArrayList<Device>> listener, Response.ErrorListener errorListener) {
        String url = URL + "rooms/" + roomId + "/devices";
        GsonRequest<Object, ArrayList<Device>> request =
                new GsonRequest<>(Request.Method.GET, url, null, "result", new TypeToken<ArrayList<Device>>(){}, null, listener, errorListener);
        String uuid = UUID.randomUUID().toString();
        request.setTag(uuid);
        requestQueue.add(request);
        return uuid;
    }

    public String setAction(Device device, String actionName,String[] args, Response.Listener<Object> listener, Response.ErrorListener errorListener){
        String url = URL + "devices/" + device.getId() + actionName;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        GsonRequest<String[], Object> request =
                new GsonRequest<>(Request.Method.PUT, url, args, "result", new TypeToken<Object>(){}, headers, listener,errorListener );
        String uuid = UUID.randomUUID().toString();
        request.setTag(uuid);
        requestQueue.add(request);

        return uuid;
    }



    public void cancelRequest(String uuid) {
        if ((uuid != null) && (requestQueue != null)) {
            requestQueue.cancelAll(uuid);
        }
    }


}
