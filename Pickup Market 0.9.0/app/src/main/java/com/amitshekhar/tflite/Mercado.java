package com.amitshekhar.tflite;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.google.android.gms.maps.GoogleMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Mercado extends AppCompatActivity {

    ListView listView;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    EditText editText, numero;
    private GoogleMap mMap;
    ArrayList<Subject_mercado> SubjectList = new ArrayList<Subject_mercado>();
    String HttpURL = "https://ecoruta.webcindario.com/BuscarObjeto.php",ide;
    String[]Publicacion;
    String[] Expiracion;
    private String drawerTitle;
    Subject_mercado subject;
    String[] numeros_notificacion;
    String[] Cantidad;
    TextView num_notificacion;
    String[] Descripcion_estado;
    String[] Unidad_medida;
    String[] id_estado;
    String[] nombre_estado;
    String[] id_objeto;
    String[] Descripcion;
    String[] Desde;
    private DrawerLayout drawerLayout;
    String[] Sub;
    ImageButton notificacion;
    String[] Imagen;
    String[] Hasta;
    String[] Estado;
    String[] tempFullForm;
    private SwipeRefreshLayout swipeRefresh;
    String [] direccion;
    String [] Latitud,Longitud;
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
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);


        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        ide=String.valueOf(b.get("ide"));

        listView = (ListView) findViewById(R.id.listView1);
        setToolbar();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        editText = (EditText) findViewById(R.id.edittext1);
        numero= (EditText) findViewById(R.id.number);
        numero.setText( Integer.toString(n));


        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        enableMyLocationIfPermitted();
        next = (Button) findViewById(R.id.siguiente);
        back = (Button) findViewById(R.id.previo);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                n=0;
                tamaño=5;
                i=0;
                numero.setText( Integer.toString(n));
                SubjectList = new ArrayList<Subject_mercado>();
                subject=null;
                new ParseJSonDataClass(Mercado.this).execute();
                swipeRefresh.setRefreshing(false);
            }
        });
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        if (savedInstanceState == null) {
            selectItem(drawerTitle);
        }

        listView.setTextFilterEnabled(true);
        FloatingActionButton my_fab = (FloatingActionButton) findViewById(R.id.my_fab);
        my_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent (Mercado.this,subir.class);
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
                intent.putExtra("descripcion_estado","");
                intent.putExtra("direccion","Click en Ubicacion(agregar direccion)");
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Subject_mercado ListViewClickData = (Subject_mercado) parent.getItemAtPosition(position);

                Intent intent =new Intent(Mercado.this,articulo.class);
                if(n>=1){
                    intent.putExtra("ide",ide);
                    intent.putExtra("id_objeto",id_objeto[position+(5*n)]);
                    intent.putExtra("name",ListViewClickData.getSubName());
                    intent.putExtra("image",Imagen[position+(5*n)]);
                    intent.putExtra("categoria",ListViewClickData.getSubFullForm());
                    intent.putExtra("publicacion",Publicacion[position+(5*n)]);
                    intent.putExtra("sub",Sub[position+(5*n)]);
                    intent.putExtra("expiracion",Expiracion[position+(5*n)]);
                    intent.putExtra("horario",Desde[position+(5*n)]);
                    intent.putExtra("descripcion",Descripcion[position+(5*n)]);
                    intent.putExtra("desde",Desde[position+(5*n)]);
                    intent.putExtra("hasta",Hasta[position+(5*n)]);
                    intent.putExtra("estado",Estado[position+(5*n)]);
                    intent.putExtra("direccion",direccion[position+(5*n)]);
                    intent.putExtra("descripcion_estado",Descripcion_estado[position+(5*n)]);
                    intent.putExtra("cantidad",Cantidad[position+(5*n)]);
                    intent.putExtra("unidad_medida",Unidad_medida[position+(5*n)]);
                    intent.putExtra("id_estado",id_estado[position+(5*n)]);
                    intent.putExtra("nombre_estado",nombre_estado[position+(5*n)]);
                }else {
                    intent.putExtra("ide", ide);
                    intent.putExtra("id_objeto",id_objeto[position]);
                    intent.putExtra("name", ListViewClickData.getSubName());
                    intent.putExtra("image", Imagen[position]);
                    intent.putExtra("categoria", ListViewClickData.getSubFullForm());
                    intent.putExtra("publicacion", Publicacion[position]);
                    intent.putExtra("sub", Sub[position]);
                    intent.putExtra("expiracion", Expiracion[position]);
                    intent.putExtra("horario", Desde[position]);
                    intent.putExtra("descripcion", Descripcion[position]);
                    intent.putExtra("desde", Desde[position]);
                    intent.putExtra("hasta", Hasta[position]);
                    intent.putExtra("descripcion_estado",Descripcion_estado[position]);
                    intent.putExtra("estado",Estado[position]);
                    intent.putExtra("direccion", direccion[position]);
                    intent.putExtra("cantidad", Cantidad[position]);
                    intent.putExtra("unidad_medida", Unidad_medida[position]);
                    intent.putExtra("id_estado",id_estado[position]);
                    intent.putExtra("nombre_estado",nombre_estado[position]);

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
                SubjectList = new ArrayList<Subject_mercado>();
                subject=null;
                for(;i<tamaño;i++) {
                    subject = new Subject_mercado(tempName[i], tempFullForm[i], Imagen[i],nombre_estado[i]);

                    SubjectList.add(subject);
                }
                progressBar.setVisibility(View.INVISIBLE);
                listAdapter = new ListAdapter(Mercado.this, R.layout.custom_layout, SubjectList);
                listView.setAdapter(listAdapter);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n--;
                tamaño=tamaño-5;
                i=i-10;
                if(i%5!=0){
                    i=n*5;
                    tamaño=5*(n+1);

                }
                if( n<0){
                    n=0;
                    new ParseJSonDataClass(Mercado.this).execute();

                }
                numero.setText( Integer.toString(n));

                if(tamaño<5){
                    tamaño=5;

                }
                if(i<0){
                    i=0;
                }
                SubjectList = new ArrayList<Subject_mercado>();
                subject=null;
                try {
                    for (; i < tamaño; i++) {
                        subject = new Subject_mercado(tempName[i], tempFullForm[i], Imagen[i],nombre_estado[i]);

                        SubjectList.add(subject);
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    listAdapter = new ListAdapter(Mercado.this, R.layout.custom_layout, SubjectList);
                    listView.setAdapter(listAdapter);
                }
                catch (Exception e) {
                    numero.setText( Integer.toString(0));
                    SubjectList = new ArrayList<Subject_mercado>();
                    subject=null;
                    i=0;
                    for (; i < jsonArray.length(); i++) {
                        subject = new Subject_mercado(tempName[i], tempFullForm[i], Imagen[i],nombre_estado[i]);

                        SubjectList.add(subject);
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    listAdapter = new ListAdapter(Mercado.this, R.layout.custom_layout, SubjectList);
                    listView.setAdapter(listAdapter);

                }
            }
        });


        new ParseJSonDataClass(this).execute();


    }
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Marcar item presionado
                        int id = menuItem.getItemId();
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
                        else if(id==R.id.mis_reservas) {
                            Intent intent = new Intent(Mercado.this,
                                    mis_reservas.class);
                            intent.putExtra("ide",ide);
                            startActivity(intent);
                        }
                        else if(id==R.id.historial_adq) {
                            Intent intent = new Intent(Mercado.this,
                                    hist_adq.class);
                            intent.putExtra("ide",ide);
                            startActivity(intent);
                        }
                        else if(id==R.id.mapa) {
                            Intent intent = new Intent(Mercado.this, Mapa.class);
                            intent.putExtra("latitud",Latitud);
                            intent.putExtra("longitud",Longitud);
                            intent.putExtra("nombre",tempName);
                            intent.putExtra("categoria",tempFullForm);
                            intent.putExtra("estado",Estado);
                            intent.putExtra("imagen",Imagen);
                            intent.putExtra("id_objeto",id_objeto);
                            intent.putExtra("sub",Sub);
                            intent.putExtra("publicacion",Publicacion);
                            intent.putExtra("descripcion_estado",Descripcion_estado);
                            intent.putExtra("expiracion",Expiracion);
                            intent.putExtra("desde",Desde);
                            intent.putExtra("hasta",Hasta);
                            intent.putExtra("direccion",direccion);
                            intent.putExtra("cantidad",Cantidad);
                            intent.putExtra("descripcion",Descripcion);
                            intent.putExtra("unidad_medida",Unidad_medida);
                            intent.putExtra("id_estado",id_estado);
                            intent.putExtra("nombre_estado",nombre_estado);
                            intent.putExtra("tamaño",jsonArray.length());
                            intent.putExtra("ide",ide);
                            startActivity(intent);
                        }
                        menuItem.setChecked(true);
                        // Crear nuevo fragmento
                        return true;
                    }
                }
        );
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void selectItem(String title) {
        // Enviar título como arguemento del fragmento
        Bundle args = new Bundle();
        args.putString(PlaceholderFragment.ARG_SECTION_TITLE, title);
        Fragment fragment = PlaceholderFragment.newInstance(title);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.main_content, fragment)
                .commit();

        drawerLayout.closeDrawers(); // Cerrar drawer

        setTitle(title); // Setear título actual

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


                            tamaño_real=jsonArray.length();

                            SubjectList = new ArrayList<Subject_mercado>();
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
                            Imagen= new String[jsonArray.length()];
                            direccion =new String [jsonArray.length()];
                            Estado = new String [jsonArray.length()];
                            Latitud= new  String [jsonArray.length()];
                            Longitud = new String [jsonArray.length()];
                            nombre_estado = new String [jsonArray.length()];
                            id_estado =new String [jsonArray.length()];
                            id_objeto=new String [jsonArray.length()];
                            Descripcion_estado=new String [jsonArray.length()];

                            for (int x=0; x < jsonArray.length(); x++) {

                                jsonObject = jsonArray.getJSONObject(x);

                                tempName[x] = jsonObject.getString("nombre_objeto");
                                id_objeto[x] = jsonObject.getString("id_objeto");
                                Estado[x]=jsonObject.getString("nombre_estado_objeto");
                                Publicacion[x] = jsonObject.getString("fecha_publicacion");
                                Sub[x] = jsonObject.getString("subcategoria");
                                tempFullForm[x]=jsonObject.getString("categoria");
                                Expiracion[x] = jsonObject.getString("fecha_expiracion");
                                Imagen [x] = jsonObject.getString("imagen");
                                Desde[x] = jsonObject.getString("desde");
                                Descripcion[x] = jsonObject.getString("descripcion");
                                Latitud [x] =jsonObject.getString("Latitud");
                                Longitud [x] =jsonObject.getString("Longitud");
                                Cantidad[x]= jsonObject.getString("cantidad");
                                Descripcion_estado[x]= jsonObject.getString("descripcion_estado");
                                Unidad_medida[x]=jsonObject.getString("unidad_medida");
                                Hasta[x]=jsonObject.getString("hasta");
                                direccion[x]=jsonObject.getString("direccion");

                                id_estado [x] =jsonObject.getString("id_estado");
                                nombre_estado [x]= jsonObject.getString("nombre_estado");


                            }
                            if(jsonArray.length()<5){
                                for(;i<jsonArray.length();i++) {
                                    subject = new Subject_mercado(tempName[i], tempFullForm[i], Imagen[i],nombre_estado[i]);
                                    SubjectList.add(subject);
                                }
                            }
                            else {
                                for (; i < tamaño; i++) {
                                    subject = new Subject_mercado(tempName[i], tempFullForm[i], Imagen[i],nombre_estado[i]);
                                    SubjectList.add(subject);
                                }
                            }
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
    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

}