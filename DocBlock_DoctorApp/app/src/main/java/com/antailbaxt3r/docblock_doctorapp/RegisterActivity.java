package com.antailbaxt3r.docblock_doctorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.antailbaxt3r.docblock_doctorapp.api.RSA;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private EditText password, confirmPassword;
    private EditText name, email, contact, designation;
    private LinearLayout registerButton, alreadyRegistered;
    private String nameText, passwordText, emailText, confirmPasswordText, contactText, dobText;
    private LinearLayout progressBar;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference userReference = FirebaseDatabase.getInstance().getReference();

    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name_register);
        email = findViewById(R.id.email_register);
        contact = findViewById(R.id.contact_register);
        password = findViewById(R.id.password_register);
        confirmPassword = findViewById(R.id.confirm_password_register);
        registerButton = findViewById(R.id.register_button);
        alreadyRegistered = findViewById(R.id.alreadyRegistered);
        progressBar = findViewById(R.id.progress_bar);
        designation = findViewById(R.id.designation);


        firebaseAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);

                nameText = name.getText().toString();
                emailText = email.getText().toString();
                passwordText = password.getText().toString();
                confirmPasswordText = confirmPassword.getText().toString();
                contactText = contact.getText().toString();
                dobText = designation.getText().toString();

                if(!(nameText.isEmpty()) && !(emailText.isEmpty()) && !(contactText.isEmpty()) &&  !(passwordText.isEmpty()) && !(confirmPasswordText.isEmpty()) && !(dobText.isEmpty())){
                    if(passwordText.equals(confirmPasswordText)){
                        registerUser();

                    }else{
                        Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void registerUser(){

        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            email.setError("Please enter a valid email");
            email.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }

        if(passwordText.length() < 6){
            password.setError("Password length should be atleast 6 characters long");
            password.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }



        firebaseAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            final FirebaseUser user = firebaseAuth.getCurrentUser();


                            System.out.println("USER is : " + user);
                            System.out.println("UID is: " + user.getUid());
                            try {
                                KeyPair pair = RSA.generateKeyPair();
                                userReference.child("Users").child(user.getUid()).child("publicKey").setValue(pair.getPublic().toString());
                                System.out.println(pair.getPublic().toString());
                                userReference.child("Users").child(user.getUid()).child("privateKey").setValue(pair.getPrivate().toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            userReference.child("allDoctors").child(user.getUid()).child("username").setValue(nameText);
                            userReference.child("allDoctors").child(user.getUid()).child("contactNumber").setValue(contactText);
                            userReference.child("allDoctors").child(user.getUid()).child("UID").setValue(user.getUid());
                            userReference.child("allDoctors").child(user.getUid()).child("designation").setValue(dobText);

                            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String privatek = dataSnapshot.child("allDoctors").child(user.getUid()).child("privateKey").getValue().toString();
                                    privatek = privatek.replace("OpenSSLRSAPublicKey{modulus=", "");
                                    privatek = privatek.replace(",publicExponent=10001}","");
                                    String publick = dataSnapshot.child("allDoctors").child(user.getUid()).child("publicKey").getValue().toString();
                                    publick = publick.replace("OpenSSLRSAPublicKey{modulus=", "");
                                    publick = publick.replace(",publicExponent=10001}","");
                                    userReference.child("allDoctors").child(user.getUid()).child("publicKey").setValue(publick);
                                    userReference.child("allDoctors").child(user.getUid()).child("privateKey").setValue(privatek);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            Toast.makeText(RegisterActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            finish();
                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            startActivity(intent);

                        }else{

                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(RegisterActivity.this, "You are already registered", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }else{
                                Toast.makeText(RegisterActivity.this, "User could not be registered. Please try again.", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }
                });

    }

}
