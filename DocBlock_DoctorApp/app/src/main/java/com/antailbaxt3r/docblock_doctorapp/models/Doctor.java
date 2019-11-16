package com.antailbaxt3r.docblock_doctorapp.models;

public class Doctor {

    private String name, designation, imageURL;

    public Doctor(String name, String designation, String imageURL) {
        this.name = name;
        this.designation = designation;
        this.imageURL = imageURL;
    }

    public Doctor() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
