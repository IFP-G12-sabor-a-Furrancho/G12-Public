package es.empresa.comergallego;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

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

    // Implementación del método actualizarNombre
    public static void actualizarNombre(String idUsuario, String nuevoNombre, Statement s) throws SQLException {
        String consulta = "UPDATE usuarios SET nombre = '" + nuevoNombre + "' WHERE id_Usuario = '" + idUsuario + "'";
        insertarModificarEliminar(consulta, s);
    }

    // Implementación del método actualizarApellido
    public static void actualizarApellido(String idUsuario, String nuevoApellido, Statement s) throws SQLException {
        String consulta = "UPDATE usuarios SET apellido = '" + nuevoApellido + "' WHERE id_Usuario = '" + idUsuario + "'";
        insertarModificarEliminar(consulta, s);
    }

    // Implementación del método actualizarCorreoElectronico
    public static void actualizarCorreoElectronico(String idUsuario, String nuevoCorreo, Statement s) throws SQLException {
        String consulta = "UPDATE usuarios SET correoElectronico = '" + nuevoCorreo + "' WHERE id_Usuario = '" + idUsuario + "'";
        insertarModificarEliminar(consulta, s);
    }
}

