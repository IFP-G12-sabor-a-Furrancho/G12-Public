public class AlmacenAtributosLocales {
    private String nombreLocal;
    private String direccion;
    private String descripcion;
    private String tipoLocal;
    private String horario;
    private String telefono;
    private String coordenadasGPS;
    private int capacidad;
    private String valoracion;

    // Constructor vacío
    public AlmacenAtributosLocales() {
    }

    // Constructor con todos los atributos
    public AlmacenAtributosLocales(String nombreLocal, String direccion, String descripcion, 
                                    String tipoLocal, String horario, String telefono, 
                                    String coordenadasGPS, int capacidad, String valoracion) {
        this.nombreLocal = nombreLocal;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.tipoLocal = tipoLocal;
        this.horario = horario;
        this.telefono = telefono;
        this.coordenadasGPS = coordenadasGPS;
        this.capacidad = capacidad;
        this.valoracion = valoracion;
    }

    // Getters
    public String getNombreLocal() {
        return nombreLocal;
    }

    public String getDireccion() {
        return direccion;
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

    public String getTelefono() {
        return telefono;
    }

    public String getCoordenadasGPS() {
        return coordenadasGPS;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public String getValoracion() {
        return valoracion;
    }

    // Setters
    public void setNombreLocal(String nombreLocal) {
        this.nombreLocal = nombreLocal;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCoordenadasGPS(String coordenadasGPS) {
        this.coordenadasGPS = coordenadasGPS;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }
}
