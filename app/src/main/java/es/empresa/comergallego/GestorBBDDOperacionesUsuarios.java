package es.empresa.comergallego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GestorBBDDOperacionesUsuarios {

    public GestorBBDDOperacionesUsuarios () {

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

    public static void insertarUsuario(Usuarios u, Connection bd) throws SQLException {
        String sql = "INSERT INTO usuarios (nombreusuario, nombre, apellidos, fechanacimiento, correoelectronico, password, rolusuario) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement statement = bd.prepareStatement(sql);
        statement.setString(1,u.getNombreUsuario());
        statement.setString(2,u.getNombre());
        statement.setString(3,u.getApellido());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date fechaNacimiento = null;
        try {
            fechaNacimiento = sdf.parse(u.getFechaNacimiento());
        } catch (ParseException e) {
            // Manejar el error de análisis de fecha
            e.printStackTrace();
            // Lanzar una excepción o manejar el error de alguna otra manera apropiada
        }
        java.sql.Date fechaNacimientoSql = new java.sql.Date(fechaNacimiento.getTime());
        statement.setDate(4,fechaNacimientoSql);
        statement.setString(5,u.getCorreoElectronico());
        statement.setString(6,u.getPassword());
        statement.setString(7, String.valueOf(u.getRolUsuario()));
        statement.executeUpdate();


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
}


