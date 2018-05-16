package com.android4dev.navigationview.promo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android4dev.navigationview.HttpsQuerys;
import com.android4dev.navigationview.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class promo5 extends Activity implements View.OnClickListener {

    private static final String LOGIN_URL = "http://bishop94.000webhostapp.com/actualizar_promo.php";
    private static final String url_precio = "http://bishop94.000webhostapp.com/actualizar_precio.php";
    String url="http://bishop94.000webhostapp.com/id.php";
    EditText inicio;
    String ids;
    EditText precio;
    TextView dias;
    Button boton_inicio;
    Button boton_precio;
    int idclient =1;//cambiar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo5);
        inicio = (EditText) findViewById(R.id.nombreagregar11);
        dias = (TextView) findViewById(R.id.dias11);
        precio = (EditText) findViewById(R.id.precio5);
        id();

        boton_inicio = (Button) findViewById(R.id.btninicio11);

        boton_inicio.setOnClickListener(this);
        boton_precio=(Button) findViewById(R.id.btnprecio5);
        boton_precio.setOnClickListener(this);
    }

    private void registro_inicio(){
        String inicios = inicio.getText().toString().trim();
        String dia = "promo5";
        if(ids==null) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }
        registrar(dia,inicios,ids,"5");
    }
    private void precio(){
        String precios = precio.getText().toString().trim();
        String promo = "promo5";
        if(ids==null) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }
        precio_nuevo(promo,precios,ids,"5");
    }

    private void registrar(final String dia,final String dia_dato,final String id,final String posicion){
        class UserLoginClass extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(promo5.this,"Espere por favor",null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("promo",params[0]);
                data.put("promo_dato",params[1]);
                data.put("id",params[2]);
                data.put("posicion",params[3]);
                HttpsQuerys ruc = new HttpsQuerys();

                String result = ruc.sendPostRequest(LOGIN_URL,data);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(dia,dia_dato,id,posicion);
    }

    public void onClick(View v) {
        if(v==boton_inicio) {
            registro_inicio();
        }
        else if (v==boton_precio){
            precio();
        }

    }

    public void id() {
        class UserLoginClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONArray obj = new JSONArray(s);
                    if (obj.getJSONObject(0).getInt("id_botilleria")==idclient) {
                        ids = obj.getJSONObject(0).getString("id_botilleria");
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();

                HttpsQuerys ruc = new HttpsQuerys();

                String result = ruc.sendPostRequest(url, data);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute();

    }
    private void precio_nuevo(final String dia,final String dia_dato,final String id,final String posicion){
        class UserLoginClass extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(promo5.this,"Espere por favor",null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("promo",params[0]);
                data.put("precio",params[1]);
                data.put("id",params[2]);
                data.put("posicion",params[3]);
                HttpsQuerys ruc = new HttpsQuerys();

                String result = ruc.sendPostRequest(url_precio,data);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(dia,dia_dato,id,posicion);
    }
}
