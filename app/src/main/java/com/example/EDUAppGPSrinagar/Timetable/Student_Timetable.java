package com.example.EDUAppGPSrinagar.Timetable;

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

import com.example.EDUAppGPSrinagar.Notice.NoticeData;
import com.example.EDUAppGPSrinagar.Notice.StudentNoticeAdapter;
import com.example.EDUAppGPSrinagar.Notice.Student_Notice;
import com.example.EDUAppGPSrinagar.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Student_Timetable extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<TimetableData> list;
    private Toolbar toolbar;
    private TimetableAdapter adapter;
    private DatabaseReference reference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_timetable);
        recyclerView=findViewById(R.id.recyclerViewTimetable);
        progressBar=findViewById(R.id.studentTimetablePB);

        //toolbar
        toolbar = findViewById(R.id.timetableToolbarStudent);
        TextView textView = toolbar.findViewById((R.id.timetableToolbartvStudent));
        ImageButton imageButton = toolbar.findViewById(R.id.timetableToolbaribStudent);
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());

        reference= FirebaseDatabase.getInstance().getReference().child("Timetable");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getNotice();
    }
    private void getNotice() {
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list=new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    TimetableData data=snapshot.getValue(TimetableData.class);
                    list.add(data);
                }
                adapter=new TimetableAdapter(list, Student_Timetable.this);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Student_Timetable.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}