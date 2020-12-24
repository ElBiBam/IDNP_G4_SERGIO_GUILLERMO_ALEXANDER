package com.project.runexperience.viewmodel;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.runexperience.R;
import com.project.runexperience.model.clases.Exercise;
import com.project.runexperience.model.sensor.Haversine;
import com.project.runexperience.model.clases.LastLocation;
import com.project.runexperience.model.sensor.LocationBroadcastReceiver;
import com.project.runexperience.viewmodel.ui.main.SectionsPagerAdapter;


import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    public static MainActivity ins;
    private static final String TAG = "MainActivity";


    //Creamos el locationbradcast
    private LocationBroadcastReceiver broadcastReceiver;



    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private LastLocation lastLocation = new LastLocation();
    private float first_location, last_location;
    private float distanceTraveled = 0;

    private static final float minDistanceToCount = 0.1f;


    private static final int minDistanceToSave = 1;
    public String accion = null;
    private String exerciseRandomKey;
    private String rutaRandomKey;
    private DatabaseReference ExcerciseRef;
    private Exercise exercise;
    public boolean isrunning = false;
    private FirebaseAuth mAuth;
    private Chronometer chronometer = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        ins = this;
        setContentView(R.layout.activity_main);




        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);

        ExcerciseRef = FirebaseDatabase.getInstance().getReference();


        tabs.setupWithViewPager(viewPager);
        //stopSensor();
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                if (isrunning) {
                    stopSensor();
                }
                finish();
            }
        });



    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
