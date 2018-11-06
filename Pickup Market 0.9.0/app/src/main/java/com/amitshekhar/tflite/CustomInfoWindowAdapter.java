package com.amitshekhar.tflite;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowAdapter(Context ctx){
        context = ctx;
    }


    @Override
    public View getInfoContents(final Marker m) {
        //Carga layout personalizado.
        View v = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.infowindow_layout, null);
        ImageView imagen =(ImageView)v.findViewById(R.id.info_window_imagen);
        TextView nombre =(TextView)v.findViewById(R.id.info_window_nombre);
        TextView detalle =(TextView)v.findViewById(R.id.info_window_placas);
        TextView estado =(TextView)v.findViewById(R.id.info_window_estado);
        nombre.setText(m.getTitle());
        estado.setText(m.getSnippet());
        InfoWindowData infoWindowData = (InfoWindowData) m.getTag();

        detalle.setText(infoWindowData.getDetalle());
        if(infoWindowData.getImage().isEmpty()){
            imagen.setImageResource(R.drawable.sin_imagen);        }
        else {
            Picasso.get().load(infoWindowData.getImage()).into(imagen);
        }
        return v;
    }

    @Override
    public View getInfoWindow(Marker m) {
        return null;
    }

}
