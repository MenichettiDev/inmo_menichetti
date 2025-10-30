package com.example.inmo.ui.inquilino;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmo.models.Contrato;
import com.example.inmo.models.Inmueble;
import com.example.inmo.models.Inquilino;
import com.example.inmo.request.ApiClient;

import java.util.List;

import retrofit2.Call;

public class DetalleInquilinoViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> mutableInmueble = new MutableLiveData<>();
    private MutableLiveData<Inquilino> mutableInquilino = new MutableLiveData<>();
    private MutableLiveData<Contrato> mutableContrato = new MutableLiveData<>();
    public DetalleInquilinoViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Inmueble> getMutableInmueble(){
        if(mutableInmueble == null){
            mutableInmueble = new MutableLiveData<>();
        }
        return mutableInmueble;
    }

    public MutableLiveData<Inquilino> getMutableInquilino(){
        if(mutableInquilino == null){
            mutableInquilino = new MutableLiveData<>();
        }
        return mutableInquilino;
    }

    public MutableLiveData<Contrato> getMutableContrato(){
        if(mutableContrato == null){
            mutableContrato = new MutableLiveData<>();
        }
        return mutableContrato;
    }

    public void recuperarInquilino(int idInmueble){

        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoServicio servicio = ApiClient.getInmoServicio();
        Call<Contrato> call = servicio.getContratoByInmueble("Bearer "+token, idInmueble);

        call.enqueue(new retrofit2.Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, retrofit2.Response<Contrato> response) {
                if(response.isSuccessful()) {

                    mutableInquilino.postValue(response.body().getInquilino());

                }else{
                    Log.d("Detalle inquilino VM", "Error al obtener el contrato: " + response.code());
                    Toast.makeText(getApplication(), "Error al obtener el contrato", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Log.d("errorContrato",t.getMessage());

                Toast.makeText(getApplication(),"Error al obtener Contrato",Toast.LENGTH_LONG).show();
            }

        });
    }
}