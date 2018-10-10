package com.amitshekhar.tflite;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import java.util.List;
import java.util.Locale;


public class Ubicacion extends AppCompatActivity implements
        OnMapReadyCallback,GoogleMap.OnMapClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    String ide, nombre,fecha,numero,fecha_e,desde,hasta,categoria,sub,estado,direccion,tipo,descripcion;
    Marker marcador;
    Button listo;
    float distance, numeromenor=100000000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);
        Toolbar tb = findViewById(R.id.toolbar);
        enableMyLocationIfPermitted();
        tb.setSubtitle("Bienvenido a Pickup Market");
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        ide=String.valueOf(b.get("ide"));
        nombre=String.valueOf(b.get("nombre"));
        fecha=String.valueOf(b.get("fecha"));
        numero=String.valueOf(b.get("numero"));
        fecha_e=String.valueOf(b.get("fecha_e"));
        desde=String.valueOf(b.get("desde"));
        hasta=String.valueOf(b.get("hasta"));
        categoria=String.valueOf(b.get("categoria"));
        sub=String.valueOf(b.get("sub"));
        estado=String.valueOf(b.get("estado"));
        tipo= String.valueOf(b.get("tipo"));
        descripcion =String.valueOf(b.get("descripcion"));
        listo =(Button) findViewById(R.id.listo_ubicacion);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.current_location);
        mapFragment.getMapAsync(this);
        listo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
Intent intent =new Intent (Ubicacion.this,subir.class);
intent.putExtra("ide",ide);
intent.putExtra("latitud",marcador.getPosition().latitude);
intent.putExtra("longitud",marcador.getPosition().longitude);
intent.putExtra("nombre",nombre);
intent.putExtra("fecha",fecha);
intent.putExtra("numero",numero);
intent.putExtra("fecha_e",fecha_e);
intent.putExtra("desde",desde);
intent.putExtra("hasta",hasta);
intent.putExtra("categoria",categoria);
intent.putExtra("sub",sub);
intent.putExtra("estado",estado);
intent.putExtra("tipo",tipo);
intent.putExtra("descripcion",descripcion);
intent.putExtra("direccion",getCompleteAddressString(marcador.getPosition().latitude,marcador.getPosition().longitude));
startActivity(intent);
finish();

            }
        });
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
            Intent intent = new Intent(Ubicacion.this,
                    Perfil.class);
            startActivity(intent);
        }
        return true;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        mMap.setOnMapClickListener(this);
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
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(latitude,
                    longitude));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

            googleMap.moveCamera(center);
            googleMap.animateCamera(zoom);
        }
        enableMyLocationIfPermitted();
        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        mMap.setOnMyLocationClickListener(onMyLocationClickListener);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(11);
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
    private void showDefaultLocation() {
        Toast.makeText(this, "Location permission not granted, " +
                        "showing default location",
                Toast.LENGTH_SHORT).show();
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

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    mMap.setMinZoomPreference(16);
                    return false;
                }
            };

    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
            new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {

                    mMap.setMinZoomPreference(12);

                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.center(new LatLng(location.getLatitude(),
                            location.getLongitude()));

                    mMap.addCircle(circleOptions);
                }
            };
    @Override public void onMapClick(LatLng puntoPulsado) {
        if (marcador!=null){
            marcador.remove();
        }
     marcador= mMap.addMarker(new MarkerOptions().position(puntoPulsado)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
    }
    @SuppressLint("LongLogTag")
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                android.location.Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current loction address", strReturnedAddress.toString());
            } else {
                Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

}
