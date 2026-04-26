package com.example.EDUAppGPSrinagar.Notes;

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

import com.example.EDUAppGPSrinagar.Assignment.AssignmentViewerActivity;
import com.example.EDUAppGPSrinagar.R;

import java.util.List;

public class StudentNotesAdapter extends RecyclerView.Adapter<StudentNotesAdapter.StudentNotesViewHolder>{
    private Context context;
    private List<StudentNotesData> list;

    public StudentNotesAdapter(Context context, List<StudentNotesData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public StudentNotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.student_notes_item_layout,parent,false);
        return new StudentNotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentNotesViewHolder holder, int position) {
        StudentNotesData item=list.get(position);
        holder.studentNotesTitle.setText(item.getTitle());
        holder.getStudentNotesSubject.setText(item.getSubject());
        holder.timeNotes.setText(item.getTime());
        holder.dateNotes.setText(item.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, NotesViewerActivity.class);
                intent.putExtra("pdfUrl",list.get(position).getPdfUrl());
                context.startActivity(intent);
            }
        });

        holder.studentNotesDownload.setOnClickListener(new View.OnClickListener() {
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

    public class StudentNotesViewHolder extends RecyclerView.ViewHolder{
        private TextView studentNotesTitle,timeNotes,dateNotes,getStudentNotesSubject;
        private ImageView studentNotesDownload;

        public StudentNotesViewHolder(@NonNull View itemView) {
            super(itemView);
            studentNotesTitle=itemView.findViewById(R.id.studentNotesTitle);
            getStudentNotesSubject=itemView.findViewById(R.id.studentNotesSubject);
            studentNotesDownload=itemView.findViewById(R.id.studentNotesDownload);
            timeNotes=itemView.findViewById(R.id.timeNotes);
            dateNotes=itemView.findViewById(R.id.dateNotes);
        }
    }
}
