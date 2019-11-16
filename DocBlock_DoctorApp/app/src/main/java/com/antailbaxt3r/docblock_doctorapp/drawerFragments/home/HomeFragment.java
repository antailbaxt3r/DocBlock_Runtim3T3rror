package com.antailbaxt3r.docblock_doctorapp.drawerFragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.antailbaxt3r.docblock_doctorapp.R;
import com.antailbaxt3r.docblock_doctorapp.adapters.RecentsRVAdapter;
import com.antailbaxt3r.docblock_doctorapp.drawerFragments.searchDocs.SearchDocsFragment;
import com.antailbaxt3r.docblock_doctorapp.models.Doctor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private LinearLayout noRecents;

    private RecyclerView recyclerView;
    private RecentsRVAdapter adapter;
    private CardView searchButton;
    private ArrayList<Doctor> docList = new ArrayList<>();
    private DatabaseReference docRef = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("recent");


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        searchButton = root.findViewById(R.id.search_button);
        noRecents = root.findViewById(R.id.no_recents);
        recyclerView = root.findViewById(R.id.recents_rv);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(((ViewGroup) getView().getParent()).getId(), new SearchDocsFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });


        docRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()){
                    noRecents.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }else{
                    docList.clear();
                    for (DataSnapshot shot : dataSnapshot.getChildren()){
                        Doctor doc = shot.getValue(Doctor.class);
                        docList.add(doc);
                    }

                    recyclerView.setVisibility(View.VISIBLE);
                    noRecents.setVisibility(View.GONE);

                    adapter = new RecentsRVAdapter(docList, getContext());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        return root;

    }


}