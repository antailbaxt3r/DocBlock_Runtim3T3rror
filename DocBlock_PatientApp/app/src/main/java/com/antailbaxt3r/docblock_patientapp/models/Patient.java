package com.antailbaxt3r.docblock_patientapp.models;

public class Patient {

    //fields
    String username, UID, contactNumber, dob;

    //constructor

    public Patient(String username, String UID, String contactNumber, String dob) {
        this.username = username;
        this.UID = UID;
        this.contactNumber = contactNumber;
        this.dob = dob;
    }

    public Patient() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
