package com.antailbaxt3r.docblock_doctorapp.models;

public class QueryModel {

    String problem, duration, UID, name;

    public QueryModel(String problem, String duration, String UID, String name) {
        this.problem = problem;
        this.duration = duration;
        this.UID = UID;
        this.name = name;
    }

    public QueryModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
