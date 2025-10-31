package com.example.inmo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MainActivityViewModel extends AndroidViewModel {


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    private MutableLiveData<Boolean> llamarEvent = new MutableLiveData<>(false);

    public LiveData<Boolean> getLlamarEvent() {
        return llamarEvent;
    }

    public void onShakeDetected() {
        llamarEvent.setValue(true);
    }

    public void onLlamadaRealizada() {
        llamarEvent.setValue(false);
    }
}
