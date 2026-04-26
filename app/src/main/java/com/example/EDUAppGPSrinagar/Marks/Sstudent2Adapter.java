package com.example.EDUAppGPSrinagar.Marks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.EDUAppGPSrinagar.R;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Sstudent2Adapter extends RecyclerView.Adapter<Sstudent2Adapter.Sstudent2ViewHolder> {
    private Context context;
    private List<Student2Model>list;


    public Sstudent2Adapter(Context context, List<Student2Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Sstudent2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.studentlist_item2,parent,false);
        return new Sstudent2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Sstudent2ViewHolder holder, int position) {
        holder.roll.setText(list.get(position).getRoll());
        holder.name.setText(list.get(position).getName());
        holder.marks.setText(list.get(position).getMarks());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Sstudent2ViewHolder extends RecyclerView.ViewHolder {

        private TextView roll,name,marks;
        public Sstudent2ViewHolder(@NonNull View itemView) {
            super(itemView);
            roll=itemView.findViewById(R.id.student2RollNo2);
            name=itemView.findViewById(R.id.student2Name2);
            marks=itemView.findViewById(R.id.student2Marks2);
        }
    }
}
