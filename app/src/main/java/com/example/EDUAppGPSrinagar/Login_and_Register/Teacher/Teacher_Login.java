package com.example.EDUAppGPSrinagar.Login_and_Register.Teacher;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.EDUAppGPSrinagar.AdminActivity;
import com.example.EDUAppGPSrinagar.Login_and_Register.ForgotPasswordActivity;
import com.example.EDUAppGPSrinagar.Login_and_Register.Login;
import com.example.EDUAppGPSrinagar.Login_and_Register.UserProfileActivity;
import com.example.EDUAppGPSrinagar.R;
import com.example.EDUAppGPSrinagar.StudentActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;


public class Teacher_Login extends AppCompatActivity {
    private EditText mLoginEmail,mLoginPwd,mClgPwd;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;
    private LinearLayout loginPwdLinear;
    private ImageView imageViewShowHidePwd,loginprofileTeacher;
    private Button mLoginBtn,mForgotPswBtn;
    private TextView mRegister;
    private static final String TAG="Login";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        mLoginEmail=findViewById(R.id.login_email_teacher);
        mLoginPwd=findViewById(R.id.login_password_teacher);
        mClgPwd=findViewById(R.id.clg_password_teacher);
        loginPwdLinear=findViewById(R.id.loginPwdLinearTeacher);
        loginprofileTeacher=findViewById(R.id.loginProfileTeacher);
        mLoginBtn = findViewById(R.id.login_loginBtn_teacher);
        progressBar=findViewById(R.id.login_progressBar_teacher);

        authProfile=FirebaseAuth.getInstance();

        //reset Password
        mForgotPswBtn=findViewById(R.id.login_forgotPwdBtn_teacher);
        mForgotPswBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Teacher_Login.this, "You can reset your Password now!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Teacher_Login.this, ForgotPasswordActivity.class));
            }
        });

        //show Hide Password Using Eye Icon
        imageViewShowHidePwd=findViewById(R.id.login_show_hide_pwd_teacher);
        imageViewShowHidePwd.setImageResource(R.drawable.hide_pwd);
        imageViewShowHidePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLoginPwd.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    //if password is visible then hide it
                    mLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //change icon
                    imageViewShowHidePwd.setImageResource(R.drawable.hide_pwd);
                }else{
                    mLoginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowHidePwd.setImageResource(R.drawable.eye);
                }
            }
        });

        //show hide icon for clg pwd
        ImageView imageViewShowClgPwd=findViewById(R.id.clg_show_hide_pwd_teacher);
        imageViewShowClgPwd.setImageResource(R.drawable.hide_pwd);
        imageViewShowClgPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClgPwd.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    //if password is visible then hide it
                    mClgPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //change icon
                    imageViewShowClgPwd.setImageResource(R.drawable.hide_pwd);
                }else{
                    mClgPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowClgPwd.setImageResource(R.drawable.eye);
                }
            }
        });

        mRegister = findViewById(R.id.login_register_teacher);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Teacher_Login.this, Teacher_Register.class));
            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mLoginEmail.getText().toString().trim();
                String password = mLoginPwd.getText().toString().trim();
                String clgPwd=mClgPwd.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mLoginEmail.setError("Email is Required.");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mLoginEmail.setError("Valid Email is Required.");
                } else if (TextUtils.isEmpty(password)) {
                    mLoginPwd.setError("Password is Required.");
                } else if (TextUtils.isEmpty(clgPwd)) {
                    mClgPwd.setError("College Password is required.");
                } else if (!clgPwd.equals("Info12")) {
                    mClgPwd.setError("Please enter right password");

                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(email,password,clgPwd);
                }
            }
        });

    }

    void loginUser(String email, String password,String clgPwd) {
        authProfile.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    //get Instance of the current user
                    FirebaseUser firebaseUser = authProfile.getCurrentUser();

                    //check if Email is verified before user can access their profile
                    if(firebaseUser.isEmailVerified()){
                        if (clgPwd.equals("Info12")) {
                            Toast.makeText(Teacher_Login.this, "You are Logged in now.", Toast.LENGTH_SHORT).show();
                            //Start  the UserprofileActivity
                            startActivity(new Intent(Teacher_Login.this, AdminActivity.class));
                            finish(); //close
                        }else {
                            mClgPwd.setError("Please enter right password.");
                        }
                    }
                    //Open User Profile
                    else{
                        firebaseUser.sendEmailVerification();
                        authProfile.signOut(); //sign out user
                        showAlertDialog();

                    }
                }else {
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        mLoginEmail.setError("User does not exists or is no longer valid. Please register again.");
                        progressBar.setVisibility(View.GONE);
                        return;
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        mLoginEmail.setError("Invalid credentials. Kindly,check and re-enter.");
                        progressBar.setVisibility(View.GONE);
                        return;
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(Teacher_Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void showAlertDialog() {
        //setup the alert builder
        AlertDialog.Builder builder=new AlertDialog.Builder(Teacher_Login.this);
        builder.setTitle("Email Not Verified");
        builder.setMessage("Please verify your email now. You can not login without email verification.");

        //open the email app if user clicks/tap continue button
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               Intent intent=new Intent(Intent.ACTION_MAIN);
               intent.addCategory(Intent.CATEGORY_APP_EMAIL);
               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //To Email app in new window and not within our app
                startActivity(intent);
            }
        });
        //Open User Profile
        //Create the AlertDialog
        AlertDialog alertDialog=builder.create();

        //show the alertDialog
        alertDialog.show();
    }

    //check if user is already logged in.In such case, straightaway take the user user's profile
    @Override
    protected void onStart(){
        super.onStart();
        if(authProfile.getCurrentUser() != null){
            FirebaseUser firebaseUser = authProfile.getCurrentUser();
            if(firebaseUser.isEmailVerified()) {
                mLoginEmail.setVisibility(View.GONE);
                mLoginPwd.setVisibility(View.GONE);
                loginPwdLinear.setVisibility(View.GONE);
                mRegister.setVisibility(View.GONE);
                mForgotPswBtn.setVisibility(View.GONE);
                loginprofileTeacher.setVisibility(View.VISIBLE);
                imageViewShowHidePwd.setVisibility(View.GONE);
                mClgPwd=findViewById(R.id.clg_password_teacher);
                mLoginBtn = findViewById(R.id.login_loginBtn_teacher);
                mLoginBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String clgPwd=mClgPwd.getText().toString().trim();
                        if (clgPwd.equals("Info12")) {
                            Toast.makeText(Teacher_Login.this, "You are Logged in now.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Teacher_Login.this, AdminActivity.class));
                            finish(); //close
                        } else {
                            mClgPwd.setError("Please enter right password.");
                        }
                    }
                });


            }
        }else{
            Toast.makeText(this, "You can login now!", Toast.LENGTH_SHORT).show();
        }

    }
}