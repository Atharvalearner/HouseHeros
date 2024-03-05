package com.example.constructionmodel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupTabFragment extends Fragment {

    TextInputLayout signup_email,signup_password;
    TextInputEditText sign_email,sign_pass;
    Button sign_btn;
    FirebaseAuth firebaseAuth;
    String category;
    RadioGroup radioGroup;
    ProgressDialog progressDoalog;
//    Spinner spinner;
//    String operator[] = {"Customer","Builder","Retailer","Contractor","Electrician","Real Estate Agent","Labour","Plumber","Tools Operator","Painter","Interior Designer"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_tab, container, false);

//        spinner = view.findViewById(R.id.signup_role);
        signup_email = view.findViewById(R.id.signup_email);
        signup_password = view.findViewById(R.id.signup_password);
        sign_email = view.findViewById(R.id.sign_email);
        sign_pass = view.findViewById(R.id.sign_pass);
        sign_btn = view.findViewById(R.id.signup_btn);
        radioGroup = view.findViewById(R.id.worker_category);
        firebaseAuth = FirebaseAuth.getInstance();

        sign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDoalog = new ProgressDialog(getActivity());
                progressDoalog.setTitle("Signup Loading...");
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDoalog.show();

                String user_email = sign_email.getText().toString().trim();
                String user_pass = sign_pass.getText().toString().trim();

                if (user_email.isEmpty()){
                    signup_email.setError("Please Enter Email");
                }
                if (user_pass.isEmpty()){
                    signup_password.setError("Please Enter Password");
                }
                if (category == null){
                    Toast.makeText(getActivity(), "Please set your gender", Toast.LENGTH_SHORT).show();
                    return;

                }
                firebaseAuth.createUserWithEmailAndPassword(sign_email.getText().toString(),sign_pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(getActivity(), "Register Successfully...", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getActivity(),LoginTabFragment.class);
                        startActivity(intent);
                        progressDoalog.dismiss();
                    }
                });
            }
        });

        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {
            if (i == R.id.sign_customer){
                category = "Professional Working";
            }else {
                category = "Customer";
            }
        });
//        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,operator);
//        spinner.setAdapter(arrayAdapter);

        return view;
    }
}