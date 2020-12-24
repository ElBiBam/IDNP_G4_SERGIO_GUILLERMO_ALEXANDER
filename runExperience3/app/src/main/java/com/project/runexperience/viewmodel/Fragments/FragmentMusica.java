package com.project.runexperience.viewmodel.Fragments;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.runexperience.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentMusica extends Fragment  {
    Button play_pause, btn_repetir, btn_stop, btn_anterior, btn_siguiente;
    MediaPlayer mp;
    ImageView iv;
    int repetir = 2, posicion = 0;

    MediaPlayer vectormp [] = new MediaPlayer [3];

    public FragmentMusica() {


    }


    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceStatus){
        View view = inflater.inflate(R.layout.fragment_musica, container, false);

        play_pause = view.findViewById(R.id.btn_play);
        btn_repetir = view.findViewById(R.id.btn_repetir);
        btn_stop = view.findViewById(R.id.btn_stop);
        btn_anterior = view.findViewById(R.id.btn_anterior);
        btn_siguiente = view.findViewById(R.id.btn_siguiente);
        iv = view.findViewById(R.id.imageView);

        vectormp[0] = MediaPlayer.create(getActivity(), R.raw.race);
        vectormp[1] = MediaPlayer.create(getActivity(), R.raw.sound);
        vectormp[2] = MediaPlayer.create(getActivity(), R.raw.tea);


        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayPause(view);
            }
        });
        btn_repetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Repetir(view);
            }
        });
        btn_anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Anterior(view);
            }
        });
        btn_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Siguiente(view);
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Stop(view);
            }
        });


        return view;
    }

    //Método para el botón PlayPause
    public void PlayPause(View view){
        if(vectormp[posicion].isPlaying()){
            vectormp[posicion].pause();
            play_pause.setBackgroundResource(R.drawable.reproducir);
            Toast.makeText(getActivity(), "Pausa", Toast.LENGTH_SHORT).show();
        } else {
            vectormp[posicion].start();
            play_pause.setBackgroundResource(R.drawable.pausa);
            Toast.makeText(getActivity(), "Play", Toast.LENGTH_SHORT).show();
        }
    }

    //Método para el botón Stop
    public void Stop(View view){
        if(vectormp[posicion] != null){
            vectormp[posicion].stop();

            vectormp[0] = MediaPlayer.create(getActivity(), R.raw.race);
            vectormp[1] = MediaPlayer.create(getActivity(), R.raw.sound);
            vectormp[2] = MediaPlayer.create(getActivity(), R.raw.tea);
            posicion = 0;
            play_pause.setBackgroundResource(R.drawable.reproducir);
            iv.setImageResource(R.drawable.portada1);
            Toast.makeText(getActivity(), "Stop", Toast.LENGTH_SHORT).show();
        }
    }

    //Método repetir una pista
    public void Repetir(View view){
        if(repetir == 1){
            btn_repetir.setBackgroundResource(R.drawable.no_repetir);
            Toast.makeText(getActivity(), "No repetir", Toast.LENGTH_SHORT).show();
            vectormp[posicion].setLooping(false);
            repetir = 2;
        } else {
            btn_repetir.setBackgroundResource(R.drawable.repetir);
            Toast.makeText(getActivity(), "Repetir", Toast.LENGTH_SHORT).show();
            vectormp[posicion].setLooping(true);
            repetir = 1;
        }
    }

    //Método para saltar a la siguiente canción.
    public void Siguiente(View view){
        if(posicion < vectormp.length -1){

            if(vectormp[posicion].isPlaying()){
                vectormp[posicion].stop();
                posicion++;
                vectormp[posicion].start();

                if(posicion == 0){
                    iv.setImageResource(R.drawable.portada1);
                } else if(posicion == 1){
                    iv.setImageResource(R.drawable.portada2);
                }else if(posicion == 2){
                    iv.setImageResource(R.drawable.portada3);
                }

            } else {
                posicion++;

                if(posicion == 0){
                    iv.setImageResource(R.drawable.portada1);
                } else if(posicion == 1){
                    iv.setImageResource(R.drawable.portada2);
                }else if(posicion == 2){
                    iv.setImageResource(R.drawable.portada3);
                }
            }

        } else {
            Toast.makeText(getContext(), "No hay más canciones", Toast.LENGTH_SHORT).show();
        }
    }


    //Método para regresar a la canción anterior
    public void Anterior(View view){
        if(posicion >= 1){

            if(vectormp[posicion].isPlaying()){
                vectormp[posicion].stop();
                vectormp[0] = MediaPlayer.create(getContext(), R.raw.race);
                vectormp[1] = MediaPlayer.create(getContext(), R.raw.sound);
                vectormp[2] = MediaPlayer.create(getContext(), R.raw.tea);
                posicion--;

                if(posicion == 0){
                    iv.setImageResource(R.drawable.portada1);
                } else if(posicion == 1){
                    iv.setImageResource(R.drawable.portada2);
                }else if(posicion == 2){
                    iv.setImageResource(R.drawable.portada3);
                }

                vectormp[posicion].start();

            } else {
                posicion--;

                if(posicion == 0){
                    iv.setImageResource(R.drawable.portada1);
                } else if(posicion == 1){
                    iv.setImageResource(R.drawable.portada2);
                }else if(posicion == 2){
                    iv.setImageResource(R.drawable.portada3);
                }
            }

        } else {
            Toast.makeText(getActivity(), "No hay más canciones", Toast.LENGTH_SHORT).show();
        }
    }
}
