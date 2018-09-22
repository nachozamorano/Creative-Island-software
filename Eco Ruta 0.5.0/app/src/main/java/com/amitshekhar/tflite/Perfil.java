package com.amitshekhar.tflite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Perfil extends AppCompatActivity {
    String ide;
    ImageView imagen_pefil;
    Bitmap bitmap;
    String HttpURL = "https://ecoruta.webcindario.com/verPerfil.php"
            ,url_donados="https://ecoruta.webcindario.com/contarDonados.php"
            ,url_adquiridos="https://ecoruta.webcindario.com/contarAdquiridos.php";
    TextView nombre,donados,adquiridos,correo,telefono_movil,telefono_fijo,genero;
    String[] nombre1,donados1,adquiridos1,correo1,telefono_movil1,telefono_fijo1,genero1,img_perfil;
    Button modificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        ide= String.valueOf(b.get("ide"));
        imagen_pefil=(ImageView) findViewById(R.id.imagen_perfil);
        nombre= (TextView) findViewById(R.id.tv_name);
        donados=(TextView) findViewById(R.id.donados);
        adquiridos=(TextView) findViewById(R.id.adquiridos);
        correo =(TextView) findViewById(R.id.correo);
        telefono_movil=(TextView) findViewById(R.id.telefono);
        telefono_fijo=(TextView) findViewById(R.id.fijo);
        genero =(TextView) findViewById(R.id.genero);
        modificar= (Button) findViewById(R.id.modificar);

        httpquery(ide);
        count_don(ide);
        count_adq(ide);
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfil.this,Modificar_perfil.class);
                intent.putExtra("ide",ide);
                intent.putExtra("imagen",img_perfil[0].trim());
                intent.putExtra("nombre",nombre.getText().toString());
                intent.putExtra("donados",donados.getText().toString());
                intent.putExtra("adquiridos",adquiridos.getText().toString());
                intent.putExtra("correo",correo.getText().toString());
                intent.putExtra("telefono_movil",telefono_movil.getText().toString());
                intent.putExtra("telefono_fijo",telefono_fijo.getText().toString());
                intent.putExtra("genero",genero.getText().toString());

                startActivity(intent);
            }
        });

    }
    public void httpquery(final String ide) {
        class User extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                loading = ProgressDialog.show(Perfil.this, "Sincronizando", null, true, true);
            }


            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    JSONObject jsonObject;
                    Subject subject;
                    nombre1=new String[jsonArray.length()];
                    correo1=new String[jsonArray.length()];
                    telefono_movil1=new String[jsonArray.length()];
                    telefono_fijo1=new String[jsonArray.length()];
                    img_perfil=new String[jsonArray.length()];
                    genero1=new String[jsonArray.length()];
                    for (int x = 0; x < jsonArray.length(); x++) {

                        jsonObject = jsonArray.getJSONObject(x);
                        nombre1[x]=jsonObject.getString("nombre");
                        img_perfil[x] = jsonObject.getString("img_perfil");
                        correo1[x] = jsonObject.getString("correo");
                        telefono_movil1[x] = jsonObject.getString("telefono_movil");
                        telefono_fijo1[x] = jsonObject.getString("telefono_fijo");
                        genero1[x] = jsonObject.getString("nombre_genero");
                    }
                    nombre.setText(nombre1[0]);
                    correo.setText(correo1[0]);
                    genero.setText(genero1[0]);
                    telefono_fijo.setText(telefono_fijo1[0]);
                    telefono_movil.setText(telefono_movil1[0]);
                    new DownloadImageTask(imagen_pefil).execute(img_perfil[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<>();
                data.put("id_usuario", params[0]);

                HttpQuerys ruc1 = new HttpQuerys();

                String result = ruc1.sendPostRequest(HttpURL, data);

                return result;
            }
        }
        User ulc = new User();
        ulc.execute(ide);



    }
    public void count_don(final String ide) {
        class User extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                loading = ProgressDialog.show(Perfil.this, "Sincronizando", null, true, true);
            }


            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    JSONObject jsonObject;
                    Subject subject;
                    donados1=new String[jsonArray.length()];
                    for (int x = 0; x < jsonArray.length(); x++) {

                        jsonObject = jsonArray.getJSONObject(x);
                        donados1[x]=jsonObject.getString("DONADOS");

                    }
                    donados.setText(donados1[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<>();
                data.put("id_usuario", params[0]);

                HttpQuerys ruc1 = new HttpQuerys();

                String result = ruc1.sendPostRequest(url_donados, data);

                return result;
            }
        }
        User ulc = new User();
        ulc.execute(ide);



    }
    public void count_adq(final String ide) {
        class User extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                loading = ProgressDialog.show(Perfil.this, "Sincronizando", null, true, true);
            }


            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    JSONObject jsonObject;
                    Subject subject;
                    adquiridos1=new String[jsonArray.length()];
                    for (int x = 0; x < jsonArray.length(); x++) {

                        jsonObject = jsonArray.getJSONObject(x);
                        adquiridos1[x]=jsonObject.getString("ADQUIRIDOS");

                    }
                    adquiridos.setText(adquiridos1[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<>();
                data.put("id_usuario", params[0]);

                HttpQuerys ruc1 = new HttpQuerys();

                String result = ruc1.sendPostRequest(url_adquiridos, data);

                return result;
            }
        }
        User ulc = new User();
        ulc.execute(ide);



    }
  class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            bmImage.setImageBitmap(result);
        }
    }
}