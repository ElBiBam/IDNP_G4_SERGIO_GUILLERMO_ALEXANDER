package com.project.runexperience.ui.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.runexperience.R
import com.project.runexperience.core.services.GPSTracker
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RunningFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RunningFragment : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var googleMap: GoogleMap? = null
    lateinit var gps: GPSTracker

    //private val broadcastReceiver = LocationBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("gps", "running on create view")
        var root = inflater.inflate(R.layout.fragment_running, container, false)
        (childFragmentManager!!.findFragmentById(R.id.googleMapFragment) as SupportMapFragment).getMapAsync(this)
        val serviceIntent = Intent(activity?.applicationContext, GPSTracker::class.java)
        activity!!.startService(serviceIntent)
        // Inflate the layout for this fragment
        return root
    }

    override fun onResume() {
        super.onResume()
        if (broadcastReceiver != null) {
            val intentFilter = IntentFilter()
            intentFilter.addAction(LocationManager.KEY_LOCATION_CHANGED)
            activity!!.registerReceiver(broadcastReceiver, intentFilter)
        }
    }
    var broadcastReceiver: BroadcastReceiver = object: BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent!!.hasExtra(LocationManager.KEY_LOCATION_CHANGED)) {
                val locationChanged = LocationManager.KEY_LOCATION_CHANGED
                val location = intent.extras!![locationChanged] as Location?
                val latitude = location!!.latitude
                val longitude = location.longitude

                val sydney = LatLng(latitude, longitude)
                // Write a message to the database
                val database = Firebase.database


                val current = LocalDateTime.now()

                val formatter_fecha = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val formatted_fecha = current.format(formatter_fecha)

                val formatter_hora = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")
                val formatted_hora = current.format(formatter_hora)

                val ref_fecha = database.getReference("fecha")
                ref_fecha.setValue(formatted_fecha.toString())
                val ref_hora = database.getReference("hora")
                ref_hora.setValue(formatted_hora.toString())
                val ref_latitude = database.getReference("latitude")
                ref_latitude.setValue(latitude)
                val ref_longitude = database.getReference("longitude")
                ref_longitude.setValue(longitude)
                val ref_speed = database.getReference("speed")
                ref_speed.setValue(location.speed)

                Log.d("gps", "$formatted_fecha,$formatted_hora,$latitude,$longitude,$location.speed")

                if(googleMap != null) {
                    googleMap!!.addMarker(
                        MarkerOptions().position(sydney)
                            .title("Marker in Sydney")
                    )
                    googleMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                }
            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RunningFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String = null.toString(), param2: String = null.toString()) =
            RunningFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        Log.d("gps", "map on ready")
        googleMap?.let { this.googleMap = it
        Log.d("gps", "init google map")}


    }

}