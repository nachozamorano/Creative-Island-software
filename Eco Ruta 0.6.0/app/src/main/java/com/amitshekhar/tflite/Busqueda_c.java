package com.amitshekhar.tflite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Busqueda_c extends AppCompatActivity {
    String ide;
    ListView listView1;
    EditText editText, numero;
    ArrayList<Subject> SubjectList = new ArrayList<Subject>();
    String HttpURL = "https://ecoruta.webcindario.com/historiales.php";
    String[]Publicacion,Estado,Descripcion,Sub,id_objeto,Categoria;
    List_publicacion listAdapter;
    int tamaño=5,i=0,n=0,tamaño_real;
    Subject subject;
    ProgressBar progressBar ;
    String[] tempFullForm;
    JSONArray jsonArray;
    String[] Cantidad;
    String[] imagen;
    String[] Unidad_medida;
    String[] direccion;
    String[] Desde;
    String[] nombre_propietario;
    String[] Hasta;
    String[] latitud;
    String[] longitud;
    String[] tempName;
    Button next,back;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_c);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        ide=String.valueOf(b.get("ide"));
        listView1 = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.edittext10);
        next = (Button) findViewById(R.id.siguiente1);
        back = (Button) findViewById(R.id.previo1);


        progressBar = (ProgressBar)findViewById(R.id.progressbar2);
        numero= (EditText) findViewById(R.id.number1);
        numero.setText( Integer.toString(n));

        listView1.setTextFilterEnabled(true);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Subject ListViewClickData = (Subject)parent.getItemAtPosition(position);

                Intent intent =new Intent(Busqueda_c.this,Detalle_adq.class);
                if(n>=1){
                    intent.putExtra("ide",ide);
                    intent.putExtra("id_objeto",id_objeto[position+(5*n)]);
                    intent.putExtra("name",ListViewClickData.getSubName());
                    intent.putExtra("image",imagen[position+(5*n)]);
                    intent.putExtra("estado",ListViewClickData.getSubFullForm());
                    intent.putExtra("publicacion",Publicacion[position+(5*n)]);
                    intent.putExtra("sub",Sub[position+(5*n)]);
                    intent.putExtra("descripcion",Descripcion[position+(5*n)]);
                    intent.putExtra("desde",Desde[position+(5*n)]);
                    intent.putExtra("hasta",Hasta[position+(5*n)]);
                    intent.putExtra("direccion",direccion[position+(5*n)]);
                    intent.putExtra("cantidad",Cantidad[position+(5*n)]);
                    intent.putExtra("unidad_medida",Unidad_medida[position+(5*n)]);
                    intent.putExtra("latitud",latitud[position+(5*n)]);
                    intent.putExtra("longitud",longitud[position+(5*n)]);
                    intent.putExtra("nombre_estado",Estado[position+(5*n)]);
                    intent.putExtra("nombre_propietario",nombre_propietario[position+(5*n)]);
                    intent.putExtra("categoria",Categoria[position+(5*n)]);
                }
                else {
                    intent.putExtra("ide", ide);
                    intent.putExtra("id_objeto", id_objeto[position]);
                    intent.putExtra("name", ListViewClickData.getSubName());
                    intent.putExtra("image", imagen[position]);
                    intent.putExtra("estado", ListViewClickData.getSubFullForm());
                    intent.putExtra("publicacion", Publicacion[position]);
                    intent.putExtra("sub", Sub[position]);
                    intent.putExtra("descripcion", Descripcion[position]);
                    intent.putExtra("desde", Desde[position]);
                    intent.putExtra("hasta", Hasta[position]);
                    intent.putExtra("direccion", direccion[position]);
                    intent.putExtra("cantidad", Cantidad[position]);
                    intent.putExtra("unidad_medida", Unidad_medida[position]);
                    intent.putExtra("latitud", latitud[position]);
                    intent.putExtra("longitud", longitud[position]);
                    intent.putExtra("nombre_estado", Estado[position]);
                    intent.putExtra("nombre_propietario", nombre_propietario[position]);
                    intent.putExtra("categoria", Categoria[position]);
                }

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
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(jsonArray.length()>=5) {
                    n++;
                }
                numero.setText( Integer.toString(n));
                tamaño=5*(n+1);
                if(tamaño_real<tamaño){
                    tamaño=tamaño-5;
                    tamaño=tamaño_real;

                }
                if(i<0){
                    i=0;
                }
                SubjectList = new ArrayList<Subject>();
                subject=null;
                for(;i<tamaño;i++) {
                    subject = new Subject(tempName[i], tempFullForm[i], imagen[i]);

                    SubjectList.add(subject);
                }
                progressBar.setVisibility(View.INVISIBLE);
                listAdapter = new List_publicacion(Busqueda_c.this, R.layout.custom_layout, SubjectList);
                listView1.setAdapter(listAdapter);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n--;
                if (i % 5 != 0) {
                    i = n * 5;
                    tamaño = 5 * (n + 1);

                }
                if (n < 0) {
                    n = 0;
                    httpquery(ide);

                }
                numero.setText(Integer.toString(n));

                tamaño = tamaño - 5;
                i = i - 10;
                if (tamaño < 5) {
                    tamaño = 5;

                }
                if (i < 0) {
                    i = 0;
                }
                SubjectList = new ArrayList<Subject>();
                subject=null;
                try {
                    for (; i < tamaño; i++) {
                        subject = new Subject(tempName[i], tempFullForm[i], imagen[i]);

                        SubjectList.add(subject);
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    listAdapter = new List_publicacion(Busqueda_c.this, R.layout.custom_layout, SubjectList);
                    listView1.setAdapter(listAdapter);
                } catch (Exception e) {
                    numero.setText( Integer.toString(0));
                    SubjectList = new ArrayList<Subject>();
                    subject = null;
                    i = 0;
                    for (; i < jsonArray.length(); i++) {
                        subject = new Subject(tempName[i], tempFullForm[i], imagen[i]);

                        SubjectList.add(subject);
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    listAdapter = new List_publicacion(Busqueda_c.this, R.layout.custom_layout, SubjectList);
                    listView1.setAdapter(listAdapter);

                }
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
                loading = ProgressDialog.show(Busqueda_c.this, "Sincronizando", null, true, true);
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
                    tamaño_real=jsonArray.length();
                    nombre_propietario=new String[jsonArray.length()];
                    Estado=new String[jsonArray.length()];
                    Categoria= new String[jsonArray.length()];
                    for (int x = 0; x < jsonArray.length(); x++) {

                        jsonObject = jsonArray.getJSONObject(x);
                        id_objeto[x]=jsonObject.getString("id_objeto");

                        tempName[x] = jsonObject.getString("nombre_objeto");
                        tempFullForm[x]=jsonObject.getString("nombre_estado_objeto");
                        Publicacion[x] = jsonObject.getString("fecha_publicacion");
                        Sub[x] = jsonObject.getString("subcategoria");
                        Categoria[x] =jsonObject.getString("categoria");
                        nombre_propietario[x]= jsonObject.getString("nombre_propietario");
                        imagen[x]= jsonObject.getString("imagen");
                        Desde[x] = jsonObject.getString("desde");
                        Descripcion[x] = jsonObject.getString("descripcion");
                        Cantidad[x]= jsonObject.getString("cantidad");
                        Unidad_medida[x]=jsonObject.getString("unidad_medida");
                        Hasta[x]=jsonObject.getString("hasta");
                        direccion[x]=jsonObject.getString("direccion");
                        Estado[x]= jsonObject.getString("nombre_estado");
                        latitud[x]=jsonObject.getString("Latitud");
                        longitud[x]=jsonObject.getString("Longitud");

                    }
                    if(jsonArray.length()<5) {
                        for (; i < jsonArray.length(); i++) {
                            subject = new Subject(tempName[i], tempFullForm[i], imagen[i]);
                            SubjectList.add(subject);
                        }
                    }
                    else {
                        for (; i < tamaño; i++) {
                            subject = new Subject(tempName[i], tempFullForm[i], imagen[i]);
                        }
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    listAdapter = new List_publicacion(Busqueda_c.this, R.layout.custom_publicaciones, SubjectList);
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
        ulc.execute(ide,"ADQUIRIDOS");



    }


}

