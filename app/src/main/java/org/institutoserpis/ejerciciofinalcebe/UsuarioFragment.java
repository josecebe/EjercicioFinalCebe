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

import org.institutoserpis.ejerciciofinalcebe.Tables.Usuario;

public class UsuarioFragment extends Fragment {
    View view;

    SQLiteHelper helper;

    String id = "0";
    String usuario = "";
    String usuarioPreEdit = "";
    String password = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_usuario_fragment, container, false);
        helper = new SQLiteHelper(getActivity(), "DBCebe", null, 1);

        String modo = "";
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            modo = bundle.getString("modo");
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.imageButtonSave);

        if (modo.equals("edit")) {
            TextView label = (TextView) view.findViewById(R.id.label);
            label.setText("Editar usuario");

            printUsuarioData(bundle);

            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    editUsuario();
                }
            });
        } else {
            TextView label = (TextView) view.findViewById(R.id.label);
            label.setText("Nuevo usuario");

            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    newUsuario();
                }
            });
        }

        return view;
    }

    private void printUsuarioData(Bundle bundle) {
        if (bundle != null) {
            id = bundle.getString("id");
            usuario = bundle.getString("usuario");
            usuarioPreEdit = bundle.getString("usuario");
            password = bundle.getString("password");
        }

        TextView textViewId = (TextView) view.findViewById(R.id.textViewId);
        textViewId.setText(id);

        EditText editTextUsuario = (EditText) view.findViewById(R.id.editTextUsuario);
        editTextUsuario.setText(usuario);

        EditText editTextPassword = (EditText) view.findViewById(R.id.editTextPassword);
        editTextPassword.setText(password);
    }

    private void editUsuario() {
        TextView textViewId = (TextView) view.findViewById(R.id.textViewId);
        id = textViewId.getText().toString();

        EditText editTextUsuario = (EditText) view.findViewById(R.id.editTextUsuario);
        usuario = editTextUsuario.getText().toString();

        EditText editTextPassword = (EditText) view.findViewById(R.id.editTextPassword);
        password = editTextPassword.getText().toString();

        if (!validarDatos()) {
            return;
        }

        Usuario usuarioObj = new Usuario();

        usuarioObj.setId(Integer.parseInt(id));
        usuarioObj.setUsuario(usuario);
        usuarioObj.setPassword(password);

        helper.setUsuario(usuarioObj);

        getFragmentManager().popBackStack();
    }

    private void newUsuario() {
        EditText editTextUsuario = (EditText) view.findViewById(R.id.editTextUsuario);
        usuario = editTextUsuario.getText().toString();

        EditText editTextPassword = (EditText) view.findViewById(R.id.editTextPassword);
        password = editTextPassword.getText().toString();

        if (!validarDatos()) {
            return;
        }

        Usuario usuarioObj = new Usuario();

        usuarioObj.setUsuario(usuario);
        usuarioObj.setPassword(password);

        id = Long.toString(helper.setUsuario(usuarioObj));

        getActivity().getSupportFragmentManager().popBackStack();

        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        UsuarioViewFragment usuarioViewFragment = new UsuarioViewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", Integer.parseInt(id));
        usuarioViewFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment, usuarioViewFragment, "UsuarioViewFragment");
        fragmentTransaction.addToBackStack("UsuarioViewFragment");
        fragmentTransaction.commit();
    }

    private boolean validarDatos() {
        if (usuario.length() < 1) {
            showToast("Debe introducir un nombre de usuario");
            return false;
        }

        if (helper.getId("usuario", "usuario", usuario) > 0 && !usuario.equals(usuarioPreEdit)) {
            showToast("Ya existe un usuario con este nombre");
            return false;
        }

        if (password.length() < 1) {
            showToast("Debe introducir una contraseña");
            return false;
        }

        return true;
    }

    public void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}
