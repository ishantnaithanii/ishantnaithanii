package com.example.EDUAppGPSrinagar.Notice;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.EDUAppGPSrinagar.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Student_Notice extends AppCompatActivity {
    private RecyclerView studentNoticeRecycler;
    private ProgressBar progressBar;
    private ArrayList<NoticeData> list;
    private Toolbar toolbar;
    private StudentNoticeAdapter adapter;
    private DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_notice);

        studentNoticeRecycler=findViewById(R.id.studentNoticeRecycler);
        progressBar=findViewById(R.id.studentNoticePB);
        //toolbar
        toolbar = findViewById(R.id.noticeToolbarStudent);
        TextView textView = toolbar.findViewById((R.id.noticeToolbartvStudent));
        ImageButton imageButton = toolbar.findViewById(R.id.noticeToolbaribStudent);
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());

        reference= FirebaseDatabase.getInstance().getReference().child("Notice");
        studentNoticeRecycler.setLayoutManager(new LinearLayoutManager(this));
        studentNoticeRecycler.setHasFixedSize(true);

        getNotice();

    }

    private void getNotice() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list=new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    NoticeData data=snapshot.getValue(NoticeData.class);
                    data.setKey(snapshot.getKey());
                    list.add(data);
                }
                adapter=new StudentNoticeAdapter(Student_Notice.this,list);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                studentNoticeRecycler.setAdapter(adapter);
                Toast.makeText(Student_Notice.this, "Please Click image for Zoom in and Zoom out", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(Student_Notice.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}