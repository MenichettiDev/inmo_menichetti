package com.example.inmo.ui.inmueble;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmo.models.Inmueble;
import com.example.inmo.request.ApiClient;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class CrearInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Uri> uriMutableLiveData;
    private MutableLiveData<Inmueble> mInmueble;
    private static Inmueble inmueblelleno;

    public CrearInmuebleViewModel(@NonNull Application application) {
        super(application);
        inmueblelleno = new Inmueble();
    }

    public LiveData<Uri> getUriMutable() {
        if (uriMutableLiveData == null) {
            uriMutableLiveData = new MutableLiveData<>();
        }
        return uriMutableLiveData;
    }

    public LiveData<Inmueble> getmInmueble() {
        if (mInmueble == null) {
            mInmueble = new MutableLiveData<>();
        }
        return mInmueble;
    }

    public void recibirFoto(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
            Log.d("salada", uri.toString());
            uriMutableLiveData.setValue(uri);
        }
    }

    public void guardarInmueble(String ambiente, String superficie, String direccion, String uso, String tipo, String latitud, String longitud, String valor, boolean disponible) {

        try {
            inmueblelleno.setAmbientes(Integer.parseInt(ambiente));
            inmueblelleno.setSuperficie(Integer.parseInt(superficie));
            inmueblelleno.setDireccion(direccion);
            inmueblelleno.setUso(uso);
            inmueblelleno.setTipo(tipo);
            inmueblelleno.setLatitud(Double.parseDouble(latitud));
            inmueblelleno.setLongitud(Double.parseDouble(longitud));
            inmueblelleno.setValor(Double.parseDouble(valor.toString()));
            //Convertir la imagen a bits
            byte imagenBytes[] = transformarImagen();
            if( imagenBytes.length == 0 ){
                inmueblelleno.setImagen("");
                Toast.makeText(getApplication(), "Imagen vacia", Toast.LENGTH_SHORT).show();
                return;
            }
            //Pongo el json en la request body
            String inmuebleJson = new Gson().toJson(inmueblelleno);
            RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJson);
            //Pongo la imagen en la request body
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagenBytes);

            MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);

            //llamada
            ApiClient.InmoServicio servicio = ApiClient.getInmoServicio();
            String token = ApiClient.leerToken(getApplication());
            Call<Inmueble> call = servicio.CargarInmueble("Bearer "+token, imagenPart, inmuebleBody);

            call.enqueue(new retrofit2.Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, retrofit2.Response<Inmueble> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplication(), "Inmueble cargado con exito", Toast.LENGTH_SHORT).show();
                        mInmueble.setValue(response.body());
                    } else {
                        Toast.makeText(getApplication(), "No se pudo cargar el inmueble", Toast.LENGTH_SHORT).show();
                        Log.d("errorInmueble", response.message());
                    }
                }

                @Override
                public void onFailure(Call<Inmueble> call, Throwable t) {
                    Toast.makeText(getApplication(), "No se pudo cargar el inmueble", Toast.LENGTH_SHORT).show();
                    Log.d("errorInmueble", t.getMessage());
                }
            });

        } catch (NumberFormatException e) {
            Toast.makeText(getApplication(), "Error debe ingresar un numero", Toast.LENGTH_SHORT).show();
        }

    }

    private byte[] transformarImagen() {
        Uri uri = uriMutableLiveData.getValue();
        try {
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();

        } catch (FileNotFoundException e) {
            Toast.makeText(getApplication(), "No se encontro imagen ", Toast.LENGTH_SHORT).show();
            return new byte[]{};
        }

    }

}