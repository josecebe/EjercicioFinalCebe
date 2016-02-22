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

import org.institutoserpis.ejerciciofinalcebe.Tables.Usuario;

public class UsuarioViewFragment extends Fragment {
    View view;

    SQLiteHelper helper;
    String id = "";
    String usuarioStr = "";
    String passwordStr = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_usuario_view_fragment, container, false);

        ImageButton imageButtonEdit = (ImageButton) view.findViewById(R.id.imageButton);
        imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUsuario();
            }
        });

        helper = new SQLiteHelper(getActivity(), "DBCebe", null, 1);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = Integer.toString(bundle.getInt("id"));
        }

        loadUsuario(Integer.parseInt(id));

        return view;
    }

    private void loadUsuario(int id) {
        Usuario usuario = helper.getUsuario(id);

        TextView textViewUsuario = (TextView) view.findViewById(R.id.textViewNombreUsuario);
        usuarioStr = usuario.getUsuario();
        textViewUsuario.setText(usuarioStr);

        TextView textViewPassword = (TextView) view.findViewById(R.id.textViewPassword);
        passwordStr = usuario.getPassword();
        textViewPassword.setText(passwordStr);
    }

    public void editUsuario() {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        UsuarioFragment usuarioFragment = new UsuarioFragment();
        Bundle bundle = new Bundle();
        bundle.putString("modo", "edit");
        bundle.putString("id", id);
        bundle.putString("usuario", usuarioStr);
        bundle.putString("password", passwordStr);
        usuarioFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment, usuarioFragment, "UsuarioFragment");
        fragmentTransaction.addToBackStack("UsuarioFragment");
        fragmentTransaction.commit();
    }
}
