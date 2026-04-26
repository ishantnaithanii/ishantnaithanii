package com.example.EDUAppGPSrinagar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.EDUAppGPSrinagar.Marks.ReportSndYear;
import com.example.EDUAppGPSrinagar.Marks.ReportTrdYear;
import com.example.EDUAppGPSrinagar.Marks.SndYearSubject;
import com.example.EDUAppGPSrinagar.Marks.Student_SndYearSubject;
import com.example.EDUAppGPSrinagar.Marks.Student_TrdYearSubject;
import com.example.EDUAppGPSrinagar.Marks.TrdYearSubject;

public class UploadMarks extends AppCompatActivity {
    private CardView sndYearCard,trdYearCard,sndYearCardReport,trdYearCardReport;
    private Toolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_marks);
        sndYearCard=findViewById(R.id.sndYearMarks);
        trdYearCard=findViewById(R.id.trdYearMarks);
        sndYearCardReport=findViewById(R.id.sndYearMarksReport);
        trdYearCardReport=findViewById(R.id.trdYearMarksReport);

        toolbar=findViewById(R.id.marksToolBar);
        TextView textView=toolbar.findViewById((R.id.marksToolbartv));
        ImageButton imageButton=toolbar.findViewById(R.id.marksToolbarib);
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());

        sndYearCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UploadMarks.this,SndYearSubject.class));
            }
        });
        trdYearCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UploadMarks.this, TrdYearSubject.class));
            }
        });
        sndYearCardReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UploadMarks.this, ReportSndYear.class));
            }
        });
        trdYearCardReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UploadMarks.this, ReportTrdYear.class));
            }
        });
    }
}