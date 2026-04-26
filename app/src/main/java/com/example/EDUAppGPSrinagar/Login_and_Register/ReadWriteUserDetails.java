package com.example.EDUAppGPSrinagar.Login_and_Register;

public class ReadWriteUserDetails {
    public String fullName, mobile,dob,branch,gender,clgPwd;

    //Constructor
    public  ReadWriteUserDetails(){};

    public ReadWriteUserDetails(String fullName, String mobile,String gender,String branch,String dob,String clgPwd) {
        this.fullName = fullName;
        this.mobile = mobile;
        this.branch=branch;
        this.dob=dob;
        this.gender=gender;
        this.clgPwd=clgPwd;
    }
    public ReadWriteUserDetails(String fullName, String mobile,String gender,String branch,String dob) {
        this.fullName = fullName;
        this.mobile = mobile;
        this.branch=branch;
        this.dob=dob;
        this.gender=gender;
    }
}
