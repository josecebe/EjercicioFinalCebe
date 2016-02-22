package org.institutoserpis.ejerciciofinalcebe;

/**
 * Created by JoseCebe
 */
public class ListaCiudades {
    private String nombre;
    private String diminutivo;
    private String foto;
    private String s_foto;
    private int id;

    public ListaCiudades() {

    }

    public ListaCiudades(String nombre, String diminutivo, String foto, String s_foto, int id) {
        this.nombre = nombre;
        this.diminutivo = diminutivo;
        this.foto = foto;
        this.s_foto = s_foto;
        this.id = id;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDiminutivo() {
        return diminutivo;
    }

    public void setDiminutivo(String diminutivo) {
        this.diminutivo = diminutivo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getS_foto() {
        return s_foto;
    }

    public void setS_foto(String s_foto) {
        this.s_foto = s_foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
