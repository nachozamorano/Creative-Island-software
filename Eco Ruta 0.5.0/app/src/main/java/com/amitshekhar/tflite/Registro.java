package com.amitshekhar.tflite;

import android.annotation.TargetApi;
import android.app.Activity;
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
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

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

public class Registro extends AppCompatActivity{
    private EditText password;
    private EditText nombre;
    private EditText telefono;
    public ImageView mSetImage;
    private EditText email;
    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";
    private EditText telefono_fijo;
    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private Button mOptionButton;
    private String mPath,encodedImage;
    private RelativeLayout mRlView;
    private final int SELECT_PICTURE = 300;
    private Spinner sexo;
    private Button insertar;
    String url_sexo= "https://ecoruta.webcindario.com/BuscarGenero.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        nombre= (EditText)findViewById(R.id.txtNombre);
        mSetImage=(ImageView) findViewById(R.id.imagen_R);
        password= (EditText)findViewById(R.id.txtPassword);
        email = (EditText)findViewById(R.id.txtEmail);
        telefono= (EditText)findViewById(R.id.txtTelefono);
        telefono_fijo= (EditText)findViewById(R.id.txtTelefono_f);
        sexo = (Spinner)findViewById(R.id.txtSexo);
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
        insertar = (Button)findViewById(R.id.btnInsertar);
        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!password.getText().toString().trim().equalsIgnoreCase("")||
                        !nombre.getText().toString().trim().equalsIgnoreCase("")||
                        !telefono.getText().toString().trim().equalsIgnoreCase("")||
                        !email.getText().toString().trim().equalsIgnoreCase("")||
                        !sexo.getSelectedItem().toString().trim().equalsIgnoreCase("")) {
                    new Insertar(Registro.this).execute();
                    Toast.makeText(Registro.this, "Registrado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Registro.this,LoginActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(Registro.this, "Hay información por rellenar", Toast.LENGTH_LONG).show();
            }
        });
        Sexo(sexo);
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


    //Insertamos los datos a nuestra webService
    private boolean insertar(){
        HttpClient httpClient;
        List<NameValuePair> nameValuePairs;
        HttpPost httpPost;
        httpClient = new DefaultHttpClient();
        httpPost = new HttpPost("https://ecoruta.webcindario.com/crearUsuario.php");//url del servidor
        //empezamos añadir nuestros datos
        nameValuePairs = new ArrayList<NameValuePair>(6);
        nameValuePairs.add(new BasicNameValuePair("nombre",nombre.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("password",password.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("img_perfil",encodedImage));
        nameValuePairs.add(new BasicNameValuePair("correo",email.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("telefono_movil",telefono.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("telefono_fijo",telefono_fijo.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("sexo",sexo.getSelectedItem().toString().trim()));
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
                        Toast.makeText(context, "Persona insertada con éxito", Toast.LENGTH_LONG).show();
                        nombre.setText("");
                        password.setText("");
                        telefono.setText("");
                        email.setText("");
                        telefono_fijo.setText("");
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
    public void Sexo(final Spinner spinner) {
        class UserLoginClass extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Registro.this, "Sincronizando", null, true, true);
            }

            @Override
            protected void onPostExecute(String s1) {
                super.onPostExecute(s1);
                loading.dismiss();
                try {
                    JSONArray obj = new JSONArray(s1);
                    String[] arreglo_estado= new String[obj.length()];
                    for(int i=0;i < obj.length();i++){
                        arreglo_estado[i] = obj.getJSONObject(i).getString("nombre_genero");
                    }
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Registro.this, android.R.layout.simple_spinner_item,arreglo_estado );
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
    private void showOptions() {
        final CharSequence[] option = {"Tomar foto", "Elegir de galeria", "Cancelar"};
        final android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(Registro.this);
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
                    getStringImagen(bitmap);
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
                    getStringImagen(bmp);
                    break;

            }
        }
    }
    public void getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

}
