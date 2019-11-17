package com.antailbaxt3r.docblock_doctorapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RespondToQuery extends AppCompatActivity {

    LinearLayout uploadButton, sendButton;
    ImageView image;
    private static int PICK_IMAGE_REQUEST = 777;
    Uri imageUri;
    String patientUID;
    LinearLayout progressBar;

    private StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("allDoctors").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("prescriptions");
    private DatabaseReference patientRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respond_to_query);

        uploadButton = findViewById(R.id.upload_btn);
        sendButton = findViewById(R.id.send_btn);
        image = findViewById(R.id.pres_image);
        progressBar = findViewById(R.id.progress_bar);

        patientUID = getIntent().getStringExtra("uid");

        patientRef = FirebaseDatabase.getInstance().getReference().child("Users").child(patientUID).child("prescriptions");
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                uploadImage();
            }
        });

    }
    private void openFileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            image.setImageURI(imageUri);
        }
    }

    private String getFileExtention(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadImage() {

        final String id = String.valueOf( System.currentTimeMillis());
        final StorageReference fileReference = storageReference.child(id + "." + getFileExtention(imageUri));
        fileReference.putFile(imageUri)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RespondToQuery.this, "Image could not be uploaded", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(RespondToQuery.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                        fileReference.getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        patientRef.child(id).child("imageURL").setValue(uri.toString());
                                    }
                                });


                        patientRef.child(id).child("id").setValue(id);
                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                        String formattedDate = df.format(c);
                        patientRef.child(id).child("date").setValue(formattedDate);
                        progressBar.setVisibility(View.GONE);

                        Intent intent = new Intent(RespondToQuery.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });


    }
}
