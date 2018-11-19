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

public class hist_don extends AppCompatActivity {
    String ide;
    ListView listView1;
    EditText editText;
    ArrayList<Subject> SubjectList = new ArrayList<Subject>();
    String HttpURL = "https://ecoruta.webcindario.com/historiales.php";
    Subject subject;
    String[]Publicacion,Expiracion,Estado,Descripcion,Horario,Sub,id_objeto,estado,nombre_estado;
    List_publicacion listAdapter;
    JSONArray jsonArray;
    int i;
    ProgressBar progressBar ;
    String[] tempFullForm;
    String[] Cantidad;
    String[] imagen;
    String[] Unidad_medida;
    private SwipeRefreshLayout swipeRefresh;
    String[] direccion;
    String[] Desde;
    String[] nombre_interesado;
    String[] Hasta;
    String[] latitud;
    String[] longitud;
    String[] tempName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hist_don);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_don);
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
        listView1.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Subject ListViewClickData = (Subject)parent.getItemAtPosition(position);

                Intent intent =new Intent(hist_don.this,Detalle_don.class);
                    intent.putExtra("ide", ide);
                    intent.putExtra("id_objeto", id_objeto[position]);
                    intent.putExtra("name", ListViewClickData.getSubName());
                    intent.putExtra("image", imagen[position]);
                    intent.putExtra("categoria", ListViewClickData.getSubFullForm());
                    intent.putExtra("publicacion", Publicacion[position]);
                    intent.putExtra("sub", Sub[position]);
                    intent.putExtra("expiracion", Expiracion[position]);
                    intent.putExtra("descripcion", Descripcion[position]);
                    intent.putExtra("estado", estado[position]);
                    intent.putExtra("desde", Desde[position]);
                    intent.putExtra("hasta", Hasta[position]);
                    intent.putExtra("direccion", direccion[position]);
                    intent.putExtra("cantidad", Cantidad[position]);
                    intent.putExtra("unidad_medida", Unidad_medida[position]);
                    intent.putExtra("latitud", latitud[position]);
                    intent.putExtra("longitud", longitud[position]);
                    intent.putExtra("nombre_interesado", nombre_interesado[position]);
                    intent.putExtra("nombre_estado", nombre_estado[position]);

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
                loading = ProgressDialog.show(hist_don.this, "Sincronizando", null, true, true);
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
                    imagen = new String [jsonArray.length()];
                    latitud =new String[jsonArray.length()];
                    longitud =new String [jsonArray.length()];
                    nombre_interesado=new String[jsonArray.length()];
                    estado= new String[jsonArray.length()];
                    nombre_estado =new String[jsonArray.length()];
                    if (jsonArray.length()==0) {
                        Toast.makeText(hist_don.this, "Historial de donaciones vacio", Toast.LENGTH_LONG).show();
                    }
                    for (int x = 0; x < jsonArray.length(); x++) {

                        jsonObject = jsonArray.getJSONObject(x);
                        id_objeto[x]=jsonObject.getString("id_objeto");

                        tempName[x] = jsonObject.getString("nombre_objeto");
                        estado[x]=jsonObject.getString("nombre_estado_objeto");
                        Publicacion[x] = jsonObject.getString("fecha_publicacion");
                        tempFullForm [x] =jsonObject.getString("categoria");
                        Sub[x] = jsonObject.getString("subcategoria");
                        nombre_interesado[x]= jsonObject.getString("nombre_interesado");
                        imagen[x]= jsonObject.getString("imagen");
                        Expiracion[x] = jsonObject.getString("fecha_expiracion");
                        Desde[x] = jsonObject.getString("desde");
                        Descripcion[x] = jsonObject.getString("descripcion");
                        Cantidad[x]= jsonObject.getString("cantidad");
                        Unidad_medida[x]=jsonObject.getString("unidad_medida");
                        Hasta[x]=jsonObject.getString("hasta");
                        direccion[x]=jsonObject.getString("direccion");
                        latitud[x]=jsonObject.getString("Latitud");
                        nombre_estado[x]=jsonObject.getString("nombre_estado");
                        longitud[x]=jsonObject.getString("Longitud");

                    }
                        for (i=0; i < jsonArray.length(); i++) {
                            subject = new Subject(tempName[i], tempFullForm[i], imagen[i],nombre_estado[i]);
                            SubjectList.add(subject);
                        }
                    progressBar.setVisibility(View.INVISIBLE);
                    listAdapter = new List_publicacion(hist_don.this, R.layout.custom_publicaciones, SubjectList);
                    listView1.setAdapter(listAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<>();
                data.put("id_usuario", params[0]);
                data.put("n_historial",params[1]);

                HttpQuerys ruc1 = new HttpQuerys();

                String result = ruc1.sendPostRequest(HttpURL, data);

                return result;
            }
        }
        User ulc = new User();
        ulc.execute(ide,"DONADOS");



    }


}

