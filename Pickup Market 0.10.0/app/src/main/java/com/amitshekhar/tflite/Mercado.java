package com.amitshekhar.tflite;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
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
import java.util.List;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
    EditText editText;
    private GoogleMap mMap;
    ArrayList<Subject_mercado> SubjectList = new ArrayList<Subject_mercado>();
    String HttpURL = "https://ecoruta.webcindario.com/BuscarObjeto.php",ide;
    String[]Publicacion;
    String[] Expiracion;
    private String drawerTitle;
    Subject_mercado subject;
    String[] Cantidad;
    String[] Descripcion_estado;
    public Handler mHandler;
    boolean userScrolled = false;
    String[] Unidad_medida;
    String[] id_estado;
    String[] nombre_estado;
    String[] id_objeto;
    String[] Descripcion;
    public boolean isLoading = false;
    String[] Desde;
    private DrawerLayout drawerLayout;
    String[] Sub;
    int i;
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
        final View footerView =  ((LayoutInflater)Mercado.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);
        listView.addFooterView(footerView);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int fin =i+5;
                if (jsonArray.length()<fin){
                    for (; i < jsonArray.length(); i++) {
                        subject = new Subject_mercado(tempName[i], tempFullForm[i], Imagen[i], nombre_estado[i],id_objeto[i],Publicacion[i],
                                Sub[i],Expiracion[i],Desde[i],Hasta[i],Descripcion_estado[i],Descripcion[i],Cantidad[i],Unidad_medida[i],
                                id_estado[i],nombre_estado[i]);
                        SubjectList.add(subject);
                    }
                    listAdapter = new ListAdapter(Mercado.this, R.layout.custom_layout, SubjectList);
                    listView.setAdapter(listAdapter);
                    footerView.setVisibility(View.GONE);
                }
                else {
                    for (; i < fin; i++) {
                        subject = new Subject_mercado(tempName[i], tempFullForm[i], Imagen[i], nombre_estado[i],id_objeto[i],Publicacion[i],
                                Sub[i],Expiracion[i],Desde[i],Hasta[i],Descripcion_estado[i],Descripcion[i],Cantidad[i],Unidad_medida[i],
                                id_estado[i],nombre_estado[i]);
                        SubjectList.add(subject);
                    }
                    listAdapter = new ListAdapter(Mercado.this, R.layout.custom_layout, SubjectList);
                    listView.setAdapter(listAdapter);
                }
                    }});
        setToolbar();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        editText = (EditText) findViewById(R.id.edittext1);

        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        enableMyLocationIfPermitted();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                new ParseJSonDataClass(Mercado.this).execute();
                footerView.setVisibility(View.VISIBLE);
                swipeRefresh.setRefreshing(false);
            }
        });
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        if (savedInstanceState == null) {
            selectItem(drawerTitle);
        }
        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);

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
                    intent.putExtra("ide", ide);
                    intent.putExtra("id_objeto",ListViewClickData.getId_objeto());
                    intent.putExtra("name", ListViewClickData.getSubName());
                    intent.putExtra("image", ListViewClickData.getImage());
                    intent.putExtra("categoria", ListViewClickData.getSubFullForm());
                    intent.putExtra("publicacion", ListViewClickData.getPublicacion());
                    intent.putExtra("sub", ListViewClickData.getSub());
                    intent.putExtra("expiracion", ListViewClickData.getExpiracion());
                    intent.putExtra("descripcion", ListViewClickData.getDescripcion_estado());
                    intent.putExtra("desde", ListViewClickData.getDesde());
                    intent.putExtra("hasta", ListViewClickData.getHasta());
                    intent.putExtra("descripcion_estado",ListViewClickData.getDescripcion_estado());
                    intent.putExtra("estado",ListViewClickData.getEstado());
                    intent.putExtra("direccion", ListViewClickData.getDireccion());
                    intent.putExtra("cantidad", ListViewClickData.getCantidad());
                    intent.putExtra("unidad_medida", ListViewClickData.getUnidad_medida());
                    intent.putExtra("id_estado",ListViewClickData.getId_estado());
                    intent.putExtra("nombre_estado",ListViewClickData.getNombre_estado());
                startActivity(intent);
            }
        });

        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence stringVar, int start, int before, int count) {
                footerView.setVisibility(View.GONE);
                if(editText.getText().toString().isEmpty()){
                    new ParseJSonDataClass(Mercado.this).execute();
                    footerView.setVisibility(View.VISIBLE);
                }
                subject=null;
                SubjectList = new ArrayList<Subject_mercado>();
                for (i=0; i < jsonArray.length(); i++) {
                    subject = new Subject_mercado(tempName[i], tempFullForm[i], Imagen[i], nombre_estado[i],id_objeto[i],Publicacion[i],
                            Sub[i],Expiracion[i],Desde[i],Hasta[i],Descripcion_estado[i],Descripcion[i],Cantidad[i],Unidad_medida[i],
                            id_estado[i],nombre_estado[i]);
                    SubjectList.add(subject);
                }
                listAdapter = new ListAdapter(Mercado.this, R.layout.custom_layout, SubjectList);
                listView.setAdapter(listAdapter);
                 listAdapter.getFilter().filter(stringVar.toString());

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
                                for (i=0; i < jsonArray.length(); i++) {
                                    subject = new Subject_mercado(tempName[i], tempFullForm[i], Imagen[i], nombre_estado[i],id_objeto[i],Publicacion[i],
                                            Sub[i],Expiracion[i],Desde[i],Hasta[i],Descripcion_estado[i],Descripcion[i],Cantidad[i],Unidad_medida[i],
                                            id_estado[i],nombre_estado[i]);
                                    SubjectList.add(subject);
                                }
                            }
                            else {
                                for (i = 0; i < 5; i++) {
                                    subject = new Subject_mercado(tempName[i], tempFullForm[i], Imagen[i], nombre_estado[i],id_objeto[i],Publicacion[i],
                                            Sub[i],Expiracion[i],Desde[i],Hasta[i],Descripcion_estado[i],Descripcion[i],Cantidad[i],Unidad_medida[i],
                                            id_estado[i],nombre_estado[i]);
                                    SubjectList.add(subject);
                                }
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else {

                    Toast.makeText(context, httpServiceClass.getErrorMessage(), Toast.LENGTH_LONG).show();
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