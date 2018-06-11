package com.android4dev.navigationview.horario;

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


public class dia_martes extends Activity implements View.OnClickListener {

    private static final String LOGIN_URL = "http://bishop94.000webhostapp.com/actualizar_dia.php";
    String url="http://bishop94.000webhostapp.com/id.php";
    EditText inicio;
    String ids;
    EditText cierre;
    TextView dias;
    Button boton_inicio;
    Button boton_cierre;
    int idclient =1;//cambiar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_martes);
        inicio = (EditText) findViewById(R.id.nombreagregar1);
        cierre = (EditText) findViewById(R.id.apellidoagregar1);
        dias = (TextView) findViewById(R.id.dias1);
        id();

        boton_inicio = (Button) findViewById(R.id.btninicio1);
        boton_cierre = (Button) findViewById(R.id.btncierre1);

        boton_inicio.setOnClickListener(this);
        boton_cierre.setOnClickListener(this);
    }

    private void registro_inicio(){
        String inicios = inicio.getText().toString().trim();
        String dia = "martes_i";
        if(ids==null) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }
        registrar(dia,inicios,ids);
    }
    private void registro_cierre(){
        String cierres = cierre.getText().toString().trim();
        String dia = "martes_f";
        if(ids==null) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }
        registrar(dia,cierres,ids);
    }

    private void registrar(final String dia,final String dia_dato,final String id){
        class UserLoginClass extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(dia_martes.this,"Espere por favor",null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                    JSONObject obj = new JSONObject(s);
                    if(obj.isNull("error")){
                        Toast.makeText(dia_martes.this,"modificado exitosamente",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(dia_martes.this,obj.getString("error"),Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("dia",params[0]);
                data.put("dia_dato",params[1]);
                data.put("id",params[2]);
                HttpsQuerys ruc = new HttpsQuerys();

                String result = ruc.sendPostRequest(LOGIN_URL,data);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(dia,dia_dato,id);
    }

    public void onClick(View v) {
        if(v == boton_inicio){
            registro_inicio();
        }
        else if(v== boton_cierre){
            registro_cierre();
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
}
