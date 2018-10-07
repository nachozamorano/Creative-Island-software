package com.amitshekhar.tflite;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Modificar_perfil extends AppCompatActivity {
    TextView nombre,donados,adquiridos,correo,telefono_movil,telefono_fijo;
    Spinner genero,preferencia;
    String url_sexo= "https://ecoruta.webcindario.com/BuscarGenero.php";
    String url= "https://ecoruta.webcindario.com/BuscarCategoria.php";
    ImageView imagen_pefil;
    String[] arreglo_estado;
    String ide ;
    AlertDialog.Builder dialogo1;
    String[] arreglo_c;
    private final int MY_PERMISSIONS = 100;
    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";
    private String mPath;
    String encodedImage;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;
    private RelativeLayout mRlView;
    String imagen;
    Button mOptionButton,listo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_perfil);
        Intent ub = getIntent();
        Bundle b = ub.getExtras();
        ide=String.valueOf(b.get("ide"));
        imagen_pefil=(ImageView) findViewById(R.id.imagen_perfil);
        nombre= (TextView) findViewById(R.id.tv_name);
        donados=(TextView) findViewById(R.id.donados);
        adquiridos=(TextView) findViewById(R.id.adquiridos);
        correo =(TextView) findViewById(R.id.correo);
        telefono_movil=(TextView) findViewById(R.id.telefono);
        listo= (Button) findViewById(R.id.listo) ;
        telefono_fijo=(TextView) findViewById(R.id.fijo);
        preferencia =(Spinner) findViewById(R.id.preferencia);
        genero =(Spinner) findViewById(R.id.genero);
        imagen=String.valueOf(b.get("imagen"));
        Categorias(preferencia);

        if(imagen.isEmpty()){
            imagen_pefil.setImageResource(R.drawable.sin_imagen);
        }

        else {
            Picasso.get().load(imagen).into(imagen_pefil);
            Bitmap bmap = ((BitmapDrawable) imagen_pefil.getDrawable()).getBitmap();
            encodedImage=getStringImagen(bmap);
        }
        nombre.setText(String.valueOf(b.get("nombre")));
        donados.setText(String.valueOf(b.get("donados")));
        adquiridos.setText(String.valueOf(b.get("adquiridos")));
        correo.setText(String.valueOf(b.get("correo")));
        telefono_fijo.setText(String.valueOf(b.get("telefono_fijo")));
        telefono_movil.setText(String.valueOf(b.get("telefono_movil")));
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
        Sexo(genero);
        listo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo1 = new AlertDialog.Builder(Modificar_perfil.this);
                dialogo1.setTitle("Alerta");
                dialogo1.setMessage("¿Desea guardar los cambios?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        new Modificar_perfil.Insertar(Modificar_perfil.this).execute();
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
        final android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(Modificar_perfil.this);
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
                    imagen_pefil.setImageBitmap(bitmap);
                    encodedImage=getStringImagen(bitmap);
                    break;
                case SELECT_PICTURE:
                    Uri path = data.getData();
                    imagen_pefil.setImageURI(path);
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
                finish();
            }
        });

        builder.show();
    }
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
                        Toast.makeText(context, "Actualizado con éxito", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Modificar_perfil.this,Perfil.class);
                        intent.putExtra("ide",ide);
                        startActivity(intent);
                        finish();
                    }
                });
            else
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "Persona no insertada con éxito", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }
    private boolean insertar(){
        HttpClient httpClient;
        List<NameValuePair> nameValuePairs;
        HttpPost httpPost;
        httpClient = new DefaultHttpClient();
        httpPost = new HttpPost("https://ecoruta.webcindario.com/actualizarPerfil.php");//url del servidor
        //empezamos añadir nuestros datos
        nameValuePairs = new ArrayList<NameValuePair>(7);
        nameValuePairs.add(new BasicNameValuePair("id_usuario",ide.trim()));
        nameValuePairs.add(new BasicNameValuePair("nombre",nombre.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("img_perfil",encodedImage.trim()));
        nameValuePairs.add(new BasicNameValuePair("correo",correo.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("telefono_movil",telefono_movil.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("telefono_fijo",telefono_fijo.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("id_genero",String.valueOf(genero.getSelectedItemPosition()+1)));
        nameValuePairs.add(new BasicNameValuePair("id_categoria",String.valueOf(preferencia.getSelectedItemPosition()+1)));
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
    public String getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public void Sexo(final Spinner spinner) {
        class UserLoginClass extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Modificar_perfil.this, "Sincronizando", null, true, true);
            }

            @Override
            protected void onPostExecute(String s1) {
                super.onPostExecute(s1);
                loading.dismiss();
                try {
                    JSONArray obj = new JSONArray(s1);
                    arreglo_estado = new String[obj.length()];
                    for (int i = 0; i < obj.length(); i++) {
                        arreglo_estado[i] = obj.getJSONObject(i).getString("nombre_genero");
                    }
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Modificar_perfil.this, android.R.layout.simple_spinner_item, arreglo_estado);
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

                String result = ruc1.sendPostRequest(url_sexo, data1);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(String.valueOf(spinner));
    }
    public void cancelar() {
        Intent intent1 = new Intent(Modificar_perfil.this, Perfil.class);
        Toast.makeText(this,"Cancelado", Toast.LENGTH_SHORT);
        intent1.putExtra("ide",ide);
        startActivity(intent1);
        finish();
    }
    public void Categorias(final Spinner spinner) {
        class UserLoginClass extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Modificar_perfil.this, "Sincronizando", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                    JSONArray obj = new JSONArray(s);
                    arreglo_c= new String[obj.length()];
                    for(int i=0;i < obj.length();i++){
                        arreglo_c[i] = obj.getJSONObject(i).getString("nombre_categoria");
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Modificar_perfil.this, android.R.layout.simple_spinner_item,arreglo_c );
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
}
