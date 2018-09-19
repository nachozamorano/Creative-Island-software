package com.amitshekhar.tflite;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toast;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class subir extends AppCompatActivity  implements View.OnClickListener {
    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";
    String url= "http://bishop94.000webhostapp.com/Trashapp/BuscarCategoria.php";
    String url1= "http://bishop94.000webhostapp.com/Trashapp/BusquedaSubCategoria.php";
    String url_i= "http://bishop94.000webhostapp.com/Trashapp/insertarObjeto.php";
    String[] arreglo_id;
    String url_estado ="http://bishop94.000webhostapp.com/Trashapp/BuscarEstado.php";
    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;
    private static final String CERO = "0";
    private static final String BARRA = "-";
    String encodedImage;
    public ImageView mSetImage;
    private Button mOptionButton;
    private RelativeLayout mRlView;
    Button listo;

    private String mPath;
    Spinner Categoria, Sub_categoria,Estado;
    EditText etFecha, Nombre, Fecha_de_expiracion, Horario_retiro, Descripcion, Ubicacion ;
    ImageButton ibObtenerFecha;
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir);
        etFecha = (EditText) findViewById(R.id.et_mostrar_fecha_picker);
        ibObtenerFecha = (ImageButton) findViewById(R.id.ib_obtener_fecha);
        Nombre= (EditText) findViewById(R.id.editText2);
        Categoria = (Spinner) findViewById(R.id.Spinner01);
        Fecha_de_expiracion = (EditText) findViewById(R.id.et_mostrar_fecha_picker);
        Horario_retiro = (EditText) findViewById(R.id.editText4);
        Sub_categoria = (Spinner) findViewById(R.id.Spinner02);
        Estado =(Spinner) findViewById(R.id.Spinner03);
        mSetImage = (ImageView) findViewById(R.id.set_picture);
        Ubicacion = (EditText) findViewById(R.id.editText9);
        Descripcion = (EditText) findViewById(R.id.editText8);

        //Evento setOnClickListener - clic
        listo=(Button) findViewById(R.id.listo);
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
        listo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Nombre.getText().toString().trim().equalsIgnoreCase("")||
                        !Categoria.getSelectedItem().toString().trim().equalsIgnoreCase("")||
                        !Fecha_de_expiracion.getText().toString().trim().equalsIgnoreCase("")||
                        !Horario_retiro.getText().toString().trim().equalsIgnoreCase("")||
                        !Sub_categoria.getSelectedItem().toString().trim().equalsIgnoreCase("")||
                        !Descripcion.getText().toString().trim().equalsIgnoreCase("")||
                        !encodedImage.getBytes().toString().trim().equalsIgnoreCase("") ||
                        !Ubicacion.getText().toString().trim().equalsIgnoreCase("")) {
                    new Insertar(subir.this).execute();
                }
                else {
                    Toast.makeText(subir.this, "Hay información por rellenar", Toast.LENGTH_LONG).show();
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
    }
    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                Toast.LENGTH_SHORT).show();
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
        final android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(subir.this);
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
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
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
                    ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos1);
                    byte[] imageBytes1 = baos1.toByteArray();
                    encodedImage = Base64.encodeToString(imageBytes1, Base64.DEFAULT);
                    break;

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(subir.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                mOptionButton.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(subir.this);
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
                finish();
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
        nameValuePairs = new ArrayList<NameValuePair>(9);
        nameValuePairs.add(new BasicNameValuePair("id_propietario","1"));
        nameValuePairs.add(new BasicNameValuePair("nombre_objeto",Nombre.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("descripcion",Descripcion.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("imagen",encodedImage.getBytes().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("subcategoria",arreglo_id[Sub_categoria.getSelectedItemPosition()].trim()));
        nameValuePairs.add(new BasicNameValuePair("fecha_expiracion",Fecha_de_expiracion.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("horario_retiro",Horario_retiro.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("id_estado_objeto","1"));
        nameValuePairs.add(new BasicNameValuePair("ubicacion",Ubicacion.getText().toString().trim()));

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
                        Toast.makeText(context, "Insertada con éxito", Toast.LENGTH_LONG).show();
                        Intent insert= new Intent(subir.this,Mercado.class);
                        startActivity(insert);
                    }
                });
            else
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "No insertada con éxito", Toast.LENGTH_LONG).show();

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
                loading = ProgressDialog.show(subir.this, "Sincronizando", null, true, true);
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(subir.this, android.R.layout.simple_spinner_item,arreglo_c );
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
                loading = ProgressDialog.show(subir.this, "Sincronizando", null, true, true);
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
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(subir.this, android.R.layout.simple_spinner_item,arreglo_s );
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
                loading = ProgressDialog.show(subir.this, "Sincronizando", null, true, true);
            }

            @Override
            protected void onPostExecute(String s1) {
                super.onPostExecute(s1);
                loading.dismiss();
                try {
                    JSONArray obj = new JSONArray(s1);
                    String[] arreglo_estado= new String[obj.length()];
                    for(int i=0;i < obj.length();i++){
                        arreglo_estado[i] = obj.getJSONObject(i).getString("nombre_estado_objeto");
                    }
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(subir.this, android.R.layout.simple_spinner_item,arreglo_estado );
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
}