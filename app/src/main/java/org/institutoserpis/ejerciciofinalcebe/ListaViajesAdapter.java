package org.institutoserpis.ejerciciofinalcebe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by JoseCebe
 */
public class ListaViajesAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<ListaViajes> datos;

    public ListaViajesAdapter(Context context, ArrayList<ListaViajes> datos) {
        super(context, R.layout.lista_viajes_item, datos);
        // Guardamos los parámetros en variables de clase.
        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.lista_viajes_item, null);

        int res;

        CircleImageView circleImageView = (CircleImageView)  item.findViewById(R.id.imageCiudad);
        res = context.getResources().getIdentifier(datos.get(position).getFotoDestino(), "drawable", context.getPackageName());
        circleImageView.setImageResource(res);

        TextView textViewOrigenDestino = (TextView) item.findViewById(R.id.origenDestino);
        textViewOrigenDestino.setText(datos.get(position).getNombreOrigen() + " - " + datos.get(position).getNombreDestino());

        TextView textViewDescripcion = (TextView) item.findViewById(R.id.descripcion);
        if (datos.get(position).getVuelta() == 0) {
            textViewDescripcion.setText("Clase " + datos.get(position).getNombreClase() + ", " + datos.get(position).getPasajeros() + " pasajeros. Vuelta: no");
        } else {
            textViewDescripcion.setText("Clase " + datos.get(position).getNombreClase() + ", " + datos.get(position).getPasajeros() + " pasajeros. Vuelta: sí");
        }

        TextView textViewIdViaje = (TextView) item.findViewById(R.id.idViaje);
        textViewIdViaje.setText(Integer.toString(datos.get(position).getId()));

        return item;
    }

}
