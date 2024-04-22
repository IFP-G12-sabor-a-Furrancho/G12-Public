package es.empresa.comergallego;

import java.io.Serializable;

public class Local implements Serializable {
    private int id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String descripcion;
    private String tipoLocal;
    private String horario;
    private String coordenadasGPS;

    // Constructor que inicializa todos los atributos
    public Local(int id, String nombre, String direccion, String telefono,
                 String descripcion, String tipoLocal, String horario, String coordenadasGPS) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.descripcion = descripcion;
        this.tipoLocal = tipoLocal;
        this.horario = horario;
        this.coordenadasGPS = coordenadasGPS;
    }

    public Local() {

    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipoLocal() {
        return tipoLocal;
    }

    public String getHorario() {
        return horario;
    }

    public String getCoordenadasGPS() {
        return coordenadasGPS;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTipoLocal(String tipoLocal) {
        this.tipoLocal = tipoLocal;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void setCoordenadasGPS(String coordenadasGPS) {
        this.coordenadasGPS = coordenadasGPS;
    }
}
