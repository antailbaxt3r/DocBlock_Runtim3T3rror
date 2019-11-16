package com.antailbaxt3r.docblock_patientapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.antailbaxt3r.docblock_patientapp.models.Prescription;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private TextView name, number, email;
    private SimpleDraweeView image;
    private CardView changeImage;
    private static int PICK_IMAGE_REQUEST = 777;
    private Uri imageUri;

    private StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    private DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String UID = user.getUid();
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("Users").child(UID);

        name = findViewById(R.id.profile_name);
        number = findViewById(R.id.profile_number);
        email = findViewById(R.id.profile_email);
        image = findViewById(R.id.profile_image);
        changeImage = findViewById(R.id.change_image);


        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("username").getValue().toString());
                number.setText(dataSnapshot.child("contactNumber").getValue().toString());
                email.setText(user.getEmail().toString());

                if (dataSnapshot.child("imageURL").exists()){
                    image.setImageURI(Uri.parse(dataSnapshot.child("imageURL").toString()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog changeImageDialog = new Dialog(ProfileActivity.this);
                changeImageDialog.setContentView(R.layout.dialog_box);
                changeImageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView heading =  changeImageDialog.findViewById(R.id.dialog_box_heading);
                heading.setText("Change Profile Image?");
                TextView body = changeImageDialog.findViewById(R.id.dialog_box_body);
                body.setText("Click on OK to select a new image");
                Button positiveButton = changeImageDialog.findViewById(R.id.dialog_box_positive_button);
                positiveButton.setText("OK");
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openFileChooser();
                        changeImageDialog.dismiss();
                    }
                });
                Button negativeButton = changeImageDialog.findViewById(R.id.dialog_box_negative_button);
                negativeButton.setText("Cancel");
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeImageDialog.dismiss();
                    }
                });

                changeImageDialog.show();

            }
        });


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog changeImageDialog = new Dialog(ProfileActivity.this);
                changeImageDialog.setContentView(R.layout.dialog_box);
                changeImageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView heading =  changeImageDialog.findViewById(R.id.dialog_box_heading);
                heading.setText("Change Profile Image?");
                TextView body = changeImageDialog.findViewById(R.id.dialog_box_body);
                body.setText("Click on OK to select a new image");
                Button positiveButton = changeImageDialog.findViewById(R.id.dialog_box_positive_button);
                positiveButton.setText("OK");
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openFileChooser();
                        changeImageDialog.dismiss();
                    }
                });
                Button negativeButton = changeImageDialog.findViewById(R.id.dialog_box_negative_button);
                negativeButton.setText("Cancel");
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeImageDialog.dismiss();
                    }
                });

                changeImageDialog.show();

            }
        });

    }

    private void openFileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    private String getFileExtention(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            image.setImageURI(imageUri);
            uploadImage();
        }
    }

    private void uploadImage() {

        StorageReference fileReference = storageReference.child("profile." + getFileExtention(imageUri));
        fileReference.putFile(imageUri)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this, "Image could not be uploaded", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(ProfileActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                        userReference.child("imageURL").setValue(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                    }
                });


    }
}
