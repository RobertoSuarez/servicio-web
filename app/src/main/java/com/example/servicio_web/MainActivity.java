package com.example.servicio_web;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textRequest = (TextView) findViewById(R.id.textRequest);
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "https://cidu.uteq.edu.ec/index.php/inscrito/getjson_totalinscritosporpais";
        String url = "https://gorest.co.in/public/v1/users";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String info = "";
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);
                        String nombre = "Nombre: " + jo.getString("name") + "\n" + "email: "+ jo.getString("email") + "\n\n";
                        info += nombre;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                textRequest.setText(info);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    textRequest.setText("NetworkError: No se puede conectar a Internet ... ¡Compruebe su conexión!");
                } else if (error instanceof ServerError) {
                    textRequest.setText("ServerError: No se pudo encontrar el servidor. ¡Inténtalo de nuevo después de un tiempo!");
                } else if (error instanceof AuthFailureError) {
                    textRequest.setText("AuthFailError: No se puede conectar a Internet ... ¡Compruebe su conexión!");
                }else {
                    textRequest.setText("Error: " + error.toString());
                }
            }
        });

        queue.add(stringRequest);
    }

    public void showRetrofit(View view) {
        Intent i = new Intent(this, retrofit.class);

        startActivity(i);
    }


}