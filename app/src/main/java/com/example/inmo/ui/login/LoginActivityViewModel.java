package com.example.inmo.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmo.MainActivity;
import com.example.inmo.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<String> errorMutable;
    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context = getApplication();

    }


    public LiveData<String> getErrorMutableLiveData() {
        if (errorMutable == null) {
            errorMutable = new MutableLiveData<>();
        }
        return errorMutable;
    }

    public void login(String email, String password){
        if (email.isEmpty() || password.isEmpty()) {
            errorMutable.setValue("Todos los campos son obligatorios");
            return;
        }

        //Instancia de inmoServicio
        ApiClient.InmoServicio inmoServicio = ApiClient.getInmoServicio();
        //Llamada a la API de login --> asyncrono
        Call<String> call = inmoServicio.loginForm(email, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if( response.isSuccessful() ) {
                    String token = response.body();
                    ApiClient.guardarToken(context,token);
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(intent);
            }else{
                    Log.d("Error", response.message());
                    Log.d("Error", response.code()+"");
                    Log.d("Error", response.errorBody().toString());
                }
                }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                errorMutable.setValue(t.getMessage());
            }
        });

    }
}
