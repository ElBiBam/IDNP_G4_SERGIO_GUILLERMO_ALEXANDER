package com.project.runexperience.viewmodel;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.runexperience.R;
import java.util.ArrayList;

public class MapLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    private ArrayList<Marker> tmpRealTimeMarker = new ArrayList<>();
    private ArrayList<Marker>realTimeMarker = new ArrayList<>();



    private LatLng puntoinicio = new LatLng(0,0);
    private LatLng puntofinal = new LatLng(1, 1);
    private String PID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //id de la ruta esleccionada
        PID = getIntent().getStringExtra("pid");
        Log.d("ssss",PID);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
       Antut(mMap);
    }
    public void Antut ( GoogleMap googleMap){
        String pid = PID;
        mMap = googleMap;

        for(Marker marker: realTimeMarker){
            marker.remove();
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //referencia a la base de datos de rutas y filtramos  por PID del ejercicio
        mDatabase.child("ruta").orderByChild("pid").equalTo(pid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(Marker marker: realTimeMarker){
                    marker.remove();
                }

                if (snapshot.exists()) {
                    for (DataSnapshot ds: snapshot.getChildren()){
                        Double latitud = Double.parseDouble(ds.child("latitud").getValue().toString());
                        Double longitud = Double.parseDouble(ds.child("longitud").getValue().toString());
                        MarkerOptions markerOptions = new MarkerOptions();
                        LatLng punto = new LatLng(latitud, longitud);
                        markerOptions.position(punto);
                        tmpRealTimeMarker.add(mMap.addMarker(markerOptions));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(punto));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(punto, 15));
                    }
                    realTimeMarker.clear();
                    realTimeMarker.addAll(tmpRealTimeMarker);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //    }



    }
}