package com.amitshekhar.tflite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class ListAdapter extends ArrayAdapter<Subject_mercado> {

    public ArrayList<Subject_mercado> MainList;
    URL imagenUrl=null;
    HttpURLConnection conn = null;

    public ArrayList<Subject_mercado> SubjectListTemp;

    public ListAdapter.SubjectDataFilter subjectDataFilter ;

    public ListAdapter(Context context, int id, ArrayList<Subject_mercado> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<Subject_mercado>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<Subject_mercado>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null){

            subjectDataFilter  = new ListAdapter.SubjectDataFilter();
        }
        return subjectDataFilter;
    }



    public static class ViewHolder {

        TextView SubjectName;
        TextView SubjectFullForm;
        ImageView imagen;
        TextView estado;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListAdapter.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.custom_layout, null);

            holder = new ListAdapter.ViewHolder();

            holder.SubjectName = (TextView) convertView.findViewById(R.id.nameTxt);

            holder.SubjectFullForm = (TextView) convertView.findViewById(R.id.descTxt);
            holder.imagen =(ImageView) convertView.findViewById(R.id.movieImage) ;
            holder.estado = (TextView) convertView.findViewById(R.id.estado_objeto) ;

            convertView.setTag(holder);

        } else {
            holder = (ListAdapter.ViewHolder) convertView.getTag();
        }

        Subject_mercado subject = SubjectListTemp.get(position);

        holder.SubjectName.setText(subject.getSubName());

        holder.SubjectFullForm.setText(subject.getSubFullForm());
        holder.estado.setText(subject.getEstado());
        if(subject.getImage().isEmpty()){
            holder.imagen.setImageResource(R.drawable.sin_imagen);        }
        else {
            Picasso.get().load(subject.getImage()).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(holder.imagen);
        }

        return convertView;

    }

    class SubjectDataFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if(charSequence != null && charSequence.toString().length() > 0)
            {
                ArrayList<Subject_mercado> arrayList1 = new ArrayList<Subject_mercado>();

                for(int i = 0, l = MainList.size(); i < l; i++)
                {
                    Subject_mercado subject = MainList.get(i);
                    if(subject.toString().toLowerCase().contains(charSequence))

                    arrayList1.add(subject);
                    else if (arrayList1.isEmpty()){
                        showToastMessage("No se encontraron resultados", 1000);
                    }


                }
                filterResults.count = arrayList1.size();

                filterResults.values = arrayList1;
            }
            else
            {
                synchronized(this)
                {
                    filterResults.values = MainList;

                    filterResults.count = MainList.size();
                }
            }
            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            SubjectListTemp = (ArrayList<Subject_mercado>)filterResults.values;

            notifyDataSetChanged();

            clear();

            for(int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));

            notifyDataSetInvalidated();
        }
    }
    public void showToastMessage(String text, int duration) {
        final Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, duration);
    }


}