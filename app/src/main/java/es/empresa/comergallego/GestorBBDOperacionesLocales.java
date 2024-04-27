package es.empresa.comergallego;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class GestorBBDOperacionesLocales{
    //String url = "jdbc:postgresql://ep-shy-glade-57906898.eu-central-1.aws.neon.fl0.io:5432/comergallego";
    //String user = "fl0user";
    //String password = "8Zizcvy1rMhs";

    String url = "jdbc:postgresql://ep-proud-queen-a25i44xx.eu-central-1.aws.neon.tech/comergallego";
    String user = "comergallego_owner";
    String password = "MnexL8Y1OZCc";
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

    public Local getLocalDetallesById(String localId) throws SQLException {
        Local local = null;
        String sql = "SELECT id_localizacion, nombrelocal, direccion, descripcion, tipoLocal, horario, telefono, coordenadasgps FROM localizaciones WHERE nombrelocal = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, localId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            local = new Local();
            local.setId(rs.getInt("id_localizacion"));  // Asegurándose que el ID es un entero y está alineado con la base de datos
            local.setNombre(rs.getString("nombrelocal"));
            local.setDireccion(rs.getString("direccion"));
            local.setDescripcion(rs.getString("descripcion"));
            local.setTipoLocal(rs.getString("tipoLocal"));
            local.setHorario(rs.getString("horario"));
            local.setTelefono(rs.getString("telefono"));
            local.setCoordenadasGPS(rs.getString("coordenadasgps"));
        }
        rs.close();
        stmt.close();

        return local;
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

                filas.add(rs.getString(1) + "\nDirección: " + rs.getString(2));

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
        String datosLocal = "";

        String consulta = "SELECT * FROM " + nombreTabla + " WHERE id_localizacion=?";

        // Utilizar try-with-resources para asegurar que los recursos se cierran correctamente
        try (PreparedStatement statement = conn.prepareStatement(consulta)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Integer idLocal = rs.getInt("id_localizacion");
                    String nombreLocal = rs.getString("nombrelocal");
                    String direccion = rs.getString("direccion");
                    String descripcion = rs.getString("descripcion");
                    String tipoLocal = rs.getString("tipolocal");
                    String horario = rs.getString("horario");
                    String telefono = rs.getString("telefono");
                    String coordenadasGPS = rs.getString("coordenadasgps");

                    // Formar el string con los datos separados por "-"
                    datosLocal = idLocal + "-.-" + nombreLocal + "-.-" + direccion + "-.-" + descripcion + "-.-" + tipoLocal + "-.-" + horario + "-.-" + telefono + "-.-" + coordenadasGPS;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los datos del local: " + e.getMessage());
            // Manejar la excepción según la lógica de tu aplicación
        }

        return datosLocal;
    }

    //Método para las pruebas de testing
    public int numeroRegistrosUsuarioAdministrador(int idUsuario) throws SQLException {
        int numeroRegistros=0;
        // Asumimos que 'conn' es una conexión válida a tu base de datos

        //String consulta = "SELECT id_localizacion, nombrelocal FROM " + nombreTabla + " ORDER BY id_localizacion ASC";

        String consulta = "SELECT l.*\n" +
                "FROM localizaciones l\n" +
                "INNER JOIN usuarios_localizaciones ul ON l.id_localizacion = ul.id_localizacion\n" +
                "INNER JOIN usuarios u ON ul.id_usuario = u.id_usuario\n" +
                "WHERE u.id_usuario = "+idUsuario;

        // Utilizar try-with-resources para asegurar que los recursos se cierran correctamente
        try (Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(consulta)) {
            while (rs.next()) {
              numeroRegistros++;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los nombres de los locales: " + e.getMessage());
            // Considera lanzar la excepción o manejarla según la lógica de tu aplicación
        }
        return numeroRegistros;
    }


    //Método para obtener el ID y el NombreLocal - Funcionamiento actividad 3B
    //Este método sirve para mostrar el ID y Nombre de los locales que tiene un usuario publicados

    public ArrayList<String> getNombresLocales(int idUsuario) throws SQLException {
        ArrayList<String> filas = new ArrayList<>();
        // Asumimos que 'conn' es una conexión válida a tu base de datos

        //String consulta = "SELECT id_localizacion, nombrelocal FROM " + nombreTabla + " ORDER BY id_localizacion ASC";

        String consulta = "SELECT l.*\n" +
                "FROM localizaciones l\n" +
                "INNER JOIN usuarios_localizaciones ul ON l.id_localizacion = ul.id_localizacion\n" +
                "INNER JOIN usuarios u ON ul.id_usuario = u.id_usuario\n" +
                "WHERE u.id_usuario = "+idUsuario;

        // Utilizar try-with-resources para asegurar que los recursos se cierran correctamente
        try (Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(consulta)) {
            while (rs.next()) {
                int idLocal = rs.getInt("id_localizacion");
                String nombreLocal = rs.getString("nombrelocal"); // Usar getString para obtener el nombre
                String idNombreLocal = idLocal + "-" + nombreLocal;
                filas.add(idNombreLocal);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los nombres de los locales: " + e.getMessage());
            // Considera lanzar la excepción o manejarla según la lógica de tu aplicación
        }
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
        String consulta = "UPDATE " + nombreTabla + " SET nombreLocal = '" + nuevoNombreLocal + "' WHERE id_localizacion = '" + idLocal + "'";
        insertarModificarEliminar(consulta);
    }

    // Método para actualizar la dirección
    public void actualizarDireccion(int idLocal, String nuevaDireccion) throws SQLException {
        String consulta = "UPDATE " + nombreTabla + " SET direccion = '" + nuevaDireccion + "' WHERE id_localizacion = '" + idLocal + "'";
        insertarModificarEliminar(consulta);
    }

    // Método para actualizar la descripción
    public void actualizarDescripcion(int idLocal, String nuevaDescripcion) throws SQLException {
        String consulta = "UPDATE " + nombreTabla + " SET descripcion = '" + nuevaDescripcion + "' WHERE id_localizacion = '" + idLocal + "'";
        insertarModificarEliminar(consulta);
    }

    // Método para actualizar el tipo de local (Furrancho o Pulpeiro)
    public void actualizarTipoLocal(int idLocal, String nuevoTipoLocal) throws SQLException {
        //String consulta = "UPDATE " + nombreTabla + " SET tipolocal = '" + nuevoTipoLocal + "' WHERE id_localizacion = '" + idLocal + "' AND (tipoLocal = 'Furrancho' OR tipoLocal = 'Pulpeiro')";
        String consulta = "UPDATE " + nombreTabla + " SET tipolocal = '" + nuevoTipoLocal + "' WHERE id_localizacion = '" + idLocal + "'";
        insertarModificarEliminar(consulta);
    }

    // Método para actualizar el horario
    public void actualizarHorario(int idLocal, String nuevoHorario) throws SQLException {
        String consulta = "UPDATE " + nombreTabla + " SET horario = '" + nuevoHorario + "' WHERE id_localizacion = '" + idLocal + "'";
        insertarModificarEliminar(consulta);
    }

    // Método para actualizar el teléfono
    public void actualizarTelefono(int idLocal, String nuevoTelefono) throws SQLException {
        String consulta = "UPDATE " + nombreTabla + " SET telefono = '" + nuevoTelefono + "' WHERE id_localizacion = '" + idLocal + "'";
        insertarModificarEliminar(consulta);
    }

    // Método para actualizar las coordenadas GPS
    public void actualizarCoordenadasGPS(int idLocal, String nuevasCoordenadasGPS) throws SQLException {
        String consulta = "UPDATE " + nombreTabla + " SET coordenadasGPS = '" + nuevasCoordenadasGPS + "' WHERE id_localizacion = '" + idLocal + "'";
        insertarModificarEliminar(consulta);
    }

    public void insertarTablaIntermedia(int idUsuario) throws SQLException {
        int idLocalizacion=0;

        try (PreparedStatement statementId = conn.prepareStatement("SELECT currval('localizaciones_id_localizacion_seq')")) {
            try (ResultSet rs = statementId.executeQuery()) {
                rs.next();
                idLocalizacion = rs.getInt(1);
            }
        }
        // Inserta la relación entre el usuario y el local en la tabla intermedia usuarios_localizaciones
        String consultaRelacion = "INSERT INTO usuarios_localizaciones (id_usuario, id_localizacion) VALUES (?, ?)";
        try (PreparedStatement statementRelacion = conn.prepareStatement(consultaRelacion)) {
            statementRelacion.setInt(1, idUsuario);
            statementRelacion.setInt(2, idLocalizacion);
            statementRelacion.executeUpdate();
        }

        System.out.println("Local insertado correctamente y asociado al usuario.");
    }
}
