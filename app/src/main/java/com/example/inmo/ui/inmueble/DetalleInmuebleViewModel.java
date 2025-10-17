package com.example.inmo.ui.inmueble;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmo.models.Inmueble;
import com.example.inmo.request.ApiClient;

import java.util.List;

import retrofit2.Call;

public class DetalleInmuebleViewModel extends AndroidViewModel {
//    private MutableLiveData<Inmueble> mutableInmueble;
    private MutableLiveData<Inmueble> mutableInmueble = new MutableLiveData<>();

    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Inmueble> getMutableInmueble(){
        if(mutableInmueble == null){
            mutableInmueble = new MutableLiveData<>();
        }
        return mutableInmueble;
    }


    public void recuperarInmueble(Bundle bundle){
        Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
        if(inmueble != null){
            mutableInmueble.setValue(inmueble);
        }
    }

    public void updateInmueble(Inmueble inmueble){
        Inmueble actual = mutableInmueble.getValue();
        if (actual == null) {
            Toast.makeText(getApplication(), "No hay inmueble cargado para actualizar", Toast.LENGTH_SHORT).show();
            Log.e("DetalleInmuebleVM", "mutableInmueble es null, no se puede actualizar");
            return;
        }
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoServicio servicio = ApiClient.getInmoServicio();
        inmueble.setIdInmueble(mutableInmueble.getValue().getIdInmueble());
        Call<Inmueble> call = servicio.updateInmueble("Bearer "+token, inmueble);

        call.enqueue(new retrofit2.Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, retrofit2.Response<Inmueble> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplication(), "Inmueble actualizado", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplication(), "No se pudo actualizar el inmueble", Toast.LENGTH_SHORT).show();
                    Log.d("errorInmueble",response.message());
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Toast.makeText(getApplication(), "No se pudo actualizar el inmueble", Toast.LENGTH_SHORT).show();
                Log.d("errorInmueble",t.getMessage());
            }
        });


    }
}