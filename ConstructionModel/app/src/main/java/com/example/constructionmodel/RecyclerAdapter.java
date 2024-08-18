package com.example.constructionmodel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.WorkerViewHolder> {

    Context context;
    List<uploadData> workersList;

    public RecyclerAdapter(Context context, List<uploadData> workersList) {
        this.context = context;
        this.workersList = workersList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.WorkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.worker_profile_recycler_view,null);
        return new WorkerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.WorkerViewHolder holder, int position) {
        uploadData uploadData = workersList.get(position);
        holder.worker_name.setText(uploadData.getName());
        holder.worker_address.setText(uploadData.getAddress());
        holder.worker_exp.setText(String.valueOf(uploadData.getExperience()));
        holder.worker_contact.setText(String.valueOf(uploadData.getContact()));

        String imageUri = null;
        imageUri = uploadData.getImage();
        Picasso.get().load(imageUri).into(holder.worker_image);

        holder.view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentapply = new Intent(context, View_Dashboard.class);
                intentapply.putExtra("W_Profile_Photo",uploadData.getImage());
                intentapply.putExtra("W_Id",uploadData.getId());
                intentapply.putExtra("W_Name",uploadData.getName());
                intentapply.putExtra("W_Occupation",uploadData.getOccupation());
                intentapply.putExtra("W_Contact",uploadData.getContact());
                intentapply.putExtra("W_Email",uploadData.getEmail());
                intentapply.putExtra("W_Address",uploadData.getAddress());
                intentapply.putExtra("W_Work_Experience",uploadData.getExperience());
                intentapply.putExtra("W_Office_Shop_Address",uploadData.getOfficeAddress());
                intentapply.putExtra("W_Testimonals",uploadData.getTestimonals());
                context.startActivity(intentapply);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workersList.size();
    }

    public class WorkerViewHolder extends RecyclerView.ViewHolder{
        ImageView worker_image;
        TextView worker_name,worker_address,worker_exp,worker_contact;
        androidx.appcompat.widget.AppCompatButton view_details;
        public WorkerViewHolder(@NonNull View itemView){
            super(itemView);
            worker_image = itemView.findViewById(R.id.w_profile_image);
            worker_name = itemView.findViewById(R.id.w_name);
            worker_address = itemView.findViewById(R.id.w_address);
            worker_exp = itemView.findViewById(R.id.w_experience);
            worker_contact = itemView.findViewById(R.id.w_contact);
            view_details = itemView.findViewById(R.id.applyb);
        }
    }
}
