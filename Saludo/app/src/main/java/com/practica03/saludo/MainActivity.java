package com.practica03.saludo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("mainctivity", "create");

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, SaludoActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void onStart() {
        super.onStart();
        Log.d("mainactivity", "start");
    }

    protected void onResume() {
        super.onResume();
        Log.d("mainactivity", "resume");
    }
    protected void onPause() {
        super.onPause();
        Log.d("mainactivity", "pause");
    }
    protected void onStop() {
        super.onStop();
        Log.d("mainactivity", "stop");
    }
    protected void onDestroy() {
        super.onDestroy();
        Log.d("mainactivity", "destroy");
    }
    protected void onRestart() {
        super.onRestart();
        Log.d("mainactivity", "restart");
    }
}