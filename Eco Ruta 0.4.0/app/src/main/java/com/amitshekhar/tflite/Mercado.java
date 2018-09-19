package com.amitshekhar.tflite;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Mercado extends AppCompatActivity {

    ListView listView;
    EditText editText;
    ArrayList<Subject> SubjectList = new ArrayList<Subject>();
    String Publicacion,Expiracion,Estado,Descripcion,Horario,Sub,HttpURL = "http://bishop94.000webhostapp.com/Trashapp/BuscarObjeto.php";
    ListAdapter listAdapter;
    ProgressBar progressBar ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercado);

        listView = (ListView) findViewById(R.id.listView1);

        editText = (EditText) findViewById(R.id.edittext1);

        progressBar = (ProgressBar)findViewById(R.id.progressbar);

        listView.setTextFilterEnabled(true);
        FloatingActionButton my_fab = (FloatingActionButton) findViewById(R.id.my_fab);
        my_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent (Mercado.this,subir.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Subject ListViewClickData = (Subject)parent.getItemAtPosition(position);

                Intent intent =new Intent(Mercado.this,articulo.class);
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


        new ParseJSonDataClass(this).execute();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==R.id.modificacion) {
            Intent intent = new Intent(Mercado.this,
                    Publicaciones.class);
            startActivity(intent);
        }
        return true;
    }

    private class ParseJSonDataClass extends AsyncTask<Void, Void, Void> {
        public Context context;
        String FinalJSonResult;

        public ParseJSonDataClass(Context context) {

            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpServiceClass httpServiceClass = new HttpServiceClass(HttpURL);

            try {
                httpServiceClass.ExecutePostRequest();

                if (httpServiceClass.getResponseCode() == 200) {

                    FinalJSonResult = httpServiceClass.getResponse();

                    if (FinalJSonResult != null) {

                        JSONArray jsonArray = null;
                        try {

                            jsonArray = new JSONArray(FinalJSonResult);

                            JSONObject jsonObject;

                            Subject subject;

                            SubjectList = new ArrayList<Subject>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                jsonObject = jsonArray.getJSONObject(i);

                                String tempName = jsonObject.getString("nombre_objeto").toString();

                                String tempFullForm = "Ubicación:"+" "+jsonObject.getString("ubicacion").toString();
                                Publicacion = jsonObject.getString("fecha_publicacion").toString();
                                Sub = jsonObject.getString("subcategoria").toString();
                                Expiracion = jsonObject.getString("fecha_expiracion").toString();
                                Horario = jsonObject.getString("horario_retiro").toString();
                                //Estado = jsonObject.getString("estado").toString();
                                Descripcion = jsonObject.getString("descripcion").toString();

                                if (i==0) {
                                    subject = new Subject(tempName, tempFullForm, R.drawable.verde);
                                    SubjectList.add(subject);
                                }
                                else if (i==1){
                                    subject = new Subject(tempName, tempFullForm, R.drawable.negro);
                                    SubjectList.add(subject);
                                }
                                else {
                                    subject = new Subject(tempName, tempFullForm, R.drawable.rojo);
                                    SubjectList.add(subject);
                                }
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else {

                    Toast.makeText(context, httpServiceClass.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)

        {
            progressBar.setVisibility(View.INVISIBLE);
            listAdapter = new ListAdapter(Mercado.this, R.layout.custom_layout, SubjectList);
            listView.setAdapter(listAdapter);
        }
    }

}