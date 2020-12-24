package com.project.runexperience.model.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.runexperience.viewmodel.MainActivity;
import com.project.runexperience.R;
import com.project.runexperience.model.clases.Exercise;
import com.project.runexperience.viewmodel.MapLocation;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> implements View.OnClickListener {
    private int resource;
    private ArrayList<Exercise> exerciseList;

    private View.OnClickListener listener;

    public ExerciseAdapter(ArrayList<Exercise>exercises, int resource){
        this.exerciseList = exercises;
        this.resource = resource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Exercise exercise = exerciseList.get(position);

        holder.textViewExercise.setText(exercise.getDistanceTraveled()+" mts.");
        holder.time.setText(exercise.getTime()+" min");
        holder.pid.setText(exercise.getPid());


        //en caso se seleeccione un holder se extrae el pid y se inicializara un mapa, se puede seleccionar el utlimo ejercicio que se este realizando
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("main",exercise.getPid()+"");


                MainActivity ins = MainActivity.getInstance();
                ins.startActivity(new Intent(ins, MapLocation.class).putExtra("pid", exercise.getPid()));
            }
        });
    }

    @Override
    public int getItemCount() {

        return exerciseList.size();
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }
    public void  setOnclickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public  TextView textViewExercise;
        public  TextView time;
        public  TextView pid;
        public  View view;


        public ViewHolder(@NonNull View view) {
            super(view);

            this.view = view;
            this.textViewExercise = (TextView) view.findViewById(R.id.TotalMeters);
            this.time = (TextView) view.findViewById(R.id.time);
            this.pid = (TextView) view.findViewById(R.id.pid);
        }
    }
}
