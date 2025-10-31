package com.example.inmo.ui.contrato;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmo.models.Inmueble;
import com.example.inmo.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoViewModel extends AndroidViewModel {
    private MutableLiveData<List<Inmueble>> listaContratos = new MutableLiveData<>();
    public ContratoViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<List<Inmueble>> getListaContratos() {
        return listaContratos;
    }

    public void obtenerContratos() {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        Call<List<Inmueble>> call = api.getInmuebleContratoVigente("Bearer " + token);

        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaContratos.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) { }
        });
    }

}