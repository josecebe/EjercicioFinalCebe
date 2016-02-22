package org.institutoserpis.ejerciciofinalcebe.Tables;

/**
 * Created by JoseCebe
 */
public class Viaje {
    private int id;
    private int id_usuario;
    private int id_ciudad_1;
    private int id_ciudad_2;
    private int id_claseviaje;
    private int pasajeros;
    private int vuelta;
    private int precio;

    public Viaje() {
    }

    public Viaje(int id_usuario, int id_ciudad_1, int id_ciudad_2, int id_claseviaje, int pasajeros, int vuelta, int precio) {
        this.id_usuario = id_usuario;
        this.id_ciudad_1 = id_ciudad_1;
        this.id_ciudad_2 = id_ciudad_2;
        this.id_claseviaje = id_claseviaje;
        this.pasajeros = pasajeros;
        this.vuelta = vuelta;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_ciudad_1() {
        return id_ciudad_1;
    }

    public void setId_ciudad_1(int id_ciudad_1) {
        this.id_ciudad_1 = id_ciudad_1;
    }

    public int getId_ciudad_2() {
        return id_ciudad_2;
    }

    public void setId_ciudad_2(int id_ciudad_2) {
        this.id_ciudad_2 = id_ciudad_2;
    }

    public int getId_claseviaje() {
        return id_claseviaje;
    }

    public void setId_claseviaje(int id_claseviaje) {
        this.id_claseviaje = id_claseviaje;
    }

    public int getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(int pasajeros) {
        this.pasajeros = pasajeros;
    }

    public int getVuelta() {
        return vuelta;
    }

    public void setVuelta(int vuelta) {
        this.vuelta = vuelta;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
