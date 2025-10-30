package com.example.inmo.ui.inquilino;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmo.models.Inmueble;
import com.example.inmo.request.ApiClient;

import java.util.List;

import retrofit2.Call;

public class InquilinoViewModel extends AndroidViewModel {
    public MutableLiveData<List<Inmueble>> listaInmuebleMutable = new MutableLiveData<>();
    public InquilinoViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<List<Inmueble>> getMutableInmueble() {
        return listaInmuebleMutable;
    }

    public void getInmueblesAlquilados(){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoServicio servicio = ApiClient.getInmoServicio();
        Call<List<Inmueble>> listaInmueble = servicio.getInmuebleContratoVigente("Bearer " + token);

        listaInmueble.enqueue(new retrofit2.Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, retrofit2.Response<List<Inmueble>> response) {
                if(response.isSuccessful()) {

                    listaInmuebleMutable.postValue(response.body());

                }else{
                    Log.d("InmuebleViewModel", "Error al obtener el inmueble: " + response.code());
                    Toast.makeText(getApplication(), "Error al obtener el inmueble", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Log.d("errorInmueble",t.getMessage());

                Toast.makeText(getApplication(),"Error al obtener Inmuebles",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void obtenerInquilinoPorInmueble(int id){

    }

}