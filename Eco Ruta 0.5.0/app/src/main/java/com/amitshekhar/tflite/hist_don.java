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
    EditText editText, numero;
    ArrayList<Subject> SubjectList = new ArrayList<Subject>();
    String HttpURL = "https://ecoruta.webcindario.com/historiales.php";
    String[]Publicacion,Expiracion,Estado,Descripcion,Horario,Sub,id_objeto,Categoria,nombre_estado;
    List_publicacion listAdapter;
    int tamaño=5,i=0,n=0,tamaño_real;
    ProgressBar progressBar ;
    String[] tempFullForm;
    String[] Cantidad;
    String[] imagen;
    String[] Unidad_medida;
    String[] direccion;
    String[] Desde;
    String[] nombre_interesado;
    String[] Hasta;
    String[] latitud;
    String[] longitud;
    String[] tempName;
    Button next,back;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hist_don);
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
        listView1.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Subject ListViewClickData = (Subject)parent.getItemAtPosition(position);

                Intent intent =new Intent(hist_don.this,Detalle_don.class);
                intent.putExtra("ide",ide);
                intent.putExtra("id_objeto",id_objeto[position]);
                intent.putExtra("name",ListViewClickData.getSubName());
                intent.putExtra("image",ListViewClickData.getImage());
                intent.putExtra("estado",ListViewClickData.getSubFullForm());
                intent.putExtra("publicacion",Publicacion[position]);
                intent.putExtra("sub",Sub[position]);
                intent.putExtra("expiracion",Expiracion[position]);
                intent.putExtra("descripcion",Descripcion[position]);
                intent.putExtra("categoria",Categoria[position]);
                intent.putExtra("desde",Desde[position]);
                intent.putExtra("hasta",Hasta[position]);
                intent.putExtra("direccion",direccion[position]);
                intent.putExtra("cantidad",Cantidad[position]);
                intent.putExtra("unidad_medida",Unidad_medida[position]);
                intent.putExtra("latitud",latitud[position]);
                intent.putExtra("longitud",longitud[position]);
                intent.putExtra("nombre_interesado",nombre_interesado[position]);
                intent.putExtra("nombre_estado",nombre_estado[position]);

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
                n++;
                numero.setText( Integer.toString(n));
                tamaño=5*(n+1);
                if(tamaño_real<tamaño){
                    tamaño=tamaño-5;
                    tamaño=tamaño_real;

                }
                if(i<0){
                    i=0;
                }
                System.out.println("hola1"+" "+i+" "+tamaño);
                httpquery(ide);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n--;
                if(i%5!=0){
                    i=n*5;
                    tamaño=5*(n+1);

                }
                if( n<0){
                    n=0;
                    httpquery(ide);

                }
                numero.setText( Integer.toString(n));

                tamaño=tamaño-5;
                i=i-10;
                if(tamaño<5){
                    tamaño=5;

                }
                if(i<0){
                    i=0;
                }
                System.out.println("hola2"+" "+i+" "+tamaño);
                httpquery(ide);
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
                    JSONArray jsonArray = new JSONArray(s);
                    JSONObject jsonObject;
                    Subject subject;
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
                    tamaño_real=jsonArray.length();
                    nombre_interesado=new String[jsonArray.length()];
                    Categoria= new String[jsonArray.length()];
                    nombre_estado =new String[jsonArray.length()];
                    for (int x = 0; x < jsonArray.length(); x++) {

                        jsonObject = jsonArray.getJSONObject(x);
                        id_objeto[x]=jsonObject.getString("id_objeto");

                        tempName[x] = jsonObject.getString("nombre_objeto");
                        tempFullForm[x]=jsonObject.getString("nombre_estado_objeto");
                        Publicacion[x] = jsonObject.getString("fecha_publicacion");
                        Categoria [x] =jsonObject.getString("categoria");
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
                    for(;i<jsonArray.length();i++) {
                        subject = new Subject(tempName[i], tempFullForm[i], R.drawable.verde);
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

