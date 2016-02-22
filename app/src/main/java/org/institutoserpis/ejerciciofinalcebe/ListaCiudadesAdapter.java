package org.institutoserpis.ejerciciofinalcebe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JoseCebe
 */
public class ListaCiudadesAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<ListaCiudades> datos;

    public ListaCiudadesAdapter(Context context, ArrayList<ListaCiudades> datos) {
        super(context, R.layout.lista_ciudades_item, datos);
        // Guardamos los parámetros en variables de clase.
        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.lista_ciudades_item, null);

        int res;

        TextView textViewNombre = (TextView) item.findViewById(R.id.textViewNombre);
        textViewNombre.setText(datos.get(position).getNombre());

        TextView textViewIdCiudad = (TextView) item.findViewById(R.id.idCiudad);
        textViewIdCiudad.setText(Integer.toString(datos.get(position).getId()));

        return item;
    }

}
