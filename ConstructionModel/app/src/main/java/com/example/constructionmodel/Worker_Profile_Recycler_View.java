package com.example.constructionmodel;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Worker_Profile_Recycler_View extends AppCompatActivity {
    FirebaseDatabase mDatabase;
    FirebaseStorage mStorage;
    DatabaseReference mRef;
    List<uploadData> dataList;
    TextView vname,voccupation,vcontact,vaddress,vemail,vexp,vofficeaddress;
    ImageView vprofile,vtestimonal;
    ArrayList<Worker_Set_profile> workerid;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_profile_view_after_recycler_onclick);

        dataList = new ArrayList<uploadData>();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("Worker");
        mStorage = FirebaseStorage.getInstance();
        vname=findViewById(R.id.vp_name);
        voccupation=findViewById(R.id.vp_occupation);
        vcontact=findViewById(R.id.vp_contactNumber);
        vaddress=findViewById(R.id.vp_address);
        vemail=findViewById(R.id.vp_email);
        vexp=findViewById(R.id.vp_experience);
        vofficeaddress=findViewById(R.id.vp_shop_address);
//        vprofile=findViewById(R.id.vp_image);
//        vtestimonal=findViewById(R.id.vp_testimonal);

        workerid.get(0);
        workerid.get(1);
        workerid.get(2);
        workerid.get(3);

        Intent intent=getIntent();
        String name=intent.getStringExtra("W_Name");
        String occup=intent.getStringExtra("W_Occupation");
        String contact=intent.getStringExtra("W_Contact");
        String address=intent.getStringExtra("W_Address");
        String email=intent.getStringExtra("W_Email");
        String experience=intent.getStringExtra("W_Work_Experience");
        String officeaddress=intent.getStringExtra("W_Office_Shop_Address");
        String profileImage=intent.getStringExtra("W_Profile_Photo");
        String testimonal=intent.getStringExtra("W_Testimonals");

        String imageUri = null;
        imageUri = profileImage;
        Picasso.get().load(imageUri).into(vprofile);

        String testimonalUri = null;
        testimonalUri = testimonal;
        Picasso.get().load(testimonalUri).into(vtestimonal);

        vname.setText(name);
        voccupation.setText(occup);
        vcontact.setText(contact);
        vaddress.setText(address);
        vemail.setText(email);
        vexp.setText(experience);
        vofficeaddress.setText(officeaddress);

//        vprofile.setImageURI(Uri.parse(imageUri));
//        vtestimonal.setImageURI(Uri.parse(testimonalUri));


    }
}