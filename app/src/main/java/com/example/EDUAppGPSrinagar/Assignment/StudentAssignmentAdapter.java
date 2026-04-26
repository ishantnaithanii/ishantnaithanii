package com.example.EDUAppGPSrinagar.Assignment;

import android.annotation.SuppressLint;
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

import com.example.EDUAppGPSrinagar.R;

import java.util.List;

public class StudentAssignmentAdapter extends RecyclerView.Adapter<StudentAssignmentAdapter.StudentAssignmentViewHolder> {
    private Context context;
    private List<StudentAssignmentData> list;

    public StudentAssignmentAdapter(Context context, List<StudentAssignmentData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public StudentAssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.student_assignment_item_layout,parent,false);
        return new StudentAssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAssignmentViewHolder holder, @SuppressLint("RecyclerView")final int position) {
        StudentAssignmentData item=list.get(position);
        holder.studentAssignmentTitle.setText(item.getTitle());
        holder.studentAsgSub.setText(item.getSubject());
        holder.timeAssignment.setText(item.getTime());
        holder.dateAssignment.setText(item.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AssignmentViewerActivity.class);
                intent.putExtra("pdfUrl",list.get(position).getPdfUrl());
                context.startActivity(intent);
            }
        });

        holder.studentAssignmentDownload.setOnClickListener(new View.OnClickListener() {
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

    public class StudentAssignmentViewHolder extends RecyclerView.ViewHolder {
        private TextView studentAssignmentTitle,timeAssignment,dateAssignment,studentAsgSub;
        private ImageView studentAssignmentDownload;

        public StudentAssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentAssignmentDownload=itemView.findViewById(R.id.studentAssignmentDownload);
            studentAssignmentTitle=itemView.findViewById(R.id.studentAssignmentTitle);
            timeAssignment=itemView.findViewById(R.id.timeAssignment);
            dateAssignment=itemView.findViewById(R.id.dateAssignment);
            studentAsgSub=itemView.findViewById(R.id.studentAsgSub);

        }
    }
}
