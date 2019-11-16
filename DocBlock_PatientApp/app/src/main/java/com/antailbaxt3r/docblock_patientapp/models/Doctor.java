package com.antailbaxt3r.docblock_patientapp.models;

public class Doctor {

    private String username, designation, imageURL, UID;

    public Doctor(String username, String designation, String imageURL, String UID) {
        this.username = username;
        this.designation = designation;
        this.imageURL = imageURL;
        this.UID = UID;
    }

    public Doctor() {
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
