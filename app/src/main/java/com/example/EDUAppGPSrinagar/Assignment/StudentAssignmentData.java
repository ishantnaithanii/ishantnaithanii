package com.example.EDUAppGPSrinagar.Assignment;

public class StudentAssignmentData {
    private String title;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    private String subject;
    private String pdfUrl;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String date;
    private String time;

    public StudentAssignmentData(String title,String subject, String pdfUrl, String date, String time, String key) {
        this.title = title;
        this.subject=subject;
        this.pdfUrl = pdfUrl;
        this.date = date;
        this.time = time;
        this.key = key;
    }

    private String key;

    public StudentAssignmentData() {
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String name) {
        this.title = name;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

}
