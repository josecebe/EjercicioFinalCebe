package org.institutoserpis.ejerciciofinalcebe.Tables;

/**
 * Created by JoseCebe
 */
public class Ciudad {
    private int id;
    private String nombre;
    private String diminutivo;
    private String foto;
    private String sFoto;

    public Ciudad() {
    }

    public Ciudad(String nombre, String diminutivo, String foto, String sFoto) {
        this.nombre = nombre;
        this.diminutivo = diminutivo;
        this.foto = foto;
        this.sFoto = sFoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getSFoto() {
        return sFoto;
    }

    public void setSFoto(String sFoto) {
        this.sFoto = sFoto;
    }

    public String getDiminutivo() {
        return diminutivo;
    }

    public void setDiminutivo(String diminutivo) {
        this.diminutivo = diminutivo;
    }
}
