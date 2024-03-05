package com.example.constructionmodel;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class profileAdapter extends RecyclerView.Adapter<profileAdapter.MyViewHolder> {
    Context context;
    List<uploadData> dataList;

    public profileAdapter(Context context, List<uploadData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.preview_profile,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        uploadData uploadData = dataList.get(position);
        holder.worker_name.setText(uploadData.getName());
        holder.worker_occupation.setText(uploadData.getOccupation());
        holder.worker_address.setText(uploadData.getAddress());
        holder.worker_contact.setText(String.valueOf(uploadData.getContact()));
        holder.worker_email.setText(String.valueOf(uploadData.getEmail()));
        holder.worker_exp.setText(String.valueOf(uploadData.getExperience()));
        holder.worker_office_address.setText(String.valueOf(uploadData.getOfficeAddress()));

        String imageUri = null,testimageUri = null;
        imageUri = uploadData.getImage();
        testimageUri = uploadData.getTestimonals();
        Picasso.get().load(imageUri).into(holder.worker_image);
        Picasso.get().load(testimageUri).into(holder.worker_testimonals);

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
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView worker_image,worker_testimonals;
        TextView worker_name,worker_occupation,worker_address,worker_exp,worker_contact,worker_email,worker_office_address;
        androidx.appcompat.widget.AppCompatButton view_details;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            worker_image = itemView.findViewById(R.id.view_image);
            worker_name = itemView.findViewById(R.id.view_name);
            worker_occupation = itemView.findViewById(R.id.view_occupation);
            worker_address = itemView.findViewById(R.id.view_address);
            worker_exp = itemView.findViewById(R.id.view_experience);
            worker_contact = itemView.findViewById(R.id.view_contactNumber);
            worker_email = itemView.findViewById(R.id.view_email);
            worker_office_address = itemView.findViewById(R.id.view_shop_address);
            worker_testimonals = itemView.findViewById(R.id.view_testimonal);
        }
    }
}
