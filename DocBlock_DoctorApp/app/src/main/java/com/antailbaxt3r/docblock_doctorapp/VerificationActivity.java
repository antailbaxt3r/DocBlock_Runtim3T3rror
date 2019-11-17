package com.antailbaxt3r.docblock_doctorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class VerificationActivity extends AppCompatActivity {

    EditText reg;
    TextView tempText;
    ArrayList<String> list = new ArrayList<>();
    LinearLayout verifyButton;
    private ArrayList<String> numberList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        DatabaseReference docReference = FirebaseDatabase.getInstance().getReference().child("allDoctors").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reg = findViewById(R.id.reg_number);
        verifyButton = findViewById(R.id.verify_button);
        tempText = findViewById(R.id.tempText);

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String regNumber = reg.getText().toString();
                if (!regNumber.isEmpty()){
                String name = null;
                try {
                    name = getJSON(regNumber);
                    if (name == null){
                        list.add(0, "");
                    }else{
                        list.add(0,name);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                FirebaseDatabase.getInstance().getReference().child("allDoctors").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                String uploadedName = dataSnapshot.child("username").getValue().toString();

                                list.add(1, uploadedName);

//                                System.out.println("GET TEXT : " + list.get(0));
//                                System.out.println("NAME : " + name);
                                if (list.get(0).equals(list.get(1))) {
                                    Toast.makeText(VerificationActivity.this, "Congratulation! You have been verified.", Toast.LENGTH_SHORT).show();
                                    FirebaseDatabase.getInstance().getReference().child("allDoctors").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child("verified").setValue(true);
                                    finish();
                                } else {
                                    Toast.makeText(VerificationActivity.this, "Your registration number does not match our records.", Toast.LENGTH_SHORT).show();
                                    FirebaseDatabase.getInstance().getReference().child("allDoctors").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child("verified").setValue(false);
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });





            }else{
                    Toast.makeText(VerificationActivity.this, "Please Fill The Field", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public String getJSON(String givenID) throws IOException {

        String json = null;
        JsonParser parser = new JsonParser();
        try {
            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            try {

                Object obj = new JSONObject(json);

                JSONObject jsonObject = (JSONObject) obj;
                return (String) ((JSONObject) obj).get(givenID);


            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
