package com.android4dev.navigationview.horario;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android4dev.navigationview.HttpsQuerys;
import com.android4dev.navigationview.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

public class horario extends ListFragment {
    String url="http://bishop94.000webhostapp.com/horarios.php";
    SwipeRefreshLayout refresh;
    private Integer[] imgid={
            R.drawable.editar,
            R.drawable.editar,
            R.drawable.editar,
            R.drawable.editar,
            R.drawable.editar,
            R.drawable.editar,
            R.drawable.editar
    };

    // Array de String que contiene nuestros queridos Sistemas Operativos
    private String[] devuelve =new String[7];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.horario, container, false);
        refresh= (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        return rootView;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    userLogin();

        // Establecemos el Adapter a la Lista del Fragment
    }
    public void userLogin() {
        class UserLoginClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONArray obj = new JSONArray(s);
                    devuelve[0]="Lunes:"+"  "+obj.getJSONObject(0).getString("lunes_i")+"/-/"+obj.getJSONObject(0).getString("lunes_f");
                    devuelve[1]="Martes:"+"  "+obj.getJSONObject(0).getString("martes_i")+"/-/"+obj.getJSONObject(0).getString("martes_f");
                    devuelve[2]="Miercoles:"+"  "+obj.getJSONObject(0).getString("miercoles_i")+"/-/"+obj.getJSONObject(0).getString("miercoles_f");
                    devuelve[3]="Jueves:"+"  "+obj.getJSONObject(0).getString("jueves_i")+"/-/"+obj.getJSONObject(0).getString("jueves_f");
                    devuelve[4]="Viernes:"+"  "+obj.getJSONObject(0).getString("viernes_i")+"/-/"+obj.getJSONObject(0).getString("viernes_f");
                    devuelve[5]="Sabado:"+"  "+obj.getJSONObject(0).getString("sabado_i")+"/-/"+obj.getJSONObject(0).getString("sabado_f");
                    devuelve[6]="Domingo:"+"  "+obj.getJSONObject(0).getString("domingo_i")+"/-/"+obj.getJSONObject(0).getString("domingo_f");
                    lenguaje_horario adapter=new lenguaje_horario(getActivity(),devuelve,imgid);
                    setListAdapter(adapter);
                    refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    userLogin();
                                    refresh.setRefreshing(false);
                                }
                            },1000);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();

                HttpsQuerys ruc = new HttpsQuerys();

                String result = ruc.sendPostRequest(url, data);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute();

    }

}