package com.idnp.samplelocationservice;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static int UNIQUE_ID = 0;
    private LocationBroadcastReceiver broadcastReceiver = new LocationBroadcastReceiver();
    /*private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "ID:" + UNIQUE_ID);
            if (intent.hasExtra(LocationManager.KEY_LOCATION_CHANGED)) {

                String locationChanged = LocationManager.KEY_LOCATION_CHANGED;
                Location location = (Location) intent.getExtras().get(locationChanged);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Log.d(TAG, latitude + "," + longitude);

            }
        }
    };*/
    private TextView txtLocation;
    private TextView txtProvider;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkLocationPermission();

        Button btnStart = (Button) findViewById(R.id.btnStart);

        txtLocation = (TextView) findViewById(R.id.textViewLocation);
        txtProvider = (TextView) findViewById(R.id.textViewProvider);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (broadcastReceiver != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(LocationManager.KEY_LOCATION_CHANGED);
            registerReceiver(broadcastReceiver, intentFilter);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(broadcastReceiver);
        sendBroadcast(new Intent());
    }

    public boolean checkLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);

        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        initGPS();

                    }

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d(TAG, "Location not allowed");
                }
                return;
            }

        }
    }

    public void initGPS() {

        Intent intent = new Intent(this, LocationBroadcastReceiver.class);
        //intent.setAction(LocationManager.KEY_LOCATION_CHANGED);
        //Intent intent = new Intent(LocationManager.KEY_LOCATION_CHANGED);

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

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0,
                0,
                pendingIntent);

    }

    private MainActivityInf mainActivityInf = new MainActivityInf() {
        @Override
        public void DisplayLocationChange(String location) {
            Log.d(TAG, "Location: " + location);
            txtLocation.setText(location);
        }

    };

}
