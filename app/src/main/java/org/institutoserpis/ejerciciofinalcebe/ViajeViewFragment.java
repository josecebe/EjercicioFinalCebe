package org.institutoserpis.ejerciciofinalcebe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.institutoserpis.ejerciciofinalcebe.Tables.Ciudad;
import org.institutoserpis.ejerciciofinalcebe.Tables.Claseviaje;
import org.institutoserpis.ejerciciofinalcebe.Tables.Usuario;
import org.institutoserpis.ejerciciofinalcebe.Tables.Viaje;

public class ViajeViewFragment extends Fragment {
    static public final String packagename = ListaViajesFragment.class.getPackage().getName();
    View view;

    SQLiteHelper helper;
    String id = "";
    String ciudadOrigenStr = "";
    String ciudadDestinoStr = "";
    String claseviajeStr = "";
    String usuarioStr = "";
    String pasajerosStr = "";
    String vueltaStr = "";
    String precioStr = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_viaje_view_fragment, container, false);

        // Si se pulsa el botón Editar...
        ImageButton imageButtonEdit = (ImageButton) view.findViewById(R.id.imageButton);
        imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editViaje();
            }
        });

        helper = new SQLiteHelper(getActivity(), "DBCebe", null, 1);

        // Obtenemos el bundle de datos
        Bundle bundle = this.getArguments();
        // Si no es nulo...
        if (bundle != null) {
            // Guardamos en la variable id el parámetro id del bundle
            id = Integer.toString(bundle.getInt("id"));
        }

        // Cargamos los datos del viaje con id (id)
        loadViaje(Integer.parseInt(id));

        return view;
    }

    // Método que se encarga de imprimir en pantalla los datos del viaje con id (id)
    private void loadViaje(int id) {
        // Hacemos la consulta a la BBDD para obtener el viaje con id (id)
        Viaje viaje = helper.getViaje(id);

        // Obtenemos los datos de las claves ajenas
        Claseviaje claseviaje = helper.getClaseviaje(viaje.getId_claseviaje());
        Ciudad ciudadOrigen = helper.getCiudad(viaje.getId_ciudad_1());
        Ciudad ciudadDestino = helper.getCiudad(viaje.getId_ciudad_2());
        Usuario usuario = helper.getUsuario(viaje.getId_usuario());

        ImageView imageViewFoto = (ImageView) view.findViewById(R.id.imageViewFoto);
        String foto = ciudadDestino.getFoto();

        try {
            int res = getResources().getIdentifier(foto, "drawable", packagename);
            imageViewFoto.setImageResource(res);
        } catch (Exception e) {}

        // Imprimimos en pantalla los datos
        TextView textViewOrigen = (TextView) view.findViewById(R.id.textViewOrigen);
        ciudadOrigenStr = ciudadOrigen.getNombre();
        textViewOrigen.setText(ciudadOrigenStr);

        TextView textViewDestino = (TextView) view.findViewById(R.id.textViewDestino);
        ciudadDestinoStr = ciudadDestino.getNombre();
        textViewDestino.setText(ciudadDestinoStr);

        TextView textViewClaseviaje = (TextView) view.findViewById(R.id.textViewClaseviaje);
        claseviajeStr = claseviaje.getDescripcion();
        textViewClaseviaje.setText(claseviajeStr);

        TextView textViewUsuario = (TextView) view.findViewById(R.id.textViewUsuario);
        usuarioStr = usuario.getUsuario();
        textViewUsuario.setText(usuarioStr);

        TextView textViewPasajeros = (TextView) view.findViewById(R.id.textViewPasajeros);
        pasajerosStr = Integer.toString(viaje.getPasajeros());
        textViewPasajeros.setText(pasajerosStr);

        TextView textViewPrecio = (TextView) view.findViewById(R.id.textViewPrecio);
        precioStr = Integer.toString(viaje.getPrecio());
        textViewPrecio.setText(precioStr);

        TextView textViewVuelta = (TextView) view.findViewById(R.id.textViewVuelta);
        if (viaje.getVuelta() == 0) {
            textViewVuelta.setText("No");
            vueltaStr = "No";
        } else {
            textViewVuelta.setText("Sí");
            vueltaStr = "Sí";
        }
    }

    // Método para cargar el fragment para la edición de un viaje
    public void editViaje() {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        ViajeFragment viajeFragment = new ViajeFragment();
        // Creamos un bundle de datos con toda la información del viaje a editar
        Bundle bundle = new Bundle();
        bundle.putString("modo", "edit");
        bundle.putString("id", id);
        bundle.putString("ciudadOrigen", ciudadOrigenStr);
        bundle.putString("ciudadDestino", ciudadDestinoStr);
        bundle.putString("claseviaje", claseviajeStr);
        bundle.putString("usuario", usuarioStr);
        bundle.putString("pasajeros", pasajerosStr);
        bundle.putString("vuelta", vueltaStr);
        viajeFragment.setArguments(bundle);

        // Reemplazamos el fragment actual por el de ViajeFragment
        fragmentTransaction.replace(R.id.fragment, viajeFragment, "ViajeFragment");
        // Lo añadimos al back stack
        fragmentTransaction.addToBackStack("ViajeFragment");
        fragmentTransaction.commit();
    }
}
