package com.antailbaxt3r.docblock_patientapp.drawerFragments.searchDocs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.antailbaxt3r.docblock_patientapp.R;
import com.antailbaxt3r.docblock_patientapp.adapters.RecentsRVAdapter;
import com.antailbaxt3r.docblock_patientapp.adapters.SearchRVAdapter;
import com.antailbaxt3r.docblock_patientapp.models.Doctor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchDocsFragment extends Fragment {

    private SearchDocsViewModel searchDocsViewModel;
    private RecyclerView recyclerView;
    private SearchRVAdapter adapter;
    private ArrayList<Doctor> docList = new ArrayList<>();
    private DatabaseReference docRef = FirebaseDatabase.getInstance().getReference().child("allDoctors");


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchDocsViewModel =
                ViewModelProviders.of(this).get(SearchDocsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search_docs, container, false);
        recyclerView = root.findViewById(R.id.allDocs_rv);
        docRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    docList.clear();
                    for (DataSnapshot shot : dataSnapshot.getChildren()){
                        Doctor doctor = shot.getValue(Doctor.class);
                        docList.add(doctor);
                    }

                    adapter = new SearchRVAdapter(docList, getContext());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        return root;
    }
}