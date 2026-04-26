package com.example.EDUAppGPSrinagar.Attendance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.EDUAppGPSrinagar.Marks.Student_SndYearSubject;
import com.example.EDUAppGPSrinagar.Marks.Student_StudentMarksActivity;
import com.example.EDUAppGPSrinagar.Marks.Student_TrdYearSubject;
import com.example.EDUAppGPSrinagar.R;

public class StudentAttActivity extends AppCompatActivity {
    private CardView sndCardView,trdCardView;
    private Toolbar toolbar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_att);
        sndCardView=findViewById(R.id.sndYearAttStudentAc);
        trdCardView=findViewById(R.id.trdYearAttStudentAc);

        toolbar=findViewById(R.id.attToolBarStudent);
        TextView textView=toolbar.findViewById((R.id.attToolbartvStudent));
        ImageButton imageButton=toolbar.findViewById(R.id.attToolbaribStudent);
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());

        sndCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentAttActivity.this, StudentSndSubject.class));
            }
        });
        trdCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentAttActivity.this, StudentTrdSubject.class));
            }
        });

    }
}