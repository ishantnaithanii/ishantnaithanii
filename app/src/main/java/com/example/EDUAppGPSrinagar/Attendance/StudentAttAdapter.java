package com.example.EDUAppGPSrinagar.Attendance;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.EDUAppGPSrinagar.Marks.Student2Model;
import com.example.EDUAppGPSrinagar.R;

import java.util.List;

public class StudentAttAdapter extends RecyclerView.Adapter<StudentAttAdapter.StudentAttViewHolder> {
    private List<StudentModel>list;
    private Context context;

    private StudentAdapter.OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onClick(int position);
    }
    public StudentAttAdapter(List<StudentModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentAttViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.student_item_att,parent,false);
        return new StudentAttViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAttViewHolder holder, int position) {
        StudentModel item=list.get(position);
        holder.roll.setText(item.getRoll());
        holder.name.setText(item.getName());
        holder.status.setText(item.getStatus());
        holder.cardView.setCardBackgroundColor(getColor(position));

    }

    private int getColor(int position) {
        String status=list.get(position).getStatus();
        if(status.equals("P"))
            return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.present)));
        else if (status.equals("A"))
            return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.absent)));
        else if (status.equals("M"))
            return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.medical)));
        return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.normal)));

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StudentAttViewHolder extends RecyclerView.ViewHolder {
        private TextView roll;
        private TextView name;
        private TextView status;
        private CardView cardView;
        public StudentAttViewHolder(@NonNull View itemView) {
            super(itemView);
            roll=itemView.findViewById(R.id.attRoll);
            name=itemView.findViewById(R.id.attName);
            status=itemView.findViewById(R.id.attStatus);
            cardView=itemView.findViewById(R.id.attCard);
            itemView.setOnClickListener(v -> onItemClickListener.onClick(getAdapterPosition()));
        }
    }
}
