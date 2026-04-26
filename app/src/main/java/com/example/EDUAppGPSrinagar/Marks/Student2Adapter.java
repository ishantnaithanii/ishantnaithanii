package com.example.EDUAppGPSrinagar.Marks;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.EDUAppGPSrinagar.R;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Student2Adapter extends RecyclerView.Adapter<Student2Adapter.Student2ViewHolder> {
    private Context context;
    private List<Student2Model> list;

    public List<Student2Model>getData(){
        return list;
    }

    public Student2Adapter(Context context, List<Student2Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Student2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.student_list2_item_layout,parent,false);
        return new Student2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Student2ViewHolder holder, int position) {
        holder.roll.setText(list.get(position).getRoll());
        holder.name.setText(list.get(position).getName());
        holder.marks.setText(list.get(position).getMarks());
        holder.marks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                list.get(position).setMarks(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Student2ViewHolder extends RecyclerView.ViewHolder {
        private TextView roll,name;
        private EditText marks;

        public Student2ViewHolder(@NonNull View itemView) {
            super(itemView);
            roll=itemView.findViewById(R.id.student2RollNo);
            name=itemView.findViewById(R.id.student2Name);
            marks=itemView.findViewById(R.id.student2Marks);
        }
    }
}
