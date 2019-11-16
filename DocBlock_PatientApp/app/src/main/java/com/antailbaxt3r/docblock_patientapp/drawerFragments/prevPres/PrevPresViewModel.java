package com.antailbaxt3r.docblock_patientapp.drawerFragments.prevPres;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PrevPresViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PrevPresViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is prevPres fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}