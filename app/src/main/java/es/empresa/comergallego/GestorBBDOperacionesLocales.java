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
    Statement s=null;
    ResultSet rs=null;
    public GestorBBDOperacionesLocales() throws SQLException {
        conn = DriverManager.getConnection(url, user, password);
        if (conn != null) {
            System.out.println("Conexión exitosa a la base de datos");
        }
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

    public ArrayList<String> consulta(String nombrelocal) throws SQLException {
        //String linea="";
        ArrayList<String> filas = new ArrayList<String>();
        String consulta ="";

        try {

            System.out.println("Datos obtenidos correctamente4");
            consulta = "Select * from localizaciones where nombrelocal='" + nombrelocal + "'";
            System.out.println("Datos obtenidos correctamente1");
            s = conn.createStatement();

            rs = s.executeQuery(consulta);
            System.out.println("Datos obtenidos correctamente2");
            System.out.println("");
            while (rs.next()) {
                filas.add(rs.getInt(1) + " - " + rs.getString(2) + " - "
                        + rs.getString(3) + " - " + rs.getString(4) + " - "
                        + rs.getString(5) + " - " + rs.getString(6) + " - "
                        + rs.getString(7) + " - " + rs.getString(8) + " - "
                        + rs.getInt(9) + " - " + rs.getString(10));

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
}
