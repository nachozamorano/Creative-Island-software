package com.amitshekhar.tflite;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class articulo extends Activity {
    String url= "http://bishop94.000webhostapp.com/Trashapp/BuscarObjeto.php";
    private ListView lista;
    Button donar;
    String descripcion,subcategoria,fecha_publicacion,fecha_expiracion,horario_retiro,estado,ubicacion;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo);
        articulo();
        Intent intent= getIntent();
        Bundle b = intent.getExtras();
        ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();
        imagen=(ImageView) findViewById(R.id.imageView);
        imagen.setImageResource((Integer) b.get("image"));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Nombre:", (String) b.get("name")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Publicacion", (String) b.get("publicacion")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Lugar de encuentro:",(String) b.get("lugar")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Sub categoria:", (String) b.get("sub")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Fecha de expiracion:", (String) b.get("expiracion")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Horario de retiro:", (String) b.get("horario")));
       // datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Estado:",(String) b.get("estado")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Descripcion:",(String) b.get("descripcion")));
        donar = (Button) findViewById(R.id.button8);
        lista = (ListView) findViewById(R.id.listado);
        lista.setAdapter(new Lista_adaptador(this, R.layout.entrada, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);
                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((Lista_entrada) entrada).get_textoEncima());

                    TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior);
                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((Lista_entrada) entrada).get_textoDebajo());

                    ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen);
                    if (imagen_entrada != null)
                        imagen_entrada.setImageResource(((Lista_entrada) entrada).get_idImagen());
                }
            }
        });
        donar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(articulo.this, "Adquirido", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void articulo() {
        class UserLoginClass extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(articulo.this, "Sincronizando", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                    JSONArray obj = new JSONArray(s);
                    for(int i=0;i < obj.length();i++){
                        descripcion = obj.getJSONObject(i).getString("descripcion");
                        subcategoria = obj.getJSONObject(i).getString("subcategoria");
                        fecha_publicacion = obj.getJSONObject(i).getString("fecha_publicacion");
                        fecha_expiracion = obj.getJSONObject(i).getString("fecha_expiracion");
                        horario_retiro = obj.getJSONObject(i).getString("horario_retiro");
                        estado = obj.getJSONObject(i).getString("estado");
                        ubicacion = obj.getJSONObject(i).getString("ubicacion");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();

                HttpQuerys ruc = new HttpQuerys();

                String result = ruc.sendPostRequest(url, data);

                return result;
            }
        }

        UserLoginClass ulc = new UserLoginClass();
        ulc.execute();
    }
}