package com.antailbaxt3r.docblock_doctorapp.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antailbaxt3r.docblock_doctorapp.R;

public class SearchViewHolder extends RecyclerView.ViewHolder {


    private ImageView image;
    private TextView docName, docDesignation;
    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.image_drawee_view);
        docName = itemView.findViewById(R.id.doc_name);
        docDesignation = itemView.findViewById(R.id.doc_desig);
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
