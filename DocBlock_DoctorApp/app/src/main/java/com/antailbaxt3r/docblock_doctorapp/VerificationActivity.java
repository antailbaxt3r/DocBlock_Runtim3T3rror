package com.antailbaxt3r.docblock_doctorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    LinearLayout verifyButton;
    private ArrayList<String> numberList = new ArrayList<>();



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
                if (regNumber.equals("AB72XY")){
                    Toast.makeText(VerificationActivity.this, "Congratulation! You have been verified.", Toast.LENGTH_SHORT).show();
                    FirebaseDatabase.getInstance().getReference().child("allDoctors").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("verified").setValue(true);
                    finish();
                }else{
                    Toast.makeText(VerificationActivity.this, "Your registration number does not match our records.", Toast.LENGTH_SHORT).show();
                    FirebaseDatabase.getInstance().getReference().child("allDoctors").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("verified").setValue(false);
                    finish();
                }
            }
        });


    }

    public void getJSON() throws IOException {


        JsonParser parser = new JsonParser();
        try {

            Object obj = parser.parse(new FileReader(
                    String.valueOf(getAssets().open("data.json"))));

            JSONObject jsonObject = (JSONObject) obj;


            System.out.println(((JSONObject) obj).get("id2"));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