//        stopSensor();
        super.onDestroy();


    }

    public void initSensor() {
        long currentTimeCreation = System.currentTimeMillis();
        exerciseRandomKey = "" + currentTimeCreation;
        lastLocation = new LastLocation();
        distanceTraveled = 0;

        //veificar permisos
        checkLocationPermission();
        broadcastReceiver = new LocationBroadcastReceiver();
        if (broadcastReceiver != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(LocationManager.KEY_LOCATION_CHANGED);


            // Registramos el bradcast  para iniciar el GPS
            registerReceiver(broadcastReceiver, intentFilter);

        } else {
            Log.d(TAG, "broadcastReceiver is null");
        }
        Log.d(TAG, "Iniciado");


    }

    public void stopSensor() {

        //quitamos el registro del bradcast
        unregisterReceiver(broadcastReceiver);
        accion = null;
        updateExerciseInformation(exercise);
        Log.d(TAG, "finalizado");

    }

    //pedir permisoss
    public boolean checkLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);

        return true;

    }


    //verificar permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {



                        initGPS();

                    }

                } else {

                    Log.d(TAG, "Location not allowed");
                }
                return;
            }

        }
    }

    public void initGPS() {

        Intent intent = new Intent(this, LocationBroadcastReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(//sendBroadcast(...)
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, pendingIntent);
        //actualizarlocation(LocationManager.KEY_LOCATION_CHANGED.);


    }
    //{////////////
    public static MainActivity getInstance() {
        return ins;
    }

    public void setAction(final String action) {
        accion = action;
    }

    public void updateLocation(final float longit, final float latit, final String direccion) {


        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {

                String typeExercise = null;


                TextView recorrido = null;
                //inicializamos cronometro
                chronometer = (Chronometer) findViewById(R.id.timeWalk);
                recorrido = (TextView) findViewById(R.id.metersWalk);


                if (lastLocation.isfirst) {
                    lastLocation.pushValues(latit, longit);
                    exercise = new Exercise(exerciseRandomKey, direccion, direccion, latit, longit, latit, longit, "0", "o", accion, mAuth.getCurrentUser().getEmail().toString(), "0");
                    storageExerciseInformation(exercise);

                }

                float lastLat = lastLocation.getLat();
                float lastLon = lastLocation.getLon();

                float distance = (float) Haversine.distance(latit, longit, lastLat, lastLon);

                float realDistance = (float) Haversine.distance(latit, longit, exercise.getInitLat(), exercise.getInitLon());


                if (distance >= minDistanceToCount) {
                    distanceTraveled += distance;
                }


                exercise.setFinalLat(latit);
                exercise.setFinalLon(longit);
                exercise.setLastLocation(direccion);
                exercise.setTime(chronometer.getText().toString());
                exercise.setDistanceTraveled("" + distanceTraveled);
                exercise.setRealdistance("" + realDistance);


                recorrido.setText("" + distanceTraveled);
                updateExerciseInformation( exercise);

            }
        });
    }

    private void storageExerciseInformation(Exercise exercise) {
        HashMap<String, Object> exerciseMap = new HashMap<>();
        exerciseMap.put("pid", exerciseRandomKey);
        exerciseMap.put("firstLocation", exercise.getFirstLocation());
        exerciseMap.put("lastLocation", exercise.getLastLocation());
        exerciseMap.put("latitudInit", exercise.getInitLat());
        exerciseMap.put("longitudInit", exercise.getInitLon());
        exerciseMap.put("latitudFinal", exercise.getFinalLat());
        exerciseMap.put("longitudFinal", exercise.getFinalLon());
        exerciseMap.put("distanceTraveled", exercise.getDistanceTraveled());
        exerciseMap.put("realdistance", exercise.getRealdistance());
        exerciseMap.put("typeExercise", exercise.getTypeExercise());
        exerciseMap.put("email", exercise.getEmail());
        exerciseMap.put("time", exercise.getTime());

        ExcerciseRef.child("exercise").child(exerciseRandomKey).updateChildren(exerciseMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Guardando en firebase");
                } else {
                    String message = task.getException().toString();
                    Toast.makeText(MainActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();

                }
            }
        });


        HashMap<String, Object> rutaMap = new HashMap<>();
        rutaMap.put("pid", exerciseRandomKey);
        rutaMap.put("latitud", exercise.getFinalLat());
        rutaMap.put("longitud", exercise.getFinalLon());

        long currentTimeCreation = System.currentTimeMillis();
        rutaRandomKey = "" + currentTimeCreation;

        ExcerciseRef.child("ruta").child(rutaRandomKey).updateChildren(rutaMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "Guardando ruta");
                }else {
                    String message = task.getException().toString();
                    Toast.makeText(MainActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateExerciseInformation(Exercise exercise) {
        HashMap<String, Object> exerciseMap = new HashMap<>();
        exerciseMap.put("pid", exerciseRandomKey);
        exerciseMap.put("firstLocation", exercise.getFirstLocation());
        exerciseMap.put("lastLocation", exercise.getLastLocation());
        exerciseMap.put("latitudInit", exercise.getInitLat());
        exerciseMap.put("longitudInit", exercise.getInitLon());
        exerciseMap.put("latitudFinal", exercise.getFinalLat());
        exerciseMap.put("longitudFinal", exercise.getFinalLon());
        exerciseMap.put("distanceTraveled", exercise.getDistanceTraveled());
        exerciseMap.put("realdistance", exercise.getRealdistance());
        exerciseMap.put("typeExercise", exercise.getTypeExercise());
        exerciseMap.put("time", chronometer.getText().toString());

        ExcerciseRef.child("exercise").child(exerciseRandomKey).updateChildren(exerciseMap);

        HashMap<String, Object> rutaMap = new HashMap<>();


        rutaMap.put("pid", exerciseRandomKey);
        rutaMap.put("latitud", exercise.getFinalLat());
        rutaMap.put("longitud", exercise.getFinalLon());

        long currentTimeCreation = System.currentTimeMillis();
        rutaRandomKey = "" + currentTimeCreation;
        ExcerciseRef.child("ruta").child(rutaRandomKey).updateChildren(rutaMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "Guardando ruta");
                }else {
                    String message = task.getException().toString();
                    Toast.makeText(MainActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}