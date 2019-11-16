package com.antailbaxt3r.docblock_patientapp.drawerFragments.yourDocs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class YourDocsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public YourDocsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is yourDocs fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}