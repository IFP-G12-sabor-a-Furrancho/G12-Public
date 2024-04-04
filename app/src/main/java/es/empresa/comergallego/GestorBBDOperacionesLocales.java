package es.empresa.comergallego;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class GestorBBDOperacionesLocales{
    String url = "jdbc:postgresql://ep-shy-glade-57906898.eu-central-1.aws.neon.fl0.io:5432/comergallego";
    String user = "fl0user";
    String password = "8Zizcvy1rMhs";
    Connection conn=null;
    GestorBBDD gestorBBDD;
    Statement s=null;
    ResultSet rs=null;
    static String nombreTabla = "localizaciones";

    //Constructor
    public GestorBBDOperacionesLocales() throws SQLException {
        //Inicializamos gestorBBDD para inicializar la conexión y el statement

        //Comentamos como prueba, no es necesario llamar al constructor
        // si creamos en este punto la conexión (linea 29)
        //this.gestorBBDD = new GestorBBDD();


        conn = DriverManager.getConnection(url, user, password);
        if (conn != null) {
            System.out.println("Conexión exitosa a la base de datos");
        }
    }

    //Revisar si se debería de quitar que se pase el atributo Statement
    public static void insertarModificarEliminar(String consulta, Statement s) throws SQLException, SQLException {
        try {
            s.executeUpdate(consulta);
            System.out.println("Operacion realizada correctamente");
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

    public void insertarModificarEliminar(String consulta) throws SQLException {
        s = conn.createStatement();
        s.executeUpdate(consulta);
        //gestorBBDD.getS().executeUpdate(consulta);
        System.out.println("Operación realizada correctamente");
    }



    //Método para obtener NombreLocal y Direccion - Funcionamiento del buscador
    public ArrayList<String> consulta(String nombrelocal) throws SQLException {
        //String linea="";
        ArrayList<String> filas = new ArrayList<String>();
        String consulta ="";

        try {

            System.out.println("Datos obtenidos correctamente4");
            consulta = "Select nombrelocal, direccion from localizaciones where nombrelocal LIKE '%" + nombrelocal + "%' OR direccion like '%" + nombrelocal + "%' " +
                    "OR descripcion like '%" + nombrelocal + "%' OR tipolocal like '%\" + nombrelocal + \"%'";
            System.out.println("Datos obtenidos correctamente1");
            s = conn.createStatement();

            rs = s.executeQuery(consulta);
            System.out.println("Datos obtenidos correctamente2");
            System.out.println("");
            while (rs.next()) {

                filas.add(rs.getString(1) + "\n\nDirección: " + rs.getString(2));

                //filas.add(rs.getString(1) + " - " + rs.getString(2) + " - "
                  //      + rs.getString(3) + " - " + rs.getString(4) + " - "
                    //    + rs.getString(5) + " - " + rs.getString(6) + " - "
                      //  + rs.getString(7) + " - " + rs.getString(8) + " - "
                        //+ rs.getString(9) + " - " + rs.getString(10));

                //linea = rs.getString(2);
            }
        } finally {
            if (s != null) {
                s.close();
                rs.close();
                conn.close();
            }
        }
        if (filas.isEmpty()){
            filas.add("Datos no encontrados");
        }

        return filas;
    }

    //Método para obtener todos los atributos de un local a través de su ID - Funcionamiento de la Actividad 3C
    //Este método se usa para mostrarle al usuario todos los datos de un local y que pueda modificarlos
    public String getLocales(Integer id) throws SQLException {
        String nombreLocal = "";
        String direccion = "";
        String descripcion = "";
        String tipoLocal = "";
        String horario = "";
        String telefono = "";
        String coordenadasGPS = "";
        String datosLocal = "";
        Integer idLocal = 0;
        String atributosLocal = "";


        String consulta = "SELECT * FROM " + nombreTabla + " WHERE id=" + id;
        //ResultSet rs = gestorBBDD.getS().executeQuery(consulta);
        s = conn.createStatement();
        s.executeUpdate(consulta);
        System.out.println("Datos obtenidos correctamente");

        while (rs.next()) {
            idLocal = rs.getInt(1);
            nombreLocal = rs.getCharacterStream(2).toString();
            direccion = rs.getString(3);
            descripcion = rs.getString(4);
            tipoLocal = rs.getString(5);
            horario = rs.getString(6);
            telefono = rs.getString(7);
            coordenadasGPS = rs.getString(8);

            //Metemos todos los datos en un único String para rellenar los campos de la actividad 3C
            datosLocal = Integer.toString(idLocal) + "-" + nombreLocal + "-" + direccion + "-" + descripcion + "-" + tipoLocal + "-" + horario + "-" + telefono + "-" + coordenadasGPS;
        }
        //No se cierra el statement por si se vuelve a ejecutar otra consulta
        return datosLocal;
    }

    //Método para obtener el ID y el NombreLocal - Funcionamiento actividad 3B
    //Este método sirve para mostrar el ID y Nombre de los locales que tiene un usuario publicados
    public ArrayList<String> getNombresLocales() throws SQLException {
        ArrayList<String> filas = new ArrayList<>();
        String nombreLocal = "";
        Integer idLocal = 0;
        String idNombreLocal = "";

        String consulta = "SELECT * FROM " + nombreTabla + " ORDER BY id ASC";
        //ResultSet rs = gestorBBDD.getS().executeQuery(consulta);
        s = conn.createStatement();
        s.executeUpdate(consulta);
        System.out.println("Datos obtenidos correctamente");

        while (rs.next()) {
            idLocal = rs.getInt(1);
            nombreLocal = rs.getCharacterStream(2).toString();
            //De esta forma añadimos en la tabla el id y el nombre de los locales
            idNombreLocal = Integer.toString(idLocal) + "-" + nombreLocal;
            filas.add(idNombreLocal);
        }
        //No se cierra el statement por si se vuelve a ejecutar otra consulta

        return filas;
    }

    //Método para crear un nuevo local
    public void insertarNuevoLocal(String nombreLocal, String direccion, String descripcion, String tipoLocal, String horario, String telefono, String coordenadasGPS) throws SQLException {
        String consulta = "INSERT INTO " + nombreTabla + " (nombreLocal, direccion, descripcion, tipoLocal, horario, telefono, coordenadasGPS) VALUES ('"
                + nombreLocal + "', '"
                + direccion + "', '"
                + descripcion + "', '"
                + tipoLocal + "', '"
                + horario + "', '"
                + telefono + "', '"
                + coordenadasGPS + "')";
        insertarModificarEliminar(consulta);
    }

    //Métodos para actualizar atributos de los locales

    // Método para actualizar el nombre del local
    public void actualizarNombreLocal(int idLocal, String nuevoNombreLocal) throws SQLException {
        String consulta = "UPDATE " + nombreTabla + " SET nombreLocal = '" + nuevoNombreLocal + "' WHERE id_Local = '" + idLocal + "'";
        insertarModificarEliminar(consulta);
    }

    // Método para actualizar la dirección
    public void actualizarDireccion(int idLocal, String nuevaDireccion) throws SQLException {
        String consulta = "UPDATE " + nombreTabla + " SET direccion = '" + nuevaDireccion + "' WHERE id_Local = '" + idLocal + "'";
        insertarModificarEliminar(consulta);
    }

    // Método para actualizar la descripción
    public void actualizarDescripcion(int idLocal, String nuevaDescripcion) throws SQLException {
        String consulta = "UPDATE " + nombreTabla + " SET descripcion = '" + nuevaDescripcion + "' WHERE id_Local = '" + idLocal + "'";
        insertarModificarEliminar(consulta);
    }

    // Método para actualizar el tipo de local (Furrancho o Pulpeiro)
    public void actualizarTipoLocal(int idLocal, String nuevoTipoLocal) throws SQLException {
        String consulta = "UPDATE " + nombreTabla + " SET tipoLocal = '" + nuevoTipoLocal + "' WHERE id_Local = '" + idLocal + "' AND (tipoLocal = 'Furrancho' OR tipoLocal = 'Pulpeiro')";
        insertarModificarEliminar(consulta);
    }

    // Método para actualizar el horario
    public void actualizarHorario(int idLocal, String nuevoHorario) throws SQLException {
        String consulta = "UPDATE " + nombreTabla + " SET horario = '" + nuevoHorario + "' WHERE id_Local = '" + idLocal + "'";
        insertarModificarEliminar(consulta);
    }

    // Método para actualizar el teléfono
    public void actualizarTelefono(int idLocal, String nuevoTelefono) throws SQLException {
        String consulta = "UPDATE " + nombreTabla + " SET telefono = '" + nuevoTelefono + "' WHERE id_Local = '" + idLocal + "'";
        insertarModificarEliminar(consulta);
    }

    // Método para actualizar las coordenadas GPS
    public void actualizarCoordenadasGPS(int idLocal, String nuevasCoordenadasGPS) throws SQLException {
        String consulta = "UPDATE " + nombreTabla + " SET coordenadasGPS = '" + nuevasCoordenadasGPS + "' WHERE id_Local = '" + idLocal + "'";
        insertarModificarEliminar(consulta);
    }
}
