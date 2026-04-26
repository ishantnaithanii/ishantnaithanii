package com.example.EDUAppGPSrinagar.Marks;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.EDUAppGPSrinagar.NotificationHelper;
import com.example.EDUAppGPSrinagar.R;
import com.example.EDUAppGPSrinagar.UploadAssignments;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class StudentMarksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Student2Model>list;
    private Student2Adapter adapter;
    private Toolbar toolbar;
    private String subname,type,sem;
    private Button sumbitBtn;
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_marks);

        subname=getIntent().getStringExtra("subname");
        sem=getIntent().getStringExtra("sem");
        type=getIntent().getStringExtra("type");

        //toolbar
        toolbar=findViewById(R.id.studentMarksToolbar);
        TextView textView=toolbar.findViewById((R.id.studentMarksToolbar_textView));
        ImageButton imageButton=toolbar.findViewById(R.id.studentMarksToolbar_btn);
        textView.setText(subname+" Marks");
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());

//        try {
//            database=FirebaseDatabase.getInstance();
//        }catch (Exception e){
//            Log.e("Firebase Error","Error initializing Firebase Database");
//        }
        recyclerView=findViewById(R.id.recyclerMarks2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        list=new ArrayList<>();
        list.add(new Student2Model("1","Aman Pokhriyal",""));
        list.add(new Student2Model("2","Yash Rana",""));
        list.add(new Student2Model("3","Anisha Rana",""));
        list.add(new Student2Model("4","Ankita Bhatt",""));
        list.add(new Student2Model("5","Anshul Thapa",""));
        list.add(new Student2Model("6","Ayush Negi",""));
        list.add(new Student2Model("7","Jaideep Dhanai",""));
        list.add(new Student2Model("8","Mahima Jalal",""));
        list.add(new Student2Model("9","Mohit Kohli",""));
        list.add(new Student2Model("10","Neetu",""));
        list.add(new Student2Model("11","Nikhil Chauhan",""));
        list.add(new Student2Model("12","Om Rawat",""));
        list.add(new Student2Model("13","Payal Negi",""));
        list.add(new Student2Model("14","Piyush Singh Rawat",""));
        list.add(new Student2Model("15","Priyanshu Pokhriyal",""));
        list.add(new Student2Model("16","Sahil",""));
        list.add(new Student2Model("17","Sangam Saini",""));
        list.add(new Student2Model("18","Sardul Bisht",""));
        list.add(new Student2Model("19","Shrestha Pandey",""));
        list.add(new Student2Model("20","Shubham Rawat",""));
        list.add(new Student2Model("21","Sujal Rawat",""));
        list.add(new Student2Model("22","Sumit Rawat",""));
        list.add(new Student2Model("23","Sahil Thapa",""));
        list.add(new Student2Model("24","Sushil Prasad",""));
        list.add(new Student2Model("25","Akhil Kanswal",""));
        adapter=new Student2Adapter(this,list);
        recyclerView.setAdapter(adapter);
        sumbitBtn =findViewById(R.id.marks2SubmitBtn);
        sumbitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });

    }

    private void submitData() {
        Student2Adapter adapterMarks= (Student2Adapter) recyclerView.getAdapter();
        List<Student2Model> data=adapterMarks.getData();
        reference=FirebaseDatabase.getInstance().getReference("Marks").child(sem).child(type).child(subname);
        for (int i = 0; i < data.size(); i++) {
            Student2Model item = data.get(i);
            reference.child("Item " + (i + 1)).setValue(item);
        }
        NotificationHelper.createChannel(StudentMarksActivity.this);
        Intent intent=new Intent(StudentMarksActivity.this, StudentName2.class);
        NotificationHelper.sendNotification(StudentMarksActivity.this,"Your Marks is Uploaded",subname,intent);
        Toast.makeText(this, "Marks Submitted SuccessFully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(StudentMarksActivity.this, SndYearSubject.class));
    }
}

//
//
//