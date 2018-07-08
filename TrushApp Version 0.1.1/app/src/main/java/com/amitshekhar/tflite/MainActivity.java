package com.amitshekhar.tflite;
//librerias que dan inicio a la clase principal
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class MainActivity extends AppCompatActivity {
//variable que contiene archivos que se encuentran en la carpeta para poder clasificar
    private static final String MODEL_PATH = "mobilenet_quant_v1_224.tflite";
    private static final String LABEL_PATH = "labels.txt";
    //variables para crear la carpeta donde se guardan las imagenes
    private static final String IMAGE_DIRECTORY_NAME = "Proyecto Basura";
    //tamaño para la imagen y ser reconocida
    private static final int INPUT_SIZE = 224;
    //funcion para guardar la imagen reconocida
    File mediaStorageDir = new File(
            Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            IMAGE_DIRECTORY_NAME);
    // Create a media file name
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
            Locale.getDefault()).format(new Date());
    File mediaFile;
    public String path =mediaStorageDir.getPath() + File.separator
            +"IMG_"+timeStamp+ ".jpg";
    //variables que se visualizan en la pantalla del celular
    private Classifier classifier;

    private Executor executor = Executors.newSingleThreadExecutor();
    private TextView textViewResult;
    private Button btnDetectObject, btnToggleCamera;
    private ImageView imageViewResult;
    private CameraView cameraView;

    @Override
    // donde las variables toman la relacion cn las variables xml
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraView = findViewById(R.id.cameraView);
        imageViewResult = findViewById(R.id.imageViewResult);
        textViewResult = findViewById(R.id.textViewResult);
        textViewResult.setMovementMethod(new ScrollingMovementMethod());
        btnToggleCamera = findViewById(R.id.btnToggleCamera);
        btnDetectObject = findViewById(R.id.btnDetectObject);
// pasa el evento de la camara para abrir o si hay algun error
        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }
//Donde yo paso la imagen de la camara y los clasifica
            @Override
            public void onImage(CameraKitImage cameraKitImage) {

                Bitmap bitmap = cameraKitImage.getBitmap();

                bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
                SaveImage(bitmap);
                imageViewResult.setImageBitmap(bitmap);

                final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);

                textViewResult.setText(results.toString());

            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
        // darle la funcion para cambiar de camara
        btnToggleCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.toggleFacing();
            }
        });

        btnDetectObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.captureImage();
            }
        });

        initTensorFlowAndLoadModel();
    }
//camara inicio
    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }
// camara en estado de espera
    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }
//camara cerrada
    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                classifier.close();
            }
        });
    }
// donde lee los archivos necesarios para clasificar que estan al inicio
    private void initTensorFlowAndLoadModel() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = TensorFlowImageClassifier.create(
                            getAssets(),
                            MODEL_PATH,
                            LABEL_PATH,
                            INPUT_SIZE);
                    makeButtonVisible();
                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }
// donde se le da la accion al boton al momento de sacar la imagen donde lo detecta, se le da el formato
    private void makeButtonVisible() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnDetectObject.setVisibility(View.VISIBLE);
            }
        });
    }
    private void SaveImage(Bitmap finalBitmap) {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

//puedes crear un nuevo archivo en el almacenamiento.
        File f = new File(path);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
//escribe los bytes en los archivos
        FileOutputStream fo = null;
        try {
            fo = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

// cierra el proceso de file
        try {
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
