package com.amitshekhar.tflite;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Juned on 2/1/2017.
 */

public class List_publicacion extends ArrayAdapter<Subject> {

    public ArrayList<Subject> MainList;
    public ArrayList<Subject> SubjectListTemp;

    public SubjectDataFilter subjectDataFilter ;

    public List_publicacion(Context context, int id, ArrayList<Subject> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<Subject>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<Subject>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null){

            subjectDataFilter  = new List_publicacion.SubjectDataFilter();
        }
        else {
            showToastMessage("No se encontraron resultados", 1000);
        }
        return subjectDataFilter;
    }


    public class ViewHolder {

        TextView SubjectName;
        TextView SubjectFullForm;
        ImageView imagen;
        ImageView imagen_estado;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        List_publicacion.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.custom_publicaciones, null);

            holder = new List_publicacion.ViewHolder();

            holder.SubjectName = (TextView) convertView.findViewById(R.id.nameTxt);

            holder.SubjectFullForm = (TextView) convertView.findViewById(R.id.descTxt);
            holder.imagen =(ImageView) convertView.findViewById(R.id.movieImage) ;
            holder.imagen_estado = (ImageView) convertView.findViewById(R.id.color_estado);
            convertView.setTag(holder);

        } else {
            holder = (List_publicacion.ViewHolder) convertView.getTag();
        }

        Subject subject = SubjectListTemp.get(position);

        holder.SubjectName.setText(subject.getSubName());

        holder.SubjectFullForm.setText(subject.getSubFullForm());
        if(subject.getEstado().equals("Libre")){
            holder.imagen_estado.setImageResource(R.drawable.libre);
        }
        else if(subject.getEstado().equals("Reservado")){
            holder.imagen_estado.setImageResource(R.drawable.reservado);
        }
        else{
            holder.imagen_estado.setImageResource(R.drawable.donado);
        }

        if(subject.getImage().isEmpty()){
            holder.imagen.setImageResource(R.drawable.sin_imagen);         }
        else {
            Picasso.get().load(subject.getImage()).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(holder.imagen);
        }
        return convertView;

    }

    private class SubjectDataFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if(charSequence != null && charSequence.toString().length() > 0)
            {
                ArrayList<Subject> arrayList1 = new ArrayList<Subject>();

                for(int i = 0, l = MainList.size(); i < l; i++)
                {
                    Subject subject = MainList.get(i);
                    if(subject.toString().toLowerCase().contains(charSequence))

                        arrayList1.add(subject);


                }
                if (arrayList1.isEmpty()) {
                    showToastMessage("No se encontraron resultados", 1000);
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

            SubjectListTemp = (ArrayList<Subject>)filterResults.values;

            notifyDataSetChanged();

            clear();

            for(int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));

            notifyDataSetInvalidated();
        }
    }
    public void showToastMessage(String text, int duration) {
        final Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
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