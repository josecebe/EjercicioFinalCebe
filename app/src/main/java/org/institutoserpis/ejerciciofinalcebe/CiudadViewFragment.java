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

public class CiudadViewFragment extends Fragment {
    static public final String packagename = CiudadViewFragment.class.getPackage().getName();

    View view;

    SQLiteHelper helper;
    String id = "";
    String nombreStr = "";
    String diminutivoStr = "";
    String fotoStr = "";
    String s_fotoStr = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_ciudad_view_fragment, container, false);

        ImageButton imageButtonEdit = (ImageButton) view.findViewById(R.id.imageButton);
        imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCiudad();
            }
        });

        helper = new SQLiteHelper(getActivity(), "DBCebe", null, 1);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = Integer.toString(bundle.getInt("id"));
        }

        loadCiudad(Integer.parseInt(id));

        return view;
    }

    private void loadCiudad(int id) {
        Ciudad ciudad = helper.getCiudad(id);

        TextView textViewNombre = (TextView) view.findViewById(R.id.textViewNombre);
        nombreStr = ciudad.getNombre();
        textViewNombre.setText(nombreStr);

        TextView textViewDiminutivo = (TextView) view.findViewById(R.id.textViewDiminutivo);
        diminutivoStr = ciudad.getDiminutivo();
        textViewDiminutivo.setText(diminutivoStr);

        fotoStr = ciudad.getFoto();
        s_fotoStr = ciudad.getSFoto();

        try {
            ImageView imageViewFoto = (ImageView) view.findViewById(R.id.imageViewFoto);
            String foto = ciudad.getFoto();
            int res = getResources().getIdentifier(foto, "drawable", packagename);
            imageViewFoto.setImageResource(res);
        } catch (Exception e) {}
    }

    public void editCiudad() {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        CiudadFragment ciudadFragment = new CiudadFragment();
        Bundle bundle = new Bundle();
        bundle.putString("modo", "edit");
        bundle.putString("id", id);
        bundle.putString("nombre", nombreStr);
        bundle.putString("diminutivo", diminutivoStr);
        bundle.putString("foto", fotoStr);
        bundle.putString("s_foto", s_fotoStr);
        ciudadFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment, ciudadFragment, "CiudadFragment");
        fragmentTransaction.addToBackStack("CiudadFragment");
        fragmentTransaction.commit();
    }
}
