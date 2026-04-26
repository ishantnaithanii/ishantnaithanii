package com.example.EDUAppGPSrinagar.Assignment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.EDUAppGPSrinagar.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Student_Assignment extends AppCompatActivity {
    private RecyclerView studentAssignmentRecycler;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private DatabaseReference reference;
    private List<StudentAssignmentData>list;
    private StudentAssignmentAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_assignment);

        //toolbar
        toolbar=findViewById(R.id.asgToolBarStudent);
        TextView textView=toolbar.findViewById((R.id.asgToolbartvStudent));
        ImageButton imageButton=toolbar.findViewById(R.id.asgToolbaribStudent);
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());


        progressBar=findViewById(R.id.studentAssignmentPB);

        studentAssignmentRecycler=findViewById(R.id.studentAssignmentRecycler);
        reference= FirebaseDatabase.getInstance().getReference().child("Assignments");

        getData();
    }

    private void getData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list=new ArrayList<>();
                for(DataSnapshot  snapshot : dataSnapshot.getChildren()){
                    StudentAssignmentData data=snapshot.getValue(StudentAssignmentData.class);
                    list.add(data);
                }
                adapter=new StudentAssignmentAdapter(Student_Assignment.this,list);
                adapter.notifyDataSetChanged();
                studentAssignmentRecycler.setLayoutManager(new LinearLayoutManager(Student_Assignment.this));
                studentAssignmentRecycler.setHasFixedSize(true);
                studentAssignmentRecycler.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Student_Assignment.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}