package com.example.EDUAppGPSrinagar.Notice;

public class NoticeData {
    String title;
    String teacher;

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    String image;
    String date;
    String time;
    String key;

    public NoticeData() {
    }

    public NoticeData(String title,String teacher,String image, String date, String time,String key) {
        this.title = title;
        this.teacher = teacher;
        this.image = image;
        this.date = date;
        this.time = time;
        this.key = key;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}