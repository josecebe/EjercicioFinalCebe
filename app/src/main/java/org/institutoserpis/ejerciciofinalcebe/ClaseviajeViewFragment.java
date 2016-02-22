package org.institutoserpis.ejerciciofinalcebe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.institutoserpis.ejerciciofinalcebe.Tables.Claseviaje;

public class ClaseviajeViewFragment extends Fragment {
    View view;

    SQLiteHelper helper;
    String id = "";
    String descripcionStr = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_claseviaje_view_fragment, container, false);

        ImageButton imageButtonEdit = (ImageButton) view.findViewById(R.id.imageButton);
        imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editClaseviaje();
            }
        });

        helper = new SQLiteHelper(getActivity(), "DBCebe", null, 1);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = Integer.toString(bundle.getInt("id"));
        }

        loadClaseviaje(Integer.parseInt(id));

        return view;
    }

    private void loadClaseviaje(int id) {
        Claseviaje claseviaje = helper.getClaseviaje(id);

        TextView textViewDescripcion = (TextView) view.findViewById(R.id.textViewDescripcion);
        descripcionStr = claseviaje.getDescripcion();
        textViewDescripcion.setText(descripcionStr);
    }

    public void editClaseviaje() {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        ClaseviajeFragment claseviajeFragment = new ClaseviajeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("modo", "edit");
        bundle.putString("id", id);
        bundle.putString("descripcion", descripcionStr);
        claseviajeFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment, claseviajeFragment, "ClaseviajeFragment");
        fragmentTransaction.addToBackStack("ClaseviajeFragment");
        fragmentTransaction.commit();
    }
}
