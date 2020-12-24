package com.project.runexperience.model.clases;

public class Exercise {
    private String firstLocation;
    private String lastLocation;
    private float initLat,initLon,finalLat,finalLon;
    private String distanceTraveled;
    private String realdistance;
    private String typeExercise;
    private String email;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    private String pid;

    public Exercise(String pid, String firstLocation, String lastLocation, float initLat, float initLon, float finalLat, float finalLon, String distanceTraveled, String realdistance, String typeExercise, String email, String time) {
        this.pid = pid;
        this.firstLocation = firstLocation;
        this.lastLocation = lastLocation;
        this.initLat = initLat;
        this.initLon = initLon;
        this.finalLat = finalLat;
        this.finalLon = finalLon;
        this.distanceTraveled = distanceTraveled;
        this.realdistance = realdistance;
        this.typeExercise = typeExercise;
        this.email = email;
        this.time = time;
    }

    public Exercise(String distanceTraveled, String time, String pid) {
        this.distanceTraveled = distanceTraveled;
        this.pid = pid;
        this.time = time;
    }

    private String time;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTime() {
        return time;
    }

    public float getInitLat() {
        return initLat;
    }

    public void setInitLat(float initLat) {
        this.initLat = initLat;
    }

    public float getInitLon() {
        return initLon;
    }

    public void setInitLon(float initLon) {
        this.initLon = initLon;
    }

    public float getFinalLat() {
        return finalLat;
    }

    public void setFinalLat(float finalLat) {
        this.finalLat = finalLat;
    }

    public float getFinalLon() {
        return finalLon;
    }

    public void setFinalLon(float finalLon) {
        this.finalLon = finalLon;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFirstLocation() {
        return firstLocation;
    }

    public void setFirstLocation(String firstLocation) {
        this.firstLocation = firstLocation;
    }

    public String getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(String lastLocation) {
        this.lastLocation = lastLocation;
    }

    public String getDistanceTraveled() {
        return distanceTraveled;
    }

    public void setDistanceTraveled(String distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }

    public String getRealdistance() {
        return realdistance;
    }

    public void setRealdistance(String realdistance) {
        this.realdistance = realdistance;
    }

    public String getTypeExercise() {
        return typeExercise;
    }

    public void setTypeExercise(String typeExercise) {
        this.typeExercise = typeExercise;
    }
}