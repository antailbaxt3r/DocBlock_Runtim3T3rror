package com.antailbaxt3r.docblock_doctorapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antailbaxt3r.docblock_doctorapp.R;
import com.antailbaxt3r.docblock_doctorapp.models.QueryModel;
import com.antailbaxt3r.docblock_doctorapp.viewholders.QueryViewHolder;

import java.util.ArrayList;

public class QueryAdapter extends RecyclerView.Adapter<QueryViewHolder> {

    ArrayList<QueryModel> qList;

    public QueryAdapter(ArrayList<QueryModel> qList) {
        this.qList = qList;
    }

    @NonNull
    @Override
    public QueryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QueryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.query_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull QueryViewHolder holder, int position) {
        holder.getProblem().setText(qList.get(position).getProblem());
        holder.getDuration().setText(qList.get(position).getDuration());
        holder.getSender().setText(qList.get(position).getName());
        holder.setUID(qList.get(position).getUID());
    }

    @Override
    public int getItemCount() {
        return qList.size();
    }
}
