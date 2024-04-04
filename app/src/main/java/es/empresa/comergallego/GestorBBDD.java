package es.empresa.comergallego;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class GestorBBDD {

    //static Statement s=null;
   Connection conn=null;

    public GestorBBDD() throws SQLException {

        String url = "jdbc:postgresql://ep-shy-glade-57906898.eu-central-1.aws.neon.fl0.io:5432/comergallego";
        String user = "fl0user";
        String password = "8Zizcvy1rMhs";

        conn= DriverManager.getConnection(url, user, password);
        if (conn != null) {
            System.out.println("Conexión exitosa a la base de datos");
            //Revisar el Statement
            // s = conn.createStatement();
        }
    }

    public void desconectarBBDD() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Desconexión exitosa de la base de datos");
            }
        } catch (SQLException e) {
            System.err.println("Error al desconectar de la base de datos: " + e.getMessage());
        }
    }

    //SE HA AÑADIDO EL GETTER PARA ACCEDER AL STATEMENT
    // Getter para acceder al `Statement`
    //public Statement getS() {
        //return s;
    //}
}
