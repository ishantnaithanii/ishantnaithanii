package com.example.EDUAppGPSrinagar.Paper;

public class StudentPapersData {
    private String title;
    private String pdfUrl;
    private String date;
    private String time;
    private String Key;
    private String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public StudentPapersData(String title, String subject, String pdfUrl, String date, String time, String key) {
        this.title = title;
        this.subject=subject;
        this.pdfUrl = pdfUrl;
        this.date = date;
        this.time = time;
        Key = key;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public StudentPapersData() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

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

}
