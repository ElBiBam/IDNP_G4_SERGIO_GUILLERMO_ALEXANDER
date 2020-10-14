package com.practica04.mybinder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class Secret extends Service {
    // Binder given to clients
    private final IBinder binder = new LocalBinder();
    // Random number generator
    public String mUsername = "";
    public String mPassword = "";
    public String mResponse = "0";

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        Secret getService() {
            // Return this instance of LocalService so clients can call public methods
            return Secret.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        // No sirve, porque se realiza al iniciar el sericio y no cuando se hace click
        /*
        Log.d("clickbutton", "binding");
        mUsername = intent.getExtras().getString("username");
        mPassword = intent.getExtras().getString("password");
        mResponse = getUser(mUsername, mPassword);
        Log.d("clickbutton", mUsername);
        Log.d("clickbutton", mPassword);*/
        //Log.d("clickbutton", mResponse);

        return binder;
    }

    /** method for clients */
    public String getUser(String username_client, String password_client) {
        // Get user of system
        /*
        User user = findUsername(username_client, password_client);
        if(user)
            return "1";
        else
            return "0";
        */
        if(username_client.contentEquals("user") && password_client.contentEquals("1234"))
            return "1";
        else
            return "0";
    }

    /** method for clients */
    public String getUsername() {
        return mUsername;
    }
    public String getPassword() {
        return mPassword;
    }
    public String getResponse() {
        return mResponse;
    }
}
