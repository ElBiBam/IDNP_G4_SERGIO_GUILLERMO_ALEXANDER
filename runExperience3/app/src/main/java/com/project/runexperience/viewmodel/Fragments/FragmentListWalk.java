package com.project.runexperience.viewmodel.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.runexperience.R;
import com.project.runexperience.model.adapters.ExerciseAdapter;
import com.project.runexperience.model.clases.Exercise;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentListWalk extends Fragment {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private ExerciseAdapter eAdapter;

    private RecyclerView recyclerView;
    private ArrayList<Exercise>exerciseList = new ArrayList<>();
    private Query ref;
    private String type;
    public FragmentListWalk(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceStatus){
        View view = inflater.inflate(R.layout.list_fragment_walk, container, false);
        //type = getActivity().getIntent().getStringExtra("action");
        Log.d("aqui",""+type);
        recyclerView = view.findViewById(R.id.exercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAuth = FirebaseAuth.getInstance();

        mDatabase=  FirebaseDatabase.getInstance().getReference();
        if (!mDatabase.equals(null)) {
            //filtrar ejerccicios por email del usuariio actual
            ref = mDatabase.child("exercise").orderByChild("email").equalTo(mAuth.getCurrentUser().getEmail());
            getExercisesFromFire();
        }

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void getExercisesFromFire(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int cont=0;

                    exerciseList.clear();
                    for(DataSnapshot ds : snapshot.getChildren()){
                        //ds.child()
                        double dist = Double.parseDouble(ds.child("distanceTraveled").getValue().toString());
                        dist = Math.round(dist *100)/100;
                        String distanceTraveled = ""+dist;
                        String time = ds.child("time").getValue().toString();
                        String pid = ds.child("pid").getValue().toString();
                        //if (!exercise.equals(null)&&exercise.equals(type))
                        exerciseList.add(new Exercise(distanceTraveled,time,pid));
                    }
                    exerciseList = reverse(exerciseList);
                    eAdapter = new ExerciseAdapter(exerciseList, R.layout.exercise_view);


                    recyclerView.setAdapter(eAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static <T> ArrayList<T> reverse(ArrayList<T> list) {
        int length = list.size();
        ArrayList<T> result = new ArrayList<T>(length);
        for (int i = length - 1; i >= 0; i--) {
            result.add(list.get(i));
        }
        return result; }
}