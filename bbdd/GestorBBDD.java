package bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que representa un Gestor de Base de Datos en Java.
 * Proporciona métodos para conectar y desconectar de la base de datos.
 *
 * Autor: Jonay
 */
public class GestorBBDD {

    private Connection conexion;
    private Statement statemetn;

    /**
     * Método para conectar a la base de datos.
     *
     * @param url      URL de la base de datos.
     * @param usuario  Nombre de usuario para la conexión.
     * @param contrasena Contraseña para la conexión.
     */

    //Constructor
    public GestorBBDD(String url, String usuario, String contrasena) throws SQLException {
        conectarBBDD(url, usuario, contrasena);
    }
    
    public void conectarBBDD(String url, String usuario, String contrasena) {
        try {
         
            // Establecer la conexión
            conexion = DriverManager.getConnection(url, usuario, contrasena);

            if (conexion != null) {
                System.out.println("Conexión exitosa a la base de datos");
                statement = conexion.createStatement();
            }

        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    /**
     * Método para desconectar de la base de datos.
     */
    public void desconectarBBDD() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Desconexión exitosa de la base de datos");
            }
        } catch (SQLException e) {
            System.err.println("Error al desconectar de la base de datos: " + e.getMessage());
        }
    }

    // Getter para acceder al `Statement`
    public Statement getStatement() {
        return statement;
    }

    /**
     * Método principal para probar la conexión y desconexión.
     * Reemplaza los valores de url, usuario y contrasena con la información de tu base de datos.
     */
    public static void main(String[] args) {
        // Crea una instancia del GestorBBDD
        GestorBBDD gestor = new GestorBBDD();

        final String url = "jdbc:postgresql://ep-nameless-snow-71296629.eu-central-1.aws.neon.fl0.io:5432/comergallego-Alberto?sslmode=require";
        String usuario = "fl0user"; 
        String contrasena = "lpEWc0JdMgK4";
        
        // Conectar a la base de datos
        gestor.conectarBBDD(url, usuario, contrasena);

        // Desconectar de la base de datos
        gestor.desconectarBBDD();
    }
}
