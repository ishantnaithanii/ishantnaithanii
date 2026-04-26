package com.example.EDUAppGPSrinagar.Marks;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.EDUAppGPSrinagar.NotificationHelper;
import com.example.EDUAppGPSrinagar.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class StudentMarksTrd extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Student2Model> list;
    private Student2Adapter adapter;
    private Toolbar toolbar;
    private String subname,type,sem;
    private Button sumbitBtn;
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_marks_trd);

        subname=getIntent().getStringExtra("subname");
        sem=getIntent().getStringExtra("sem");
        type=getIntent().getStringExtra("type");


        //toolbar
        toolbar=findViewById(R.id.marks3Toolbar);
        TextView textView=toolbar.findViewById((R.id.marks3Toolbar_textView));
        ImageButton imageButton=toolbar.findViewById(R.id.marks3Toolbar_btn);
        textView.setText(subname+" Marks");
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());

        recyclerView=findViewById(R.id.recyclerTrdMarks);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        list=new ArrayList<>();
        list.add(new Student2Model("1","Rajeev Rawat",""));
        list.add(new Student2Model("2","Abhishek Bhakuni",""));
        list.add(new Student2Model("3","Abhishek Miyan",""));
        list.add(new Student2Model("4","Alok Uniyal",""));
        list.add(new Student2Model("5","Akash Rawat",""));
        list.add(new Student2Model("6","Anirudh Bisht",""));
        list.add(new Student2Model("7","Ankush",""));
        list.add(new Student2Model("8","Ishant Naithani",""));
        list.add(new Student2Model("9","Km. Anjali",""));
        list.add(new Student2Model("10","Komal",""));
        list.add(new Student2Model("11","Pankaj Rawat",""));
        list.add(new Student2Model("12","Parth Gairola",""));
        list.add(new Student2Model("13","Pawan Singh",""));
        list.add(new Student2Model("14","Priyanshu Uniyal",""));
        list.add(new Student2Model("15","Rahul Bisht",""));
        list.add(new Student2Model("16","Saurabh Rana",""));
        list.add(new Student2Model("17","Ujjawal Kumar Saini",""));
        list.add(new Student2Model("18","Mohit Singh Rana",""));
        list.add(new Student2Model("19","Akriti Uniyal",""));
        adapter=new Student2Adapter(this,list);
        recyclerView.setAdapter(adapter);
        sumbitBtn =findViewById(R.id.marksTrdSubmitBtn);
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
        reference= FirebaseDatabase.getInstance().getReference("Marks").child(sem).child(type).child(subname);
        for (int i = 0; i < data.size(); i++) {
            Student2Model item = data.get(i);
            reference.child("Item " + (i + 1)).setValue(item);
        }
        NotificationHelper.createChannel(StudentMarksTrd.this);
        Intent intent=new Intent(StudentMarksTrd.this, StudentNameTrd.class);
        NotificationHelper.sendNotification(StudentMarksTrd.this,"Your Marks is Uploaded",subname,intent);

        Toast.makeText(this, "Marks Submitted SuccessFully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(StudentMarksTrd.this, TrdYearSubject.class));
    }
}
