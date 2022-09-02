package com.example.pruebavolleyraulvillodres;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pruebavolleyraulvillodres.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Configuramos el ViewBinding
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Creamos una nueva Queue de Volley con el contexto adecuado
        mQueue = Volley.newRequestQueue(this);

        binding.Boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });
    }

    private void jsonParse() {
        //La URL debe de ser la dirección IP para que Volley funcione correctamente
        String url = "http://www.raulvillodresmoreno.me/cambio.json";

        //Creamos un nuevo JsonObjectRequest
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("valores");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject valor = jsonArray.getJSONObject(i);
                                String firstName = valor.getString("valor");
                                binding.Resultado.setText(firstName);

                                //Usariamos append en vez de setText si usamos varios valores.
                                //mTextViewResult.append(firstName);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
}