package com.example.EDUAppGPSrinagar.Marks;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.EDUAppGPSrinagar.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private Context context;
    private List<Student2Model> list;
//    private FirebaseDatabase database;
    private String subname,type,sem;

    public List<Student2Model>getData(){
        return list;
    }

    public ReportAdapter(Context context, List<Student2Model> list,String type, String subname,String sem) {
        this.context = context;
        this.list = list;
        this.subname=subname;
        this.type=type;
        this.sem=sem;
//        this.database=database;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.studentlistreport_item_layout,parent,false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.studentRollReport.setText(list.get(position).getRoll());
        holder.studentNameReport.setText(list.get(position).getName());
        holder.studentMarksReport.setText(list.get(position).getMarks());
        holder.studentUpdateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(context,UpdateMarksActivity.class);
//                intent.putExtra("name",list.get(position).getName());
//                intent.putExtra("roll",list.get(position).getRoll());
//                intent.putExtra("marks",list.get(position).getMarks());
//                intent.putExtra("position",position);
//                intent.putExtra("sem",sem);
//                intent.putExtra("subname",subname);
//                intent.putExtra("type",type);
//                context.startActivity(intent);
                showUpdateDialog(position);
            }
            });
    }

    private void showUpdateDialog(int position) {
        Student2Model item=list.get(position);
        LayoutInflater inflater=LayoutInflater.from(context);
        View dialogView=inflater.inflate(R.layout.report_dialog,null);

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setView(dialogView);
        final TextView textViewRoll=dialogView.findViewById(R.id.updateDialogTv1);
        textViewRoll.setText(item.getRoll());
        final TextView textViewName=dialogView.findViewById(R.id.updateDialogTv2);
        textViewName.setText(item.getName());
        final EditText editTextMarks=dialogView.findViewById(R.id.updateDialogEt);
        editTextMarks.setText(item.getMarks());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String updatedMarks = editTextMarks.getText().toString();
                String updateRoll=textViewRoll.getText().toString();
                String updateName=textViewName.getText().toString();
                item.setName(updateName);
                item.setRoll(updateRoll);
                item.setMarks(updatedMarks);
                list.set(position, item);
                notifyItemChanged(position); // Update RecyclerView item visually

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Marks").child(sem).child(type).child(subname).child("Item " + updateRoll);
                reference.setValue(item);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Dismiss dialog if user cancels
            }
        });

        builder.create().show();
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder {
        private final TextView studentRollReport;
        private final TextView studentNameReport;
        private final TextView studentMarksReport;
        private final Button studentUpdateReport;
        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            studentRollReport=itemView.findViewById(R.id.studentRollNoReport);
            studentNameReport=itemView.findViewById(R.id.studentNameReport);
            studentMarksReport=itemView.findViewById(R.id.studentMarksReport);
            studentUpdateReport=itemView.findViewById(R.id.studentUpdateReport);
        }
    }

}
