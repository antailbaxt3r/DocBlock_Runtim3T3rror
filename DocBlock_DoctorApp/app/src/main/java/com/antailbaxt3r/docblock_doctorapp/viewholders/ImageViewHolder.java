package com.antailbaxt3r.docblock_doctorapp.viewholders;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antailbaxt3r.docblock_doctorapp.OpenImage;
import com.antailbaxt3r.docblock_doctorapp.R;

public class ImageViewHolder extends RecyclerView.ViewHolder {

    private TextView date;
    private String id;
    private ImageView image;

    public ImageViewHolder(@NonNull final View itemView) {
        super(itemView);

        date = itemView.findViewById(R.id.image_date);
        image = itemView.findViewById(R.id.image_image);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), OpenImage.class);
                intent.putExtra("id", id);
                itemView.getContext().startActivity(intent);

            }
        });
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
}
