package com.project.runexperience.viewmodel.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.runexperience.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentEstado extends Fragment {
    private TextView name,email,sesiones,KM,sesionesCaminar,KMCaminar,sesionesCorrer,KMCorrer,sesionesCiclismo,KMCiclismo;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private Query ref;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceStatus){
        View view = inflater.inflate(R.layout.fragment1_estado, container, false);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        sesiones = view.findViewById(R.id.sesiones);
        sesionesCaminar = view.findViewById(R.id.sesionesCaminar);
        KMCaminar = view.findViewById(R.id.KMCaminar);
        KM = view.findViewById(R.id.KM);
        sesionesCorrer = view.findViewById(R.id.sesionesCorrer);
        KMCorrer = view.findViewById(R.id.KMCorrer);
        sesionesCiclismo = view.findViewById(R.id.sesionesCiclismo);
        KMCiclismo = view.findViewById(R.id.KMCiclismo);




        mAuth=FirebaseAuth.getInstance();
        mDatabase=  FirebaseDatabase.getInstance().getReference();


        mDatabase.child("users").child(mAuth.getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nombre =snapshot.child("name").getValue().toString();
                    String mail = snapshot.child("email").getValue().toString();

                    name.setText(nombre);
                    email.setText(mail);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    //hisitoricos de ejercicios
        if (!mDatabase.child("exercise").equals(null)) {

            ref = mDatabase.child("exercise").orderByChild("email").equalTo(mAuth.getCurrentUser().getEmail());
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        int cont = 0;
                        int contc =0;
                        int contr = 0;
                        int contb = 0;
                        double TotalKM=0.0;
                        double TotalKMC=0.0;
                        double TotalKMR = 0.0;
                        double TotalKMB = 0.0;

                        for (DataSnapshot ds : snapshot.getChildren()) {
                            //ds.child()
                            String distanceTraveled = ds.child("distanceTraveled").getValue().toString();
                            TotalKM +=Double.parseDouble(distanceTraveled);
                            cont++;

                            if(ds.child("typeExercise").getValue().toString().equals("Caminar")){
                                String distanceCTraveled = ds.child("distanceTraveled").getValue().toString();
                                TotalKMC += Double.parseDouble(distanceCTraveled);
                                contc++;
                            }
                            if(ds.child("typeExercise").getValue().toString().equals("Correr")){
                                String distanceCTraveled = ds.child("distanceTraveled").getValue().toString();
                                TotalKMR += Double.parseDouble(distanceCTraveled);
                                contr++;
                            }
                            if(ds.child("typeExercise").getValue().toString().equals("Ciclismo")){
                                String distanceCTraveled = ds.child("distanceTraveled").getValue().toString();
                                TotalKMB += Double.parseDouble(distanceCTraveled);
                                contb++;
                            }

                        }
                        TotalKM = Math.round(TotalKM*100)/100;
                        TotalKMC = Math.round(TotalKMC*100)/100;
                        TotalKMR = Math.round(TotalKMR*100)/100;
                        TotalKMB = Math.round(TotalKMB*100)/100;
                        sesiones.setText(""+cont);
                        KM.setText((TotalKM/1000)+"");
                        sesionesCaminar.setText(""+contc);
                        KMCaminar.setText((TotalKMC/1000)+"");
                        sesionesCorrer.setText(""+contr);
                        KMCorrer.setText((TotalKMR/1000)+"");
                        sesionesCiclismo.setText(""+contb);
                        KMCiclismo.setText((TotalKMB/1000)+"");


                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        Log.d("main",""+ref);

        return view;
    }

    public void extractUser(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
