package com.amitshekhar.tflite;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Modificar extends AppCompatActivity  implements View.OnClickListener {
    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";
    String url= "https://ecoruta.webcindario.com/BuscarCategoria.php";
    String url1= "https://ecoruta.webcindario.com/BusquedaSubCategoria.php";
    String url_i= "https://ecoruta.webcindario.com/actualizarObjeto.php";
    String url_eliminar="https://ecoruta.webcindario.com/eliminarObjeto.php";
    String[] arreglo_estado;
    String[] arreglo_id;
    AlertDialog.Builder dialogo1;
    AlertDialog.Builder dialogo2;
    String url_medida="https://ecoruta.webcindario.com/buscarUnidadMedida.php",ide;
    String url_estado ="https://ecoruta.webcindario.com/BuscarEstado.php",id_objeto;
    private final int MY_PERMISSIONS = 100;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    String[] arreglo_medida;
    int currentMinute;
    String amPm;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;
    private static final String CERO = "0";
    private static final String BARRA = "-";
    Object latitud,longitud;

    String encodedImage;
    public ImageView mSetImage;
    private Button mOptionButton;
    private RelativeLayout mRlView;
    Button listo,eliminar,menos,mas,ubicacion;
    int cantidad=1;

    private String mPath;
    Spinner Categoria, Sub_categoria,Estado,tipo;
    EditText etFecha;
    TextView Nombre;
    EditText Fecha_de_expiracion;
    EditText direccion;
    TextView descripcion_estado;
    EditText Descripcion;
    EditText numero;
    EditText desde;
    EditText hasta;
    ImageButton ibObtenerFecha;
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        final Bundle b = intent.getExtras();
        setContentView(R.layout.activity_texto);
        ide=String.valueOf(b.get("ide"));
        etFecha = (EditText) findViewById(R.id.et_mostrar_fecha_picker);
        ibObtenerFecha = (ImageButton) findViewById(R.id.ib_obtener_fecha);
        Nombre= (TextView) findViewById(R.id.editText2);
        Categoria = (Spinner) findViewById(R.id.Spinner01);
        Fecha_de_expiracion = (EditText) findViewById(R.id.et_mostrar_fecha_picker);
        direccion= (EditText) findViewById(R.id.direccion);
        Sub_categoria = (Spinner) findViewById(R.id.Spinner02);
        descripcion_estado = (TextView) findViewById(R.id.descripcion_estado);
        Estado =(Spinner) findViewById(R.id.Spinner03);
        mSetImage = (ImageView) findViewById(R.id.set_picture);
        ubicacion = (Button) findViewById(R.id.ubicacion);
        numero = (EditText) findViewById(R.id.numero);
        desde= (EditText) findViewById(R.id.desde);
        hasta = (EditText) findViewById(R.id.hasta);
        Descripcion = (EditText) findViewById(R.id.editText8);
        tipo = (Spinner) findViewById(R.id.tipo);
        mas=(Button) findViewById(R.id.mas);
        menos=(Button) findViewById(R.id.menos);
        eliminar= (Button) findViewById(R.id.eliminar);
        Nombre.setText((String) b.get("name"));
        etFecha.setText((String) b.get("publicacion"));
        Fecha_de_expiracion.setText((String) b.get("expiracion"));
        direccion.setText((String) b.get("direccion"));
        Descripcion.setText((String) b.get("descripcion"));
        if(String.valueOf(b.get("image")).isEmpty()){
            System.out.println(encodedImage+"aqui paso1");
            mSetImage.setImageResource(R.drawable.sin_imagen);

        }
        else {
            try {
                Picasso.get().load(String.valueOf(b.get("image"))).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(mSetImage);
                Bitmap bmap = ((BitmapDrawable) mSetImage.getDrawable()).getBitmap();
                encodedImage = getStringImagen(bmap);
            }
            catch (Exception e) {
                mSetImage.setImageResource(R.drawable.sin_imagen);
            }

        }
        numero.setText((String) b.get("cantidad"));
        id_objeto= (String) b.get("id_objeto");
        desde.setText((String) b.get("desde"));
        hasta.setText((String) b.get("hasta"));
        latitud = b.get("latitud");
        longitud = b.get("longitud");



        //Evento setOnClickListener - clic
        listo=(Button) findViewById(R.id.listo);
        medida(tipo);
        Categorias(Categoria);
        Categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        Subcategoria(Sub_categoria,position+1);
                        break;
                    case 1:
                        Subcategoria(Sub_categoria,position+1);
                        break;
                    case 2:
                        Subcategoria(Sub_categoria,position+1);
                        break;
                    case 3:
                        Subcategoria(Sub_categoria,position+1);
                        break;
                    case 4:
                        Subcategoria(Sub_categoria,position+1);
                        break;
                    case 5:
                        Subcategoria(Sub_categoria,position+1);
                        break;
                    case 6:
                        Subcategoria(Sub_categoria,position+1);
                        break;
                    case 7:
                        Subcategoria(Sub_categoria,position+1);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });
        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cantidad++;
                numero.setText( Integer.toString(cantidad));


            }
        });
        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cantidad--;
                if (cantidad<1){
                    cantidad=1;
                }
                numero.setText( Integer.toString(cantidad));
            }
        });
        desde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(Modificar.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        desde.setText(String.format("%02d:%02d", hourOfDay, minutes) + ":"+0+0);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });        ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ub = new Intent(Modificar.this,Ubicacion_modificar.class);
                ub.putExtra("ide",ide);
                ub.putExtra("id_objeto",id_objeto);
                ub.putExtra("name",Nombre.getText().toString().trim());
                ub.putExtra("image",String.valueOf(b.get("image")));
                ub.putExtra("estado",(String) b.get("estado"));
                ub.putExtra("publicacion",(String) b.get("publicacion"));
                ub.putExtra("sub",(String) b.get("Sub"));
                ub.putExtra("expiracion",Fecha_de_expiracion.getText().toString().trim());
                ub.putExtra("descripcion",Descripcion.getText().toString().trim());
                ub.putExtra("desde",desde.getText().toString().trim());
                ub.putExtra("hasta",hasta.getText().toString().trim());
                ub.putExtra("direccion",direccion.getText().toString().trim());
                ub.putExtra("cantidad",numero.getText().toString().trim() );
                ub.putExtra("unidad_medida",(String) b.get("unidad_medida"));
                ub.putExtra("latitud",(String) b.get("latitud"));
                ub.putExtra("descripcion_estado",(String) b.get("descripcion_estado"));
                ub.putExtra("longitud",(String) b.get("longitud"));
                startActivity(ub);
            }
        });

        hasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(Modificar.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        hasta.setText(String.format("%02d:%02d", hourOfDay, minutes) + ":"+0+0);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });
        listo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Nombre.getText().toString().trim().equalsIgnoreCase("")||
                        !Categoria.getSelectedItem().toString().trim().equalsIgnoreCase("")||
                        !Fecha_de_expiracion.getText().toString().trim().equalsIgnoreCase("")||
                        !direccion.getText().toString().trim().equalsIgnoreCase("")||
                        !Sub_categoria.getSelectedItem().toString().trim().equalsIgnoreCase("")||
                        !Descripcion.getText().toString().trim().equalsIgnoreCase("")||
                        !numero.getText().toString().trim().equalsIgnoreCase("")) {
                    dialogo1 = new AlertDialog.Builder(Modificar.this);
                    dialogo1.setTitle("Alerta");
                    dialogo1.setMessage("¿Desea guardar los cambios?");
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            new Modificar.Insertar(Modificar.this).execute();
                            Toast.makeText(Modificar.this, "Actualizado con exito", Toast.LENGTH_LONG).show();
                            Intent insert= new Intent(Modificar.this,Publicaciones.class);
                            insert.putExtra("ide",ide);
                            startActivity(insert);
                            finish();
                        }
                    });
                    dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            cancelar();
                        }
                    });
                    dialogo1.show();
                }
                else {
                    Toast.makeText(Modificar.this, "Hay información por rellenar", Toast.LENGTH_LONG).show();
                }
            }
        });
        Estado(Estado);
        ibObtenerFecha.setOnClickListener(this);

        mOptionButton = (Button) findViewById(R.id.show_options_button);

        if(mayRequestStoragePermission())
            mOptionButton.setEnabled(true);
        else
            mOptionButton.setEnabled(false);


        mOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo2 = new AlertDialog.Builder(Modificar.this);
                dialogo2.setTitle("Alerta");
                dialogo2.setMessage("¿Esta seguro de eliminar el objeto?");
                dialogo2.setCancelable(false);
                dialogo2.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        eliminar(id_objeto);
                        Toast.makeText(Modificar.this, "Eliminado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent (Modificar.this,Publicaciones.class);
                        intent.putExtra("ide",ide);
                        startActivity(intent);
                        finish();
                    }
                });
                dialogo2.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        cancelar();
                    }
                });
                dialogo2.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_obtener_fecha:
                obtenerFecha();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
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


            }
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }
    private boolean mayRequestStoragePermission() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(mRlView, "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            });
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }

        return false;
    }

    private void showOptions() {
        final CharSequence[] option = {"Tomar foto", "Elegir de galeria", "Cancelar"};
        final android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(Modificar.this);
        builder.setTitle("Eleige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(option[which] == "Tomar foto"){
                    openCamera();
                }else if(option[which] == "Elegir de galeria"){
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
                }else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            startActivityForResult(intent, PHOTO_CODE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mPath = savedInstanceState.getString("file_path");
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this,
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> Uri = " + uri);
                                }
                            });


                    Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                    mSetImage.setImageBitmap(bitmap);
                    encodedImage=getStringImagen(bitmap);
                    break;
                case SELECT_PICTURE:
                    Uri path = data.getData();
                    mSetImage.setImageURI(path);
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(
                                path);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bmp = BitmapFactory.decodeStream(imageStream);
                    encodedImage=getStringImagen(bmp);
                    break;

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                mOptionButton.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }
    private boolean insertar(){
        HttpClient httpClient;
        List<NameValuePair> nameValuePairs;
        HttpPost httpPost;
        httpClient = new DefaultHttpClient();
        httpPost = new HttpPost(url_i);//url del servidor
        //empezamos añadir nuestros datos
        nameValuePairs = new ArrayList<NameValuePair>(14);
        nameValuePairs.add(new BasicNameValuePair("id_objeto",id_objeto.trim()));
        nameValuePairs.add(new BasicNameValuePair("nombre_objeto",Nombre.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("descripcion",Descripcion.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("imagen",encodedImage));
        nameValuePairs.add(new BasicNameValuePair("subcategoria",arreglo_id[Sub_categoria.getSelectedItemPosition()].trim()));
        nameValuePairs.add(new BasicNameValuePair("fecha_expiracion",Fecha_de_expiracion.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("id_estado_objeto",String.valueOf(Estado.getSelectedItemPosition()+1)));
        nameValuePairs.add(new BasicNameValuePair("cantidad",numero.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("id_unidad",String.valueOf(tipo.getSelectedItemPosition()+1)));
        nameValuePairs.add(new BasicNameValuePair("Latitud",String.valueOf(latitud)));
        nameValuePairs.add(new BasicNameValuePair("Longitud",String.valueOf(longitud)));
        nameValuePairs.add(new BasicNameValuePair("desde",desde.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("hasta",hasta.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("direccion",direccion.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("descripcion_estado",descripcion_estado.getText().toString().trim()));

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
    //AsyncTask para insertar Personas
    class Insertar extends AsyncTask<String,String,String> {
        private Activity context;
        Insertar(Activity context){
            this.context=context;
        }
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if(insertar())
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
    public void Categorias(final Spinner spinner) {
        class UserLoginClass extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Modificar.this, "Sincronizando", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                    JSONArray obj = new JSONArray(s);
                    String[] arreglo_c= new String[obj.length()];
                    for(int i=0;i < obj.length();i++){
                        arreglo_c[i] = obj.getJSONObject(i).getString("nombre_categoria");
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Modificar.this, android.R.layout.simple_spinner_item,arreglo_c );
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
    public void Subcategoria(final Spinner spinner, final int pos) {
        class UserLoginClass extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Modificar.this, "Sincronizando", null, true, true);
            }

            @Override
            protected void onPostExecute(String s1) {
                super.onPostExecute(s1);
                loading.dismiss();
                try {
                    JSONArray obj = new JSONArray(s1);
                    arreglo_id= new String[obj.length()];
                    String[] arreglo_s= new String[obj.length()];
                    for(int i=0;i < obj.length();i++){
                        arreglo_s[i] = obj.getJSONObject(i).getString("nombre");
                        arreglo_id[i]=obj.getJSONObject(i).getString("id_subcategoria");


                    }
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Modificar.this, android.R.layout.simple_spinner_item,arreglo_s );
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data1 = new HashMap<>();
                data1.put("id_categoria", String.valueOf(pos));

                HttpQuerys ruc1 = new HttpQuerys();

                String result = ruc1.sendPostRequest(url1, data1);

                return result;
            }
        }

        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(String.valueOf(spinner));

    }
    public void Estado(final Spinner spinner) {
        class UserLoginClass extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Modificar.this, "Sincronizando", null, true, true);
            }

            @Override
            protected void onPostExecute(String s1) {
                super.onPostExecute(s1);
                loading.dismiss();
                try {
                    JSONArray obj = new JSONArray(s1);
                    arreglo_estado= new String[obj.length()];
                    for(int i=0;i < obj.length();i++){
                        arreglo_estado[i] = obj.getJSONObject(i).getString("nombre_estado_objeto");
                    }
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Modificar.this, android.R.layout.simple_spinner_item,arreglo_estado );
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data1 = new HashMap<>();

                HttpQuerys ruc1 = new HttpQuerys();

                String result = ruc1.sendPostRequest(url_estado, data1);

                return result;
            }
        }

        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(String.valueOf(spinner));

    }
    public String getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public void eliminar(final String ide) {
        class UserLoginClass extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                loading = ProgressDialog.show(Modificar.this, "Sincronizando", null, true, true);
            }


            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                loading.dismiss();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<>();
                data.put("id_objeto", params[0]);

                HttpQuerys ruc1 = new HttpQuerys();

                String result = ruc1.sendPostRequest(url_eliminar, data);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(ide);



    }
    public void medida(final Spinner spinner) {
        class UserLoginClass extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Modificar.this, "Sincronizando", null, true, true);
            }

            @Override
            protected void onPostExecute(String s2) {
                super.onPostExecute(s2);
                loading.dismiss();
                try {
                    JSONArray obj = new JSONArray(s2);
                    arreglo_medida= new String[obj.length()];
                    for(int i=0;i < obj.length();i++){
                        arreglo_medida[i] = obj.getJSONObject(i).getString("unidad_medida");
                    }
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Modificar.this, android.R.layout.simple_spinner_item,arreglo_medida );
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data1 = new HashMap<>();

                HttpQuerys ruc1 = new HttpQuerys();

                String result = ruc1.sendPostRequest(url_medida, data1);

                return result;
            }
        }

        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(String.valueOf(spinner));

    }
    public void cancelar() {
        Intent intent1 = new Intent(Modificar.this, Publicaciones.class);
        Toast.makeText(this,"Cancelado", Toast.LENGTH_SHORT);
        intent1.putExtra("ide",ide);
        startActivity(intent1);
        finish();
    }
}