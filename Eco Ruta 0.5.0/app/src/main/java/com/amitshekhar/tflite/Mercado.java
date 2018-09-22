package com.amitshekhar.tflite;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Mercado extends AppCompatActivity {

    ListView listView;
    EditText editText, numero;
    ArrayList<Subject> SubjectList = new ArrayList<Subject>();
    String HttpURL = "https://ecoruta.webcindario.com/BuscarObjeto.php",ide="1";
    String[]Publicacion;
    String[] Expiracion;
    String[] Cantidad;
    String[] Unidad_medida;
    String[] Descripcion;
    String[] Desde;
    String[] Sub;
    String[] Hasta;
    String[] Categoria;
    String[] tempFullForm;
    String [] direccion;
    String[] tempName;
    JSONArray jsonArray = null;
    ListAdapter listAdapter;
    int tamaño=5,i=0,tamaño_real;
    Button next,back;
    int n=0;
    ProgressBar progressBar ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercado);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        listView = (ListView) findViewById(R.id.listView1);

        editText = (EditText) findViewById(R.id.edittext1);
        numero= (EditText) findViewById(R.id.number);
        numero.setText( Integer.toString(n));


        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        next = (Button) findViewById(R.id.siguiente);
        back = (Button) findViewById(R.id.previo);

        listView.setTextFilterEnabled(true);
        FloatingActionButton my_fab = (FloatingActionButton) findViewById(R.id.my_fab);
        my_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent (Mercado.this,subir.class);
                intent.putExtra("latitud",1.0);
                intent.putExtra("longitud",1.0);
                intent.putExtra("ide",ide);
                intent.putExtra("direccion","Click en Ubicacion(agregar direccion)");
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Subject ListViewClickData = (Subject)parent.getItemAtPosition(position);

                Intent intent =new Intent(Mercado.this,articulo.class);
                intent.putExtra("ide",ide);
                intent.putExtra("name",ListViewClickData.getSubName());
                intent.putExtra("image",ListViewClickData.getImage());
                intent.putExtra("estado",ListViewClickData.getSubFullForm());
                intent.putExtra("publicacion",Publicacion[position]);
                intent.putExtra("sub",Sub[position]);
                intent.putExtra("expiracion",Expiracion[position]);
                intent.putExtra("horario",Desde[position]);
                intent.putExtra("descripcion",Descripcion[position]);
                intent.putExtra("desde",Desde[position]);
                intent.putExtra("hasta",Hasta[position]);
                intent.putExtra("categoria",Categoria[position]);
                intent.putExtra("direccion",direccion[position]);
                intent.putExtra("cantidad",Cantidad[position]);
                intent.putExtra("unidad_medida",Unidad_medida[position]);
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
                new ParseJSonDataClass(Mercado.this).execute();
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
                    new ParseJSonDataClass(Mercado.this).execute();

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
                new ParseJSonDataClass(Mercado.this).execute();
            }
        });

        System.out.println("hola0"+" "+i+" "+tamaño);
        new ParseJSonDataClass(this).execute();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==R.id.modificacion) {
            Intent intent = new Intent(Mercado.this,
                    Publicaciones.class);
            intent.putExtra("ide",ide);
            startActivity(intent);
        }
        else if(id==R.id.perfil) {
            Intent intent = new Intent(Mercado.this,
                    Perfil.class);
            intent.putExtra("ide",ide);
            startActivity(intent);
        }
        else if(id==R.id.historial_don) {
            Intent intent = new Intent(Mercado.this,
                    hist_don.class);
            intent.putExtra("ide",ide);
            startActivity(intent);
        }
        else if(id==R.id.historial_adq) {
            Intent intent = new Intent(Mercado.this,
                    hist_adq.class);
            intent.putExtra("ide",ide);
            startActivity(intent);
        }
        return true;
    }

    private class ParseJSonDataClass extends AsyncTask<Void, Void, Void> {
        public Context context;
        String FinalJSonResult;

        public ParseJSonDataClass(Context context) {

            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpServiceClass httpServiceClass = new HttpServiceClass(HttpURL);

            try {
                httpServiceClass.ExecutePostRequest();

                if (httpServiceClass.getResponseCode() == 200) {

                    FinalJSonResult = httpServiceClass.getResponse();

                    if (FinalJSonResult != null) {

                        try {

                            jsonArray = new JSONArray(FinalJSonResult);

                            JSONObject jsonObject;

                            Subject subject;
                            tamaño_real=jsonArray.length();

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
                            Categoria = new String [jsonArray.length()];

                            for (int x=0; x < jsonArray.length(); x++) {

                                jsonObject = jsonArray.getJSONObject(x);

                                tempName[x] = jsonObject.getString("nombre_objeto");
                                tempFullForm[x]=jsonObject.getString("nombre_estado_objeto");
                                Publicacion[x] = jsonObject.getString("fecha_publicacion");
                                Sub[x] = jsonObject.getString("subcategoria");
                                Categoria[x]=jsonObject.getString("categoria");
                                Expiracion[x] = jsonObject.getString("fecha_expiracion");
                                Desde[x] = jsonObject.getString("desde");
                                Descripcion[x] = jsonObject.getString("descripcion");
                                Cantidad[x]= jsonObject.getString("cantidad");
                                Unidad_medida[x]=jsonObject.getString("unidad_medida");
                                Hasta[x]=jsonObject.getString("hasta");
                                direccion[x]=jsonObject.getString("direccion");


                            }
                            for(;i<tamaño;i++) {
                                subject = new Subject(tempName[i], tempFullForm[i], R.drawable.verde);
                                SubjectList.add(subject);
                            }
                            System.out.println("hola"+" "+i+" "+tamaño);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else {

                    Toast.makeText(context, httpServiceClass.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)

        {
            progressBar.setVisibility(View.INVISIBLE);
            listAdapter = new ListAdapter(Mercado.this, R.layout.custom_layout, SubjectList);
            listView.setAdapter(listAdapter);
        }
    }

}