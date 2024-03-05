package com.example.constructionmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class View_Dashboard extends AppCompatActivity {
    FirebaseDatabase mDatabase;
    FirebaseStorage mStorage;
    DatabaseReference mRef;
    RecyclerView recyclerView;
    profileAdapter adapter;
    List<uploadData> dataList;
    TextView vname,voccupation,vcontact,vaddress,vemail,vexp,vofficeaddress;
    ImageView vprofile,vtestimonal;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dashboard);

        dataList = new ArrayList<uploadData>();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("Sport");
        mStorage = FirebaseStorage.getInstance();
        vname=findViewById(R.id.view_name);
        voccupation=findViewById(R.id.view_occupation);
        vcontact=findViewById(R.id.view_contactNumber);
        vaddress=findViewById(R.id.view_address);
        vemail=findViewById(R.id.view_email);
        vexp=findViewById(R.id.view_experience);
        vofficeaddress=findViewById(R.id.view_shop_address);
        vprofile=findViewById(R.id.view_image);
        vtestimonal=findViewById(R.id.view_testimonal);

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

        vprofile.setImageURI(Uri.parse(imageUri));
        vtestimonal.setImageURI(Uri.parse(testimonalUri));

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                uploadData uploadmodel1= snapshot.getValue(uploadData.class);
                dataList.add(uploadmodel1);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}