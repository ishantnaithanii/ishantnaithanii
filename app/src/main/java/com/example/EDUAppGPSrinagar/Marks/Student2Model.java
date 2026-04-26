package com.example.EDUAppGPSrinagar.Marks;

public class Student2Model {
    private String roll;
    private String name;

    public Student2Model(String roll, String name) {
        this.roll = roll;
        this.name = name;
    }

    private String marks;

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

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public Student2Model() {
    }

    public Student2Model(String roll, String name, String marks) {
        this.roll = roll;
        this.name = name;
        this.marks = marks;
    }
}
