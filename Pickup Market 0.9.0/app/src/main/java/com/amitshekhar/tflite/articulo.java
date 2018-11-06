package com.amitshekhar.tflite;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
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
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.joooonho.SelectableRoundedImageView.TAG;

public class articulo extends Activity {
        String url= "https://ecoruta.webcindario.com/BotonReservar.php",mio="https://ecoruta.webcindario.com/noObjeto.php";
    private ListView lista;
    Button donar;
    String ide,id_estado;
    String id_objeto;
    AlertDialog.Builder dialogo1;
    LayoutInflater layoutInflater;
    JSONArray jsonArray;
    boolean respuesta;
    Button btn_Cerrar;
    View popupView;
    Button listo;
    String [] respuest;
    String fecha,expiracion;
    String amPm;
    ImageButton btn_agregar;
    private static final String CERO = "0";
    private static final String BARRA = "-";
    public final Calendar c = Calendar.getInstance();
    int mes = c.get(Calendar.MONTH);
    int dia = c.get(Calendar.DAY_OF_MONTH);
    int anio = c.get(Calendar.YEAR);
    EditText etFecha;
    PopupWindow popupWindow;
    String descripcion,estado,ubicacion;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        id_objeto = String.valueOf(b.get("id_objeto"));
        ide=String.valueOf(b.get("ide"));
        expiracion= (String) b.get("expiracion");
        id_estado =String.valueOf(b.get("id_estado"));
        ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();
        imagen = (ImageView) findViewById(R.id.imageView);
        mis_productos(ide,id_objeto);
        if(String.valueOf(b.get("image")).isEmpty()){
            imagen.setImageResource(R.drawable.sin_imagen);
        }
        else {
            Picasso.get().load(String.valueOf(b.get("image"))).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(imagen);
        }
        datos.add(new Lista_entrada( "Nombre:", (String) b.get("name")));
        datos.add(new Lista_entrada( "Estado objeto:", (String) b.get("nombre_estado")));
        datos.add(new Lista_entrada( "Publicación", (String) b.get("publicacion")));
        datos.add(new Lista_entrada( "Dirección:", (String) b.get("direccion")));
        datos.add(new Lista_entrada( "Categoria:", (String) b.get("categoria")));
        datos.add(new Lista_entrada( "Sub categoria:", (String) b.get("sub")));
        datos.add(new Lista_entrada( "Fecha de expiracion:", (String) b.get("expiracion")));
        datos.add(new Lista_entrada( "Horario (desde/hasta):", (String) b.get("desde")+"/"+(String) b.get("hasta")));
        datos.add(new Lista_entrada( "Cantidad:", (String) b.get("cantidad")+" "+(String) b.get("unidad_medida")));
        datos.add(new Lista_entrada( "Estado:", (String) b.get("estado")));
        datos.add(new Lista_entrada( "Descripcion estado:", (String) b.get("descripcion_estado")));
        datos.add(new Lista_entrada( "Descripción:", (String) b.get("descripcion")));
        donar = (Button) findViewById(R.id.button8);
        donar.setVisibility(View.GONE);
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
        donar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                btn_agregar =(ImageButton)popupView.findViewById(R.id.ib_obtener_fecha);
                btn_Cerrar = (Button)popupView.findViewById(R.id.id_cerrar);
                etFecha = (EditText) popupView.findViewById(R.id.et_mostrar_fecha_picker);
                listo= (Button)popupView.findViewById(R.id.agregar);
                btn_agregar.setOnClickListener(new Button.OnClickListener(){

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {
                        obtenerFecha();
                    }});
                btn_Cerrar.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }});
                listo.setOnClickListener(new Button.OnClickListener(){

                   @Override
                public void onClick(View v) {
                       dialogo1 = new AlertDialog.Builder(articulo.this);
                       dialogo1.setTitle("Alerta");
                       dialogo1.setMessage("¿Desea agregar este producto?");
                       dialogo1.setCancelable(false);
                       dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialogo1, int id) {
                               if(fecha==null) {
                                   Toast.makeText(articulo.this, "Agregue una fecha", Toast.LENGTH_SHORT).show();
                               }else {
                                   new articulo.Reservar(articulo.this).execute();
                               }
                           }
                       });
                       dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialogo1, int id) {
                               cancelar();
                           }
                       });
                       dialogo1.show();

                   }

                });

            }
        });

    }
    private boolean reservar(){
        HttpClient httpClient;
        List<NameValuePair> nameValuePairs;
        HttpPost httpPost;

        httpClient = new DefaultHttpClient();
        httpPost = new HttpPost(url);//url del servidor
        //empezamos añadir nuestros datos
        nameValuePairs = new ArrayList<NameValuePair>(3);
        nameValuePairs.add(new BasicNameValuePair("id_objeto",id_objeto.trim()));
        nameValuePairs.add(new BasicNameValuePair("fecha_retiro",fecha.trim()));
        nameValuePairs.add(new BasicNameValuePair("id_interesado",ide.trim()));
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
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void obtenerFecha(){
        final DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                etFecha.setText(year + BARRA + mesFormateado + BARRA + diaFormateado );
                fecha = String.valueOf(etFecha.getText());


            }
        },anio, mes, dia);
        //Muestro el widget
        String date = expiracion;
        String[] values = date.split("-");
        c.set( Integer.parseInt(values[0]),Integer.parseInt(values[1])-1,Integer.parseInt(values[2]));
        long currentTime = new Date().getTime();
        recogerFecha.getDatePicker().setMaxDate(c.getTimeInMillis());
        recogerFecha.getDatePicker().setMinDate(currentTime);
        recogerFecha.show();

    }
    class Reservar extends AsyncTask<String,String,String> {
        private Activity context;
        Reservar(Activity context){
            this.context=context;
        }
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if(reservar())
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(articulo.this, "Adquirido", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(articulo.this, Mercado.class);
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
    public void cancelar() {
        Intent intent1 = new Intent(articulo.this, Mercado.class);
        Toast.makeText(this,"Cancelado", Toast.LENGTH_SHORT);
        intent1.putExtra("ide",ide);
        startActivity(intent1);
        finish();
    }
    public void mis_productos(final String ide,final String id_objeto) {
        class product extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    if (jsonArray.length()>0){
                        respuesta=true;
                    }
                    else{
                        respuesta=false;
                    }
                    if(id_estado.equals("1")&& respuesta==false){
                        donar.setVisibility(View.VISIBLE);
                    }
                    else {
                        donar.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<>();
                data.put("id_objeto", params[0]);
                data.put("id_propietario", params[1]);

                HttpQuerys ruc1 = new HttpQuerys();

                String result = ruc1.sendPostRequest(mio, data);

                return result;
            }
        }
        product ulc = new product();
        ulc.execute(id_objeto,ide);



    }
}