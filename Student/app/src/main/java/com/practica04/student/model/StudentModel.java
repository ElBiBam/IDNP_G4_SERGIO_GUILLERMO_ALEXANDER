package com.practica04.student.model;

public class StudentModel {
    private String cui;
    private String lastName;
    private String name;

    public StudentModel(String cui, String lastName, String name) {
        this.cui = cui;
        this.lastName = lastName;
        this.name = name;
    }

    public String getCui() {
        return cui;
    }
    public String getLastName() {
        return lastName;
    }
    public String getName() {
        return name;
    }
}
