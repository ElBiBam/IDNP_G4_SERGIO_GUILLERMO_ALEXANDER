package com.practica03.saludo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class SaludoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saludo);
        Log.d("saludoctivity", "create");
    }

    protected void onStart() {
        super.onStart();
        Log.d("saludoactivity", "start");
    }

    protected void onResume() {
        super.onResume();
        Log.d("saludoactivity", "resume");
    }
    protected void onPause() {
        super.onPause();
        Log.d("saludoactivity", "pause");
    }
    protected void onStop() {
        super.onStop();
        Log.d("saludoactivity", "stop");
    }
    protected void onDestroy() {
        super.onDestroy();
        Log.d("saludoactivity", "destroy");
    }
    protected void onRestart() {
        super.onRestart();
        Log.d("saludoactivity", "restart");
    }
}