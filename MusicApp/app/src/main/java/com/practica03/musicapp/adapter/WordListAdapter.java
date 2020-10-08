package com.practica03.musicapp.adapter;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


import com.practica03.musicapp.MainActivity;
import com.practica03.musicapp.R;
import com.practica03.musicapp.data.WordData;

import java.io.IOException;
import java.util.List;

import androidx.fragment.app.Fragment;

public class WordListAdapter extends ArrayAdapter<WordData> {
    private int resourceLayout;
    private Context mContext;
    public MediaPlayer mediaPlayer;

    MainActivity mainActivity;

    public WordListAdapter(Context context, int resource, List<WordData> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    public void setFragment(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        WordData p = getItem(position);

        if (p != null) {
            Log.d("adapter", "1");
            //TextView tt1 = (TextView) v.findViewById(R.id.textView_icon);
            Log.d("adapter", "2");
            TextView tt2 = (TextView) v.findViewById(R.id.textView_word);
            ImageButton tt3 = (ImageButton) v.findViewById(R.id.imageButton);
            tt3.setOnClickListener(voiceButtonClickListener);
            //if (tt1 != null) {
            //tt1.setText(p.getId());
            //}

            if (tt2 != null) {
                tt2.setText(p.getTranslation());
            }

            if (tt3 != null) {
                //tt3.setText(p.getDescription());
            }
        }

        return v;
    }
    private View.OnClickListener voiceButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (mediaPlayer == null) {
                View parentRow = (View) v.getParent();
                TextView tt2 = (TextView) parentRow.findViewById(R.id.textView_word);

                try {
                    try{
                        Uri mp3 = Uri.parse("android.resource://"
                                + mContext.getPackageName() + "/raw/"
                                + tt2.getText());

                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.setDataSource(mContext, mp3);
                        mediaPlayer.prepare(); // might take long! (for buffering, etc)
                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(onCompletionListener);
                        mainActivity.mediaPlayer = mediaPlayer;
                    }catch(IOException e){

                    }

                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    };
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            // TODO Auto-generated method stub
            mediaPlayer.release();
            mediaPlayer = null;
        }
    };
}
