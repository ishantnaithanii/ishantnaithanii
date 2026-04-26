package com.example.EDUAppGPSrinagar.Marks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.EDUAppGPSrinagar.R;

import java.util.ArrayList;
import java.util.List;

public class SndYearSubject extends AppCompatActivity implements SubjectListener{
    private RecyclerView recyclerView3,recyclerView4,recyclerView3Mid,recyclerView3Ct2,recyclerView4Mid,recyclerView4Ct2;
    private List<SubjectModel>myModelList;
    private Toolbar toolbar;
    private SubjectAdapter subjectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snd_year_subject);

        toolbar=findViewById(R.id.sndYearToolbar);
        TextView textView=toolbar.findViewById((R.id.sndYearToolbar_textView));
        ImageButton imageButton=toolbar.findViewById(R.id.sndYearToolbar_btn);
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());


        displayItems3Sem();
        displayItemsCt23Sem();
        displayItemsMid3Sem();
        displayItems4Sem();
        displayItemsCt24Sem();
        displayItemsMid4Sem();
    }

    private void displayItemsCt24Sem() {
        recyclerView4Ct2=findViewById(R.id.recyclerCt24thSub);
        recyclerView4Ct2.setHasFixedSize(true);
        recyclerView4Ct2.setLayoutManager(new GridLayoutManager(this,1));
        myModelList=new ArrayList<>();
        myModelList.add(new SubjectModel("Computer Organization And Architecture","Ct2","4th"));
        myModelList.add(new SubjectModel("Internet And Web Technology","Ct2","4th"));
        myModelList.add(new SubjectModel("Data Structure Using C","Ct2","4th"));
        myModelList.add(new SubjectModel("Object Oriented Concepts","Ct2","4th"));
        myModelList.add(new SubjectModel("Relational Database Management System","Ct2","4th"));
        myModelList.add(new SubjectModel("Computer Network And Security","Ct2","4th"));
        subjectAdapter=new SubjectAdapter(this,myModelList,this);
        recyclerView4Ct2.setAdapter(subjectAdapter);
    }
    private void displayItemsMid4Sem() {
        recyclerView4Mid=findViewById(R.id.recyclerMid4thSub);
        recyclerView4Mid.setHasFixedSize(true);
        recyclerView4Mid.setLayoutManager(new GridLayoutManager(this,1));
        myModelList=new ArrayList<>();
        myModelList.add(new SubjectModel("Computer Organization And Architecture","Mid","4th"));
        myModelList.add(new SubjectModel("Internet And Web Technology","Mid","4th"));
        myModelList.add(new SubjectModel("Data Structure Using C","Mid","4th"));
        myModelList.add(new SubjectModel("Object Oriented Concepts","Mid","4th"));
        myModelList.add(new SubjectModel("Relational Database Management System","Mid","4th"));
        myModelList.add(new SubjectModel("Computer Network And Security","Mid","4th"));
        subjectAdapter=new SubjectAdapter(this,myModelList,this);
        recyclerView4Mid.setAdapter(subjectAdapter);
    }

    private void displayItemsCt23Sem() {
        recyclerView3Ct2=findViewById(R.id.recyclerCt23rdSub);
        recyclerView3Ct2.setHasFixedSize(true);
        recyclerView3Ct2.setLayoutManager(new GridLayoutManager(this,1));
        myModelList=new ArrayList<>();
        myModelList.add(new SubjectModel("C Programming","Ct2","3rd"));
        myModelList.add(new SubjectModel("Internet Of Things","Ct2","3rd"));
        myModelList.add(new SubjectModel("Digital Techniques","Ct2","3rd"));
        myModelList.add(new SubjectModel("Operating System","Ct2","3rd"));
        myModelList.add(new SubjectModel("Data Communication","Ct2","3rd"));
        subjectAdapter=new SubjectAdapter(this,myModelList,this);
        recyclerView3Ct2.setAdapter(subjectAdapter);
    }
    private void displayItemsMid3Sem() {
        recyclerView3Mid=findViewById(R.id.recyclerMid3rdSub);
        recyclerView3Mid.setHasFixedSize(true);
        recyclerView3Mid.setLayoutManager(new GridLayoutManager(this,1));
        myModelList=new ArrayList<>();
        myModelList.add(new SubjectModel("C Programming","Mid","3rd"));
        myModelList.add(new SubjectModel("Internet Of Things","Mid","3rd"));
        myModelList.add(new SubjectModel("Digital Techniques","Mid","3rd"));
        myModelList.add(new SubjectModel("Operating System","Mid","3rd"));
        myModelList.add(new SubjectModel("Data Communication","Mid","3rd"));
        subjectAdapter=new SubjectAdapter(this,myModelList,this);
        recyclerView3Mid.setAdapter(subjectAdapter);
    }

    private void displayItems4Sem() {
        recyclerView4=findViewById(R.id.recyclerView4thSub);
        recyclerView4.setHasFixedSize(true);
        recyclerView4.setLayoutManager(new GridLayoutManager(this,1));
        myModelList=new ArrayList<>();
        myModelList.add(new SubjectModel("Computer Organization And Architecture","Ct1","4th"));
        myModelList.add(new SubjectModel("Internet And Web Technology","Ct1","4th"));
        myModelList.add(new SubjectModel("Data Structure Using C","Ct1","4th"));
        myModelList.add(new SubjectModel("Object Oriented Concepts","Ct1","4th"));
        myModelList.add(new SubjectModel("Relational Database Management System","Ct1","4th"));
        myModelList.add(new SubjectModel("Computer Network And Security","Ct1","4th"));
        subjectAdapter=new SubjectAdapter(this,myModelList,this);
        recyclerView4.setAdapter(subjectAdapter);

    }

    private void displayItems3Sem() {
        recyclerView3=findViewById(R.id.recyclerView3rdSub);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new GridLayoutManager(this,1));
        myModelList=new ArrayList<>();
        myModelList.add(new SubjectModel("C Programming","Ct1","3rd"));
        myModelList.add(new SubjectModel("Internet Of Things","Ct1","3rd"));
        myModelList.add(new SubjectModel("Digital Techniques","Ct1","3rd"));
        myModelList.add(new SubjectModel("Operating System","Ct1","3rd"));
        myModelList.add(new SubjectModel("Data Communication","Ct1","3rd"));
        subjectAdapter=new SubjectAdapter(this,myModelList,this);
        recyclerView3.setAdapter(subjectAdapter);

    }

    @Override
    public void onItemClicked(SubjectModel subjectModel) {
        Intent intent=new Intent(SndYearSubject.this, StudentMarksActivity.class);
        intent.putExtra("subname",subjectModel.getSubject());
        intent.putExtra("type",subjectModel.getType());
        intent.putExtra("sem",subjectModel.getSem());
        startActivity(intent);
    }
}