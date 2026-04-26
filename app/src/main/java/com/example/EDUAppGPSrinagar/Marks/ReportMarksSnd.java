package com.example.EDUAppGPSrinagar.Marks;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.EDUAppGPSrinagar.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReportMarksSnd extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private List<Student2Model>list;
    private String subname,sem,type;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_marks_snd);

        subname=getIntent().getStringExtra("subname");
        sem=getIntent().getStringExtra("sem");
        type=getIntent().getStringExtra("type");
        recyclerView=findViewById(R.id.recyclerMarks2Report);

        progressBar=findViewById(R.id.studentNameSndPBReport);

        //toolbar
        toolbar=findViewById(R.id.studentMarksToolbarReport);
        TextView textView=toolbar.findViewById((R.id.studentMarksToolbar_textViewReport));
        ImageButton imageButton=toolbar.findViewById(R.id.studentMarksToolbar_btnReport);
        textView.setText(subname+" Marks");
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());

        try {
            database=FirebaseDatabase.getInstance();
        }catch (Exception e){
            Log.e("Firebase Error","Error initializing Firebase Database");
        }
        Query query=database.getReference("Marks").child(sem).child(type).child(subname).orderByKey();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()){
                    Student2Model item=childSnapshot.getValue(Student2Model.class);
                    list.add(item);
                }
                ReportAdapter adapter=new ReportAdapter(ReportMarksSnd.this,list,type,subname,sem);
                recyclerView.setLayoutManager(new LinearLayoutManager(ReportMarksSnd.this));
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Log.w("Firebase Error","Failed to read Value",error.toException());
            }
        });
    }

}