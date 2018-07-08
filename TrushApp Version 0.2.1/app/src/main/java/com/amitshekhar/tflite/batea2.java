package com.amitshekhar.tflite;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class batea2 extends Activity {

    private ListView lista;
    Button donar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batea2);

        ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();

        datos.add(new Lista_entrada(R.drawable.ic_send_black_24dp, "Fecha Inicio:", "27/6/2018"));
        datos.add(new Lista_entrada(R.drawable.ic_send_black_24dp, "Fecha Termino", "27/7/2018"));
        datos.add(new Lista_entrada(R.drawable.ic_send_black_24dp, "Direccion:", "Vicuña Mackenna 683"));
        datos.add(new Lista_entrada(R.drawable.ic_send_black_24dp, "Estado:", "Alto"));
        datos.add(new Lista_entrada(R.drawable.ic_send_black_24dp, "Volumen:", "1000Kg"));
        datos.add(new Lista_entrada(R.drawable.ic_send_black_24dp, "Capacidad:", "1000Kg"));

        lista = (ListView) findViewById(R.id.listado);
        donar = (Button) findViewById(R.id.button9);
        lista.setAdapter(new Lista_adaptador(this, R.layout.entrada, datos){
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

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                Lista_entrada elegido = (Lista_entrada) pariente.getItemAtPosition(posicion);

                CharSequence texto = "Seleccionado: " + elegido.get_textoDebajo();
                Toast toast = Toast.makeText(batea2.this, texto, Toast.LENGTH_LONG);
                toast.show();
            }
        });
        donar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(batea2.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}