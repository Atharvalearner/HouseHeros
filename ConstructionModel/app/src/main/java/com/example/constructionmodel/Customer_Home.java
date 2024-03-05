package com.example.constructionmodel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

public class Customer_Home extends AppCompatActivity {

    GridView gridView;
    GridAdapter gridAdapter;
    String[] occ_names = {"Builder","Contractor","Architect","Estate Agent","Retailer","Electrician","Labour","Plumber","Tools Distributer","Interior Designer","Painter","Furniture","Fabrication","Carpenter"};
    int[] occ_images = {R.drawable.builder,R.drawable.contractor,R.drawable.architect,R.drawable.estate1,R.drawable.retail,R.drawable.elec,R.drawable.labour1,R.drawable.plumber,R.drawable.tool,R.drawable.interior,R.drawable.painter,R.drawable.furnit,R.drawable.fabric,R.drawable.carp};
    Toolbar toolbar;
    ProgressDialog progressDoalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        toolbar = findViewById(R.id.toolbar);
        gridView = findViewById(R.id.grid_view);

        gridAdapter = new GridAdapter(this,occ_names,occ_images);
        gridView.setAdapter(gridAdapter);
        gridView.smoothScrollToPosition(0);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(position == id){
                    progressDoalog = new ProgressDialog(Customer_Home.this);
                    progressDoalog.setTitle("Data Fetching Please wait...");
                    progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDoalog.show();
                    Intent intent = new Intent(Customer_Home.this, Profile_Dashboard.class);
                    intent.putExtra("welcome to ",occ_names[position]);
                    startActivity(intent);
                }
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuId = item.getItemId();
                if (menuId == R.id.fourth){
                    Toast.makeText(Customer_Home.this, "Logout", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(Customer_Home.this);
                    builder.setTitle("Alert for Logout");
                    builder.setMessage("You really want to logout");
                    builder.setIcon(R.drawable.logo_bw);

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Customer_Home.this,MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(Customer_Home.this, "Logout Successfull", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(Customer_Home.this, "Logout Cancel", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                }
                return true;
            }
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.customer_home_menu,menu);
        return true;
    }
}