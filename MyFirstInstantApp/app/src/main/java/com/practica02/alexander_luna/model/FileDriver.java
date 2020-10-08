package com.practica02.alexander_luna.model;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class FileDriver {
    public static final String FILENAME = "audio.mp3";
    public static int RECORD_COUNTER = -1;
    private static final String TAG = "FileDriver";
    private File file;
    private FileOutputStream fileOutputStream;
    private Context context;

    public FileDriver(Context context) {
        this.context = context;
        try {
            FileDriver.RECORD_COUNTER = 0;
            file = new File(context.getFilesDir()+"/"+ FILENAME);
            if(file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));

                String line = null;
                while((line = br.readLine()) != null) {
                    Log.i(TAG, line);
                    FileDriver.RECORD_COUNTER = FileDriver.RECORD_COUNTER + 1;
                }
                br.close();
            }

            fileOutputStream = new FileOutputStream(file, true);

            Log.i(TAG, file.getAbsolutePath());
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
