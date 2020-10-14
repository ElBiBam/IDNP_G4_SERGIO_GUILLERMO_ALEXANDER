package com.practica04.mybinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Secret mService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBound = true;
        Button btn = findViewById(R.id.button);
        //btn.setOnClickListener(btnListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to Secret
        // Esto se hace antes de hacer click
        /*
        Intent intent = new Intent(this, Secret.class);
        EditText et_username = (EditText)findViewById(R.id.editText_username);
        EditText et_password = (EditText)findViewById(R.id.editText_password);
        intent.putExtra("username", et_username.getText().toString());
        intent.putExtra("password", et_password.getText().toString());*/
        Intent intent = new Intent(this, Secret.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        mBound = false;
    }

    /** Called when a button is clicked (the button in the layout file attaches to
     * this method with the android:onClick attribute) */
    public void onButtonClick(View v) {
        Log.d("clickbutton", "click");
        if (mBound == true) {
            // Call a method from the LocalService.
            // However, if this call were something that might hang, then this request should
            // occur in a separate thread to avoid slowing down the activity performance.
            //String username = mService.getUsername();
            //String password = mService.getPassword();
            EditText et_username = (EditText)findViewById(R.id.editText_username);
            EditText et_password = (EditText)findViewById(R.id.editText_password);
            String response = mService.getUser(et_username.getText().toString(), et_password.getText().toString());
            //Log.d("clickbutton", username);
            //Log.d("clickbutton", password);
            //Log.d("clickbutton", response);
            TextView tw_response = findViewById(R.id.textView_response);
            tw_response.setText(response);
            //Toast.makeText(getApplicationContext(), "username: " + username, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), "password: " + password, Toast.LENGTH_SHORT).show();
        }
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            Log.d("clickbutton", "connected");
            Secret.LocalBinder binder = (Secret.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.d("clickbutton", "disconnected");
            mBound = false;
        }
    };
}