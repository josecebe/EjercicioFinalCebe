package org.institutoserpis.ejerciciofinalcebe.Tables;

/**
 * Created by JoseCebe
 */
public class Claseviaje {
    private int id;
    private String descripcion;

    public Claseviaje() {
    }

    public Claseviaje(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
