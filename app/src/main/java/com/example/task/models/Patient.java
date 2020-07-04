package com.example.task.models;

public class Patient {

    private String name, email;
    private int age;
    private char gender;

    public Patient(String name, String email, int age, char gender) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
    }
}
