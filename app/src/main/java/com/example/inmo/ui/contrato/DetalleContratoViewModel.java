package com.example.inmo.ui.contrato;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmo.models.Contrato;
import com.example.inmo.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {

    private MutableLiveData<Contrato> contratoMutable = new MutableLiveData<>();

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Contrato> getContrato() {
        return contratoMutable;
    }

    public void cargarContrato(int idInmueble) {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        Call<Contrato> call = api.getContratoByInmueble("Bearer " + token, idInmueble);
        Log.d("DetalleContratoVM", "Solicitando contrato para inmueble id: " + idInmueble);
        call.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                Log.d("DetalleContratoVM", "onResponse: code=" + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("DetalleContratoVM", "Contrato recibido: " + response.body().toString());
                    contratoMutable.postValue(response.body());
                } else {
                    Log.e("DetalleContratoVM", "Respuesta no exitosa o vacía");
                }
            }
            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Log.e("DetalleContratoVM", "Error en la solicitud: " + t.getMessage());
            }
        });
    }

}