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

import com.antailbaxt3r.docblock_patientapp.R;

public class SearchDocsFragment extends Fragment {

    private SearchDocsViewModel searchDocsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchDocsViewModel =
                ViewModelProviders.of(this).get(SearchDocsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search_docs, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        searchDocsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}