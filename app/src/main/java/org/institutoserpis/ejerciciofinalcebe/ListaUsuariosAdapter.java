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
public class ListaUsuariosAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<ListaUsuarios> datos;

    public ListaUsuariosAdapter(Context context, ArrayList<ListaUsuarios> datos) {
        super(context, R.layout.lista_usuarios_item, datos);
        // Guardamos los parámetros en variables de clase.
        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.lista_usuarios_item, null);

        int res;

        TextView textViewNombreUsuario = (TextView) item.findViewById(R.id.textViewNombreUsuario);
        textViewNombreUsuario.setText(datos.get(position).getNombreUsuario());

        TextView textViewIdUsuario = (TextView) item.findViewById(R.id.idUsuario);
        textViewIdUsuario.setText(Integer.toString(datos.get(position).getId()));

        return item;
    }

}
