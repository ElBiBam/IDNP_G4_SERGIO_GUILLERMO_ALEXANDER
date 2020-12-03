package com.project.runexperience.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.util.Log


class LocationBroadcastReceiver : BroadcastReceiver() {
    private val TAG = "LocationBroadcastReceiver"

    //private val mainActivityInf: MainActivityInf? = null
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "ID:" + UNIQUE_ID)
        if (intent.hasExtra(LocationManager.KEY_LOCATION_CHANGED)) {
            val locationChanged = LocationManager.KEY_LOCATION_CHANGED
            val location = intent.extras!![locationChanged] as Location?
            val latitude = location!!.latitude
            val longitude = location.longitude
            Log.d(TAG, "$latitude,$longitude")
        }
    }

    companion object {
        var UNIQUE_ID = 0
        var LOCATION_CHANGE = "location_changed"
        var ACTION = "action"
    }
}