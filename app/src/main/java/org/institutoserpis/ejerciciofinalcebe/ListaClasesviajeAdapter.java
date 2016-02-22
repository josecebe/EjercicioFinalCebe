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
public class ListaClasesviajeAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<ListaClasesviaje> datos;

    public ListaClasesviajeAdapter(Context context, ArrayList<ListaClasesviaje> datos) {
        super(context, R.layout.lista_clasesviaje_item, datos);
        // Guardamos los parámetros en variables de clase.
        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.lista_clasesviaje_item, null);

        int res;

        TextView textViewDescripcion = (TextView) item.findViewById(R.id.textViewDescripcion);
        textViewDescripcion.setText(datos.get(position).getDescripcion());

        TextView textViewIdClaseviaje = (TextView) item.findViewById(R.id.idClaseviaje);
        textViewIdClaseviaje.setText(Integer.toString(datos.get(position).getId()));

        return item;
    }

}
