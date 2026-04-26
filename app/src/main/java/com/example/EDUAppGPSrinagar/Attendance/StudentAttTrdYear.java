package com.example.EDUAppGPSrinagar.Attendance;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
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
import java.util.Calendar;
import java.util.List;

public class StudentAttTrdYear extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private String subname,sem;
    private Toolbar toolbar;
    private CalendarView calendarView;
    private String stringDate;
    private MyCalendar calendar;
    private List<StudentModel>list;
    private ProgressBar progressBar;
    private StudentAttAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_att_trd_year);
        subname=getIntent().getStringExtra("subname");
        sem=getIntent().getStringExtra("sem");
        calendarView=findViewById(R.id.calendarAttTrd);

        toolbar =findViewById(R.id.attTrdStudentToolbar);
        TextView textView=toolbar.findViewById((R.id.attTrdStudentToolbar_textView));
        ImageButton imageButton=toolbar.findViewById(R.id.attTrdStudentToolbar_btn);
        textView.setText(subname);
        imageButton.setOnClickListener(v -> onBackPressed());

        recyclerView=findViewById(R.id.studentRecyclerAttTrd);
        progressBar=findViewById(R.id.attTrdPBStudent);

//        date=calendar.getDate().toString();
        reference=FirebaseDatabase.getInstance().getReference("Attendance").child("2021").child(sem).child(subname);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar=Calendar.getInstance();
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                stringDate= DateFormat.format("dd-MM-yyyy",calendar).toString();
                Toast.makeText(StudentAttTrdYear.this, stringDate, Toast.LENGTH_SHORT).show();
                clickCalendar();
            }
        });
    }

    private void clickCalendar() {
        reference.child(stringDate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list=new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    StudentModel item=snapshot.getValue(StudentModel.class);
                    list.add(item);
                }
                adapter=new StudentAttAdapter(list, StudentAttTrdYear.this);
                adapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(StudentAttTrdYear.this));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(StudentAttTrdYear.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
//    private void setToolbar() {
//        toolbar=findViewById(R.id.attendance_toolbar);
//        TextView title=toolbar.findViewById(R.id.title_toolbar);
//        subtitle=toolbar.findViewById(R.id.subtitle_toolbar);
//        ImageButton back=toolbar.findViewById(R.id.back);
//        title.setText(subname);
//        subtitle.setText(stringDate);
//        back.setOnClickListener(v -> onBackPressed());
////        toolbar.inflateMenu(R.menu.student__menu);
////        toolbar.setOnMenuItemClickListener(menuItem -> onMenuItemClick(menuItem));
//    }
//    private boolean onMenuItemClick(MenuItem menuItem){
//        if(menuItem.getItemId()==R.id.add_calendar){
//            showCalendar();
//        }
//        return true;
//    }
//
//    private void showCalendar() {
//        MyCalendar calendar=new MyCalendar();
//        calendar.show(getSupportFragmentManager(),"");
//        calendar.setOnCalendarOkClickListener(this::onCalendarOkClicked);
//    }
//
//    private void onCalendarOkClicked(int year, int month, int day) {
//        calendar.setDate(year,month,day);
//        subtitle.setText(calendar.getDate());
//        date=calendar.getDate().toString();
//
//    }
}