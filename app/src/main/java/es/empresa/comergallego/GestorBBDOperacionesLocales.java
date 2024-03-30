package es.empresa.comergallego;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
public class GestorBBDOperacionesLocales {

    public GestorBBDOperacionesLocales() {

    }

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

    // Método para consultar usuarios
    public static void consulta(String consulta, Statement s) throws SQLException {
        try {
            ResultSet rs = s.executeQuery(consulta);
            System.out.println("Datos obtenidos correctamente");
            System.out.println("");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " - " + rs.getString(2) + " - "
                        + rs.getString(3) + " - " + rs.getString(4) + " - "
                        + rs.getString(5) + " - " + rs.getString(6) + " - "
                        + rs.getString(7) + " - " + rs.getString(8) + " - "
                        + rs.getInt(9) + " - " + rs.getString(10));
            }
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
}
