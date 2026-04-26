package com.example.EDUAppGPSrinagar.Attendance;

public class StudentModel {
    String roll;
    String name;
    String Status;

    public StudentModel() {
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public StudentModel(String roll, String name, String status) {
        this.roll = roll;
        this.name = name;
        Status = status;
    }
}
