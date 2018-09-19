package com.amitshekhar.tflite;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Juned on 2/1/2017.
 */

public class List_publicacion extends ArrayAdapter<Subject> {

    public ArrayList<Subject> MainList;
    String id="1",url="http://bishop94.000webhostapp.com/Trashapp/eliminarObjeto.php";

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
        return subjectDataFilter;
    }


    public class ViewHolder {

        TextView SubjectName;
        TextView SubjectFullForm;
        ImageView imagen;
        ImageButton button;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        List_publicacion.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.custom_publicaciones, null);

            holder = new List_publicacion.ViewHolder();

            holder.SubjectName = (TextView) convertView.findViewById(R.id.nameTxt);

            holder.SubjectFullForm = (TextView) convertView.findViewById(R.id.descTxt);
            holder.imagen =(ImageView) convertView.findViewById(R.id.movieImage) ;
            holder.button = (ImageButton) convertView.findViewById(R.id.imageButton12);

            convertView.setTag(holder);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View convertView) {
                eliminar(id);
                }
            });

        } else {
            holder = (List_publicacion.ViewHolder) convertView.getTag();
        }

        Subject subject = SubjectListTemp.get(position);

        holder.SubjectName.setText(subject.getSubName());

        holder.SubjectFullForm.setText(subject.getSubFullForm());

        holder.imagen.setImageResource(subject.getImage());
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
    public void eliminar(final String ide) {
        class UserLoginClass extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                loading = ProgressDialog.show(getContext(), "Sincronizando", null, true, true);
            }


            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                loading.dismiss();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<>();
                data.put("id_objeto", params[0]);

                HttpQuerys ruc1 = new HttpQuerys();

                String result = ruc1.sendPostRequest(url, data);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(ide);
        Toast.makeText(getContext(), "Eliminado", Toast.LENGTH_SHORT).show();



    }


}