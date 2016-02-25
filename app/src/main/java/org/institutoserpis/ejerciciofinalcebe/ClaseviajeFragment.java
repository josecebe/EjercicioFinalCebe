package org.institutoserpis.ejerciciofinalcebe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.institutoserpis.ejerciciofinalcebe.Tables.Claseviaje;

public class ClaseviajeFragment extends Fragment {
    View view;

    SQLiteHelper helper;

    String id = "0";
    String descripcion = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_claseviaje_fragment, container, false);
        helper = new SQLiteHelper(getActivity(), "DBCebe", null, 1);

        String modo = "";
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            modo = bundle.getString("modo");
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.imageButtonSave);

        if (modo.equals("edit")) {
            TextView label = (TextView) view.findViewById(R.id.label);
            label.setText("Editar claseviaje");

            printClaseviajeData(bundle);

            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    editClaseviaje();
                }
            });
        } else {
            TextView label = (TextView) view.findViewById(R.id.label);
            label.setText("Nuevo claseviaje");

            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    newClaseviaje();
                }
            });
        }

        return view;
    }

    private void printClaseviajeData(Bundle bundle) {
        if (bundle != null) {
            id = bundle.getString("id");
            descripcion = bundle.getString("descripcion");
        }

        TextView textViewId = (TextView) view.findViewById(R.id.textViewId);
        textViewId.setText(id);

        EditText editTextDescripcion = (EditText) view.findViewById(R.id.editTextDescripcion);
        editTextDescripcion.setText(descripcion);
    }

    private void editClaseviaje() {
        TextView textViewId = (TextView) view.findViewById(R.id.textViewId);
        id = textViewId.getText().toString();

        EditText editTextDescripcion = (EditText) view.findViewById(R.id.editTextDescripcion);
        descripcion = editTextDescripcion.getText().toString();

        if (!validarDatos()) {
            return;
        }

        Claseviaje claseviaje = new Claseviaje();

        claseviaje.setId(Integer.parseInt(id));
        claseviaje.setDescripcion(descripcion);

        helper.setClaseviaje(claseviaje);

        getFragmentManager().popBackStack();
    }

    private void newClaseviaje() {
        EditText editTextDescripcion = (EditText) view.findViewById(R.id.editTextDescripcion);
        descripcion = editTextDescripcion.getText().toString();

        if (!validarDatos()) {
            return;
        }

        Claseviaje claseviaje = new Claseviaje();

        claseviaje.setDescripcion(descripcion);

        id = Long.toString(helper.setClaseviaje(claseviaje));

        getActivity().getSupportFragmentManager().popBackStack();

        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        ClaseviajeViewFragment claseviajeViewFragment = new ClaseviajeViewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", Integer.parseInt(id));
        claseviajeViewFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment, claseviajeViewFragment, "ClaseviajeViewFragment");
        fragmentTransaction.addToBackStack("ClaseviajeViewFragment");
        fragmentTransaction.commit();
    }

    private boolean validarDatos() {
        if (descripcion.length() < 1) {
            showToast("Debe introducir una descripción");
            return false;
        }

        return true;
    }

    public void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}
