package es.empresa.comergallego;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class GestorBBDD {
    Connection conn = null;

    public GestorBBDD() {
        String url = "jdbc:postgresql://ep-proud-queen-a25i44xx.eu-central-1.aws.neon.tech/comergallego";
        String user = "comergallego_owner";
        String password = "MnexL8Y1OZCc";

        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a la base de datos");
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            // You could rethrow the exception or handle it based on your application needs
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    //Método para testear la conexión
    public boolean conectarBBDD( String url, String user, String password){
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a la base de datos");
            return true;
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            // You could rethrow the exception or handle it based on your application needs
            return false;
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
