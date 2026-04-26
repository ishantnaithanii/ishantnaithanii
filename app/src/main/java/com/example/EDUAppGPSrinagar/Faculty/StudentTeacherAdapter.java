package com.example.EDUAppGPSrinagar.Faculty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.EDUAppGPSrinagar.R;

import java.util.List;

public class StudentTeacherAdapter extends RecyclerView.Adapter<StudentTeacherAdapter.StudentTeacherViewAdapter> {
    private Context context;
    private List<TeacherData>list;

    public StudentTeacherAdapter(Context context, List<TeacherData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public StudentTeacherViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_faculty_item_layout,parent,false);
        return new StudentTeacherViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentTeacherViewAdapter holder, int position) {
        TeacherData item=list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.post.setText(item.getPost());
        try {
            if (item.getImage() !=null) {
                Glide.with(context).load(list.get(position).getImage()).into(holder.imageView);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StudentTeacherViewAdapter extends RecyclerView.ViewHolder{
        private TextView name,email,post;
        private ImageView imageView;

        public StudentTeacherViewAdapter(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.studentTeacherName);
            post=itemView.findViewById(R.id.studentTeacherPost);
            email=itemView.findViewById(R.id.studentTeacherEmail);
            imageView=itemView.findViewById(R.id.studentTeacherImage);

        }
    }
}
