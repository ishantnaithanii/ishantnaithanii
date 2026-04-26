package com.example.EDUAppGPSrinagar.Paper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.EDUAppGPSrinagar.Notes.NotesViewerActivity;
import com.example.EDUAppGPSrinagar.Notes.StudentNotesAdapter;
import com.example.EDUAppGPSrinagar.Notes.StudentNotesData;
import com.example.EDUAppGPSrinagar.R;

import java.util.List;

public class StudentPapersAdapter extends RecyclerView.Adapter<StudentPapersAdapter.StudentPapersViewHolder> {
    private Context context;
    private List<StudentPapersData> list;

    public StudentPapersAdapter(Context context, List<StudentPapersData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public StudentPapersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.student_papers_item_layout,parent,false);
        return new StudentPapersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentPapersViewHolder holder, int position) {

        StudentPapersData item=list.get(position);
        holder.studentPapersTitle.setText(item.getTitle());
        holder.studentPapersSubject.setText(item.getSubject());
        holder.timePapers.setText(item.getTime());
        holder.datePapers.setText(item.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PaperViewerActivity.class);
                intent.putExtra("pdfUrl",list.get(position).getPdfUrl());
                context.startActivity(intent);
            }
        });

        holder.studentPapersDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(list.get(position).getPdfUrl()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StudentPapersViewHolder extends RecyclerView.ViewHolder {

        private TextView studentPapersTitle,timePapers,datePapers,studentPapersSubject;
        private ImageView studentPapersDownload;
        public StudentPapersViewHolder(@NonNull View itemView) {
            super(itemView);
            studentPapersTitle=itemView.findViewById(R.id.studentPapersTitle);
            studentPapersDownload=itemView.findViewById(R.id.studentPapersDownload);
            studentPapersSubject=itemView.findViewById(R.id.studentPapersSubject);
            timePapers=itemView.findViewById(R.id.timePapers);
            datePapers=itemView.findViewById(R.id.datePapers);
        }
    }
}
