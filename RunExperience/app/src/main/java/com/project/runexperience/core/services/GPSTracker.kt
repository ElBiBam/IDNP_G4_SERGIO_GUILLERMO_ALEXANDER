package com.project.runexperience.core.services

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.IBinder
import android.util.Log
import androidx.core.content.ContextCompat
import com.project.runexperience.utils.LocationBroadcastReceiver


class GPSTracker() : Service(),
    LocationListener {
    var context: Context? = null
    // flag for GPS status
    var isGPSEnabled = false

    // flag for network status
    var isNetworkEnabled = false

    // flag for GPS status
    var canGetLocation = false
    var location: Location? = null
    var latitude = 0.0f
    var longitude = 0.0f

    override fun onCreate() {
        super.onCreate()
        startMyService()
    }

    @SuppressLint("MissingPermission")
    fun startMyService(){
        //val intent = Intent(this, LocationBroadcastReceiver::class.java)
        //intent.setAction(LocationManager.KEY_LOCATION_CHANGED);
        //Intent intent = new Intent(LocationManager.KEY_LOCATION_CHANGED);

        //intent.setAction(LocationManager.KEY_LOCATION_CHANGED);
        //Intent intent = new Intent(LocationManager.KEY_LOCATION_CHANGED);
        val intent = Intent(LocationManager.KEY_LOCATION_CHANGED)

        Log.d("gps", "start my service")

        val pendingIntent = PendingIntent.getBroadcast( //sendBroadcast(...)
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
            0,
            0f,
            pendingIntent)


    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onLocationChanged(location: Location) {
        TODO("Not yet implemented")
    }

}