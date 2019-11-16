package com.antailbaxt3r.docblock_doctorapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antailbaxt3r.docblock_doctorapp.R;
import com.antailbaxt3r.docblock_doctorapp.models.Prescription;
import com.antailbaxt3r.docblock_doctorapp.viewholders.ImageViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    ArrayList<Prescription> imgList;
    Context context;

    public ImageAdapter(ArrayList<Prescription> imgList, Context context) {
        this.imgList = imgList;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.getDate().setText(imgList.get(position).getDate());
        Picasso.get().load(imgList.get(position).getImageURL()).into(holder.getImage());
        holder.setId(imgList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }
}
