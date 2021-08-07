package com.example.b07projectgroup4;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Appointment implements Serializable {
    private String doctor_username;
    private String doctor_name;
    private String patient_username;
    private String patient_name;
    private String time;

    public Appointment(){

    }

    public Appointment(String doctor_username, String doctor_name, String patient_username, String patient_name, String time) {
        this.doctor_username = doctor_username;
        this.doctor_name = doctor_name;
        this.patient_username = patient_username;
        this.patient_name = patient_name;
        this.time = time;
    }

    public String displayForPatient(){
        String [] str = time.split(" 2021 ");
        return "Doctor " + doctor_name + " on " + str[0] + " from " + str[1] ;
    }

    @NonNull
    @Override
    public String toString() {
        return "Dr." + doctor_name + " at " + time;
    }

    public String getDoctor_username() {
        return doctor_username;
    }

    public void setDoctor_username(String doctor_username) {
        this.doctor_username = doctor_username;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getPatient_username() {
        return patient_username;
    }

    public void setPatient_username(String patient_username) {
        this.patient_username = patient_username;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
