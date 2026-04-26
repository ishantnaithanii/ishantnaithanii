package com.example.EDUAppGPSrinagar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.EDUAppGPSrinagar.Attendance.SndYearAttSubject;
import com.example.EDUAppGPSrinagar.Attendance.StudentSndSubject;
import com.example.EDUAppGPSrinagar.Attendance.StudentTrdSubject;
import com.example.EDUAppGPSrinagar.Attendance.TrdYearAttSubject;


public class UploadAttendance extends AppCompatActivity {
    private CardView sndYearCard,trdYearCard,sndYearCardReport,trdYearCardReport;
    private Toolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_attendance);
        sndYearCard=findViewById(R.id.sndYearAtt);
        trdYearCard=findViewById(R.id.trdYearAtt);
        sndYearCardReport=findViewById(R.id.sndYearAttReport);
        trdYearCardReport=findViewById(R.id.trdYearAttReport);

        toolbar=findViewById(R.id.attToolBar);
        TextView textView=toolbar.findViewById((R.id.attToolbartv));
        ImageButton imageButton=toolbar.findViewById(R.id.attToolbarib);
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());

        sndYearCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UploadAttendance.this, SndYearAttSubject.class));
            }
        });
        trdYearCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UploadAttendance.this, TrdYearAttSubject.class));
            }
        });
        sndYearCardReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UploadAttendance.this, StudentSndSubject.class));
            }
        });
        trdYearCardReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UploadAttendance.this, StudentTrdSubject.class));
            }
        });
    }
}