package com.example.EDUAppGPSrinagar.Paper;

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

import com.example.EDUAppGPSrinagar.Notes.StudentNotesAdapter;
import com.example.EDUAppGPSrinagar.Notes.StudentNotesData;
import com.example.EDUAppGPSrinagar.Notes.Student_Notes;
import com.example.EDUAppGPSrinagar.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Student_Papers extends AppCompatActivity {

    private RecyclerView studentPapersRecycler;
    private ProgressBar progressBar;
    private DatabaseReference reference;
    private Toolbar toolbar;
    private List<StudentPapersData> list;
    private StudentPapersAdapter adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_papers);

        toolbar=findViewById(R.id.paperToolBarStudent);
        TextView textView=toolbar.findViewById((R.id.paperToolbartvStudent));
        ImageButton imageButton=toolbar.findViewById(R.id.paperToolbaribStudent);
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());

        studentPapersRecycler=findViewById(R.id.studentPapersRecycler);
        progressBar=findViewById(R.id.studentPapersPB);
        reference= FirebaseDatabase.getInstance().getReference().child("Papers");
        getData();
    }

    private void getData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list=new ArrayList<>();
                for(DataSnapshot  snapshot : dataSnapshot.getChildren()){
                    StudentPapersData data=snapshot.getValue(StudentPapersData.class);
                    list.add(data);
                }
                adapter=new StudentPapersAdapter(Student_Papers.this,list);
                adapter.notifyDataSetChanged();
                studentPapersRecycler.setLayoutManager(new LinearLayoutManager(Student_Papers.this));
                studentPapersRecycler.setHasFixedSize(true);
                studentPapersRecycler.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Student_Papers.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}