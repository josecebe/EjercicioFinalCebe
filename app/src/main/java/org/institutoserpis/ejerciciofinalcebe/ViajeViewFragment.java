package org.institutoserpis.ejerciciofinalcebe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
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
    DrawerLayout drawerLayout;
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

    TextView textViewOrigen;

    int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_viaje_view_fragment, container, false);

        ImageButton imageButtonEdit = (ImageButton) view.findViewById(R.id.imageButton);
        imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editViaje();
            }
        });

        helper = new SQLiteHelper(getActivity(), "DBCebe", null, 1);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = Integer.toString(bundle.getInt("id"));
        }

        loadViaje(Integer.parseInt(id));

        return view;
    }

    private void loadViaje(int id) {
        Viaje viaje = helper.getViaje(id);
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

    public void editViaje() {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        ViajeFragment viajeFragment = new ViajeFragment();
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

        fragmentTransaction.replace(R.id.fragment, viajeFragment, "ViajeFragment");
        fragmentTransaction.addToBackStack("ViajeFragment");
        fragmentTransaction.commit();
    }

    public void showToast(String text) {
        /*Toast.makeText(this, text, Toast.LENGTH_SHORT).show();*/
    }
}
