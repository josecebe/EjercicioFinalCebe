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

import org.institutoserpis.ejerciciofinalcebe.Tables.Ciudad;

public class CiudadFragment extends Fragment {
    View view;

    SQLiteHelper helper;

    String id = "0";
    String nombre = "";
    String diminutivo = "";
    String foto = "";
    String s_foto = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_ciudad_fragment, container, false);
        helper = new SQLiteHelper(getActivity(), "DBCebe", null, 1);

        String modo = "";
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            modo = bundle.getString("modo");
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.imageButtonSave);

        if (modo.equals("edit")) {
            TextView label = (TextView) view.findViewById(R.id.label);
            label.setText("Editar ciudad");

            printCiudadData(bundle);

            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    editCiudad();
                }
            });
        } else {
            TextView label = (TextView) view.findViewById(R.id.label);
            label.setText("Nueva ciudad");

            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    newCiudad();
                }
            });
        }

        return view;
    }

    private void printCiudadData(Bundle bundle) {
        if (bundle != null) {
            id = bundle.getString("id");
            nombre = bundle.getString("nombre");
            diminutivo = bundle.getString("diminutivo");
            foto = bundle.getString("foto");
            s_foto = bundle.getString("s_foto");
        }

        TextView textViewId = (TextView) view.findViewById(R.id.textViewId);
        textViewId.setText(id);

        EditText editTextNombre = (EditText) view.findViewById(R.id.editTextNombre);
        editTextNombre.setText(nombre);

        EditText editTextDiminutivo = (EditText) view.findViewById(R.id.editTextDiminutivo);
        editTextDiminutivo.setText(diminutivo);
    }

    private void editCiudad() {
        TextView textViewId = (TextView) view.findViewById(R.id.textViewId);
        id = textViewId.getText().toString();

        EditText editTextNombre = (EditText) view.findViewById(R.id.editTextNombre);
        nombre = editTextNombre.getText().toString();

        EditText editTextDiminutivo = (EditText) view.findViewById(R.id.editTextDiminutivo);
        diminutivo = editTextDiminutivo.getText().toString();

        if (!validarDatos()) {
            return;
        }

        Ciudad ciudad = new Ciudad();

        ciudad.setId(Integer.parseInt(id));
        ciudad.setNombre(nombre);
        ciudad.setDiminutivo(diminutivo);
        ciudad.setFoto(foto);
        ciudad.setSFoto(s_foto);

        helper.setCiudad(ciudad);

        getFragmentManager().popBackStack();
    }

    private void newCiudad() {
        EditText editTextNombre = (EditText) view.findViewById(R.id.editTextNombre);
        nombre = editTextNombre.getText().toString();

        EditText editTextDiminutivo = (EditText) view.findViewById(R.id.editTextDiminutivo);
        diminutivo = editTextDiminutivo.getText().toString();

        foto = null;
        s_foto = null;

        if (!validarDatos()) {
            return;
        }

        Ciudad ciudad = new Ciudad();

        ciudad.setNombre(nombre);
        ciudad.setDiminutivo(diminutivo);
        ciudad.setFoto(foto);
        ciudad.setSFoto(s_foto);


        id = Long.toString(helper.setCiudad(ciudad));

        getActivity().getSupportFragmentManager().popBackStack();

        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        CiudadViewFragment ciudadViewFragment = new CiudadViewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", Integer.parseInt(id));
        ciudadViewFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment, ciudadViewFragment, "CiudadViewFragment");
        fragmentTransaction.addToBackStack("CiudadViewFragment");
        fragmentTransaction.commit();
    }

    private boolean validarDatos() {
        if (nombre.length() < 1) {
            showToast("Debe introducir un nombre");
            return false;
        }

        if (diminutivo.length() < 1) {
            showToast("Debe introducir un diminutivo");
            return false;
        }

        return true;
    }

    public void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}
