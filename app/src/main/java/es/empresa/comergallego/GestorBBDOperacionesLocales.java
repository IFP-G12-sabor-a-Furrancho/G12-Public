package es.empresa.comergallego;

import java.sql.*;
import java.util.ArrayList;

public class GestorBBDOperacionesLocales {
    private String url = "jdbc:postgresql://ep-shy-glade-57906898.eu-central-1.aws.neon.fl0.io:5432/comergallego";
    private String user = "fl0user";
    private String password = "8Zizcvy1rMhs";
    private Connection conn = null;
    private Statement s = null;
    private ResultSet rs = null;
    private static String nombreTabla = "localizaciones";

    public GestorBBDOperacionesLocales() throws SQLException {
        conn = DriverManager.getConnection(url, user, password);
        if (conn != null) {
            System.out.println("Conexión exitosa a la base de datos");
        }
    }

    public ArrayList<Local> consulta(String nombrelocal) throws SQLException {
        ArrayList<Local> locales = new ArrayList<>();
        String consulta = "SELECT id, nombrelocal, direccion, telefono FROM " + nombreTabla +
                " WHERE nombrelocal LIKE '%" + nombrelocal + "%'" +
                " OR direccion LIKE '%" + nombrelocal + "%'" +
                " OR descripcion LIKE '%" + nombrelocal + "%'" +
                " OR tipolocal LIKE '%" + nombrelocal + "%'";
        try {
            s = conn.createStatement();
            rs = s.executeQuery(consulta);
            while (rs.next()) {
                Local local = new Local(
                        rs.getInt("id"), // Asegúrate de que esta columna exista en tu tabla.
                        rs.getString("nombrelocal"),
                        rs.getString("direccion"),
                        rs.getString("telefono") // Asegúrate de que estas columnas existan en tu tabla.
                );
                locales.add(local);
            }
        } finally {
            if (s != null) { s.close(); }
            if (rs != null) { rs.close(); }
        }
        return locales;
    }

    public void insertarLocal(Local local) throws SQLException {
        String sql = "INSERT INTO " + nombreTabla + " (nombrelocal, direccion, telefono) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, local.getNombre());
            pstmt.setString(2, local.getDireccion());
            pstmt.setString(3, local.getTelefono());
            pstmt.executeUpdate();
        }
    }

    public void modificarLocal(Local local) throws SQLException {
        String sql = "UPDATE " + nombreTabla + " SET nombrelocal = ?, direccion = ?, telefono = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, local.getNombre());
            pstmt.setString(2, local.getDireccion());
            pstmt.setString(3, local.getTelefono());
            pstmt.setInt(4, local.getId());
            pstmt.executeUpdate();
        }
    }

    public void eliminarLocal(int id) throws SQLException {
        String sql = "DELETE FROM " + nombreTabla + " WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public Local obtenerDetalleLocal(int id) throws SQLException {
        String sql = "SELECT * FROM " + nombreTabla + " WHERE id = ?";
        Local local = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                local = new Local(
                        rs.getInt("id"),
                        rs.getString("nombrelocal"),
                        rs.getString("direccion"),
                        rs.getString("telefono")
                );
            }
        }
        return local;
    }
}
