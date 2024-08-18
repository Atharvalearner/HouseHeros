package com.example.constructionmodel;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.constructionmodel.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginTabFragment extends Fragment {

    TextInputLayout login_email,login_password;
    TextInputEditText log_email,log_pass;
    Button login_btn;
    RadioGroup radioGroup;
    String category;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ProgressDialog progressDoalog;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_tab, container, false);

        login_email = view.findViewById(R.id.login_email);
        login_password = view.findViewById(R.id.login_password);
        log_email = view.findViewById(R.id.log_email);
        log_pass = view.findViewById(R.id.log_pass);
        login_btn = view.findViewById(R.id.login_btn);
        radioGroup = view.findViewById(R.id.log_worker_category);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        login_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                progressDoalog = new ProgressDialog(getActivity());
                progressDoalog.setTitle("Login Loading...");
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDoalog.show();

                String user_email = log_email.getText().toString().trim();
                String user_pass = log_pass.getText().toString().trim();

                if (user_email.isEmpty()){
                    login_email.setError("Please Enter Your Register Email");
                }
                if (user_pass.isEmpty()){
                    login_password.setError("Please Enter Register Password");
                }
                if (category == null){
                    Toast.makeText(getActivity(), "Please set your gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(log_email.getText().toString(),log_pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (category == "Customer"){
                                Intent intent=new Intent(getActivity(),Customer_Home.class);
                                startActivity(intent);
                                Toast.makeText(getActivity(), "Login Successfully...", Toast.LENGTH_SHORT).show();
                                progressDoalog.dismiss();
                            }else if(category == "Working Professsional"){
//                                    if(firebaseUser != null) {
//                                        Intent intent=new Intent(getActivity(),Worker_Profile_View.class);
//                                        startActivity(intent);
//                                    }
//                                    else{
//                                        Intent intent=new Intent(getActivity(),Worker.class);
//                                        startActivity(intent);
//                                    }
                                Intent intent=new Intent(getActivity(), Worker_Set_profile.class);
                                startActivity(intent);
                                Toast.makeText(getActivity(), "Login Successfully...", Toast.LENGTH_SHORT).show();
                                progressDoalog.dismiss();
                            }
                        }else {
                            Toast.makeText(getActivity(), "Fill Credentials correctly", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {
            if (i == R.id.log_customer){
                category = "Customer";
            }else {
                category = "Working Professsional";
            }
        });
        return view;
    }

}