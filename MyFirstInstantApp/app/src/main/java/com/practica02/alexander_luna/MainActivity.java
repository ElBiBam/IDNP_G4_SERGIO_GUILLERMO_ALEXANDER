package com.practica02.alexander_luna;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.io.IOException;
import java.net.CookieHandler;
import java.util.ArrayList;
import com.practica02.alexander_luna.WordListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        WordListFragment pFragment = new WordListFragment(this);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, pFragment).commit();
        }*/
        /*
        if (savedInstanceState == null) {
            WordListFragment fragmentDemo = (WordListFragment)
                    getSupportFragmentManager().findFragmentById(R.id.fragment);
        }*/
        //MediaPlayer mediaPlayer = MediaPlayer.create(getApplication(), R.raw.coldplaythescientist);

    }

}