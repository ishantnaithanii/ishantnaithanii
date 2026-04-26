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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.EDUAppGPSrinagar.R;

import java.util.ArrayList;
import java.util.List;

public class ReportTrdYear extends AppCompatActivity implements SubjectListener{
    private RecyclerView recyclerView5,recyclerView6,recyclerView5Mid,recyclerView5Ct2,recyclerView6Mid,recyclerView6Ct2;
    private List<SubjectModel> myModelList;
    private Toolbar toolbar;
    private SubjectAdapter subjectAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_trd_year);
        toolbar=findViewById(R.id.trdYearToolbarReport);
        TextView textView=toolbar.findViewById((R.id.trdYearToolbar_textViewReport));
        ImageButton imageButton=toolbar.findViewById(R.id.trdYearToolbar_btnReport);
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());


        displayItems5Sem();
        displayItemsCt25Sem();
        displayItemsMid5Sem();
        displayItems6Sem();
        displayItemsCt26Sem();
        displayItemsMid6Sem();
    }
    private void displayItemsCt26Sem() {
        recyclerView6Ct2=findViewById(R.id.recyclerCt2Sem6SubReport);
        recyclerView6Ct2.setHasFixedSize(true);
        recyclerView6Ct2.setLayoutManager(new GridLayoutManager(this,1));
        myModelList=new ArrayList<>();
        myModelList.add(new SubjectModel("Data Mining And Warehousing","Ct2","6th"));
        myModelList.add(new SubjectModel("Multimedia Application","Ct2","6th"));
        myModelList.add(new SubjectModel("Advance Web Programming","Ct2","6th"));
        myModelList.add(new SubjectModel(".Net Programming","Ct2","6th"));
        myModelList.add(new SubjectModel("Employability Skill","Ct2","6th"));
        myModelList.add(new SubjectModel("Major Project","Ct2","6th"));
        subjectAdapter=new SubjectAdapter(this,myModelList,this);
        recyclerView6Ct2.setAdapter(subjectAdapter);
    }
    private void displayItemsMid6Sem() {
        recyclerView6Mid=findViewById(R.id.recyclerMidSem6SubReport);
        recyclerView6Mid.setHasFixedSize(true);
        recyclerView6Mid.setLayoutManager(new GridLayoutManager(this,1));
        myModelList=new ArrayList<>();
        myModelList.add(new SubjectModel("Data Mining And Warehousing","Mid","6th"));
        myModelList.add(new SubjectModel("Multimedia Application","Mid","6th"));
        myModelList.add(new SubjectModel("Advance Web Programming","Mid","6th"));
        myModelList.add(new SubjectModel(".Net Programming","Mid","6th"));
        myModelList.add(new SubjectModel("Employability Skill","Mid","6th"));
        myModelList.add(new SubjectModel("Major Project","Mid","6th"));
        subjectAdapter=new SubjectAdapter(this,myModelList,this);
        recyclerView6Mid.setAdapter(subjectAdapter);
    }

    private void displayItemsCt25Sem() {
        recyclerView5Ct2=findViewById(R.id.recyclerCt2Sem5SubReport);
        recyclerView5Ct2.setHasFixedSize(true);
        recyclerView5Ct2.setLayoutManager(new GridLayoutManager(this,1));
        myModelList=new ArrayList<>();
        myModelList.add(new SubjectModel("Android Application Development","Ct2","5th"));
        myModelList.add(new SubjectModel("E - Commerce","Ct2","5th"));
        myModelList.add(new SubjectModel("Java Programming","Ct2","5th"));
        myModelList.add(new SubjectModel("Software Engineering","Ct2","5th"));
        myModelList.add(new SubjectModel("Minor Project","Ct2","5th"));
        subjectAdapter=new SubjectAdapter(this,myModelList,this);
        recyclerView5Ct2.setAdapter(subjectAdapter);
    }
    private void displayItemsMid5Sem() {
        recyclerView5Mid=findViewById(R.id.recyclerMidSem5SubReport);
        recyclerView5Mid.setHasFixedSize(true);
        recyclerView5Mid.setLayoutManager(new GridLayoutManager(this,1));
        myModelList=new ArrayList<>();
        myModelList.add(new SubjectModel("Android Application Development","Mid","5th"));
        myModelList.add(new SubjectModel("E - Commerce","Mid","5th"));
        myModelList.add(new SubjectModel("Java Programming","Mid","5th"));
        myModelList.add(new SubjectModel("Software Engineering","Mid","5th"));
        myModelList.add(new SubjectModel("Minor Project","Mid","5th"));
        subjectAdapter=new SubjectAdapter(this,myModelList,this);
        recyclerView5Mid.setAdapter(subjectAdapter);
    }

    private void displayItems6Sem() {
        recyclerView6=findViewById(R.id.recyclerCt1Sem6SubReport);
        recyclerView6.setHasFixedSize(true);
        recyclerView6.setLayoutManager(new GridLayoutManager(this,1));
        myModelList=new ArrayList<>();
        myModelList.add(new SubjectModel("Data Mining And Warehousing","Ct1","6th"));
        myModelList.add(new SubjectModel("Multimedia Application","Ct1","6th"));
        myModelList.add(new SubjectModel("Advance Web Programming","Ct1","6th"));
        myModelList.add(new SubjectModel(".Net Programming","Ct1","6th"));
        myModelList.add(new SubjectModel("Employability Skill","Ct1","6th"));
        myModelList.add(new SubjectModel("Major Project","Ct1","6th"));
        subjectAdapter=new SubjectAdapter(this,myModelList,this);
        recyclerView6.setAdapter(subjectAdapter);

    }

    private void displayItems5Sem() {
        recyclerView5=findViewById(R.id.recyclerCt1Sem5SubReport);
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
        Intent intent=new Intent(ReportTrdYear.this, ReportMarksTrd.class);
        intent.putExtra("subname",subjectModel.getSubject());
        intent.putExtra("type",subjectModel.getType());
        intent.putExtra("sem",subjectModel.getSem());
        startActivity(intent);
    }
}