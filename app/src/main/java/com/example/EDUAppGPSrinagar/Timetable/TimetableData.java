package com.example.EDUAppGPSrinagar.Timetable;

public class TimetableData {
    String uri;
    String key;

    public TimetableData() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public TimetableData(String uri, String key) {
        this.uri = uri;
        this.key = key;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
