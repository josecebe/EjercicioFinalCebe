package org.institutoserpis.ejerciciofinalcebe;

/**
 * Created by JoseCebe
 */
public class ListaViajes {
    private String nombreOrigen;
    private String nombreDestino;
    private String fotoDestino;
    private String nombreClase;
    private int pasajeros;
    private int vuelta;
    private int id;

    public ListaViajes(String nombreOrigen, String nombreDestino, String fotoDestino, String nombreClase, int pasajeros, int vuelta, int id) {
        this.nombreOrigen = nombreOrigen;
        this.nombreDestino = nombreDestino;
        this.fotoDestino = fotoDestino;
        this.nombreClase = nombreClase;
        this.pasajeros = pasajeros;
        this.vuelta = vuelta;
        this.id = id;
    }

    public ListaViajes() {

    }

    public String getNombreOrigen() {
        return nombreOrigen;
    }

    public void setNombreOrigen(String nombreOrigen) {
        this.nombreOrigen = nombreOrigen;
    }

    public String getNombreDestino() {
        return nombreDestino;
    }

    public void setNombreDestino(String nombreDestino) {
        this.nombreDestino = nombreDestino;
    }

    public String getFotoDestino() {
        return fotoDestino;
    }

    public void setFotoDestino(String fotoDestino) {
        this.fotoDestino = fotoDestino;
    }

    public String getNombreClase() {
        return nombreClase;
    }

    public void setNombreClase(String nombreClase) {
        this.nombreClase = nombreClase;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
