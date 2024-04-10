package es.empresa.comergallego;

import java.io.Serializable;

public class Local implements Serializable {
    private int id;
    private String nombre;
    private String direccion;
    private String telefono;
    // Puedes agregar otros atributos según necesites

    // Constructor que inicializa todos los atributos
    public Local(int id, String nombre, String direccion, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
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

    // Método toString() para representar el objeto Local como una cadena de texto
    @Override
    public String toString() {
        return "Local{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
    // Aquí puedes agregar otros métodos que necesites
}

