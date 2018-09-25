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

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class articulo extends Activity {
        String url= "https://ecoruta.webcindario.com/BuscarObjeto.php";
    private ListView lista;
    Button donar;
    String ide;
    String descripcion,subcategoria,fecha_publicacion,fecha_expiracion,horario_retiro,estado,ubicacion;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        ide=String.valueOf("ide");
        ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();
        imagen = (ImageView) findViewById(R.id.imageView);
        System.out.println("holii"+String.valueOf(b.get("image")));
        if(String.valueOf(b.get("image")).isEmpty()){
            imagen.setImageResource(R.drawable.sin_imagen);
        }
        else {
            Picasso.get().load(String.valueOf(b.get("image"))).into(imagen);
        }
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Nombre:", (String) b.get("name")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Publicacion", (String) b.get("publicacion")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Direccion:", (String) b.get("direccion")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Categoria:", (String) b.get("categoria")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Sub categoria:", (String) b.get("sub")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Fecha de expiracion:", (String) b.get("expiracion")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Horario (desde/hasta):", (String) b.get("desde")+"/"+(String) b.get("hasta")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Cantidad:", (String) b.get("cantidad")+" "+(String) b.get("unidad_medida")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Estado:", (String) b.get("estado")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Descripcion:", (String) b.get("descripcion")));
        donar = (Button) findViewById(R.id.button8);
        lista = (ListView) findViewById(R.id.listado);
        lista.setAdapter(new Lista_adaptador(this, R.layout.entrada, datos) {
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
                Intent intent =new Intent (articulo.this,Mercado.class);
                intent.putExtra("ide",ide);
                startActivity(intent);
                finish();
            }
        });

    }
}