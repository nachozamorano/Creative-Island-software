package com.android4dev.navigationview.promo;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android4dev.navigationview.R;

/**
 * Created by Cesar on 12/10/2015.
 */
public class lenguaje_promo extends ArrayAdapter<String> {

    private final FragmentActivity context;
    private final String[] itemname;
    private final Integer[] integers;
    private final String[] descripcion;
    public lenguaje_promo(FragmentActivity context, String[] itemname, Integer[] integers,String[] descripcion) {
        super(context, R.layout.activity_lenguaje_promo, itemname);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.itemname = itemname;
        this.integers = integers;
        this.descripcion=descripcion;

    }

    public View getView(final int posicion, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.activity_lenguaje_promo, null, true);

        final TextView txtTitle = (TextView) rowView.findViewById(R.id.texto_principal1);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon1);
        TextView etxDescripcion = (TextView) rowView.findViewById(R.id.texto_secundario1);

        txtTitle.setText(itemname[posicion]);
        imageView.setImageResource(integers[posicion]);
        etxDescripcion.setText("Precio$:"+"  "+descripcion[posicion]);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(posicion==0) {
                    Intent intent = new Intent(v.getContext(), promo1.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==1) {
                    Intent intent = new Intent(v.getContext(), promo2.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==2) {
                    Intent intent = new Intent(v.getContext(), promo3.class);
                    v.getContext().startActivity(intent);
                }

                else if(posicion==3) {
                    Intent intent = new Intent(v.getContext(), promo4.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==4) {
                    Intent intent = new Intent(v.getContext(), promo5.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==5) {
                    Intent intent = new Intent(v.getContext(), promo6.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==6) {
                    Intent intent = new Intent(v.getContext(), promo7.class);
                    v.getContext().startActivity(intent);
                }
            }
        });
        txtTitle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(posicion==0) {
                    Intent intent = new Intent(v.getContext(), promo1.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==1) {
                    Intent intent = new Intent(v.getContext(), promo2.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==2) {
                    Intent intent = new Intent(v.getContext(), promo3.class);
                    v.getContext().startActivity(intent);
                }

                else if(posicion==3) {
                    Intent intent = new Intent(v.getContext(), promo4.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==4) {
                    Intent intent = new Intent(v.getContext(), promo5.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==5) {
                    Intent intent = new Intent(v.getContext(), promo6.class);
                    v.getContext().startActivity(intent);
                }
                else if(posicion==6) {
                    Intent intent = new Intent(v.getContext(), promo7.class);
                    v.getContext().startActivity(intent);
                }
            }
        });

        return rowView;
    }
}