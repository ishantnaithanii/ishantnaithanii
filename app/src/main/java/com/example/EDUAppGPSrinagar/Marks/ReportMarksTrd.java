package com.example.EDUAppGPSrinagar.Marks;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.EDUAppGPSrinagar.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReportMarksTrd extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private List<Student2Model> list;
    private String subname,sem,type;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_marks_trd);
        subname=getIntent().getStringExtra("subname");
        sem=getIntent().getStringExtra("sem");
        type=getIntent().getStringExtra("type");
        recyclerView=findViewById(R.id.recyclerMarksTrdReport);

        progressBar=findViewById(R.id.studentNameTrdPBReport);

        //toolbar
        toolbar=findViewById(R.id.studentTrdToolbarReport);
        TextView textView=toolbar.findViewById((R.id.studentTrdToolbar_textViewReport));
        ImageButton imageButton=toolbar.findViewById(R.id.studentTrdToolbar_btnReport);
        textView.setText(subname+" Marks");
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());

        try {
            database=FirebaseDatabase.getInstance();
        }catch (Exception e){
            Log.e("Firebase Error","Error initializing Firebase Database");
        }
        Query query= database.getReference("Marks").child(sem).child(type).child(subname);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()){
                    Student2Model item=childSnapshot.getValue(Student2Model.class);
                    list.add(item);
                }
                sortItemByRollNumber(list);
                ReportAdapter adapter=new ReportAdapter(ReportMarksTrd.this,list,type,subname,sem);
                recyclerView.setLayoutManager(new LinearLayoutManager(ReportMarksTrd.this));
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

    private void sortItemByRollNumber(List<Student2Model> list) {
        Collections.sort(list, new Comparator<Student2Model>() {
            @Override
            public int compare(Student2Model item1, Student2Model item2) {
                String s1=item1.getRoll();
                String s2=item2.getRoll();
                return s1.compareToIgnoreCase(s2);
            }
        });
    }

}