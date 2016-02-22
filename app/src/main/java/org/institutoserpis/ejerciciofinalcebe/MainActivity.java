package org.institutoserpis.ejerciciofinalcebe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.institutoserpis.ejerciciofinalcebe.Tables.Usuario;


public class MainActivity extends AppCompatActivity {
    private SQLiteHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new SQLiteHelper(this, "DBCebe", null, 1);

    }

    public void login(View v) {
        final EditText usuarioEditText = (EditText) findViewById(R.id.usuario);
        final EditText passwordEditText = (EditText) findViewById(R.id.password);
        String usuario = usuarioEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        int idUsuario = helper.getId("usuario", "usuario", usuario);

        if (idUsuario == 0) {
            showToast("El usuario indicado no existe");
        } else {
            String passwordUsuario = helper.getOne("usuario", "password", idUsuario);

            if (password.equals(passwordUsuario)) {
                finish();
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);

            } else {
                showToast("La combinación de usuario y contraseña no es correcta");
            }
        }
    }

    public void register(View v) {
        final EditText usuarioEditText = (EditText) findViewById(R.id.usuario);
        final EditText passwordEditText = (EditText) findViewById(R.id.password);
        //final EditText fotoRegisterEditText = (EditText) findViewById(R.id.fotoRegister);

        String usuarioString = usuarioEditText.getText().toString();
        String passwordString = passwordEditText.getText().toString();
        //String fotoRegisterString = fotoRegisterEditText.getText().toString();

        if (usuarioString.equals("")) {
            showToast("El campo usuario está vacío");
            return;
        }

        if (passwordString.equals("")) {
            showToast("El campo contraseña está vacío");
            return;
        }

        int id = helper.getId("usuario", "usuario", usuarioString);

        if (id == 0) {
            Usuario usuario = new Usuario(usuarioString, passwordString);
            helper.setUsuario(usuario);

            finish();
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        } else {
            showToast("Ya existe una cuenta con el nombre de usuario indicado");
        }
    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}