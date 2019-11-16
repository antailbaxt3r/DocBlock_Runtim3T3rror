package com.antailbaxt3r.docblock_patientapp.models;

public class Prescription {

    private String imageURL, date, id;

    public Prescription(String imageURL, String date, String id) {
        this.imageURL = imageURL;
        this.date = date;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Prescription() {
        this.imageURL = "";
        this.date = "";
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
