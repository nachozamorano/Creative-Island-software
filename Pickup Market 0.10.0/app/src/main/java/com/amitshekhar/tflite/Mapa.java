package com.amitshekhar.tflite;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Mapa extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    String ide;
    Marker m;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    String url = "https://ecoruta.webcindario.com/BuscarCategoria.php";
    String[] latitud;
    String[] longitud, Sub, Desde, Descripcion, Expiracion, Unidad_medida, Publicacion, Hasta, Cantidad, id_objeto, id_estado, nombre_estado;
    String[] arreglo_c;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;
    String[] categoria;
    Spinner Categoria;
    EditText desde_filtro;
    EditText hasta_filtro;
    String[] direccion, nombre, estado, descripcion_estado, imagen;
    int tamaño;
    CircleOptions circleOptions;
    SeekBar bar;
    Button filtrar;
    Location localidad;
    JSONArray jsonArray = null;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        bar = (SeekBar) findViewById(R.id.bar);
        filtrar = (Button) findViewById(R.id.filtrar);
        Categoria = (Spinner) findViewById(R.id.spinner_categoría);
        desde_filtro = (EditText) findViewById(R.id.desde);
        hasta_filtro = (EditText) findViewById(R.id.hasta);
        bar.setMax(1000);
        bar.setProgress(0);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        ide = String.valueOf(b.get("ide"));
        latitud = (String[]) b.get("latitud");
        categoria = (String[]) b.get("categoria");
        longitud = (String[]) b.get("longitud");
        nombre = (String[]) b.get("nombre");
        estado = (String[]) b.get("estado");
        imagen = (String[]) b.get("imagen");
        id_objeto = (String[]) b.get("id_objeto");
        Sub = (String[]) b.get("sub");
        Publicacion = (String[]) b.get("publicacion");
        Expiracion = (String[]) b.get("expiracion");
        Desde = (String[]) b.get("desde");
        Hasta = (String[]) b.get("hasta");
        direccion = (String[]) b.get("direccion");
        Cantidad = (String[]) b.get("cantidad");
        Descripcion = (String[]) b.get("descripcion");
        Unidad_medida = (String[]) b.get("unidad_medida");
        id_estado = (String[]) b.get("id_estado");
        nombre_estado = (String[]) b.get("nombre_estado");
        descripcion_estado = (String[]) b.get("descripcion_estado");
        tamaño = (int) b.get("tamaño");
        Categorias(Categoria);
        enableMyLocationIfPermitted();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        filtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMinZoomPreference(16);
                mMap.clear();
                circleOptions = new CircleOptions()
                        .radius(bar.getProgress())
                        .strokeColor(Color.RED)
                        .fillColor(0x220000FF)
                        .strokeWidth(5);
                circleOptions.center(new LatLng(localidad.getLatitude(), localidad.getLongitude()));
                float[] metros = new float[1];

                mMap.addCircle(circleOptions);
                for (int i = 0; i < tamaño; i++) {
                    Location.distanceBetween(Double.valueOf(latitud[i]), Double.valueOf(longitud[i]), localidad.getLatitude(), localidad.getLongitude(), metros);
                    if (Categoria.getSelectedItemPosition() == 0 && Float.parseFloat(String.valueOf(bar.getProgress())) == 0.0 && desde_filtro.getText().toString().isEmpty()
                            && hasta_filtro.getText().toString().isEmpty()) {
                        generar_marcador(i);

                    } else if (metros[0] < Float.parseFloat(String.valueOf(bar.getProgress())) && Categoria.getSelectedItemPosition() == 0
                            && desde_filtro.getText().toString().isEmpty() && hasta_filtro.getText().toString().isEmpty()) {
                        generar_marcador(i);
                    } else if (Float.parseFloat(String.valueOf(bar.getProgress())) == 0.0 && Categoria.getSelectedItemPosition() == 0
                            && !desde_filtro.getText().toString().isEmpty() && !hasta_filtro.getText().toString().isEmpty()) {
                        String[] tokens = Desde[i].split(":");
                        float desde_cap = Float.parseFloat(tokens[0] + "." + tokens[1]);
                        tokens = Hasta[i].split(":");
                        float hasta_cap = Float.parseFloat(tokens[0] + "." + tokens[1]);
                        tokens = desde_filtro.getText().toString().split(":");
                        float desde = Float.parseFloat(tokens[0] + "." + tokens[1]);
                        tokens = hasta_filtro.getText().toString().split(":");
                        float hasta = Float.parseFloat(tokens[0] + "." + tokens[1]);
                        if (desde <= desde_cap && hasta >= hasta_cap) {
                            generar_marcador(i);
                        }
                    } else if (Float.parseFloat(String.valueOf(bar.getProgress())) == 0.0 && desde_filtro.getText().toString().isEmpty() && hasta_filtro.getText().toString().isEmpty()
                            && Categoria.getSelectedItemPosition() != 0) {
                        if (Categoria.getSelectedItemPosition() == 1 && categoria[i].equals("Hogar y electrodomesticos")) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 2 && categoria[i].equals("Instrumentos musicales")) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 3 && categoria[i].equals("Tecnologia")) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 4 && categoria[i].equals("Deportes y aire libre")) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 5 && categoria[i].equals("Herramientas e Industria")) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 6 && categoria[i].equals("Juguetes y Bebes")) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 7 && categoria[i].equals("Libros")) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 7 && categoria[i].equals("Ropa")) {
                            generar_marcador(i);
                        }

                    } else if (Float.parseFloat(String.valueOf(bar.getProgress())) == 0.0 && !desde_filtro.getText().toString().isEmpty()
                            && !hasta_filtro.getText().toString().isEmpty() && Categoria.getSelectedItemPosition() != 0) {
                        String[] tokens = Desde[i].split(":");
                        float desde_cap = Float.parseFloat(tokens[0] + "." + tokens[1]);
                        tokens = Hasta[i].split(":");
                        float hasta_cap = Float.parseFloat(tokens[0] + "." + tokens[1]);
                        tokens = desde_filtro.getText().toString().split(":");
                        float desde = Float.parseFloat(tokens[0] + "." + tokens[1]);
                        tokens = hasta_filtro.getText().toString().split(":");
                        float hasta = Float.parseFloat(tokens[0] + "." + tokens[1]);
                        if (Categoria.getSelectedItemPosition() == 1 && categoria[i].equals("Hogar y electrodomesticos") && desde <= desde_cap && hasta >= hasta_cap) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 2 && categoria[i].equals("Instrumentos musicales") && desde <= desde_cap && hasta >= hasta_cap) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 3 && categoria[i].equals("Tecnologia") && desde <= desde_cap && hasta >= hasta_cap) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 4 && categoria[i].equals("Deportes y aire libre") && desde <= desde_cap && hasta >= hasta_cap) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 5 && categoria[i].equals("Herramientas e Industria") && desde <= desde_cap && hasta >= hasta_cap) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 6 && categoria[i].equals("Juguetes y Bebes") && desde <= desde_cap && hasta >= hasta_cap) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 7 && categoria[i].equals("Libros") && desde <= desde_cap && hasta >= hasta_cap) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 7 && categoria[i].equals("Ropa") && desde <= desde_cap && hasta >= hasta_cap) {
                            generar_marcador(i);
                        }

                    } else if (metros[0] < Float.parseFloat(String.valueOf(bar.getProgress())) && Categoria.getSelectedItemPosition() == 0
                            && Float.parseFloat(String.valueOf(bar.getProgress())) != 0 && !desde_filtro.getText().toString().isEmpty()
                            && !hasta_filtro.getText().toString().isEmpty()) {
                        String[] tokens = Desde[i].split(":");
                        float desde_cap = Float.parseFloat(tokens[0] + "." + tokens[1]);
                        tokens = Hasta[i].split(":");
                        float hasta_cap = Float.parseFloat(tokens[0] + "." + tokens[1]);
                        tokens = desde_filtro.getText().toString().split(":");
                        float desde = Float.parseFloat(tokens[0] + "." + tokens[1]);
                        tokens = hasta_filtro.getText().toString().split(":");
                        float hasta = Float.parseFloat(tokens[0] + "." + tokens[1]);
                        if (desde <= desde_cap && hasta >= hasta_cap) {
                            generar_marcador(i);
                        }

                    } else if (metros[0] < Float.parseFloat(String.valueOf(bar.getProgress())) && Categoria.getSelectedItemPosition() != 0
                            && Float.parseFloat(String.valueOf(bar.getProgress())) != 0 && desde_filtro.getText().toString().isEmpty()
                            && hasta_filtro.getText().toString().isEmpty()) {
                        if (Categoria.getSelectedItemPosition() == 1 && categoria[i].equals("Hogar y electrodomesticos") && metros[0] < Float.parseFloat(String.valueOf(bar.getProgress()))) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 2 && categoria[i].equals("Instrumentos musicales") && metros[0] < Float.parseFloat(String.valueOf(bar.getProgress()))) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 3 && categoria[i].equals("Tecnologia") && metros[0] < Float.parseFloat(String.valueOf(bar.getProgress()))) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 4 && categoria[i].equals("Deportes y aire libre") && metros[0] < Float.parseFloat(String.valueOf(bar.getProgress()))) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 5 && categoria[i].equals("Herramientas e Industria") && metros[0] < Float.parseFloat(String.valueOf(bar.getProgress()))) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 6 && categoria[i].equals("Juguetes y Bebes") && metros[0] < Float.parseFloat(String.valueOf(bar.getProgress()))) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 7 && categoria[i].equals("Libros") && metros[0] < Float.parseFloat(String.valueOf(bar.getProgress()))) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 7 && categoria[i].equals("Ropa") && metros[0] < Float.parseFloat(String.valueOf(bar.getProgress()))) {
                            generar_marcador(i);
                        }

                    } else if (metros[0] < Float.parseFloat(String.valueOf(bar.getProgress())) && Categoria.getSelectedItemPosition() != 0
                            && Float.parseFloat(String.valueOf(bar.getProgress())) != 0 && !desde_filtro.getText().toString().isEmpty()
                            && !hasta_filtro.getText().toString().isEmpty()) {
                        String[] tokens = Desde[i].split(":");
                        float desde_cap = Float.parseFloat(tokens[0] + "." + tokens[1]);
                        tokens = Hasta[i].split(":");
                        float hasta_cap = Float.parseFloat(tokens[0] + "." + tokens[1]);
                        tokens = desde_filtro.getText().toString().split(":");
                        float desde = Float.parseFloat(tokens[0] + "." + tokens[1]);
                        tokens = hasta_filtro.getText().toString().split(":");
                        float hasta = Float.parseFloat(tokens[0] + "." + tokens[1]);
                        if (Categoria.getSelectedItemPosition() == 1 && categoria[i].equals("Hogar y electrodomesticos") && metros[0] < Float.parseFloat(String.valueOf(bar.getProgress())) && desde <= desde_cap && hasta >= hasta_cap) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 2 && categoria[i].equals("Instrumentos musicales") && metros[0] < Float.parseFloat(String.valueOf(bar.getProgress())) && desde <= desde_cap && hasta >= hasta_cap) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 3 && categoria[i].equals("Tecnologia") && metros[0] < Float.parseFloat(String.valueOf(bar.getProgress())) && desde <= desde_cap && hasta >= hasta_cap) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 4 && categoria[i].equals("Deportes y aire libre") && metros[0] < Float.parseFloat(String.valueOf(bar.getProgress())) && desde <= desde_cap && hasta >= hasta_cap) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 5 && categoria[i].equals("Herramientas e Industria") && metros[0] < Float.parseFloat(String.valueOf(bar.getProgress())) && desde <= desde_cap && hasta >= hasta_cap) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 6 && categoria[i].equals("Juguetes y Bebes") && metros[0] < Float.parseFloat(String.valueOf(bar.getProgress())) && desde <= desde_cap && hasta >= hasta_cap) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 7 && categoria[i].equals("Libros") && metros[0] < Float.parseFloat(String.valueOf(bar.getProgress())) && desde <= desde_cap && hasta >= hasta_cap) {
                            generar_marcador(i);
                        } else if (Categoria.getSelectedItemPosition() == 7 && categoria[i].equals("Ropa") && metros[0] < Float.parseFloat(String.valueOf(bar.getProgress())) && desde <= desde_cap && hasta >= hasta_cap) {
                            generar_marcador(i);
                        }
                    } else {

                    }
                }
            }
        });
        desde_filtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(Mapa.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        desde_filtro.setText(String.format("%02d:%02d", hourOfDay, minutes) + ":" + 0 + 0);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });
        hasta_filtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(Mapa.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        hasta_filtro.setText(String.format("%02d:%02d", hourOfDay, minutes) + ":" + 0 + 0);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        enableMyLocationIfPermitted();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        final Task location = mFusedLocationProviderClient.getLastLocation();
        location.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                    Location location = (Location) task.getResult();
                    localidad=location;
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(latitude,
                                longitude));
                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);

                        mMap.moveCamera(center);
                        mMap.animateCamera(zoom);
            }
        });
        mMap.setOnMyLocationClickListener(onMyLocationClickListener);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(16);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        mMap.setOnMarkerClickListener(this);
        for(int i=0; i<tamaño;i++) {
            generar_marcador(i);
        }
        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

    }
    @Override
    public boolean onMarkerClick(final Marker marker) {
        Intent intent =new Intent(Mapa.this,articulo.class);
        String[] tokens = marker.getTitle().split("/");
           int position =Integer.parseInt(tokens[0]);
        intent.putExtra("ide", ide);
        intent.putExtra("id_objeto",id_objeto[position]);
        intent.putExtra("name", nombre[position]);
        intent.putExtra("image", imagen[position]);
        intent.putExtra("categoria", categoria[position]);
        intent.putExtra("publicacion", Publicacion[position]);
        intent.putExtra("sub", Sub[position]);
        intent.putExtra("expiracion", Expiracion[position]);
        intent.putExtra("horario", Desde[position]);
        intent.putExtra("descripcion", Descripcion[position]);
        intent.putExtra("desde", Desde[position]);
        intent.putExtra("hasta", Hasta[position]);
        intent.putExtra("descripcion_estado",descripcion_estado[position]);
        intent.putExtra("estado",estado[position]);
        intent.putExtra("direccion", direccion[position]);
        intent.putExtra("cantidad", Cantidad[position]);
        intent.putExtra("unidad_medida", Unidad_medida[position]);
        intent.putExtra("id_estado",id_estado[position]);
        intent.putExtra("nombre_estado",nombre_estado[position]);
        startActivity(intent);

        return false;
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
    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
            new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {
                    localidad=location;
                }
            };
    private void showDefaultLocation() {
        Toast.makeText(this, "Location permission not granted, " +
                        "showing default location",
                Toast.LENGTH_LONG).show();
        LatLng redmond = new LatLng(47.6739881, -122.121512);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfPermitted();
                } else {
                    showDefaultLocation();
                }
                return;
            }

        }
    }
    public void Categorias(final Spinner spinner) {
        class UserLoginClass extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Mapa.this, "Sincronizando", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                    JSONArray obj = new JSONArray(s);
                    arreglo_c= new String[obj.length()+1];
                    arreglo_c[0]="Todos";
                    for(int i=0;i < obj.length();i++){
                        arreglo_c[i+1] = obj.getJSONObject(i).getString("nombre_categoria");
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Mapa.this, android.R.layout.simple_spinner_item,arreglo_c );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
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
        ulc.execute(String.valueOf(spinner));
    }
    private void generar_marcador (int i){
        final LatLng punto = new LatLng(Double.valueOf(latitud[i]), Double.valueOf(longitud[i]));
        MarkerOptions markerOptions = new MarkerOptions();
        if(nombre_estado[i].equals("Libre")) {
            markerOptions.position(punto)
                    .title(i + "/" + " " + nombre[i])
                    .snippet("Categoría:" + " " + categoria[i])
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        }
        else if(nombre_estado[i].equals("Reservado")) {
            markerOptions.position(punto)
                    .title(i + "/" + " " + nombre[i])
                    .snippet("Categoría:" + " " + categoria[i])
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        }
        else{
            markerOptions.position(punto)
                    .title(i + "/" + " " + nombre[i])
                    .snippet("Categoría:" + " " + categoria[i])
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }
        InfoWindowData info = new InfoWindowData();
        info.setImagen(imagen[i]);
        info.setDetalle("descripcion");
        CustomInfoWindowAdapter customInfoWindow = new CustomInfoWindowAdapter(Mapa.this);
        mMap.setInfoWindowAdapter(customInfoWindow);
        m = mMap.addMarker(markerOptions);
        m.setTag(info);
        m.showInfoWindow();
    }
}

