package com.antailbaxt3r.docblock_doctorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VerificationActivity extends AppCompatActivity {

    EditText reg;
    LinearLayout verifyButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        DatabaseReference docReference = FirebaseDatabase.getInstance().getReference().child("allDoctors").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reg = findViewById(R.id.reg_number);
        verifyButton = findViewById(R.id.verify_button);

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String regNumber = reg.getText().toString();

            }
        });


    }
}
