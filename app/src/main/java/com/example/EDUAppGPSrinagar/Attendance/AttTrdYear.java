package com.example.EDUAppGPSrinagar.Attendance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.EDUAppGPSrinagar.Assignment.Student_Assignment;
import com.example.EDUAppGPSrinagar.NotificationHelper;
import com.example.EDUAppGPSrinagar.R;
import com.example.EDUAppGPSrinagar.UploadAssignments;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AttTrdYear extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<StudentModel> list;
    private StudentAdapter adapter;
    private Button sumbitBtn;
    private DatabaseReference reference;
    private TextView subtitle;
    private Toolbar toolbar;
    private String subname,sem,date;
    private MyCalendar calendar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_att_trd_year);
        calendar=new MyCalendar();
        subname=getIntent().getStringExtra("subname");
        sem=getIntent().getStringExtra("sem");

        setToolbar();

        recyclerView=findViewById(R.id.recyclerAttTrd);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        list=new ArrayList<>();
        list.add(new StudentModel("1","Rajeev Rawat ",""));
        list.add(new StudentModel("2","Abhishek Bhakuni",""));
        list.add(new StudentModel("3","Abhishek Miyan",""));
        list.add(new StudentModel("4","Alok Uniyal",""));
        list.add(new StudentModel("5","Akash Rawat",""));
        list.add(new StudentModel("6","Anirudh Bisht",""));
        list.add(new StudentModel("7","Ankush",""));
        list.add(new StudentModel("8","Ishant Naithani",""));
        list.add(new StudentModel("9","Km. Anjali",""));
        list.add(new StudentModel("10","Komal",""));
        list.add(new StudentModel("11","Pankaj Rawat",""));
        list.add(new StudentModel("12","Parth Gairola",""));
        list.add(new StudentModel("13","Pawan Singh",""));
        list.add(new StudentModel("14","Priyanshu Uniyal",""));
        list.add(new StudentModel("15","Rahul Bisht",""));
        list.add(new StudentModel("16","Saurabh Rana",""));
        list.add(new StudentModel("17","Ujjawal Kumar Saini",""));
        list.add(new StudentModel("18","Mohit Singh Rana",""));
        list.add(new StudentModel("19","Akriti Uniyal",""));
        adapter=new StudentAdapter(AttTrdYear.this,list);
        adapter.setOnItemClickListener(position -> changeStatus(position));
        recyclerView.setAdapter(adapter);
        sumbitBtn =findViewById(R.id.AttTrdSubmitBtn);
        sumbitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });

    }
    private void changeStatus(int position) {
        String status=list.get(position).getStatus();
        if(status.equals(""))status="P";
        else if (status.equals("P")) status="A";
        else if(status.equals("A")) status="M";
        else if(status.equals("M")) status="P";

        list.get(position).setStatus(status);
        adapter.notifyItemChanged(position);
    }

    private void submitData() {
        StudentAdapter adapterMarks= (StudentAdapter) recyclerView.getAdapter();
        List<StudentModel> data=adapterMarks.getData();
        date=calendar.getDate().toString();
        Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
        reference= FirebaseDatabase.getInstance().getReference("Attendance").child("2021").child(sem).child(subname).child(date);
        for (int i = 0; i < data.size(); i++) {
            StudentModel item = data.get(i);
            reference.child("Item " + (i + 1)).setValue(item);
        }
        NotificationHelper.createChannel(AttTrdYear.this);
        Intent intent=new Intent(AttTrdYear.this, StudentAttTrdYear.class);
        NotificationHelper.sendNotification(AttTrdYear.this,"Your Attendance is Uploaded",subname+" "+date,intent);
        Toast.makeText(this, "Attendance Submitted SuccessFully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(AttTrdYear.this, TrdYearAttSubject.class));
    }

    private void setToolbar() {
        toolbar=findViewById(R.id.attendance_toolbar);
        TextView title=toolbar.findViewById(R.id.title_toolbar);
        subtitle=toolbar.findViewById(R.id.subtitle_toolbar);
        ImageButton back=toolbar.findViewById(R.id.back);
        title.setText(subname);
        subtitle.setText(calendar.getDate());
        back.setOnClickListener(v -> onBackPressed());
        toolbar.inflateMenu(R.menu.student__menu);
        toolbar.setOnMenuItemClickListener(menuItem -> onMenuItemClick(menuItem));
    }
    private boolean onMenuItemClick(MenuItem menuItem){
        if(menuItem.getItemId()==R.id.add_calendar){
            showCalendar();
        }
        return true;
    }

    private void showCalendar() {
        MyCalendar calendar=new MyCalendar();
        calendar.show(getSupportFragmentManager(),"");
        calendar.setOnCalendarOkClickListener(this::onCalendarOkClicked);
    }

    private void onCalendarOkClicked(int year, int month, int day) {
        calendar.setDate(year,month,day);
        subtitle.setText(calendar.getDate());
        date=calendar.getDate().toString();
        Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
    }
}