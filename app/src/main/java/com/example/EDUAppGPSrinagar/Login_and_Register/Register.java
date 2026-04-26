package com.example.EDUAppGPSrinagar.Login_and_Register;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.EDUAppGPSrinagar.R;
import com.example.EDUAppGPSrinagar.StudentActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private EditText mFullName,mEmail,mPassword,mMobile,mBranch,mDob,mClgPwd;
    private Button registerbtn;
    private RadioButton radioButtonRegisterGenderSelected;
    private RadioGroup radioGroupRegisterGender;
    private TextView mRegisterLogin;
    private DatePickerDialog picker;
    private ProgressBar progressBar;
    private static final String TAG= "Register";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFullName=findViewById(R.id.fullName);
        mEmail=findViewById(R.id.email);
        mPassword=findViewById(R.id.password);
        mMobile=findViewById(R.id.mobile);
        mClgPwd=findViewById(R.id.clg_Pwd);
        mRegisterLogin=findViewById(R.id.register_login);
        progressBar=findViewById(R.id.progressbar);
        registerbtn=findViewById(R.id.registerBtn);
        mBranch=findViewById(R.id.branch);
        mDob=findViewById(R.id.register_Dob);

        //RadioButton for Gender
        radioGroupRegisterGender=findViewById(R.id.register_radioGroup);
        radioGroupRegisterGender.clearCheck();

        //Setting up DatePicker on editText
        mDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar=Calendar.getInstance();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);

                //DatePicker Dialog
                picker=new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mDob.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                },year,month,day);
                picker.show();
            }
        });

        mRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

        registerbtn.setOnClickListener(v -> {

            int selectedGenderId= radioGroupRegisterGender.getCheckedRadioButtonId();
            radioButtonRegisterGenderSelected=findViewById(selectedGenderId);

            //Obtain the entered data
            String fullName=mFullName.getText().toString().trim();
            String email=mEmail.getText().toString().trim();
            String mobile=mMobile.getText().toString().trim();
            String password=mPassword.getText().toString().trim();
            String dob=mDob.getText().toString().trim();
            String branch=mBranch.getText().toString().trim();
            String clgPwd=mClgPwd.getText().toString().trim();
            String gender = (String)radioButtonRegisterGenderSelected.getText(); //Can't obtain the value before verifying if an button was selected or not

            //Validate mobile number using Matcher and Pattern(Regular Expression)
            String mobileRegex="[6-9][0-9]{9}"; //First no. can be {6,8,9} and rest 9 nos. can be any no.
            Matcher mobileMatcher;
            Pattern mobilePattern=Pattern.compile(mobileRegex);
            mobileMatcher=mobilePattern.matcher(mobile);

            if(TextUtils.isEmpty(fullName)){
                mFullName.setError("Full Name is Required.");
                return;
            } else if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email is Required.");
                return;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                mEmail.setError("Valid Email is Required.");
                return;
            } else if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password is Required.");
                return;
            }else if (TextUtils.isEmpty(dob)) {
                mPassword.setError("Date of Birth is Required.");
                return;
            } else if (radioGroupRegisterGender.getCheckedRadioButtonId()==-1) {
                radioButtonRegisterGenderSelected.setError("Gender is Required");
                return;
            } else if (TextUtils.isEmpty(branch)) {
                mBranch.setError("Branch is Required.");
                return;
            } else if (TextUtils.isEmpty(mobile)) {
                mMobile.setError("Mobile Number is Required.");
                return;
            } else if (mobile.length()!=10) {
                mMobile.setError("Mobile No. should be 10 digits.");
                return;
            } else if (!mobileMatcher.find()) {
                mMobile.setError("Mobile No. is not Valid.");
                return;
            } else if (password.length()<6) {
                mPassword.setError("Password too week.");
                return;
            } else if (!(clgPwd.equals("12info"))) {
                mClgPwd.setError("College Password is Wrong");
            } else{
                progressBar.setVisibility(View.VISIBLE);
                registerUser(fullName,email,mobile,password,dob,gender,branch,clgPwd);
            }
        });
    }
    private void registerUser(String fullName, String email, String mobile, String password,String dob,String gender,String branch,String clgPwd) {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        //Create user Profile
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser=auth.getCurrentUser();

                    //enter user data into firebase realtime database
                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(fullName, mobile,gender,branch,dob,clgPwd);
                    DatabaseReference referenceProfile= FirebaseDatabase.getInstance().getReference("Registered Users");
                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isComplete()) {
                                //send Verification Email
                                firebaseUser.sendEmailVerification();
                                Toast.makeText(Register.this, "User registered Successfully.Please verify your email. ", Toast.LENGTH_LONG).show();

                                //Open Login After successful registration
                                Intent intent= new Intent(Register.this, Login.class);
                                //To Prevent User from returning back ti Register Activity on pressing back button after registration
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish(); //to close register activity

                            }else {
                                Toast.makeText(Register.this, "User registration failed.Please try again. ", Toast.LENGTH_LONG).show();
                            }
                            // Hide progressbar whether user creation is successful or failed
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                } else {
                    try {
                       throw Objects.requireNonNull(task.getException());
                    }catch (FirebaseAuthWeakPasswordException e){
                        mPassword.setError("Your password is to weak. kindly use a mix of alphabets,numbers and Special character.");
                        return;
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        mPassword.setError("Your email is Invalid or already in use. Kindly re-enter.");
                        return;
                    }catch (FirebaseAuthUserCollisionException e){
                        mPassword.setError("User is already registered with this email.Use another email.");
                    }
                    catch (Exception e) {
                        Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                        Toast.makeText(Register.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                    // Hide progressbar whether user creation is successful or failed
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    //check if user is already logged in.In such case, straightaway take the user user's profile
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            FirebaseUser firebaseUser = auth.getCurrentUser();
            if(firebaseUser.isEmailVerified()) {
                Toast.makeText(this, "Already Registered!", Toast.LENGTH_SHORT).show();
                //Start  the UserprofileActivity
                startActivity(new Intent(Register.this, Login.class));
                finish(); //close
            }
        }else{
            Toast.makeText(this, "You can register now!", Toast.LENGTH_SHORT).show();
        }

    }
}