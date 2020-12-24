package com.project.runexperience.model.sensor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import com.project.runexperience.viewmodel.MainActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class LocationBroadcastReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 9999 ;
    private String TAG = "LocationBroadcastReceiver";
    float currentLon, currentLat;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        try{
            if (intent.hasExtra(LocationManager.KEY_LOCATION_CHANGED)) {

                String locationKey = LocationManager.KEY_LOCATION_CHANGED;
                Location location = (Location) intent.getExtras().get(locationKey);

                currentLon = (float) location.getLongitude();
                currentLat = (float) location.getLatitude();
                Geocoder geocoder = new Geocoder(context.getApplicationContext(), Locale.getDefault());
                String dir=null;
                try {
                    List<Address> direccion = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                    dir = direccion.get(0).getAddressLine(0);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(context, latitude+","+longitude, Toast.LENGTH_LONG).show();
                MainActivity.getInstance().updateLocation(currentLon, currentLat,dir);
            }

            else if (intent.hasExtra(LocationManager.KEY_PROVIDER_ENABLED)) {
                Log.d(TAG, "KEY_PROVIDER_ENABLED");
                if (intent.getExtras().getBoolean(LocationManager.KEY_PROVIDER_ENABLED)){
                    Log.d(TAG,  "GPS enabled");
                    Toast.makeText(context, "GPS activado",Toast.LENGTH_LONG).show();

                }
                else {
                    Log.d(TAG,  "GPS disabled");
                    Toast.makeText(context, "GPS desactivado",Toast.LENGTH_LONG).show();

                }
            }

            else if (intent.hasExtra(LocationManager.KEY_PROXIMITY_ENTERING)) {
                Log.d(TAG, "KEY_PROXIMITY_ENTERING recived");
                if (intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING,false)){
                    onEnteringProximity(context);
                }
                else {
                    onExitingProximity(context);
                }

            }

            else if (intent.hasExtra(LocationManager.KEY_STATUS_CHANGED)) {
                Log.d(TAG, "KEY_STATUS_CHANGED recived");
            }
        }catch (Exception e){
            Log.d(TAG, "error");
        }

    }

    public void onEnteringProximity(Context context){
        displayNotification(context,"Entering Proximity");
    }


    public void onExitingProximity(Context context){
        displayNotification(context,"Exiting Proximity");
    }

    private void displayNotification(Context context, String message){

    }

}
