package com.example.EDUAppGPSrinagar.Timetable;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.EDUAppGPSrinagar.FullImage;
import com.example.EDUAppGPSrinagar.R;

import java.util.List;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.TimetableViewHolder> {
    List<TimetableData>list;
    Context context;

    public TimetableAdapter(List<TimetableData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TimetableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_item,parent,false);
        return new TimetableViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TimetableViewHolder holder, int position) {
        TimetableData item=list.get(position);
        try {
            if (item.getUri() !=null) {
                Glide.with(context).load(item.getUri()).into(holder.imageView);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, FullImage.class);
                intent.putExtra("image",item.getUri());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TimetableViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public TimetableViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.timetableImageItem);
        }
    }
}
