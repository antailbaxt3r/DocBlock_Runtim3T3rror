package com.antailbaxt3r.docblock_patientapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antailbaxt3r.docblock_patientapp.R;
import com.antailbaxt3r.docblock_patientapp.models.Doctor;
import com.antailbaxt3r.docblock_patientapp.viewholders.RecentsViewHolder;

import java.util.ArrayList;

public class RecentsRVAdapter extends RecyclerView.Adapter<RecentsViewHolder> {

    ArrayList<Doctor> docList;
    Context context;

    public RecentsRVAdapter(ArrayList<Doctor> docList, Context context) {
        this.docList = docList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecentsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecentsViewHolder holder, int position) {

        holder.getDocName().setText(docList.get(position).getName());
        holder.getDocDesignation().setText(docList.get(position).getDesignation());
        if (!docList.get(position).getImageURL().isEmpty()) {
            holder.getImage().setImageURI(Uri.parse(docList.get(position).getImageURL()));
        }else{
            holder.getImage().setActualImageResource(R.drawable.avatar);
        }
    }

    @Override
    public int getItemCount() {
        return docList.size();
    }
}
