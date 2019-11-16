package com.antailbaxt3r.docblock_patientapp.viewholders;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antailbaxt3r.docblock_patientapp.OpenDoctorQuery;
import com.antailbaxt3r.docblock_patientapp.R;
import com.facebook.drawee.view.SimpleDraweeView;

public class SearchViewHolder extends RecyclerView.ViewHolder {


    private ImageView image;
    private TextView docName, docDesignation;
    private String UID;
    public SearchViewHolder(@NonNull final View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.image_drawee_view);
        docName = itemView.findViewById(R.id.doc_name);
        docDesignation = itemView.findViewById(R.id.doc_desig);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), OpenDoctorQuery.class);
                intent.putExtra("uid", UID);
                itemView.getContext().startActivity(intent);
            }
        });

    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public TextView getDocName() {
        return docName;
    }

    public void setDocName(TextView docName) {
        this.docName = docName;
    }

    public TextView getDocDesignation() {
        return docDesignation;
    }

    public void setDocDesignation(TextView docDesignation) {
        this.docDesignation = docDesignation;
    }
}
