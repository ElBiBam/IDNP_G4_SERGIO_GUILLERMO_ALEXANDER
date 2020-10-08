package com.practica03.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.practica03.musicapp.adapter.WordListAdapter;
import com.practica03.musicapp.util.Utils;
import com.practica03.musicapp.data.WordData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context;
    public ArrayList<WordData> englishWordList = null;
    public ListView wordListView = null;
    public WordListAdapter wordListAdapter = null;

    private String strUri;
    private int audioPosition;

    public MediaPlayer mediaPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            InputStream wordListSteam = this.getResources().openRawResource(R.raw.words);
            String wordListStr = Utils.readStringFromStream(wordListSteam);
            JSONObject wordList = new JSONObject(wordListStr);
            JSONArray keyArray = wordList.names();

            //sort json objects
            List<String> jsonKey = new ArrayList<String>();
            for(int i=0; i<keyArray.length(); i++) {
                jsonKey.add(keyArray.getString(i));
            }
            Collections.sort(jsonKey);
            keyArray = new JSONArray(jsonKey);
            englishWordList = new ArrayList<WordData>();

            for (int i=0; i < keyArray.length(); i++) {
                WordData wd = new WordData();
                wd.word = keyArray.getString(i);
                wd.translation = wordList.getString(wd.word);
                englishWordList.add(wd);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Log.d("wordlistfragment", englishWordList.get(0).getWord());
        wordListView = findViewById(R.id.listView_wordlist);
        wordListAdapter = new WordListAdapter(this, R.layout.worditem, englishWordList);
        wordListView.setAdapter(wordListAdapter);

        wordListAdapter.setFragment(this);
    }

    public void onStart() {
        super.onStart();

        reiniciarAudio();
    }
    private void reiniciarAudio() {
        if(audioPosition != 0)
            mediaPlayer.start();
    }
    public void onResume() {
        super.onResume();

        reiniciarAudio();
    }
    private void pausarAudio() {
        audioPosition = mediaPlayer.getCurrentPosition();
        if (audioPosition != 0) {
            mediaPlayer.pause();
        }
    }
    public void onPause() {
        super.onPause();

        pausarAudio();
    }
    public void onStop() {
        super.onStop();

        pausarAudio();
    }

}