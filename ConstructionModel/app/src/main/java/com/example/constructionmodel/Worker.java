package com.example.constructionmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.content.ContentResolver;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class Worker extends AppCompatActivity {
    EditText name,occupation,address,contactno,email,workexp,officeaddress;
    Button save;
    ImageView testimonals,profileImage;

    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage mainStorage;
    private static final int Gallary_code = 1;
    private static final int Img_code = 2;
    Uri imageUrl = null, testimonal = null;
    ArrayList<String> workerid;
    Toolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker);

        profileImage = findViewById(R.id.profile_image);
        name = findViewById(R.id.worker_name);
        occupation = findViewById(R.id.worker_occupation);
        address = findViewById(R.id.worker_address);
        contactno = findViewById(R.id.worker_contactNumber);
        email = findViewById(R.id.worker_email);
        workexp = findViewById(R.id.worker_experience);
        officeaddress = findViewById(R.id.worker_shop_address);
        testimonals = findViewById(R.id.imgupload);
        save = findViewById(R.id.save);
        toolbar = findViewById(R.id.worker_toolbar);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mainStorage = FirebaseStorage.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        FirebaseApp.initializeApp(this);

        workerid = new ArrayList<>();
        workerid.add("WorkerId");

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,Gallary_code);
            }
        });
        testimonals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,Img_code);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitdata(imageUrl,testimonal);
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuId = item.getItemId();
                if (menuId == R.id.fourth){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Worker.this);
                    builder.setTitle("Alert for Logout");
                    builder.setMessage("You really want to logout");
                    builder.setIcon(R.drawable.logo_bw);

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Worker.this,MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(Worker.this, "Logout Successfull", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(Worker.this, "Logout Cancel", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK){
            if (requestCode == Gallary_code) {
                imageUrl = data.getData();
                profileImage.setImageURI(imageUrl);
            }
            else if(requestCode == Img_code){
                testimonal = data.getData();
                testimonals.setImageURI(testimonal);
            }
        }
    }
    private void submitdata(Uri imagePath,Uri testimonal){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Saving Profile...");
        pd.show();

        final StorageReference image_reference = storageReference.child("image/" + System.currentTimeMillis() + "." + getFileExtension(imageUrl));
        final StorageReference testimonal_reference = storageReference.child("testimage/" + System.currentTimeMillis() + "." + getFileExtension(this.testimonal));

        image_reference.putFile(imagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image_reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                    @Override
                    public void onSuccess(Uri uri) {
                        String imageURL = uri.toString();
                        testimonal_reference.putFile(testimonal).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                testimonal_reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                                    @Override
                                public void onSuccess(Uri uri) {
                                    String testimonalURL = uri.toString();
                                    String id = firebaseDatabase.getReference().push().getKey();

                                        uploadData uploadData1 = new uploadData();
                                        uploadData1.setImage(imageURL);
                                        uploadData1.setName(name.getText().toString().trim());
                                        uploadData1.setOccupation(occupation.getText().toString().trim());
                                        uploadData1.setAddress(address.getText().toString().trim());
                                        uploadData1.setContact(contactno.getText().toString().trim());
                                        uploadData1.setEmail(email.getText().toString().trim());
                                        uploadData1.setExperience(workexp.getText().toString().trim());
                                        uploadData1.setOfficeAddress(officeaddress.getText().toString().trim());
                                        uploadData1.setTestimonals(testimonalURL);

                                        uploadData1.setId(id.toString());

                                        firebaseDatabase.getReference().child("Worker").child(id).setValue(uploadData1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(Worker.this, "Profile saved successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent1 = new Intent(Worker.this, Worker_Profile_View.class);
                                                startActivity(intent1);
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                float percent = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                pd.setMessage("Uploaded :" + (int) percent + "%");
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}