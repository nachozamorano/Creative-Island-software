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

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Detalle_don extends Activity {
    private ListView lista;
    String ide,url="https://ecoruta.webcindario.com/cambiarLibreEntregado.php";
    String descripcion,estado,ubicacion;
    ImageView imagen;
    Button donar,liberar;
    String id_objeto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_don);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        ide=String.valueOf("ide");
        id_objeto=(String) b.get("id_objeto");
        ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();
        imagen = (ImageView) findViewById(R.id.imageView);
        donar = (Button) findViewById(R.id.button7);
        liberar = (Button) findViewById(R.id.button8);
        if (((String) b.get("nombre_estado")).equals("Libre")){
            liberar.setVisibility(View.GONE);
            donar.setVisibility(View.GONE);
        }
        else if(((String) b.get("nombre_estado")).equals("Donado")) {
            liberar.setVisibility(View.GONE);
            donar.setVisibility(View.GONE);
        }
        else if(((String) b.get("nombre_estado")).equals("Reservado")) {
            liberar.setVisibility(View.VISIBLE);
            donar.setVisibility(View.VISIBLE);
        }
        donar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Detalle_don.liberar_donar(Detalle_don.this,"ENTREGADO").execute();
                Toast.makeText(Detalle_don.this, "Entregado", Toast.LENGTH_LONG).show();
                Intent intent =new Intent(Detalle_don.this,hist_don.class);
                intent.putExtra("ide", ide);
                startActivity(intent);
                finish();
            }
        });
        liberar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Detalle_don.liberar_donar(Detalle_don.this,"LIBRE").execute();
                Toast.makeText(Detalle_don.this, "Liberado", Toast.LENGTH_LONG).show();
                Intent intent =new Intent(Detalle_don.this,hist_don.class);
                intent.putExtra("ide", ide);
                startActivity(intent);
                finish();
            }
        });
        if(String.valueOf(b.get("image")).isEmpty()){
            imagen.setImageResource(R.drawable.sin_imagen);
        }
        else {
            Picasso.get().load(String.valueOf(b.get("image"))).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(imagen);
        }
        datos.add(new Lista_entrada("Nombre:", (String) b.get("name")));
        datos.add(new Lista_entrada("Interesado:", (String) b.get("nombre_interesado")));
        datos.add(new Lista_entrada("Publicación", (String) b.get("publicacion")));
        datos.add(new Lista_entrada("Dirección:", (String) b.get("direccion")));
        datos.add(new Lista_entrada( "Categoria:", (String) b.get("categoria")));
        datos.add(new Lista_entrada( "Sub categoria:", (String) b.get("sub")));
        datos.add(new Lista_entrada( "Fecha de expiracion:", (String) b.get("expiracion")));
        datos.add(new Lista_entrada( "Horario (desde/hasta):", (String) b.get("desde")+"/"+(String) b.get("hasta")));
        datos.add(new Lista_entrada( "Cantidad:", (String) b.get("cantidad")+" "+(String) b.get("unidad_medida")));
        datos.add(new Lista_entrada( "Estado transaccion:", (String) b.get("nombre_estado")));
        datos.add(new Lista_entrada( "Estado:", (String) b.get("estado")));
        datos.add(new Lista_entrada( "Descripción:", (String) b.get("descripcion")));
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
                }
            }
        });

    }
    private boolean liberar(String elegir){
        HttpClient httpClient;
        List<NameValuePair> nameValuePairs;
        HttpPost httpPost;
        httpClient = new DefaultHttpClient();
        httpPost = new HttpPost(url);//url del servidor
        //empezamos añadir nuestros datos
        nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("id_objeto",id_objeto.trim()));
        nameValuePairs.add(new BasicNameValuePair("n_estado",elegir.trim()));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpClient.execute(httpPost);
            return true;
        } catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return  false;
    }
    class liberar_donar extends AsyncTask<String,String,String> {
        private Activity context;
        private  String elegir;
        liberar_donar(Activity context,String elegir){
            this.context=context;
            this.elegir =elegir;
        }
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if(liberar(elegir))
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                    }
                });
            else
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "No actualizado con éxito", Toast.LENGTH_LONG).show();

                    }
                });
            return null;
        }
    }
}
