package com.example.inmo.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.inmo.models.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class ApiClient {
    private static String BASE_URL="https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";



    public static InmoServicio getInmoServicio(){
        Gson gson = new GsonBuilder().setLenient().create();
         Retrofit retrofit =new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(InmoServicio.class);
    }



    public interface InmoServicio{

        @FormUrlEncoded
        @POST("api/Propietarios/login")
        Call<String> loginForm(@Field("Usuario") String usuario, @Field("Clave") String clave);

        @GET("api/Propietarios")
        Call<Propietario> getPropietario();


    }

    public static void guardarToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static String leerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        return sp.getString("token", null);
    }
}
