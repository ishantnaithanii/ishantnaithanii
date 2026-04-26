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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.EDUAppGPSrinagar.Marks.SubjectAdapter;
import com.example.EDUAppGPSrinagar.Marks.SubjectListener;
import com.example.EDUAppGPSrinagar.Marks.SubjectModel;
import com.example.EDUAppGPSrinagar.R;

import java.util.ArrayList;
import java.util.List;

public class StudentTrdSubject extends AppCompatActivity implements SubjectListener {
    private RecyclerView recyclerView5,recyclerView6;
    private List<SubjectModel> myModelList;
    private Toolbar toolbar;
    private SubjectAdapter subjectAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_trd_subject);

        toolbar=findViewById(R.id.trdYearToolbarAttStudent);
        TextView textView=toolbar.findViewById((R.id.trdYearToolbar_textViewAttStudent));
        ImageButton imageButton=toolbar.findViewById(R.id.trdYearToolbar_btnAttStudent);
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());


        displayItems5Sem();
        displayItems6Sem();
    }
    private void displayItems6Sem() {
        recyclerView6=findViewById(R.id.recyclerAttSem6SubStudent);
        recyclerView6.setHasFixedSize(true);
        recyclerView6.setLayoutManager(new GridLayoutManager(this,1));
        myModelList=new ArrayList<>();
        myModelList.add(new SubjectModel("Data Mining And Warehousing","Ct1","6th"));
        myModelList.add(new SubjectModel("Multimedia Application","Ct1","6th"));
        myModelList.add(new SubjectModel("Advance Web Programming","Ct1","6th"));
        myModelList.add(new SubjectModel(".Net Programming","Ct1","6th"));
        myModelList.add(new SubjectModel("Employability Skill","Ct1","6th"));
        myModelList.add(new SubjectModel("Major Project","Ct1","6th"));
        subjectAdapter=new SubjectAdapter(StudentTrdSubject.this,myModelList,this);
        recyclerView6.setAdapter(subjectAdapter);

    }

    private void displayItems5Sem() {
        recyclerView5=findViewById(R.id.recyclerAttSem5SubStudent);
        recyclerView5.setHasFixedSize(true);
        recyclerView5.setLayoutManager(new GridLayoutManager(this,1));
        myModelList=new ArrayList<>();
        myModelList.add(new SubjectModel("Android Application Development","Ct1","5th"));
        myModelList.add(new SubjectModel("E - Commerce","Ct1","5th"));
        myModelList.add(new SubjectModel("Java Programming","Ct1","5th"));
        myModelList.add(new SubjectModel("Software Engineering","Ct1","5th"));
        myModelList.add(new SubjectModel("Minor Project","Ct1","5th"));
        subjectAdapter=new SubjectAdapter(this,myModelList,this);
        recyclerView5.setAdapter(subjectAdapter);

    }

    @Override
    public void onItemClicked(SubjectModel subjectModel) {
        Intent intent=new Intent(StudentTrdSubject.this, StudentAttTrdYear.class);
        intent.putExtra("subname",subjectModel.getSubject());
        intent.putExtra("sem",subjectModel.getSem());
        startActivity(intent);
    }
}