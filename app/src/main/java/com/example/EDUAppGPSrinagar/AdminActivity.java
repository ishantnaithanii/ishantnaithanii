package com.example.EDUAppGPSrinagar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.EDUAppGPSrinagar.Faculty.UpdateFaculty;
import com.example.EDUAppGPSrinagar.Login_and_Register.ChangePasswordActivity;
import com.example.EDUAppGPSrinagar.Login_and_Register.Login;
import com.example.EDUAppGPSrinagar.Login_and_Register.Teacher.Teacher_Login;
import com.example.EDUAppGPSrinagar.Login_and_Register.UpdateEmailActivity;
import com.example.EDUAppGPSrinagar.Login_and_Register.UpdateProfileActivity;
import com.example.EDUAppGPSrinagar.Notice.DeleteNotice;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    CardView uploadNotice, uploadPaper,uploadMarks, uploadAssignment, uploadNotes, uploadAttendance, noticeDelete,uploadFaculty,uploadTimetable;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        uploadNotice = findViewById(R.id.notice);
        uploadPaper = findViewById(R.id.paper);
        uploadNotes = findViewById(R.id.notes);
        uploadAssignment = findViewById(R.id.assignment);
        toolbar = findViewById(R.id.adminToolBar);
        uploadAttendance = findViewById(R.id.attendance);
        noticeDelete = findViewById(R.id.noticeDelete);
        uploadFaculty=findViewById(R.id.faculty);
        uploadTimetable=findViewById(R.id.timeTable);
        uploadMarks=findViewById(R.id.marks);

        setSupportActionBar(toolbar);

        TextView animatedText = findViewById(R.id.animated_text);
        Animation rollRightAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        animatedText.startAnimation(rollRightAnimation);

        uploadNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.notice) {
                    Intent intent = new Intent(AdminActivity.this, UploadNotice.class);
                    startActivity(intent);
                }
            }
        });
        uploadPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.paper) {
                    Intent intent = new Intent(AdminActivity.this, UploadPapers.class);
                    startActivity(intent);
                }
            }
        });
        uploadNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.notes) {
                    Intent intent = new Intent(AdminActivity.this, UploadNotes.class);
                    startActivity(intent);
                }
            }
        });
        uploadAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.assignment) {
                    Intent intent = new Intent(AdminActivity.this, UploadAssignments.class);
                    startActivity(intent);
                }
            }
        });
        uploadAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.attendance) {
                    Intent intent = new Intent(AdminActivity.this, UploadAttendance.class);
                    startActivity(intent);
                }
            }
        });
        noticeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.noticeDelete) {
                    Intent intent = new Intent(AdminActivity.this, DeleteNotice.class);
                    startActivity(intent);
                }
            }
        });
        uploadFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.faculty) {
                    Intent intent = new Intent(AdminActivity.this, UpdateFaculty.class);
                    startActivity(intent);
                }
            }
        });
        uploadMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.marks) {
                    Intent intent = new Intent(AdminActivity.this, UploadMarks.class);
                    startActivity(intent);
                }
            }
        });
        uploadTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, Upload_TimeTable.class);
                startActivity(intent);
            }
        });

        drawerLayout=findViewById(R.id.student_drawerLayout);
        navigationView=findViewById(R.id.nav_view_student);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(AdminActivity.this);
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id == R.id.menu_refresh){
            //Refresh Activity
            startActivity(getIntent());
            finish();
            overridePendingTransition(0,0);
        } else if (id == R.id.menu_update_profile) {
            Intent intent=new Intent(AdminActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        }else if (id == R.id.menu_update_email) {
            Intent intent=new Intent(AdminActivity.this, UpdateEmailActivity.class);
            startActivity(intent);
            finish();
        }else if (id == R.id.menu_settings) {
            Toast.makeText(this, "menu_settings", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.menu_change_password) {
            Intent intent=new Intent(AdminActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        }/*else if (id == R.id.menu_delete_profile) {
            Intent intent=new Intent(UserProfileActivity.this,DeleteProfileActivity.class);
            startActivity(intent);
        }*/else if (id == R.id.menu_logout) {
            FirebaseAuth authProfile=FirebaseAuth.getInstance();
            authProfile.signOut();
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(AdminActivity.this, Teacher_Login.class);

            //Clear stack to prevent user coming back to UserProfileActivity on pressing back button after Logging out
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }



}