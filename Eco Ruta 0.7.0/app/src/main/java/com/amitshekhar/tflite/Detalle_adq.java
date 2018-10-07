package com.amitshekhar.tflite;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
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

public class Detalle_adq extends Activity {
    private ListView lista;
    String url= "https://ecoruta.webcindario.com/cancelarReserva.php";
    String ide;
    AlertDialog.Builder dialogo1;
    Button cancelar;
    String id_objeto;
    String descripcion,estado,ubicacion;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservas);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        id_objeto=String.valueOf(b.get("id_objeto"));
        cancelar =(Button) findViewById(R.id.cancelar);
        ide=String.valueOf(b.get("ide"));
        ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();
        imagen = (ImageView) findViewById(R.id.imageView);
        cancelar.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                dialogo1 = new AlertDialog.Builder(Detalle_adq.this);
                dialogo1.setTitle("Alerta");
                dialogo1.setMessage("¿Esta seguro de cancelar esta reserva?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        new Detalle_adq.cancelar(Detalle_adq.this).execute();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        cancelar();
                    }
                });
                dialogo1.show();
            }});
        if(String.valueOf(b.get("image")).isEmpty()){
            imagen.setImageResource(R.drawable.sin_imagen);
        }
        else {
            Picasso.get().load(String.valueOf(b.get("image"))).into(imagen);
        }
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Nombre:", (String) b.get("name")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Nombre propietario:", (String) b.get("nombre_propietario")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Publicacion", (String) b.get("publicacion")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Direccion:", (String) b.get("direccion")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Categoria:", (String) b.get("categoria")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Sub categoria:", (String) b.get("sub")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Estado transaccion:", (String) b.get("nombre_estado")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Horario (desde/hasta):", (String) b.get("desde")+"/"+(String) b.get("hasta")));
        datos.add(new Lista_entrada(R.drawable.ic_fiber_manual_record_black_24dp, "Cantidad:", (String) b.get("cantidad")+" "+(String) b.get("unidad_medida")));
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

    class cancelar extends AsyncTask<String,String,String> {
        private Activity context;
        cancelar(Activity context){
            this.context=context;
        }
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if(cancelar_reservas())
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(Detalle_adq.this, "Reserva cancelada", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Detalle_adq.this, hist_adq.class);
                        intent.putExtra("ide", ide);
                        startActivity(intent);
                        finish();
                    }
                });
            else
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                    }
                });
            return null;
        }
    }
    private boolean cancelar_reservas(){
        HttpClient httpClient;
        List<NameValuePair> nameValuePairs;
        HttpPost httpPost;

        httpClient = new DefaultHttpClient();
        httpPost = new HttpPost(url);//url del servidor
        //empezamos añadir nuestros datos
        nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("id_objeto",id_objeto.trim()));
        System.out.println(nameValuePairs);


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
    public void cancelar() {
        Intent intent1 = new Intent(Detalle_adq.this, hist_adq.class);
        Toast.makeText(this,"Cancelado", Toast.LENGTH_SHORT);
        intent1.putExtra("ide",ide);
        startActivity(intent1);
        finish();
    }
}
