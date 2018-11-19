package com.amitshekhar.tflite;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Recuperar_contrasena extends AppCompatActivity {
    EditText correo;
    String URL_recuperar="https://ecoruta.webcindario.com/RecuperarPass.php";
    private ProgressBar loading;
    Button enviar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasena);
        loading = findViewById(R.id.loading);
        correo = (EditText) findViewById(R.id.correo_recuperar);
        enviar = (Button) findViewById(R.id.enviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Recuperar(correo.getText().toString());
            }
        });


    }
    public void Recuperar(final String correo) {
        class product extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                try {
                    Boolean respuesta;
                    JSONArray jsonArray = new JSONArray(s);
                    if (jsonArray.length()>0){
                        respuesta=true;
                    }
                    else{
                        respuesta=false;
                    }
                    if(respuesta==true){
                        Toast.makeText(Recuperar_contrasena.this, "Contraseña enviada a Correo", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Recuperar_contrasena.this,LoginActivity.class));
                    }
                    else {
                        Toast.makeText(Recuperar_contrasena.this, "Correo no valido", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<>();
                data.put("correo", params[0]);

                HttpQuerys ruc1 = new HttpQuerys();

                String result = ruc1.sendPostRequest(URL_recuperar, data);

                return result;
            }
        }
        product ulc = new product();
        ulc.execute(correo);



    }
}
