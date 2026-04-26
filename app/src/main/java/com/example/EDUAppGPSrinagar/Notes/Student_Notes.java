package com.example.EDUAppGPSrinagar.Notes;

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

import com.example.EDUAppGPSrinagar.Assignment.StudentAssignmentAdapter;
import com.example.EDUAppGPSrinagar.Assignment.StudentAssignmentData;
import com.example.EDUAppGPSrinagar.Assignment.Student_Assignment;
import com.example.EDUAppGPSrinagar.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Student_Notes extends AppCompatActivity {
    private RecyclerView studentNotesRecycler;
    private ProgressBar progressBar;
    private DatabaseReference reference;
    private List<StudentNotesData> list;
    private Toolbar notesToolbar;
    private StudentNotesAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_notes);

        studentNotesRecycler=findViewById(R.id.studentNotesRecycler);
        progressBar=findViewById(R.id.studentNotesPB);

        notesToolbar =findViewById(R.id.notesToolBarStudent);
        TextView textView=notesToolbar.findViewById((R.id.notesToolbartvStudent));
        ImageButton imageButton=notesToolbar.findViewById(R.id.notesToolbaribStudent);
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());

        reference= FirebaseDatabase.getInstance().getReference().child("Notes");
        getData();
    }

    private void getData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list=new ArrayList<>();
                for(DataSnapshot  snapshot : dataSnapshot.getChildren()){
                    StudentNotesData data=snapshot.getValue(StudentNotesData.class);
                    list.add(data);
                }
                adapter=new StudentNotesAdapter(Student_Notes.this,list);
                adapter.notifyDataSetChanged();
                studentNotesRecycler.setLayoutManager(new LinearLayoutManager(Student_Notes.this));
                studentNotesRecycler.setHasFixedSize(true);
                studentNotesRecycler.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Student_Notes.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}