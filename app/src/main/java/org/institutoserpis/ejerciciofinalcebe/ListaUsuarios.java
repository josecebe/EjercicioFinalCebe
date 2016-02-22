package org.institutoserpis.ejerciciofinalcebe;

/**
 * Created by JoseCebe
 */
public class ListaUsuarios {
    private String nombreUsuario;
    private String password;
    private int id;

    public ListaUsuarios() {

    }

    public ListaUsuarios(String nombreUsuario, String password, int id) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.id = id;
    }

    public String getNombreUsuario() {

        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
