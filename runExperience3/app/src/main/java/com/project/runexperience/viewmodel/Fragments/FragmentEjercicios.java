package com.project.runexperience.viewmodel.Fragments;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.runexperience.viewmodel.MainActivity;
import com.project.runexperience.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentEjercicios extends Fragment  {
    private Button startWalk;
    private TextView metersWalk;
    private Chronometer chronometer;
    private String action = "caminar";
    private Spinner spinner;
    private String[]ejercicios = new String[]{"Caminar","Correr","Ciclismo"};


    public FragmentEjercicios() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceStatus){
        View view = inflater.inflate(R.layout.fragment2_ejercicios, container, false);


        startWalk = view.findViewById(R.id.startWalk);
        metersWalk = view.findViewById(R.id.metersWalk);
        chronometer= view.findViewById(R.id.timeWalk);
        spinner = view.findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,ejercicios);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                action = (String) adapterView.getItemAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        startWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!MainActivity.getInstance().isrunning){
                    MainActivity.getInstance().setAction(action);
                    MainActivity.getInstance().initSensor();
                    startWalk.setText("Stop");
                    Log.d("locatio", (String) startWalk.getText());
                    long systemCurrTime = SystemClock.elapsedRealtime();
                    chronometer.setBase(systemCurrTime);
                    chronometer.start();
                    MainActivity.getInstance().isrunning = true;
                }
                else {
                    MainActivity.getInstance().setAction(action);
                    MainActivity.getInstance().stopSensor();
                    startWalk.setText("Start");
                    long systemCurrTime = SystemClock.elapsedRealtime();
                    chronometer.setBase(systemCurrTime);
                    chronometer.stop();
                    MainActivity.getInstance().isrunning = false;
                }


            }
        });


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
