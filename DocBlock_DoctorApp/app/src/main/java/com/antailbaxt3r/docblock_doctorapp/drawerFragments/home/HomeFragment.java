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
import com.antailbaxt3r.docblock_doctorapp.adapters.QueryAdapter;
import com.antailbaxt3r.docblock_doctorapp.adapters.RecentsRVAdapter;
import com.antailbaxt3r.docblock_doctorapp.drawerFragments.searchDocs.SearchDocsFragment;
import com.antailbaxt3r.docblock_doctorapp.models.Doctor;
import com.antailbaxt3r.docblock_doctorapp.models.QueryModel;
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
    private QueryAdapter adapter;
    private CardView searchButton;
    private ArrayList<QueryModel> qList = new ArrayList<>();
    private DatabaseReference docRef = FirebaseDatabase.getInstance().getReference().child("allDoctors").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        searchButton = root.findViewById(R.id.search_button);
        noRecents = root.findViewById(R.id.no_recents);
        recyclerView = root.findViewById(R.id.recents_rv);




        docRef.child("queries").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()){
                    noRecents.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }else{
                    qList.clear();
                    for (DataSnapshot shot : dataSnapshot.getChildren()){

                        String problem = shot.child("problem").getValue().toString();
                        String duration = shot.child("duration").getValue().toString();
                        String name = shot.child("sender").child("username").getValue().toString();
                        String UID = shot.child("sender").child("UID").getValue().toString();

                        QueryModel q = new QueryModel(problem, duration, UID, name);
                        qList.add(q);
                    }

                    recyclerView.setVisibility(View.VISIBLE);
                    noRecents.setVisibility(View.GONE);

                    adapter = new QueryAdapter(qList);
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