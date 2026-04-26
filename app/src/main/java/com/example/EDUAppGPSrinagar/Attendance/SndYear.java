package com.example.EDUAppGPSrinagar.Attendance;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SndYear extends AppCompatActivity {
    private CalendarView calendarView;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private FirebaseDatabase database;
    private String stringDate;
    private List<StudentModel> list;
    private String subname,sem;
    DatabaseReference reference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snd_year);
        calendarView=findViewById(R.id.calendarView);
        recyclerView=findViewById(R.id.recyclerStudent);

        subname=getIntent().getStringExtra("subname");
        sem=getIntent().getStringExtra("sem");

        toolbar =findViewById(R.id.attSndStudentToolbar);
        TextView textView=toolbar.findViewById((R.id.attSndStudentToolbar_textView));
        ImageButton imageButton=toolbar.findViewById(R.id.attSndStudentToolbar_btn);
        textView.setText(subname);
        imageButton.setOnClickListener(v -> onBackPressed());

        progressBar=findViewById(R.id.attSndStudentPB);

        try {
            database=FirebaseDatabase.getInstance();
        }catch (Exception e){
            Log.e("Firebase Error","Error initializing Firebase Database");
        }

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar=Calendar.getInstance();
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                stringDate= DateFormat.format("dd-MM-yyyy",calendar).toString();
                Toast.makeText(SndYear.this, stringDate, Toast.LENGTH_SHORT).show();
                clickCalendar();
            }
        });
        reference=database.getReference("Attendance").child("2022").child(sem).child(subname);
    }

    private void clickCalendar() {
        reference.child(stringDate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()){
                    StudentModel item=childSnapshot.getValue(StudentModel.class);
                    list.add(item);
                }
                StudentAttAdapter adapter=new StudentAttAdapter(list,SndYear.this);
                adapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(SndYear.this));
                recyclerView.setHasFixedSize(true);
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