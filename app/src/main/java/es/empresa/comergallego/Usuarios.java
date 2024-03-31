package es.empresa.comergallego;

public class Usuarios {
    private  String nombreUsuario;
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private String correoElectronico;

    private String password;
    private String historialBusquedas;
    private int ubicacionActual;
    private String idiomaPreferido;
    private String rolUsuario;


    //Metodo con sobrecarga
    public Usuarios(String nU,String n, String a, String f, String c, String p, String h, int u , String i, String r){
        this.nombreUsuario= nU;
        this.nombre=n;
        this.apellido=a;
        this.fechaNacimiento=f;
        this.correoElectronico=c;
        this.password=p;
        this.historialBusquedas=h;
        this.ubicacionActual=u;
        this.idiomaPreferido=i;
        this.rolUsuario=r;
    }
    //Getters y setters
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHistorialBusquedas() {
        return historialBusquedas;
    }

    public void setHistorialBusquedas(String historialBusquedas) {
        this.historialBusquedas = historialBusquedas;
    }

    public int getUbicacionActual() {
        return ubicacionActual;
    }

    public void setUbicacionActual(int ubicacionActual) {
        this.ubicacionActual = ubicacionActual;
    }

    public String getIdiomaPreferido() {
        return idiomaPreferido;
    }

    public void setIdiomaPreferido(String idiomaPreferido) {
        this.idiomaPreferido = idiomaPreferido;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }
}

