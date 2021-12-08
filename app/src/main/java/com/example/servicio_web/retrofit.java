package com.example.servicio_web;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.servicio_web.models.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        getUsuarios();
    }
    
    
    private void getUsuarios() {

        TextView textInfo = (TextView) findViewById(R.id.textInfo);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gorest.co.in/public/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIUsers api = retrofit.create(APIUsers.class);
        Call<Usuario> call = api.getUsuarios();
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (!response.isSuccessful()) {
                    textInfo.setText("Ups! " + response.code());
                    return;
                }
                String textofinal = "";
                Usuario info = response.body();
                for (Usuario.Data user:info.data) {
                    String line = "Nombre: " + user.getName() + "\nEmail: " + user.getEmail() + "\n\n";
                    textofinal += line;
                }
                textInfo.setText(textofinal);
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                textInfo.setText(t.getMessage());
            }
        });
    }
}