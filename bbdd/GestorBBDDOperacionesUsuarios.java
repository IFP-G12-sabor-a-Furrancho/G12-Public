package es.empresa.comergallego;

import java.sql.SQLException;
import java.sql.Statement;

public class GestorBBDDOperacionesUsuarios {

	public GestorBBDDOperacionesUsuarios() {
	}

	// Método genérico para insertar, modificar, eliminar
	public static void insertarModificarEliminar(String consulta, Statement s) throws SQLException {
		try {
			s.executeUpdate(consulta);
			System.out.println("Operación realizada correctamente");
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	// Método para consultar usuarios
	public static void consulta(String consulta, Statement s) throws SQLException {
		// Implementación del método...
	}

	// Métodos específicos para actualizar campos de Usuarios
	public static void actualizarNombreUsuario(String idUsuario, String nuevoNombreUsuario, Statement s) throws SQLException {
		String consulta = "UPDATE Usuarios SET nombreUsuario = '" + nuevoNombreUsuario + "' WHERE id_Usuario = '" + idUsuario + "'";
		insertarModificarEliminar(consulta, s);
	}

	public static void actualizarNombre(String idUsuario, String nuevoNombre, Statement s) throws SQLException {
		String consulta = "UPDATE Usuarios SET nombre = '" + nuevoNombre + "' WHERE id_Usuario = '" + idUsuario + "'";
		insertarModificarEliminar(consulta, s);
	}

	public static void actualizarApellido(String idUsuario, String nuevoApellido, Statement s) throws SQLException {
		String consulta = "UPDATE Usuarios SET apellido = '" + nuevoApellido + "' WHERE id_Usuario = '" + idUsuario + "'";
		insertarModificarEliminar(consulta, s);
	}

	public static void actualizarFechaNacimiento(String idUsuario, String nuevaFechaNacimiento, Statement s) throws SQLException {
		String consulta = "UPDATE Usuarios SET fechaNacimiento = '" + nuevaFechaNacimiento + "' WHERE id_Usuario = '" + idUsuario + "'";
		insertarModificarEliminar(consulta, s);
	}

	public static void actualizarCorreoElectronico(String idUsuario, String nuevoCorreoElectronico, Statement s) throws SQLException {
		String consulta = "UPDATE Usuarios SET correoElectronico = '" + nuevoCorreoElectronico + "' WHERE id_Usuario = '" + idUsuario + "'";
		insertarModificarEliminar(consulta, s);
	}
	public static void actualizarContraseña(String idUsuario, String nuevaContraseña, Statement s) throws SQLException {
		String consulta = "UPDATE Usuarios SET password = '" + nuevaContraseña + "' WHERE id_Usuario = '" + idUsuario + "'";
		insertarModificarEliminar(consulta, s);
	}

}
