package com.example.EDUAppGPSrinagar.Faculty;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.List;

public class Student_Faculty extends AppCompatActivity {
    private RecyclerView studentCsDepartment,studentItDepartment;
    private LinearLayout studentCsNoData,studentITNoData;
    private List<TeacherData> list1,list2;
    private Toolbar toolbar;
    private DatabaseReference databaseReference;
    private StudentTeacherAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_faculty);
        studentItDepartment=findViewById(R.id.studentItDepartment);
        studentCsDepartment=findViewById(R.id.studentCsDepartment);
        studentITNoData=findViewById(R.id.studentITNoData);
        studentCsNoData=findViewById(R.id.studentCsNoData);

        //toolbar
        toolbar=findViewById(R.id.facultyToolbarStudent);
        TextView textView=toolbar.findViewById((R.id.facultyToolbar_textViewStudent));
        ImageButton imageButton=toolbar.findViewById(R.id.facultyToolbar_btnStudent);
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());
        csDepartment();
        ItDepartment();
    }
    private void csDepartment() {
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Teacher").child("Computer Science");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list1=new ArrayList<>();
                if (!dataSnapshot.exists()){
                    studentCsNoData.setVisibility(View.VISIBLE);
                    studentCsDepartment.setVisibility(View.GONE);
                }
                else {
                    studentCsNoData.setVisibility(View.GONE);
                    studentCsDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        TeacherData data=snapshot.getValue((TeacherData.class));
                        data.setKey(snapshot.getKey());
                        list1.add(data);
                    }
                    adapter=new StudentTeacherAdapter(Student_Faculty.this,list1);
                    adapter.notifyDataSetChanged();
                    studentCsDepartment.setLayoutManager(new LinearLayoutManager((Student_Faculty.this)));
                    studentCsDepartment.setHasFixedSize(true);
                    studentCsDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Student_Faculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ItDepartment() {
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Teacher").child("Information Technology");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list2=new ArrayList<>();
                if (!dataSnapshot.exists()){
                    studentITNoData.setVisibility(View.VISIBLE);
                    studentItDepartment.setVisibility(View.GONE);
                }
                else {
                    studentITNoData.setVisibility(View.GONE);
                    studentItDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        TeacherData data=snapshot.getValue((TeacherData.class));
                        data.setKey(snapshot.getKey());
                        list2.add(data);
                    }

                    adapter=new StudentTeacherAdapter(Student_Faculty.this,list2);
                    adapter.notifyDataSetChanged();
                    studentItDepartment.setLayoutManager(new LinearLayoutManager((Student_Faculty.this)));
                    studentItDepartment.setHasFixedSize(true);
                    studentItDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Student_Faculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}