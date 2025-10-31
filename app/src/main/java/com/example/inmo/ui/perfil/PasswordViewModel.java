package com.example.inmo.ui.perfil;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.inmo.models.Propietario;
import com.example.inmo.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;

public class PasswordViewModel extends AndroidViewModel {
    public PasswordViewModel(@NonNull Application application) {
        super(application);
    }

    public void cambiarBoton(String pass, String nuevaPass, String repetirPass) {
        //Validacion de contrasenias
        if (!nuevaPass.equals(repetirPass)) {
            Toast.makeText(getApplication(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }
        //validacion de campos
        if (pass.isEmpty() || nuevaPass.isEmpty() || repetirPass.isEmpty()) {
            Toast.makeText(getApplication(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Propietario nuevo = new Propietario();
            nuevo.setClave("DEEKQW");

            String token = ApiClient.leerToken(getApplication());
            ApiClient.InmoServicio servicio = ApiClient.getInmoServicio();
            Call<Propietario> propietarioCreado = servicio.editPropietario("Bearer " + token, nuevo);
            propietarioCreado.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, retrofit2.Response<Propietario> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplication(), "Perfil actualizado", Toast.LENGTH_SHORT).show();
                        //navegar a perfil con intent
                        Intent intent = new Intent(getApplication(), PerfilFragment.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplication().startActivity(intent);

                    } else {
                        Log.d("PasswordViewModel", "Error al actualizar el perfil: " + response.code());
                        Toast.makeText(getApplication(), "Error al actualizar el perfil", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    Log.d("PasswordViewModel", "onFailure: " + t.getMessage());
                    Toast.makeText(getApplication(), "Error de red al actualizar el perfil, posible error de Api", Toast.LENGTH_SHORT).show();
                }
            });


        }

    }
}