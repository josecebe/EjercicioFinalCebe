package org.institutoserpis.ejerciciofinalcebe;

/**
 * Created by JoseCebe
 */
public class ListaClasesviaje {
    private String descripcion;
    private int id;

    public ListaClasesviaje() {

    }

    public ListaClasesviaje(String descripcion, int id) {
        this.descripcion = descripcion;
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
