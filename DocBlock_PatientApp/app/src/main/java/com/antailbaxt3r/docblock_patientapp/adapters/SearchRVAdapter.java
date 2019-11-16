package com.antailbaxt3r.docblock_patientapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antailbaxt3r.docblock_patientapp.R;
import com.antailbaxt3r.docblock_patientapp.models.Doctor;
import com.antailbaxt3r.docblock_patientapp.viewholders.RecentsViewHolder;
import com.antailbaxt3r.docblock_patientapp.viewholders.SearchViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SearchRVAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    ArrayList<Doctor> docList, docListFiltered;
    Context context;

    public SearchRVAdapter(ArrayList<Doctor> docList, Context context) {
        this.docList = docList;
        this.context = context;
        this.docListFiltered = new ArrayList<>(docList);
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_layout_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {

        holder.getDocName().setText(docList.get(position).getName());
        holder.getDocDesignation().setText(docList.get(position).getDesignation());
        if (!docList.get(position).getImageURL().isEmpty()) {
            holder.getImage().setImageURI(Uri.parse(docList.get(position).getImageURL()));
        }else{
            holder.getImage().setImageResource(R.drawable.avatar);
        }
    }

    @Override
    public int getItemCount() {
        return docList.size();
    }

    public Filter getFilter(){
        return pokemonFilter;
    }

    private Filter pokemonFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Doctor> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(docList);
            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(Doctor item : docListFiltered){

                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            docList.clear();
            docList = (ArrayList<Doctor>) filterResults.values;
            notifyDataSetChanged();
        }
    };
}
