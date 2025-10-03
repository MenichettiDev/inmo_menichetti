package com.example.inmo.ui.inmueble;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InmuebleViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public InmuebleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}