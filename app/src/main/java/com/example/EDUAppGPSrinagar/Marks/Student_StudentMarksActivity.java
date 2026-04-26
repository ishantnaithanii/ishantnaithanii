package com.example.EDUAppGPSrinagar.Marks;

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

import com.example.EDUAppGPSrinagar.R;
import com.example.EDUAppGPSrinagar.UploadMarks;

public class Student_StudentMarksActivity extends AppCompatActivity {
    private CardView sndCardView,trdCardView;
    private Toolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_student_marks);
        sndCardView=findViewById(R.id.sndYearMarksStudent);
        trdCardView=findViewById(R.id.trdYearMarksStudent);

        toolbar=findViewById(R.id.marksToolBarStudent);
        TextView textView=toolbar.findViewById((R.id.marksToolbartvStudent));
        ImageButton imageButton=toolbar.findViewById(R.id.marksToolbaribStudent);
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());

        sndCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Student_StudentMarksActivity.this,Student_SndYearSubject.class));
            }
        });
        trdCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Student_StudentMarksActivity.this, Student_TrdYearSubject.class));
            }
        });

    }
}