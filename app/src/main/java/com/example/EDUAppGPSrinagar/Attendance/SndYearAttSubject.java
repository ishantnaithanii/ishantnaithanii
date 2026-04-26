package com.example.EDUAppGPSrinagar.Attendance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.EDUAppGPSrinagar.Marks.SndYearSubject;
import com.example.EDUAppGPSrinagar.Marks.StudentMarksActivity;
import com.example.EDUAppGPSrinagar.Marks.SubjectAdapter;
import com.example.EDUAppGPSrinagar.Marks.SubjectListener;
import com.example.EDUAppGPSrinagar.Marks.SubjectModel;
import com.example.EDUAppGPSrinagar.R;

import java.util.ArrayList;
import java.util.List;

public class SndYearAttSubject extends AppCompatActivity implements SubjectListener {
    private RecyclerView recyclerView3,recyclerView4;
    private List<SubjectModel> myModelList;
    private Toolbar toolbar;
    private SubjectAdapter subjectAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snd_year_att_subject);

        toolbar=findViewById(R.id.sndYearToolbarAtt);
        TextView textView=toolbar.findViewById((R.id.sndYearToolbar_textViewAtt));
        ImageButton imageButton=toolbar.findViewById(R.id.sndYearToolbar_btnAtt);
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());

        displayItems3Sem();
        displayItems4Sem();
    }

    private void displayItems3Sem() {
        recyclerView3=findViewById(R.id.recyclerAtt3rdSub);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new GridLayoutManager(this,1));
        myModelList=new ArrayList<>();
        myModelList.add(new SubjectModel("C Programming","3rd"));
        myModelList.add(new SubjectModel("Internet Of Things","3rd"));
        myModelList.add(new SubjectModel("Digital Techniques","3rd"));
        myModelList.add(new SubjectModel("Operating System","3rd"));
        myModelList.add(new SubjectModel("Data Communication","3rd"));
        subjectAdapter=new SubjectAdapter(this,myModelList,this);
        recyclerView3.setAdapter(subjectAdapter);

    }
    private void displayItems4Sem() {
        recyclerView4=findViewById(R.id.recyclerAtt4thSub);
        recyclerView4.setHasFixedSize(true);
        recyclerView4.setLayoutManager(new GridLayoutManager(this,1));
        myModelList=new ArrayList<>();
        myModelList.add(new SubjectModel("Computer Organization And Architecture","4th"));
        myModelList.add(new SubjectModel("Internet And Web Technology","4th"));
        myModelList.add(new SubjectModel("Data Structure Using C","4th"));
        myModelList.add(new SubjectModel("Object Oriented Concepts","4th"));
        myModelList.add(new SubjectModel("Relational Database Management System","4th"));
        myModelList.add(new SubjectModel("Computer Network And Security","4th"));
        subjectAdapter=new SubjectAdapter(this,myModelList,this);
        recyclerView4.setAdapter(subjectAdapter);

    }
    @Override
    public void onItemClicked(SubjectModel subjectModel) {
        Intent intent=new Intent(SndYearAttSubject.this, AttSndYear.class);
        intent.putExtra("subname",subjectModel.getSubject());
        intent.putExtra("sem",subjectModel.getSem());
        startActivity(intent);
    }
}