package com.example.EDUAppGPSrinagar.Notice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.EDUAppGPSrinagar.FullImage;
import com.example.EDUAppGPSrinagar.R;

import java.util.ArrayList;

public class StudentNoticeAdapter extends RecyclerView.Adapter<StudentNoticeAdapter.NoticeViewAdapter> {

    private Context context;
    private ArrayList<NoticeData> list;

    public StudentNoticeAdapter(Context context, ArrayList<NoticeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.student_newsfeed_item_layout,parent,false);
        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter holder, @SuppressLint("RecyclerView") int position) {
        NoticeData currentItem =list.get(position);
        holder.studentNoticeTitle.setText(currentItem.getTitle());
        holder.studentNoticeTeacher.setText(currentItem.getTeacher());
        holder.studentTime.setText(currentItem.getTime());
        holder.studentDate.setText(currentItem.getDate());


        try {
            if (currentItem.getImage() !=null) {
                Glide.with(context).load(list.get(position).getImage()).into(holder.studentNoticeImage);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        holder.studentNoticeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, FullImage.class);
                intent.putExtra("image",list.get(position).getImage());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeViewAdapter extends RecyclerView.ViewHolder {
        private TextView studentNoticeTitle,studentDate,studentTime,studentNoticeTeacher;
        private ImageView studentNoticeImage;

        public NoticeViewAdapter(@NonNull View itemView) {
            super(itemView);
            studentNoticeTitle=itemView.findViewById(R.id.studentNoticeTitle);
            studentNoticeImage=itemView.findViewById(R.id.studentNoticeImage);
            studentDate=itemView.findViewById(R.id.date);
            studentTime=itemView.findViewById(R.id.time);
            studentNoticeTeacher=itemView.findViewById(R.id.studentNoticeTeacher);

        }
    }
}
