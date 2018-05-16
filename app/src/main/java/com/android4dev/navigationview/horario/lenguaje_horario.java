package com.android4dev.navigationview.horario;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android4dev.navigationview.R;
import com.android4dev.navigationview.horario.dia_jueves;
import com.android4dev.navigationview.horario.dia_martes;
import com.android4dev.navigationview.horario.dia_miercoles;
import com.android4dev.navigationview.horario.dia_sabado;
import com.android4dev.navigationview.horario.dia_viernes;
import com.android4dev.navigationview.horario.horario;

/**
 * Created by Cesar on 12/10/2015.
 */
public class lenguaje_horario extends ArrayAdapter<String> {

    private final FragmentActivity context;
    private final String[] itemname;
    private final Integer[] integers;
    public lenguaje_horario(FragmentActivity context, String[] itemname, Integer[] integers) {
        super(context, R.layout.activity_lenguaje_horario, itemname);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.itemname = itemname;
        this.integers = integers;

    }

    public View getView(final int posicion, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.activity_lenguaje_horario, null, true);

        final TextView txtTitle = (TextView) rowView.findViewById(R.id.texto_principal);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        txtTitle.setText(itemname[posicion]);
        imageView.setImageResource(integers[posicion]);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(posicion==0) {
                    Intent intent = new Intent(v.getContext(), dia_lunes.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==1) {
                    Intent intent = new Intent(v.getContext(), dia_martes.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==2) {
                    Intent intent = new Intent(v.getContext(), dia_miercoles.class);
                    v.getContext().startActivity(intent);
                }

                else if(posicion==3) {
                    Intent intent = new Intent(v.getContext(), dia_jueves.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==4) {
                    Intent intent = new Intent(v.getContext(), dia_viernes.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==5) {
                    Intent intent = new Intent(v.getContext(), dia_sabado.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==6) {
                    Intent intent = new Intent(v.getContext(), dia_domingo.class);
                    v.getContext().startActivity(intent);
                }
            }
        });
        txtTitle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(posicion==0) {
                    Intent intent = new Intent(v.getContext(), dia_lunes.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==1) {
                    Intent intent = new Intent(v.getContext(), dia_martes.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==2) {
                    Intent intent = new Intent(v.getContext(), dia_miercoles.class);
                    v.getContext().startActivity(intent);
                }

                else if(posicion==3) {
                    Intent intent = new Intent(v.getContext(), dia_jueves.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==4) {
                    Intent intent = new Intent(v.getContext(), dia_viernes.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==5) {
                    Intent intent = new Intent(v.getContext(), dia_sabado.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==6) {
                    Intent intent = new Intent(v.getContext(), dia_domingo.class);
                    v.getContext().startActivity(intent);
                }
            }
        });

        return rowView;
    }
}