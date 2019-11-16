package com.antailbaxt3r.docblock_patientapp.drawerFragments.searchDocs;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.antailbaxt3r.docblock_patientapp.R;
import com.antailbaxt3r.docblock_patientapp.adapters.RecentsRVAdapter;
import com.antailbaxt3r.docblock_patientapp.adapters.SearchRVAdapter;
import com.antailbaxt3r.docblock_patientapp.models.Doctor;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchDocsFragment extends Fragment {

    private SearchDocsViewModel searchDocsViewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private SearchRVAdapter adapter;
    private ArrayList<Doctor> docList = new ArrayList<>();
    private SearchView searchView;
    private androidx.appcompat.widget.SearchView.OnQueryTextListener searchQuery;
    private DatabaseReference docRef = FirebaseDatabase.getInstance().getReference().child("allDoctors");


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchDocsViewModel =
                ViewModelProviders.of(this).get(SearchDocsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search_docs, container, false);
        recyclerView = root.findViewById(R.id.allDocs_rv);
        fab = root.findViewById(R.id.search_fab);
        searchView = root.findViewById(R.id.searchView);
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setVisibility(View.VISIBLE);
                searchView.setIconified(false);
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.setVisibility(View.GONE);
                docList.clear();
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

                return true;
            }
        });
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);


        if (searchView != null){
            searchQuery = new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    if (adapter != null) {
                        adapter.getFilter().filter(s);
                    }

                    return false;
                }
            };
            searchView.setOnQueryTextListener(searchQuery);
        }

        searchView.setOnQueryTextListener(searchQuery);



        return root;
    }
}