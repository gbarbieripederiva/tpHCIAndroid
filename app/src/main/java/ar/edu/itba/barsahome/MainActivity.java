package ar.edu.itba.barsahome;

import android.os.Bundle;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.view.Menu;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import ar.edu.itba.barsahome.api.Api;
import ar.edu.itba.barsahome.api.Device;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    public static ArrayList<Device> localDevices = new ArrayList<>();

    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Api.getInstance(this).getDevices(new Response.Listener<ArrayList<Device>>() {
            @Override
            public void onResponse(ArrayList<Device> response) {
                localDevices = new ArrayList<>(response.size());
                for(int i = 0; i < response.size(); i++){
                    localDevices.add(response.get(i));

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



        Constraints constraints = new Constraints.Builder().setRequiresBatteryNotLow(true).build();


        PeriodicWorkRequest notifRequest = new PeriodicWorkRequest.Builder(DevWorkManager.class, 15, TimeUnit.MINUTES).setConstraints(constraints).build();

        WorkManager.getInstance().enqueue(notifRequest);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_rooms,R.id.nav_rutinas,R.id.nav_alarma)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu=menu;
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public Menu getMenu(){
        return menu;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }



}
