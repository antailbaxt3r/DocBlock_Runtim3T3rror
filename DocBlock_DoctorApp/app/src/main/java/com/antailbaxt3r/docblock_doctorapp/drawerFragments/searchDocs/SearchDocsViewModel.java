package com.antailbaxt3r.docblock_doctorapp.drawerFragments.searchDocs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchDocsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SearchDocsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is search fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}