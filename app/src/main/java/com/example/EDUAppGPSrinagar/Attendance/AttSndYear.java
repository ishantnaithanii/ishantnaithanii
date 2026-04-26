package com.example.EDUAppGPSrinagar.Attendance;

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

import com.example.EDUAppGPSrinagar.Marks.StudentMarksActivity;
import com.example.EDUAppGPSrinagar.NotificationHelper;
import com.example.EDUAppGPSrinagar.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AttSndYear extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<StudentModel>list;
    private StudentAdapter adapter;
    private Button sumbitBtn;
    private DatabaseReference reference;
    private TextView subtitle;
    private Toolbar toolbar;
    private String subname,sem,date;
    private MyCalendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_att_snd_year);

        calendar=new MyCalendar();
        subname=getIntent().getStringExtra("subname");
        sem=getIntent().getStringExtra("sem");

        setToolbar();

        recyclerView=findViewById(R.id.recyclerAttSnd);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        list=new ArrayList<>();
        list.add(new StudentModel("1","Aman Pokhriyal",""));
        list.add(new StudentModel("2","Yash Rana",""));
        list.add(new StudentModel("3","Anisha Rana",""));
        list.add(new StudentModel("4","Ankita Bhatt",""));
        list.add(new StudentModel("5","Anshul Thapa",""));
        list.add(new StudentModel("6","Ayush Negi",""));
        list.add(new StudentModel("7","Jaideep Dhanai",""));
        list.add(new StudentModel("8","Mahima Jalal",""));
        list.add(new StudentModel("9","Mohit Kohli",""));
        list.add(new StudentModel("10","Neetu",""));
        list.add(new StudentModel("11","Nikhil Chauhan",""));
        list.add(new StudentModel("12","Om Rawat",""));
        list.add(new StudentModel("13","Payal Negi",""));
        list.add(new StudentModel("14","Piyush Singh Rawat",""));
        list.add(new StudentModel("15","Priyanshu Pokhriyal",""));
        list.add(new StudentModel("16","Sahil",""));
        list.add(new StudentModel("17","Sangam Saini",""));
        list.add(new StudentModel("18","Sardul Bisht",""));
        list.add(new StudentModel("19","Shrestha Pandey",""));
        list.add(new StudentModel("20","Shubham Rawat",""));
        list.add(new StudentModel("21","Sujal Rawat",""));
        list.add(new StudentModel("22","Sumit Rawat",""));
        list.add(new StudentModel("23","Sahil Thapa",""));
        list.add(new StudentModel("24","Sushil Prasad",""));
        list.add(new StudentModel("25","Akhil Kanswal",""));
        adapter=new StudentAdapter(AttSndYear.this,list);
        adapter.setOnItemClickListener(position -> changeStatus(position));
        recyclerView.setAdapter(adapter);
        sumbitBtn =findViewById(R.id.AttSndSubmitBtn);
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
        reference= FirebaseDatabase.getInstance().getReference("Attendance").child("2022").child(sem).child(subname).child(date);
        for (int i = 0; i < data.size(); i++) {
            StudentModel item = data.get(i);
            reference.child("Item " + (i + 1)).setValue(item);
        }
        NotificationHelper.createChannel(AttSndYear.this);
        Intent intent=new Intent(AttSndYear.this, SndYear.class);
        NotificationHelper.sendNotification(AttSndYear.this,"Your Attendance is Uploaded",subname+" "+date,intent);
        Toast.makeText(this, "Attendance Submitted SuccessFully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(AttSndYear.this, SndYearAttSubject.class));
        finish();
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