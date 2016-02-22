package org.institutoserpis.ejerciciofinalcebe;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.institutoserpis.ejerciciofinalcebe.Tables.Viaje;

public class ViajeFragment extends Fragment {
    static public final String packagename = ListaViajesFragment.class.getPackage().getName();
    DrawerLayout drawerLayout;
    View view;

    SQLiteHelper helper;
    MenuActivity menuActivity;

    String id = "0";
    String ciudadOrigen = "";
    String ciudadDestino = "";
    String claseviaje = "";
    String usuario = "";
    String pasajeros = "";
    String vuelta = "";
    String precio = "";

    int precioFinal = 0;
    int precioClase = 0;
    int numeroPasajeros = 1;
    boolean precioVuelta = false;

    int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_viaje_fragment, container, false);
        helper = new SQLiteHelper(getActivity(), "DBCebe", null, 1);

        Spinner spinnerOrigen = (Spinner) view.findViewById(R.id.spinnerOrigen);
        Spinner spinnerDestino = (Spinner) view.findViewById(R.id.spinnerDestino);






        Cursor c = helper.getNombreCiudades();
        SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(view.getContext(),android.R.layout.simple_spinner_item,c,new String[] {"nombre"},    new int[] {android.R.id.text1});
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrigen.setAdapter(adapter2);
        spinnerDestino.setAdapter(adapter2);

        final Spinner spinnerClase = (Spinner) view.findViewById(R.id.spinnerClase);
        c = helper.getClaseViajes();
        adapter2 = new SimpleCursorAdapter(view.getContext(),android.R.layout.simple_spinner_item,c,new String[] {"descripcion"},    new int[] {android.R.id.text1});
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClase.setAdapter(adapter2);

        String modo = "";
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            modo = bundle.getString("modo");
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.imageButtonSave);

        if (modo.equals("edit")) {
            TextView label = (TextView) view.findViewById(R.id.label);
            label.setText("Editar viaje");

            printViajeData(bundle);

            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    editViaje();
                }
            });
        } else {
            TextView label = (TextView) view.findViewById(R.id.label);
            label.setText("Nuevo viaje");

            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    newViaje();
                }
            });
        }

        spinnerClase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerClase.getSelectedItem().toString().equals("Turista")) {
                    precioClase = 10;
                    calcularPrecioFinal();
                }

                if (spinnerClase.getSelectedItem().toString().equals("Business")) {
                    precioClase = 30;
                    calcularPrecioFinal();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button buttonRestar = (Button) view.findViewById(R.id.buttonRestarPasajero);
        buttonRestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restarPasajero();
            }
        });

        Button buttonSumar = (Button) view.findViewById(R.id.buttonSumarPasajero);
        buttonSumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumarPasajero();
            }
        });

        final SwitchCompat switchCompat = (SwitchCompat) view.findViewById(R.id.switchVuelta);
        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchCompat.isChecked()) {
                    precioVuelta = true;
                    calcularPrecioFinal();
                } else {
                    precioVuelta = false;
                    calcularPrecioFinal();
                }
            }
        });

        return view;
    }

    private void printViajeData(Bundle bundle) {
        if (bundle != null) {
            id = bundle.getString("id");
            ciudadOrigen = bundle.getString("ciudadOrigen");
            ciudadDestino = bundle.getString("ciudadDestino");
            claseviaje = bundle.getString("claseviaje");
            usuario = bundle.getString("usuario");
            pasajeros = bundle.getString("pasajeros");
            vuelta = bundle.getString("vuelta");
            precio = bundle.getString("precio");
            numeroPasajeros = Integer.parseInt(pasajeros);
        }

        TextView textViewId = (TextView) view.findViewById(R.id.textViewId);
        textViewId.setText(id);

        Spinner spinnerOrigen = (Spinner) view.findViewById(R.id.spinnerOrigen);
        setSpinText(spinnerOrigen, ciudadOrigen, "nombre");

        Spinner spinnerDestino = (Spinner) view.findViewById(R.id.spinnerDestino);
        setSpinText(spinnerDestino, ciudadDestino, "nombre");

        Spinner spinnerClase = (Spinner) view.findViewById(R.id.spinnerClase);
        setSpinText(spinnerClase, claseviaje, "descripcion");

        EditText editTextUsuario = (EditText) view.findViewById(R.id.editTextUsuario);
        editTextUsuario.setText(usuario);

        EditText editTextPrecio = (EditText) view.findViewById(R.id.editTextPrecio);
        editTextPrecio.setText(precio);

        TextView textViewPasajeros = (TextView) view.findViewById(R.id.textViewPasajeros);
        textViewPasajeros.setText(pasajeros);
        int numPasajeros = Integer.parseInt(textViewPasajeros.getText().toString());
        numeroPasajeros = numPasajeros;
        Button buttonRestarPasajero = (Button) view.findViewById(R.id.buttonRestarPasajero);
        Button buttonSumarPasajero = (Button) view.findViewById(R.id.buttonSumarPasajero);
        if (numPasajeros < 2) {
            buttonRestarPasajero.setEnabled(false);
            buttonSumarPasajero.setEnabled(true);
        } else if (numPasajeros >= 10) {
            buttonSumarPasajero.setEnabled(false);
            buttonRestarPasajero.setEnabled(true);
        } else {
            buttonSumarPasajero.setEnabled(true);
            buttonRestarPasajero.setEnabled(true);
        }

        SwitchCompat switchCompat = (SwitchCompat) view.findViewById(R.id.switchVuelta);
        if (vuelta.equals("No")) {
            precioVuelta = false;
            switchCompat.setChecked(false);
        } else {
            precioVuelta = true;
            switchCompat.setChecked(true);
        }

        calcularPrecioFinal();
    }

    private void editViaje() {
        TextView textViewId = (TextView) view.findViewById(R.id.textViewId);
        id = textViewId.getText().toString();

        Spinner spinnerOrigen = (Spinner) view.findViewById(R.id.spinnerOrigen);
        Cursor colCur=(Cursor)spinnerOrigen.getSelectedItem();
        String ciudad = colCur.getString(colCur.getColumnIndex("nombre"));
        ciudadOrigen = Integer.toString(helper.getId("ciudad", "nombre", ciudad));

        Spinner spinnerDestino = (Spinner) view.findViewById(R.id.spinnerDestino);
        colCur=(Cursor)spinnerDestino.getSelectedItem();
        ciudad = colCur.getString(colCur.getColumnIndex("nombre"));
        ciudadDestino = Integer.toString(helper.getId("ciudad", "nombre", ciudad));

        Spinner spinnerClase = (Spinner) view.findViewById(R.id.spinnerClase);
        colCur=(Cursor)spinnerClase.getSelectedItem();
        String clase = colCur.getString(colCur.getColumnIndex("descripcion"));
        claseviaje = Integer.toString(helper.getId("claseviaje", "descripcion", clase));

        EditText editTextUsuario = (EditText) view.findViewById(R.id.editTextUsuario);
        usuario = Integer.toString(helper.getId("usuario", "usuario", editTextUsuario.getText().toString()));

        TextView textViewPasajeros = (TextView) view.findViewById(R.id.textViewPasajeros);
        pasajeros = textViewPasajeros.getText().toString();

        EditText editTextPrecio = (EditText) view.findViewById(R.id.editTextPrecio);
        precio = editTextPrecio.getText().toString();
        precio = precio.substring(0, precio.length() - 2);

        SwitchCompat switchCompat = (SwitchCompat) view.findViewById(R.id.switchVuelta);
        if (switchCompat.isChecked()) {
            vuelta = "1";
        } else {
            vuelta = "0";
        }

        if (!validarDatos()) {
            return;
        }

        setSpinText(spinnerClase, claseviaje, "descripcion");

        Viaje viaje = new Viaje();

        viaje.setId(Integer.parseInt(id));
        viaje.setId_usuario(Integer.parseInt(usuario));
        viaje.setId_ciudad_1(Integer.parseInt(ciudadOrigen));
        viaje.setId_ciudad_2(Integer.parseInt(ciudadDestino));
        viaje.setId_claseviaje(Integer.parseInt(claseviaje));
        viaje.setPasajeros(Integer.parseInt(pasajeros));
        viaje.setVuelta(Integer.parseInt(vuelta));
        viaje.setPrecio(Integer.parseInt(precio));

        helper.setViaje(viaje);

        getFragmentManager().popBackStack();
    }

    private void newViaje() {


        Spinner spinnerOrigen = (Spinner) view.findViewById(R.id.spinnerOrigen);
        Cursor colCur=(Cursor)spinnerOrigen.getSelectedItem();
        String ciudad = colCur.getString(colCur.getColumnIndex("nombre"));
        ciudadOrigen = Integer.toString(helper.getId("ciudad", "nombre", ciudad));

        Spinner spinnerDestino = (Spinner) view.findViewById(R.id.spinnerDestino);
        colCur=(Cursor)spinnerDestino.getSelectedItem();
        ciudad = colCur.getString(colCur.getColumnIndex("nombre"));
        ciudadDestino = Integer.toString(helper.getId("ciudad", "nombre", ciudad));

        Spinner spinnerClase = (Spinner) view.findViewById(R.id.spinnerClase);
        colCur=(Cursor)spinnerClase.getSelectedItem();
        String clase = colCur.getString(colCur.getColumnIndex("descripcion"));
        claseviaje = Integer.toString(helper.getId("claseviaje", "descripcion", clase));

        EditText editTextUsuario = (EditText) view.findViewById(R.id.editTextUsuario);
        usuario = Integer.toString(helper.getId("usuario", "usuario", editTextUsuario.getText().toString()));

        TextView textViewPasajeros = (TextView) view.findViewById(R.id.textViewPasajeros);
        pasajeros = textViewPasajeros.getText().toString();

        EditText editTextPrecio = (EditText) view.findViewById(R.id.editTextPrecio);
        precio = editTextPrecio.getText().toString();
        precio = precio.substring(0, precio.length()-2);

        SwitchCompat switchCompat = (SwitchCompat) view.findViewById(R.id.switchVuelta);
        if (switchCompat.isChecked()) {
            vuelta = "1";
        } else {
            vuelta = "0";
        }

        if (!validarDatos()) {
            return;
        }

        Viaje viaje = new Viaje();

        viaje.setId_usuario(Integer.parseInt(usuario));
        viaje.setId_ciudad_1(Integer.parseInt(ciudadOrigen));
        viaje.setId_ciudad_2(Integer.parseInt(ciudadDestino));
        viaje.setId_claseviaje(Integer.parseInt(claseviaje));
        viaje.setPasajeros(Integer.parseInt(pasajeros));
        viaje.setVuelta(Integer.parseInt(vuelta));
        viaje.setPrecio(Integer.parseInt(precio));

        id = Long.toString(helper.setViaje(viaje));



        getActivity().getSupportFragmentManager().popBackStack();

        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        ViajeViewFragment viajeViewFragment = new ViajeViewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", Integer.parseInt(id));
        viajeViewFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment, viajeViewFragment, "ViajeViewFragment");
        fragmentTransaction.addToBackStack("ViajeViewFragment");
        fragmentTransaction.commit();
    }

    public void sumarPasajero() {
        TextView textViewPasajeros = (TextView) view.findViewById(R.id.textViewPasajeros);
        int numPasajeros = Integer.parseInt(textViewPasajeros.getText().toString());

        numeroPasajeros = numPasajeros + 1;
        calcularPrecioFinal();

        Button buttonSumarPasajero = (Button) view.findViewById(R.id.buttonSumarPasajero);
        Button buttonRestarPasajero = (Button) view.findViewById(R.id.buttonRestarPasajero);

        if (numPasajeros < 10) {
            textViewPasajeros.setText(Integer.toString(++numPasajeros));
        }

        if (numPasajeros < 2) {
            buttonRestarPasajero.setEnabled(false);
            buttonSumarPasajero.setEnabled(true);
        } else if (numPasajeros >= 10) {
            buttonSumarPasajero.setEnabled(false);
            buttonRestarPasajero.setEnabled(true);
        } else {
            buttonSumarPasajero.setEnabled(true);
            buttonRestarPasajero.setEnabled(true);
        }

        calcularPrecioFinal();
    }

    public void restarPasajero() {
        TextView textViewPasajeros = (TextView) view.findViewById(R.id.textViewPasajeros);
        int numPasajeros = Integer.parseInt(textViewPasajeros.getText().toString());

        numeroPasajeros = numPasajeros - 1;

        if (numPasajeros > 1) {
            textViewPasajeros.setText(Integer.toString(--numPasajeros));
        }

        Button buttonSumarPasajero = (Button) view.findViewById(R.id.buttonSumarPasajero);
        Button buttonRestarPasajero = (Button) view.findViewById(R.id.buttonRestarPasajero);

        if (numPasajeros < 2) {
            buttonRestarPasajero.setEnabled(false);
            buttonSumarPasajero.setEnabled(true);
        } else if (numPasajeros >= 10) {
            buttonSumarPasajero.setEnabled(false);
            buttonRestarPasajero.setEnabled(true);
        } else {
            buttonSumarPasajero.setEnabled(true);
            buttonRestarPasajero.setEnabled(true);
        }

        calcularPrecioFinal();
    }

    public void setSpinText(Spinner spin, String text, String column)
    {
        for(int i= 0; i < spin.getAdapter().getCount(); i++)
        {
            Cursor colCur=(Cursor)spin.getItemAtPosition(i);
            String ciudad = colCur.getString(colCur.getColumnIndex(column));

            if(ciudad.contains(text))
            {
                spin.setSelection(i);
            }
        }

    }

    private void calcularPrecioFinal() {
        if (precioVuelta) {
            precioFinal = ((30 + precioClase) * numeroPasajeros) * 2;
        } else {
            precioFinal = (30 + precioClase) * numeroPasajeros;
        }

        EditText editTextPrecio = (EditText) view.findViewById(R.id.editTextPrecio);
        editTextPrecio.setText(Integer.toString(precioFinal) + " €");
    }

    private boolean validarDatos() {
        int idOrigen = helper.getId("ciudad", "id", ciudadOrigen);
        if (idOrigen < 1) {
            showToast("La ciudad de origen introducida no es válida");
            return false;
        }

        int idDestino = helper.getId("ciudad", "id", ciudadDestino);
        if (idDestino < 1) {
            showToast("La ciudad de destino introducida no es válida");
            return false;
        }

        if (idOrigen == idDestino) {
            showToast("La ciudad de origen y de destino no pueden ser la misma");
            return false;
        }

        if (helper.getId("claseviaje", "id", claseviaje) < 1) {
            showToast("La clase de viaje no es válida");
            return false;
        }

        if (helper.getId("usuario", "id", usuario) < 1) {
            showToast("El usuario introducido no es válido");
            return false;
        }

        if (Integer.parseInt(pasajeros) < 1) {
            showToast("Debe haber 1 pasajero como mínimo");
            return false;
        }

        if (Integer.parseInt(pasajeros) > 10) {
            showToast("Debe haber 10 pasajeros como máximo");
            return false;
        }

        if (!vuelta.equals("1") && !vuelta.equals("0")) {
            showToast("El campo vuelta no es válido");
            return false;
        }

        return true;
    }

    public void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}
