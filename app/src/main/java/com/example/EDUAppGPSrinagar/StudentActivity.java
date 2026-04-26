package com.example.EDUAppGPSrinagar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.EDUAppGPSrinagar.Assignment.Student_Assignment;
import com.example.EDUAppGPSrinagar.Attendance.StudentAttActivity;
import com.example.EDUAppGPSrinagar.Faculty.Student_Faculty;
import com.example.EDUAppGPSrinagar.Login_and_Register.ChangePasswordActivity;
import com.example.EDUAppGPSrinagar.Login_and_Register.Login;
import com.example.EDUAppGPSrinagar.Login_and_Register.UpdateEmailActivity;
import com.example.EDUAppGPSrinagar.Login_and_Register.UpdateProfileActivity;
import com.example.EDUAppGPSrinagar.Login_and_Register.UserProfileActivity;
import com.example.EDUAppGPSrinagar.Marks.Student_SndYearSubject;
import com.example.EDUAppGPSrinagar.Marks.Student_StudentMarksActivity;
import com.example.EDUAppGPSrinagar.Notes.Student_Notes;
import com.example.EDUAppGPSrinagar.Notice.Student_Notice;
import com.example.EDUAppGPSrinagar.Paper.Student_Papers;
import com.example.EDUAppGPSrinagar.Timetable.Student_Timetable;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class StudentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private CardView studentNotice,studentMarks,studentAttendance,studentPapers,studentNotes,studentAssignments,studentTimetable,studentFaculty,studentResult;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        //navigation drawer
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.studentToolBar);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(StudentActivity.this);

        TextView animatedText = findViewById(R.id.animated_student_text);
        Animation rollRightAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        animatedText.startAnimation(rollRightAnimation);

        studentNotice=findViewById(R.id.studentNotice);
        studentAssignments=findViewById(R.id.studentAssignment);
        studentAttendance=findViewById(R.id.studentAttendance);
        studentPapers=findViewById(R.id.studentPaper);
        studentNotes=findViewById(R.id.studentNotes);
        studentTimetable=findViewById(R.id.studentTimeTable);
        studentFaculty=findViewById(R.id.studentFaculty);
        studentMarks=findViewById(R.id.studentMarks);
        studentResult=findViewById(R.id.studentResult);

        studentNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.studentNotice) {
                    Intent intent = new Intent(StudentActivity.this, Student_Notice.class);
                    startActivity(intent);
                }
            }
        });
        studentAssignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentActivity.this,Student_Assignment.class);
                startActivity(intent);
            }
        });
        studentNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentActivity.this, Student_Notes.class);
                startActivity(intent);
            }
        });
        studentFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentActivity.this, Student_Faculty.class);
                startActivity(intent);
            }
        });
        studentPapers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentActivity.this, Student_Papers.class);
                startActivity(intent);
            }
        });
        studentMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentActivity.this, Student_StudentMarksActivity.class);
                startActivity(intent);
            }
        });
        studentAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentActivity.this, StudentAttActivity.class);
                startActivity(intent);
            }
        });
        studentTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentActivity.this, Student_Timetable.class);
                startActivity(intent);
            }
        });

        studentResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri  =Uri.parse("https://ubterex.in/Student/Login.aspx");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
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
        int id = item.getItemId();
        if (id == R.id.menu_refresh) {
            //Refresh Activity
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        } else if (id == R.id.menu_update_profile) {
            Intent intent = new Intent(StudentActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.menu_update_email) {
            Intent intent = new Intent(StudentActivity.this, UpdateEmailActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.menu_settings) {
            Toast.makeText(this, "menu_settings", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_change_password) {
            Intent intent = new Intent(StudentActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        }/*else if (id == R.id.menu_delete_profile) {
            Intent intent=new Intent(UserProfileActivity.this,DeleteProfileActivity.class);
            startActivity(intent);
        }*/ else if (id == R.id.menu_logout) {
            FirebaseAuth authProfile = FirebaseAuth.getInstance();
            authProfile.signOut();
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StudentActivity.this, Login.class);

            //Clear stack to prevent user coming back to UserProfileActivity on pressing back button after Logging out
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}