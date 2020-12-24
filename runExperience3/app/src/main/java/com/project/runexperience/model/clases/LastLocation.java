package com.project.runexperience.model.clases;

public class LastLocation {
    private float lastLat;
    private float lastLon;
    public boolean isfirst = true;

    public void pushValues( float lat, float lon){
        lastLat = lat;
        lastLon = lon;
        isfirst = false;
    }
    public float getLat(){
        return lastLat;
    }
    public float getLon(){
        return lastLon;
    }
}
