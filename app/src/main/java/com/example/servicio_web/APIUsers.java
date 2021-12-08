package com.example.servicio_web;

import com.example.servicio_web.models.Usuario;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIUsers {

    @GET("users")
    Call<Usuario> getUsuarios();
}
