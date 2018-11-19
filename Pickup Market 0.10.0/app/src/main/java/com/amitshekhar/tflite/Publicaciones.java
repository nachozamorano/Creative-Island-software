package com.amitshekhar.tflite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Publicaciones extends AppCompatActivity {
    String ide;
    ListView listView1;
    EditText editText;
    ArrayList<Subject> SubjectList = new ArrayList<Subject>();
    String HttpURL = "https://ecoruta.webcindario.com/actualizarObjeto2.php";
    String[]Publicacion,Expiracion,Estado,Descripcion,Horario,Sub,id_objeto;
    List_publicacion listAdapter;
    ProgressBar progressBar ;
    JSONArray jsonArray;
    Subject subject;
    String[] tempFullForm;
    int i;
    String[] estado;
    String[] Cantidad;
    String[] Unidad_medida;
    private SwipeRefreshLayout swipeRefresh;
    String[] Imagen;
    String[] direccion;
    String [] nombre_estado;
    String[] Desde;
    String[] Hasta;
    String[] latitud;
    String[] longitud;
    String[] tempName;
    private String[] descripcion_estado;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_publicacion);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        ide=String.valueOf(b.get("ide"));
        listView1 = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.edittext10);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                httpquery(ide);
                swipeRefresh.setRefreshing(false);
            }
        });

        progressBar = (ProgressBar)findViewById(R.id.progressbar2);

        listView1.setTextFilterEnabled(true);
        FloatingActionButton my_fab = (FloatingActionButton) findViewById(R.id.my_fab2);
        my_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent (Publicaciones.this,subir.class);
                intent.putExtra("latitud",1.0);
                intent.putExtra("longitud",1.0);
                intent.putExtra("ide",ide);
                intent.putExtra("nombre","");
                intent.putExtra("fecha","");
                intent.putExtra("numero","1");
                intent.putExtra("fecha_e","");
                intent.putExtra("desde","");
                intent.putExtra("hasta","");
                intent.putExtra("categoria",0);
                intent.putExtra("sub",0);
                intent.putExtra("estado",0);
                intent.putExtra("tipo",0);
                intent.putExtra("descripcion","");
                intent.putExtra("direccion","Click en Ubicacion(agregar direccion)");
                intent.putExtra("descripcion_estado","");
                startActivity(intent);
            }
        });
        listView1.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Subject ListViewClickData = (Subject)parent.getItemAtPosition(position);

                Intent intent =new Intent(Publicaciones.this,Modificar.class);
                    intent.putExtra("ide", ide);
                    intent.putExtra("id_objeto", id_objeto[position]);
                    intent.putExtra("name", ListViewClickData.getSubName());
                    intent.putExtra("image", Imagen[position]);
                    intent.putExtra("categoria", ListViewClickData.getSubFullForm());
                    intent.putExtra("publicacion", Publicacion[position]);
                    intent.putExtra("sub", Sub[position]);
                    intent.putExtra("expiracion", Expiracion[position]);
                    intent.putExtra("descripcion", Descripcion[position]);
                    intent.putExtra("desde", Desde[position]);
                    intent.putExtra("hasta", Hasta[position]);
                    intent.putExtra("direccion", direccion[position]);
                    intent.putExtra("cantidad", Cantidad[position]);
                    intent.putExtra("estado", estado[position]);
                    intent.putExtra("unidad_medida", Unidad_medida[position]);
                    intent.putExtra("latitud", latitud[position]);
                    intent.putExtra("descripcion_estado",descripcion_estado[position]);
                    intent.putExtra("longitud", longitud[position]);

                startActivity(intent);
            }
        });


        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence stringVar, int start, int before, int count) {

                listAdapter.getFilter().filter(stringVar.toString());
            }
        });

        httpquery(ide);

    }


    public void httpquery(final String ide) {
        class User extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                loading = ProgressDialog.show(Publicaciones.this, "Sincronizando", null, true, true);
            }


            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                     jsonArray = new JSONArray(s);
                    JSONObject jsonObject;
                    id_objeto=new String[jsonArray.length()];
                    SubjectList = new ArrayList<Subject>();
                    Publicacion=new String[jsonArray.length()];
                    Sub=new String[jsonArray.length()];
                    Expiracion=new String[jsonArray.length()];
                    Desde=new String[jsonArray.length()];
                    Descripcion=new String[jsonArray.length()];
                    tempName=new String[jsonArray.length()];
                    tempFullForm=new String[jsonArray.length()];
                    Cantidad=new String[jsonArray.length()];
                    Unidad_medida= new String[jsonArray.length()];
                    Hasta =new String[jsonArray.length()];
                    direccion =new String [jsonArray.length()];
                    estado =new String [jsonArray.length()];
                    latitud =new String[jsonArray.length()];
                    longitud =new String [jsonArray.length()];
                    Imagen= new String [jsonArray.length()];
                    descripcion_estado =new String [jsonArray.length()];
                    nombre_estado= new String [jsonArray.length()];
                    if (jsonArray.length()==0) {
                        Toast.makeText(Publicaciones.this, "Publiaciones vacias", Toast.LENGTH_LONG).show();
                    }
                    for (int x = 0; x < jsonArray.length(); x++) {

                        jsonObject = jsonArray.getJSONObject(x);
                        id_objeto[x]=jsonObject.getString("id_objeto");

                        tempName[x] = jsonObject.getString("nombre_objeto");
                        estado[x]=jsonObject.getString("nombre_estado_objeto");
                        Publicacion[x] = jsonObject.getString("fecha_publicacion");
                        Sub[x] = jsonObject.getString("subcategoria");
                        Expiracion[x] = jsonObject.getString("fecha_expiracion");
                        Desde[x] = jsonObject.getString("desde");
                        Descripcion[x] = jsonObject.getString("descripcion");
                        Cantidad[x]= jsonObject.getString("cantidad");
                        tempFullForm[x]= jsonObject.getString("categoria");
                        Imagen [x]=jsonObject.getString("imagen");
                        Unidad_medida[x]=jsonObject.getString("unidad_medida");
                        Hasta[x]=jsonObject.getString("hasta");
                        direccion[x]=jsonObject.getString("direccion");
                        latitud[x]=jsonObject.getString("Latitud");
                        longitud[x]=jsonObject.getString("Longitud");
                        descripcion_estado[x]=jsonObject.getString("descripcion_estado");
                        nombre_estado[x]=jsonObject.getString("nombre_estado");
                    }
                        for (i=0; i < jsonArray.length(); i++) {
                            subject = new Subject(tempName[i], tempFullForm[i], Imagen[i],nombre_estado[i]);
                            SubjectList.add(subject);
                        }
                    progressBar.setVisibility(View.INVISIBLE);
                    listAdapter = new List_publicacion(Publicaciones.this, R.layout.custom_publicaciones, SubjectList);
                    listView1.setAdapter(listAdapter);
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


}
