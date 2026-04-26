package com.example.EDUAppGPSrinagar.Marks;

public class SubjectModel {
    String subject;
    String type;
    String sem;

    public SubjectModel() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public SubjectModel(String subject, String type, String sem) {
        this.subject = subject;
        this.type = type;
        this.sem = sem;
    }

    public SubjectModel(String subject, String sem) {
        this.subject = subject;
        this.sem = sem;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}