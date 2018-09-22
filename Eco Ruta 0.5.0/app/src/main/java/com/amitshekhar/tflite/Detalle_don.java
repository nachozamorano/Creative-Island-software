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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class Detalle_don extends Activity {
    private ListView lista;
    String ide;
    String descripcion,subcategoria,fecha_publicacion,fecha_expiracion,horario_retiro,estado,ubicacion;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_don);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        ide=String.valueOf("ide");
        ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();
        imagen = (ImageView) findViewById(R.id.imageView);
        imagen.setImageResource((Integer) b.get("image"));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Nombre:", (String) b.get("name")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Interesado:", (String) b.get("nombre_interesado")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Publicacion", (String) b.get("publicacion")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Direccion:", (String) b.get("direccion")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Categoria:", (String) b.get("categoria")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Sub categoria:", (String) b.get("sub")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Fecha de expiracion:", (String) b.get("expiracion")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Horario (desde/hasta):", (String) b.get("desde")+"/"+(String) b.get("hasta")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Cantidad:", (String) b.get("cantidad")+" "+(String) b.get("unidad_medida")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Estado transaccion:", (String) b.get("nombre_estado")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Estado:", (String) b.get("estado")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Descripcion:", (String) b.get("descripcion")));
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

    }
}
