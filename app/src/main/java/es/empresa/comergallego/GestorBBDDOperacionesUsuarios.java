package es.empresa.comergallego;

import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class GestorBBDDOperacionesUsuarios {

    static String nombreTabla = "usuarios";
    Statement s=null;
    ResultSet rs=null;
    String url = "jdbc:postgresql://ep-shy-glade-57906898.eu-central-1.aws.neon.fl0.io:5432/comergallego";
    String user = "fl0user";
    String password = "8Zizcvy1rMhs";
    Connection conn=null;



    public GestorBBDDOperacionesUsuarios () throws SQLException {
        conn = DriverManager.getConnection(url, user, password);
        if (conn != null) {
            System.out.println("Conexión exitosa a la base de datos");
        }
    }

    public static void insertarModificarEliminar(String consulta, Statement s) throws SQLException {
        try {
            s.executeUpdate(consulta);
            System.out.println("Operacion realizada correctamente");
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

    public static boolean insertarUsuario(Usuarios u, GestorBBDD bd) throws SQLException {
        Connection conn = null;
        PreparedStatement statement=null;
        try {
        conn = bd.conn;
        String sql = "INSERT INTO usuarios (nombreusuario, nombre, apellidos, fechanacimiento, correoelectronico, password, rolusuario) VALUES(?,?,?,?,?,?,?)";
        statement = conn.prepareStatement(sql);
        statement.setString(1,u.getNombreUsuario());
        statement.setString(2,u.getNombre());
        statement.setString(3,u.getApellido());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date fechaNacimiento =  sdf.parse(u.getFechaNacimiento());
            java.sql.Date fechaNacimientoSql = new java.sql.Date(fechaNacimiento.getTime());
            statement.setDate(4,fechaNacimientoSql);
            statement.setString(5,u.getCorreoElectronico());
            statement.setString(6,u.getPassword());
            statement.setString(7, String.valueOf(u.getRolUsuario()));
            //controlar si la inserccion fue exitosa
            int filasInsertadas = statement.executeUpdate();
            return filasInsertadas >0;

        } catch (ParseException e) {
            // Manejar el error de análisis de fecha
            e.printStackTrace();
            // Lanzar una excepción o manejar el error de alguna otra manera apropiada
            return false;
        }finally {
            // Cerrar la conexión y el PreparedStatement en el bloque finally
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }



    }

    public static boolean consultarLogin(String nombreUsuario, String contrasena, GestorBBDD bd) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            conn = bd.conn; // Obtener la conexión almacenada en bd
            String sql = "SELECT COUNT(*) FROM usuarios WHERE nombreusuario = ? AND password = ?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, nombreUsuario);
            statement.setString(2, contrasena);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Devuelve true si se encontró una coincidencia
            }
            return false;
        } catch (SQLException e) {
            // Manejar el error
            e.printStackTrace();
            return false;
        } finally {

            // Cerrar la conexión, el PreparedStatement y el ResultSet en el bloque finally
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }


    // Método para consultar usuarios
    public static void consulta(String consulta, Statement s) throws SQLException {
        try {
            ResultSet rs = s.executeQuery(consulta);
            System.out.println("Datos obtenidos correctamente");
            System.out.println("");
            while (rs.next()) {
                System.out.println(rs.getString("id_Usuario") + " - " + rs.getString("nombreUsuario") + " - "
                        + rs.getString("nombre") + " - " + rs.getString("apellido") + " - "
                        + rs.getString("fechaNacimiento")+ " - " + rs.getString("correoElectronico") + " - " + rs.getString("password") + " - " + rs.getString("historialBusquedas") + " - " + rs.getString("ubicacionActual") + " - " + rs.getString("idiomaPreferido") + " - " + rs.getString("rolUsuario"));
            }
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

    public String consultaAdministrador(String nombreUsuario) throws SQLException {
        String rol = "";

        String consulta = "SELECT rolusuario FROM " + nombreTabla + " WHERE nombreusuario = ?";
        PreparedStatement statement = conn.prepareStatement(consulta);
        statement.setString(1, nombreUsuario);

        try (ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                rol = rs.getString("rolusuario");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rol;
    }

}


