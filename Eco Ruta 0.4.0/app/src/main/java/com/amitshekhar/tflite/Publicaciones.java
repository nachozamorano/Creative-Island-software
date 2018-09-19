package com.amitshekhar.tflite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Publicaciones extends AppCompatActivity {
    String ide="1";
    ListView listView1;
    EditText editText;
    ArrayList<Subject> SubjectList = new ArrayList<Subject>();
    String Publicacion,Expiracion,Estado,Descripcion,Horario,Sub,id_objeto,url="http://bishop94.000webhostapp.com/Trashapp/eliminarObjeto.php"
    ,HttpURL = "http://bishop94.000webhostapp.com/Trashapp/actualizarObjeto2.php";
    List_publicacion listAdapter;
    ProgressBar progressBar ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones);
        listView1 = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.edittext10);


        progressBar = (ProgressBar)findViewById(R.id.progressbar2);

        listView1.setTextFilterEnabled(true);
        FloatingActionButton my_fab = (FloatingActionButton) findViewById(R.id.my_fab2);
        my_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent (Publicaciones.this,subir.class);
                startActivity(intent);
            }
        });
        listView1.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Subject ListViewClickData = (Subject)parent.getItemAtPosition(position);

                Intent intent =new Intent(Publicaciones.this,articulo.class);
                intent.putExtra("name",ListViewClickData.getSubName());
                intent.putExtra("image",ListViewClickData.getImage());
                intent.putExtra("lugar",ListViewClickData.getSubFullForm());
                intent.putExtra("publicacion",Publicacion);
                intent.putExtra("sub",Sub);
                intent.putExtra("expiracion",Expiracion);
                intent.putExtra("horario",Horario);
                // intent.putExtra("estado",Estado);
                intent.putExtra("descripcion",Descripcion);
                startActivity(intent);
            }
        });


        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence stringVar, int start, int before, int count) {

                listAdapter.getFilter().filter(stringVar.toString());
            }
        });
        httpquery(ide);
    }


    public void httpquery(final String ide) {
        class User extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                loading = ProgressDialog.show(Publicaciones.this, "Sincronizando", null, true, true);
            }


            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    JSONObject jsonObject;
                    Subject subject;
                    SubjectList = new ArrayList<Subject>();
                    for (int i = 0; i < jsonArray.length(); i++) {

                        jsonObject = jsonArray.getJSONObject(i);
                        id_objeto=jsonObject.getString("id_objeto").toString();

                        String tempName = jsonObject.getString("nombre_objeto").toString();
                        System.out.println("hola"+ tempName);

                        String tempFullForm = "Ubicación:" + " " + jsonObject.getString("ubicacion").toString();
                        Publicacion = jsonObject.getString("fecha_publicacion").toString();
                        Sub = jsonObject.getString("subcategoria").toString();
                        Expiracion = jsonObject.getString("fecha_expiracion").toString();
                        Horario = jsonObject.getString("horario_retiro").toString();
                        //Estado = jsonObject.getString("estado").toString();
                        Descripcion = jsonObject.getString("descripcion").toString();



                        if (i==0){
                            subject = new Subject(tempName, tempFullForm, R.drawable.negro);
                            SubjectList.add(subject);
                        }
                        else if (i==1) {
                            subject = new Subject(tempName, tempFullForm, R.drawable.verde);
                            SubjectList.add(subject);
                        }
                        else {
                            subject = new Subject(tempName, tempFullForm, R.drawable.rojo);
                            SubjectList.add(subject);
                        }
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    listAdapter = new List_publicacion(Publicaciones.this, R.layout.custom_publicaciones, SubjectList);
                    listView1.setAdapter(listAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<>();
                data.put("id_usuario", params[0]);

                HttpQuerys ruc1 = new HttpQuerys();

                String result = ruc1.sendPostRequest(HttpURL, data);

                return result;
            }
        }
        User ulc = new User();
        ulc.execute(ide);



    }


}
