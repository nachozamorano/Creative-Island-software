package com.android4dev.navigationview.promo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android4dev.navigationview.HttpsQuerys;
import com.android4dev.navigationview.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

public class promos extends ListFragment {
    String url="http://bishop94.000webhostapp.com/promos.php";
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
    public String[] descripcion=new String[7];
    SwipeRefreshLayout refresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.activity_promos, container, false);
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
                    for(int i=0;i < obj.length();i++){
                        devuelve[i]=obj.getJSONObject(i).getString("promo");
                        descripcion[i]=obj.getJSONObject(i).getString("precio");
                    }
                    final lenguaje_promo adapter=new lenguaje_promo(getActivity(),devuelve,imgid,descripcion);
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
    private void refreshContent(){
    refresh.setRefreshing(false);
    }

}