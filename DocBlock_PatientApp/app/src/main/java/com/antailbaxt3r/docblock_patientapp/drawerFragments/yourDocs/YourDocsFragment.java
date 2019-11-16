package com.antailbaxt3r.docblock_patientapp.drawerFragments.yourDocs;

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

public class YourDocsFragment extends Fragment {

    private YourDocsViewModel yourDocsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        yourDocsViewModel =
                ViewModelProviders.of(this).get(YourDocsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_yourdocs, container, false);



        return root;
    }
}