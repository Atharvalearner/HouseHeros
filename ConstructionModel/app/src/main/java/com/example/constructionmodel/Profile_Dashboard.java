package com.example.constructionmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class Profile_Dashboard extends AppCompatActivity {

    FirebaseDatabase mainDatabase;
    FirebaseStorage mainStorage;
    DatabaseReference mainRef;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    List<uploadData> workerSummerizeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_dashboard);

        workerSummerizeList = new ArrayList<uploadData>();
        mainDatabase = FirebaseDatabase.getInstance();
        mainStorage = FirebaseStorage.getInstance();
        mainRef = mainDatabase.getReference().child("Worker");
        recyclerView = findViewById(R.id.recycler_view);

        initComponents();
        mainRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                uploadData uploadData1 = snapshot.getValue(uploadData.class);
                workerSummerizeList.add(uploadData1);
                recyclerAdapter.notifyDataSetChanged();
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
    private void initComponents() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new RecyclerAdapter(this,workerSummerizeList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.smoothScrollToPosition(0);

    }
}