package com.practica02.alexander_luna;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.practica02.alexander_luna.adapter.WordListAdapter;
import com.practica02.alexander_luna.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class WordListFragment extends Fragment {
    private Context context;
    public ArrayList<WordData> englishWordList = null;
    public ListView wordListView = null;
    public WordListAdapter wordListAdapter = null;

    private String strUri;
    private int audioPosition;

    public MediaPlayer mediaPlayer;

    public WordListFragment() {
    }
    public WordListFragment(Context c) {
        context = c;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            strUri = savedInstanceState.getString("STRURI");
            audioPosition = savedInstanceState.getInt("AUDIOPOSITION");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("STRURI", strUri);
        savedInstanceState.putInt("AUDIOPOSITION", audioPosition);

        //Save the fragment's state here
    }

    public void onStart() {
        super.onStart();

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
    public void onStop() {
        super.onStop();

        pausarAudio();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_word_list, container,
        false);

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
        wordListView = (ListView) rootView.findViewById(R.id.listView_wordlist);
        wordListAdapter = new WordListAdapter(getContext(), R.layout.worditem, englishWordList);
        wordListView.setAdapter(wordListAdapter);

        wordListAdapter.setFragment(this);
        //wordListView.setOnClickListener(viewClickListener);
        //wordListView.findViewById(R.id.listView_wordlist).findViewById(R.id.imageButton).setOnClickListener(voiceButtonClickListener);

        return rootView;
    }

}