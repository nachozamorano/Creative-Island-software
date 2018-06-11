package com.android4dev.navigationview.nuevos_productos;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android4dev.navigationview.R;
import com.android4dev.navigationview.horario.dia_domingo;
import com.android4dev.navigationview.horario.dia_jueves;
import com.android4dev.navigationview.horario.dia_lunes;
import com.android4dev.navigationview.horario.dia_martes;
import com.android4dev.navigationview.horario.dia_miercoles;
import com.android4dev.navigationview.horario.dia_sabado;
import com.android4dev.navigationview.horario.dia_viernes;
import com.android4dev.navigationview.horario.horario;

/**
 * Created by Cesar on 12/10/2015.
 */
public class lenguaje_productos extends ArrayAdapter<String> {

    private final FragmentActivity context;
    private final String[] itemname;
    private final Integer[] integers;
    private final String[] descripcion;
    public lenguaje_productos(FragmentActivity context, String[] itemname, Integer[] integers,String[] descripcion) {
        super(context, R.layout.activity_lenguaje_productos, itemname);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.itemname = itemname;
        this.integers = integers;
        this.descripcion=descripcion;

    }

    public View getView(final int posicion, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.activity_lenguaje_productos, null, true);

        final TextView txtTitle = (TextView) rowView.findViewById(R.id.texto_principal2);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon2);
        TextView etxDescripcion = (TextView) rowView.findViewById(R.id.texto_secundario2);

        txtTitle.setText(itemname[posicion]);
        imageView.setImageResource(integers[posicion]);
        etxDescripcion.setText("Precio$:"+"  "+descripcion[posicion]);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(posicion==0) {
                    Intent intent = new Intent(v.getContext(), producto1.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==1) {
                    Intent intent = new Intent(v.getContext(), producto2.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==2) {
                    Intent intent = new Intent(v.getContext(), producto3.class);
                    v.getContext().startActivity(intent);
                }

                else if(posicion==3) {
                    Intent intent = new Intent(v.getContext(), producto4.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==4) {
                    Intent intent = new Intent(v.getContext(), producto5.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==5) {
                    Intent intent = new Intent(v.getContext(), producto6.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==6) {
                    Intent intent = new Intent(v.getContext(), producto7.class);
                    v.getContext().startActivity(intent);
                }
            }
        });
        txtTitle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(posicion==0) {
                    Intent intent = new Intent(v.getContext(), producto1.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==1) {
                    Intent intent = new Intent(v.getContext(), producto2.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==2) {
                    Intent intent = new Intent(v.getContext(), producto3.class);
                    v.getContext().startActivity(intent);
                }

                else if(posicion==3) {
                    Intent intent = new Intent(v.getContext(), producto4.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==4) {
                    Intent intent = new Intent(v.getContext(), producto5.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==5) {
                    Intent intent = new Intent(v.getContext(), producto6.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==6) {
                    Intent intent = new Intent(v.getContext(), producto7.class);
                    v.getContext().startActivity(intent);
                }
            }
        });

        return rowView;
    }
}