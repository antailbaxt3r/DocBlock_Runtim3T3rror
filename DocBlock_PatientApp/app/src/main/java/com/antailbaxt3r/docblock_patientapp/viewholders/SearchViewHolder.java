package com.antailbaxt3r.docblock_patientapp.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antailbaxt3r.docblock_patientapp.R;
import com.facebook.drawee.view.SimpleDraweeView;

public class SearchViewHolder extends RecyclerView.ViewHolder {


    private SimpleDraweeView image;
    private TextView docName, docDesignation;
    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.image_drawee_view);
        docName = itemView.findViewById(R.id.doc_name);
        docDesignation = itemView.findViewById(R.id.doc_desig);
    }

    public SimpleDraweeView getImage() {
        return image;
    }

    public void setImage(SimpleDraweeView image) {
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
