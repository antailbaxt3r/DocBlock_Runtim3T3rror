package com.antailbaxt3r.docblock_patientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class OpenDoctorQuery extends AppCompatActivity {

    EditText problem, duration;
    LinearLayout send;
    String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_doctor_query);

        problem = findViewById(R.id.problem_et);
        duration = findViewById(R.id.duration_et);
        send = findViewById(R.id.send_btn);
        UID = getIntent().getStringExtra("uid");

        final DatabaseReference docRef = FirebaseDatabase.getInstance().getReference().child("allDoctors").child(UID);
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        docRef.child("queries").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    TextView ptv = findViewById(R.id.problem_tv);
                    TextView dtv = findViewById(R.id.duration_tv);

                    ptv.setText(dataSnapshot.child("problem").getValue().toString());
                    ptv.setText(dataSnapshot.child("duration").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String problemText = problem.getText().toString();
                String durationText = duration.getText().toString();

                docRef.child("queries").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("problem").setValue(problemText);
                docRef.child("queries").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("duration").setValue(durationText);

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        docRef.child("queries").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("sender").child("username").setValue(dataSnapshot.child("username").getValue().toString());
                        docRef.child("queries").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("sender").child("UID").setValue(dataSnapshot.child("UID").getValue().toString());
                        docRef.child("queries").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("sender").child("publicKey").setValue(dataSnapshot.child("publicKey").getValue().toString());
                        docRef.child("queries").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("sender").child("dob").setValue(dataSnapshot.child("dob").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                docRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        userRef.child("recent").child(UID).child("username").setValue(dataSnapshot.child("username").getValue().toString());
                        userRef.child("recent").child(UID).child("UID").setValue(dataSnapshot.child("UID").getValue().toString());
                        userRef.child("recent").child(UID).child("designation").setValue(dataSnapshot.child("designation").getValue().toString());
                        userRef.child("recent").child(UID).child("imageURL").setValue(dataSnapshot.child("imageURL").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                Toast.makeText(OpenDoctorQuery.this, "Query Sent. Check your prescriptions in 1 hour.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OpenDoctorQuery.this, HomeActivity.class);
                startActivity(intent);

            }
        });


    }
}
