package com.example.EDUAppGPSrinagar.Faculty;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateFaculty extends AppCompatActivity {
    private Toolbar toolbar;
    FloatingActionButton fab;
    private ProgressDialog pd;
    private RecyclerView csDepartment,itDepartment;
    private LinearLayout csNoData,iTNoData;
    private List <TeacherData>list1,list2;
    private DatabaseReference databaseReference;
    private TeacherAdapter adapter;
    @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);

        //toolbar
        toolbar=findViewById(R.id.updateFacultyToolBar);
        TextView textView=toolbar.findViewById((R.id.updateFacultyToolbartv));
        ImageButton imageButton=toolbar.findViewById(R.id.updateFacultyToolbarib);
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());

        csDepartment=findViewById(R.id.csDepartment);
        itDepartment=findViewById(R.id.ItDepartment);
        pd=new ProgressDialog(this);

        csNoData=findViewById(R.id.csNoData);
        iTNoData=findViewById(R.id.ITNoData);
        pd.setTitle("Loading...");
        pd.show();
        csDepartment();
        ItDepartment();

        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateFaculty.this, Add_Teacher.class));
            }
        });

    }

    private void csDepartment() {
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Teacher").child("Computer Science");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list1=new ArrayList<>();
                if (!dataSnapshot.exists()){
                    csNoData.setVisibility(View.VISIBLE);
                    csDepartment.setVisibility(View.GONE);
                }
                else {
                    csNoData.setVisibility(View.GONE);
                    csDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        TeacherData data=snapshot.getValue((TeacherData.class));
                        data.setKey(snapshot.getKey());
                        list1.add(data);
                    }
                    adapter=new TeacherAdapter(list1,UpdateFaculty.this,"Computer Science");
                    adapter.notifyDataSetChanged();
                    csDepartment.setLayoutManager(new LinearLayoutManager((UpdateFaculty.this)));
                    csDepartment.setHasFixedSize(true);
                    csDepartment.setAdapter(adapter);
                    pd.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pd.dismiss();
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    };

    private void ItDepartment() {
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Teacher").child("Information Technology");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list2=new ArrayList<>();
                if (!dataSnapshot.exists()){
                    iTNoData.setVisibility(View.VISIBLE);
                    itDepartment.setVisibility(View.GONE);
                }
                else {
                    iTNoData.setVisibility(View.GONE);
                    itDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        TeacherData data=snapshot.getValue((TeacherData.class));
                        data.setKey(snapshot.getKey());
                        list2.add(data);
                    }

                    adapter=new TeacherAdapter(list2,UpdateFaculty.this,"Information Technology");
                    adapter.notifyDataSetChanged();
                    itDepartment.setLayoutManager(new LinearLayoutManager((UpdateFaculty.this)));
                    itDepartment.setHasFixedSize(true);
                    itDepartment.setAdapter(adapter);
                    pd.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pd.dismiss();
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    };

}