package com.antailbaxt3r.docblock_patientapp.drawerFragments.yourDocs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.antailbaxt3r.docblock_patientapp.R;
import com.antailbaxt3r.docblock_patientapp.adapters.SearchRVAdapter;
import com.antailbaxt3r.docblock_patientapp.models.Doctor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class YourDocsFragment extends Fragment {

    private YourDocsViewModel yourDocsViewModel;
    private RecyclerView recyclerView;
    private LinearLayout noDocs;
    private DatabaseReference docRef = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("yourDocs");

    private ArrayList<Doctor> docList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        yourDocsViewModel =
                ViewModelProviders.of(this).get(YourDocsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_yourdocs, container, false);
        recyclerView = root.findViewById(R.id.your_docs_rv);
        noDocs = root.findViewById(R.id.no_docs);

        noDocs.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        docRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()){
                    noDocs.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }else {
                    for (DataSnapshot shot : dataSnapshot.getChildren()){
                        Doctor doctor = shot.getValue(Doctor.class);
                        docList.add(doctor);
                    }
                    noDocs.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    recyclerView.setAdapter(new SearchRVAdapter(docList, getContext()));
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