package com.example.EDUAppGPSrinagar.Login_and_Register;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.EDUAppGPSrinagar.R;
import com.example.EDUAppGPSrinagar.StudentActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateProfileActivity extends AppCompatActivity {
    private EditText editTextUpdateName,editTextUpdateMobile,editTextUpdateDob,editTextUpdateBranch;
    private RadioGroup radioGroupUpdateGender;
    private RadioButton radioButtonUpdateGender;
    private String textFullName,textDob,textBranch,textMobile,textGender;
    private FirebaseAuth authProfile;
    private ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        progressBar=findViewById(R.id.update_profile_progressBar);

        editTextUpdateName=findViewById(R.id.editText_update_profile_name);
        editTextUpdateBranch=findViewById(R.id.editText_update_profile_branch);
        editTextUpdateDob=findViewById(R.id.editText_update_profile_dob);
        editTextUpdateMobile=findViewById(R.id.editText_update_profile_mobile);
        radioGroupUpdateGender=findViewById(R.id.radio_group_update_gender);

        authProfile=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=authProfile.getCurrentUser();

        //toolbar
        Toolbar updateProfile_toolbar=findViewById(R.id.update_profile_Toolbar);
        TextView textView=updateProfile_toolbar.findViewById((R.id.update_profile_toolbar_textView));
        ImageButton imageButton=updateProfile_toolbar.findViewById(R.id.update_profile_toolbar_btn);
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateProfileActivity.this, StudentActivity.class));
                finish();
            }
        });

        //show Profile Method
        assert firebaseUser != null;
        showProfile(firebaseUser);

        //Upload Profile Pic
        Button buttonUploadProfile=findViewById(R.id.button_update_profile_pic);
        buttonUploadProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UpdateProfileActivity.this,UploadProfilePicActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Update Email
        Button buttonUploadEmail=findViewById(R.id.button_update_profile_email);
        buttonUploadEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UpdateProfileActivity.this,UpdateEmailActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Setting up DatePicker on editText
        editTextUpdateDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Extracting saved dd,m,yyyy into different variable by creating an array delimited by "/"
                String[] textSADoB = textDob.split("/");


                int day=Integer.parseInt(textSADoB[0]);
                int month=Integer.parseInt(textSADoB[1])-1; //to take care of month index starting from 0
                int year=Integer.parseInt(textSADoB[2]);

                DatePickerDialog picker;
                //DatePicker Dialog
                picker=new DatePickerDialog(UpdateProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                       editTextUpdateDob.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                },year,month,day);
                picker.show();
            }
        });

        //Update Profile
        Button buttonUpdateProfile =findViewById(R.id.button_update_profile);
        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(firebaseUser);
            }
        });

    }

    //update profile
    private void updateProfile(FirebaseUser firebaseUser) {
        int selectedGenderID=radioGroupUpdateGender.getCheckedRadioButtonId();
        radioButtonUpdateGender=findViewById(selectedGenderID);

        //Validate mobile number using Matcher and Pattern(Regular Expression)
        String mobileRegex="[6-9][0-9]{9}"; //First no. can be {6,8,9} and rest 9 nos. can be any no.
        Matcher mobileMatcher;
        Pattern mobilePattern=Pattern.compile(mobileRegex);
        mobileMatcher=mobilePattern.matcher(textMobile);

        if(TextUtils.isEmpty(textFullName)){
            editTextUpdateName.setError("Full Name is Required.");
        } else if (TextUtils.isEmpty(textDob)) {
            editTextUpdateDob.setText("DOB is Required");

        } else if (TextUtils.isEmpty(radioButtonUpdateGender.getText())) {
            radioButtonUpdateGender.setError("Gender is Required");
        } else if (TextUtils.isEmpty(textBranch)) {
            editTextUpdateBranch.setError("Branch is Required.");
        } else if (TextUtils.isEmpty(textMobile)) {
            editTextUpdateMobile.setError("Mobile Number is Required.");
        } else if (textMobile.length()!=10) {
            editTextUpdateMobile.setError("Mobile No. should be 10 digits.");
        } else if (!mobileMatcher.find()) {
            editTextUpdateMobile.setError("Mobile No. is not Valid.");
        } else{
            textGender=radioButtonUpdateGender.getText().toString();
            textFullName=editTextUpdateName.getText().toString();
            textBranch=editTextUpdateBranch.getText().toString();
            textDob=editTextUpdateDob.getText().toString();
            textMobile=editTextUpdateMobile.getText().toString();

            //Enter User Data in the Firebase Realtime Database. Set up dependencies
            ReadWriteUserDetails writeUserDetails=new ReadWriteUserDetails(textFullName,textMobile,textGender,textBranch,textDob);

            //Extract user reference from database for "Registered Users"
            DatabaseReference referenceProfile= FirebaseDatabase.getInstance().getReference("Registered Users");

            String userID=firebaseUser.getUid();

            referenceProfile.child(userID).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        //setting new displays Name
                        UserProfileChangeRequest profileUpdates=new UserProfileChangeRequest.Builder().
                                setDisplayName(textFullName).build();
                        firebaseUser.updateProfile(profileUpdates);

                        Toast.makeText(UpdateProfileActivity.this, "Updates Successful!", Toast.LENGTH_SHORT).show();

                        //Stop user for returning to UpdateProfileActivity on pressing back button and close activity
                        Intent intent= new Intent(UpdateProfileActivity.this, UserProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }else {
                        try {
                            throw task.getException();
                        }catch (Exception e){
                            Toast.makeText(UpdateProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    //fetch data from firebase and display
    private void showProfile(FirebaseUser firebaseUser) {
        String userIDofRegistered= firebaseUser.getUid();

        //Extracting User reference from database for "Registered Users"
        DatabaseReference referenceProfile= FirebaseDatabase.getInstance().getReference("Registered Users");

        progressBar.setVisibility(View.VISIBLE);

        referenceProfile.child(userIDofRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails =snapshot.getValue(ReadWriteUserDetails.class);
                if(readUserDetails != null){
                    textFullName = readUserDetails.fullName;
                    textBranch=readUserDetails.branch;
                    textDob=readUserDetails.dob;
                    textMobile=readUserDetails.mobile;
                    textGender=readUserDetails.gender;

                    editTextUpdateName.setText(textFullName);
                    editTextUpdateBranch.setText(textBranch);
                    editTextUpdateDob.setText(textDob);
                    editTextUpdateMobile.setText(textMobile);

                   // show Gender through Radio Button
                    if(textGender.equals("Male")){
                        radioButtonUpdateGender=findViewById(R.id.radio_male);
                    }else{
                        radioButtonUpdateGender=findViewById(R.id.radio_female);
                    }
                    radioButtonUpdateGender.setChecked(true);

                }else{
                    Toast.makeText(UpdateProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    //Creating ActionBar Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate menu items
        getMenuInflater().inflate(R.menu.common_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //When any menu item is selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();

        if(id == R.id.menu_refresh){
            //Refresh Activity
            startActivity(getIntent());
            finish();
            overridePendingTransition(0,0);
        } else if (id == R.id.menu_update_profile) {
            Intent intent=new Intent(UpdateProfileActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        }else if (id == R.id.menu_update_email) {
            Intent intent=new Intent(this, UpdateEmailActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.menu_settings) {
            Toast.makeText(this, "menu_settings", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.menu_change_password) {
            Intent intent=new Intent(UpdateProfileActivity.this,ChangePasswordActivity.class);
            startActivity(intent);
            finish();
        }/*else if (id == R.id.menu_delete_profile) {
            Intent intent=new Intent(UserProfileActivity.this,DeleteProfileActivity.class);
            startActivity(intent);
        }*/else if (id == R.id.menu_logout) {
            authProfile.signOut();
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(this, Login.class);

            //Clear stack to prevent user coming back to UserProfileActivity on pressing back button after Logging out
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}