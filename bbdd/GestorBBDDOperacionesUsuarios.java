package bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class GestorBBDDOperacionesUsuarios {

	public static void main(String[] args) {

		// Importamos el método scanner para introducir datos por pantalla
		Scanner entradaDatos = new Scanner(System.in);

		// Declaración de variables
		int opcion = 1;
		int numeroCampos = 11;
		int i = 0;
		int j = 0;
		String parentesisConsulta = "";
		String nombreTabla = "usuarios";
		String valorCampo = "";
		String consulta = "";
		String tipoCampo = "";

		final String DBURL = "jdbc:postgresql://ep-nameless-snow-71296629.eu-central-1.aws.neon.fl0.io:5432/comergallego-Alberto?sslmode=require";
		Connection conexion = null;
		try {

			// Ruta de conexión indicando la ruta y el nombre de la base de datos, el
			// usuario y la contraseña
			conexion = DriverManager.getConnection(DBURL, "fl0user", "lpEWc0JdMgK4");

			// Mostramos un mensaje de confirmación de que la conexión se ha establecido con
			// éxito
			System.out.println("Conexión establecida");

			while (opcion != 0) {

				// Pedimos al usuario que opción desea realizar
				System.out.println("");
				System.out.println("");
				System.out.println("Por favor escoja que opcion desea realizar:");
				System.out.println("");
				System.out.println("0 - Salir");
				System.out.println("1 - Consultar usuarios");
				System.out.println("2 - Agregar usuario");
				System.out.println("3 - Modificar usuario");
				System.out.println("4 - Eliminar usuario");
				System.out.println("");
				System.out.println("");
				opcion = entradaDatos.nextInt();
				

				// En el caso de que escoja la opción 0 (Salir), el programa finaliza

				// Consultar usuarios

				if (opcion == 1) {

					// Establecemos un canal de comunicación de consultas a SQL
					Statement s = (Statement) conexion.createStatement();

					// Damos al usuario la opcion de escoger si quiere leer una fila o la tabla
					// entera
					System.out.println("Ha escogido la opcion consultar usuarios");
					System.out.println("Desea leer una fila o una tabla, por favor escoja una opcion:");
					System.out.println("1 - Leer fila");
					System.out.println("2 - Leer tabla");
					opcion = entradaDatos.nextInt();
					if (opcion == 1) {
						// Leer fila, en este caso el usuario debe de indicar el nombre de la fila
						entradaDatos.nextLine();
						System.out.println(
								"Para leer una fila debe de indicar que condición se debe de cumplir, indicando el nombre de de uno de los campos y el valor del campo");
						System.out.println("Por favor, indique el nombre del campo:");
						tipoCampo = entradaDatos.nextLine();
						System.out.println("Por favor, indique el valor del campo:");
						valorCampo = entradaDatos.nextLine();
						//Agregamos comillas simples alrededor de valorCampo para indicar a PostgreSQL que se trata de una cadena de texto
						consulta = "SELECT * FROM " + nombreTabla + " WHERE " + tipoCampo + " = '" + valorCampo +"'";
						System.out.println(consulta);
					} else {
						// Leer tabla, en este caso el usuario no debe de indicar nada
						consulta = "SELECT * FROM " + nombreTabla;
					}
					/*
					 * Tras tener los datos suficientes para realizar la consulta, llamamos al
					 * metodo necesario para realizar la consulta
					 */
					consulta(consulta, s);
				}

				// Agregar usuario

				// Se tiene en cuenta que id_Usuario no es autoincremental y se debe de
				// introducir su valor a mano
				else if (opcion == 2) {
					entradaDatos.nextLine();

					// Establecemos un canal de comunicación de consultas a SQL
					Statement s = (Statement) conexion.createStatement();

					System.out.println("Ha escogido la opcion agregar usuario");
					parentesisConsulta = "";

					// Indicamos los nombres de los campos
					String[] nombreCampo = new String[numeroCampos];
					nombreCampo[0] = "id_usuario";
					nombreCampo[1] = "nombreUsuario";
					nombreCampo[2] = "nombre";
					nombreCampo[3] = "apellido";
					nombreCampo[4] = "fechaNacimiento";
					nombreCampo[5] = "correoElectronico";
					nombreCampo[6] = "password";
					nombreCampo[7] = "historialBusquedas";
					nombreCampo[8] = "ubicacionActual";
					nombreCampo[9] = "idiomaPreferido";
					nombreCampo[10] = "rolUsuario";

					i = 1;
					for (j = 0; j < numeroCampos; j++) {
						if (j == numeroCampos - 1) {
							System.out.println("Introduzca el valor del campo " + nombreCampo[j] + ":");
							valorCampo = entradaDatos.nextLine();
							parentesisConsulta = parentesisConsulta + "'" + valorCampo + "'";
						} else {
							System.out.println("Introduzca el valor del campo " + nombreCampo[j] + ":");
							valorCampo = entradaDatos.nextLine();
							parentesisConsulta = parentesisConsulta + "'" + valorCampo + "'" + ",";
						}
						i++;
					}
					parentesisConsulta = "(" + parentesisConsulta + ")";
					consulta = "INSERT INTO USUARIOS VALUES " + parentesisConsulta;
					System.out.println("La consulta para insertar los datos es: " + consulta);
					System.out.println();
					insertarModificarEliminar(consulta, s);
				}

				// Modificar usuario

				else if (opcion == 3) {
					// Establecemos un canal de comunicación de consultas a SQL
					Statement s = (Statement) conexion.createStatement();

					System.out.println("Ha escogido la opcion modificar");

					consulta = "UPDATE " + nombreTabla;

					System.out.println("¿Cuántos campos desea modificar?");
					numeroCampos = entradaDatos.nextInt();
					entradaDatos.nextLine();
					parentesisConsulta = "";

					String[] nombreCampo = new String[numeroCampos];
					i = 1;
					for (j = 0; j < numeroCampos; j++) {
						if (j == numeroCampos - 1) {
							System.out.println("Introduzca el nombre del campo " + i + " que desea modificar:");
							nombreCampo[j] = entradaDatos.nextLine();
							System.out.println("Introduzca el nuevo valor del campo " + nombreCampo[j] + ":");
							valorCampo = entradaDatos.nextLine();
							//Se debe de modificar para que se respeten las mayúsculas (Borrar comilla simple antes y después de nombreCampo[j]
							parentesisConsulta = parentesisConsulta + "\"" + nombreCampo[j] + "\"" + "=" + "'" + valorCampo + "'";
						} else {
							System.out.println("Introduzca el nombre del campo " + i + " que desea modificar:");
							nombreCampo[j] = entradaDatos.nextLine();
							System.out.println("Introduzca el nuevo valor del campo " + nombreCampo[j] + ":");
							valorCampo = entradaDatos.nextLine();
							//Se debe de modificar para que se respeten las mayúsculas
							parentesisConsulta = parentesisConsulta +  "\"" + nombreCampo[j] + "\"" + "=" + "'" + valorCampo + "'"
									+ ",";
						}
						i++;
					}
					consulta = consulta + " SET " + parentesisConsulta;

					System.out.println(
							"Para modificar una fila debe de indicar el nombre de de uno de los campos y el valor del campo");
					System.out.println("Por favor, indique el nombre del campo:");
					tipoCampo = entradaDatos.nextLine();
					System.out.println("Por favor, indique el valor del campo:");
					valorCampo = entradaDatos.nextLine();
					consulta = consulta + " WHERE " +  "\"" + tipoCampo + "\"" + " = '" + valorCampo + "'";
					System.out.println("La consulta para insertar los datos es: " + consulta);
					insertarModificarEliminar(consulta, s);
				}
				// Eliminar usuario

				else if (opcion == 4) {
					// Establecemos un canal de comunicación de consultas a SQL
					Statement s = (Statement) conexion.createStatement();

					System.out.println("Ha escogido la opcion eliminar datos");
					System.out.println("Desea eliminar sólo una fila o la tabla entera, por favor escoja una opcion:");
					System.out.println("1 - Eliminar fila");
					System.out.println("2 - Eliminar tabla");
					opcion = entradaDatos.nextInt();
					parentesisConsulta = "";
					if (opcion == 1) {
						// Eliminar fila
						System.out.println(
								"Para eliminar una fila debe de indicar el nombre de de uno de los campos y el valor del campo");
						System.out.println("Por favor, indique el nombre del campo:");
						tipoCampo = entradaDatos.nextLine();
						System.out.println("Por favor, indique el valor del campo:");
						valorCampo = entradaDatos.nextLine();
						consulta = "DELETE FROM " + nombreTabla + " WHERE " + tipoCampo + " = '" + valorCampo + "'";
						System.out.println("La consulta para eliminar la fila es: " + consulta);
						insertarModificarEliminar(consulta, s);
					} else {
						// Eliminar tabla
						System.out.println("¿Está seguro que desea borrar la tabla entera?");
						System.out.println("1 - Sí");
						System.out.println("2 - No");
						opcion = entradaDatos.nextInt();

						if (opcion == 1) {
							consulta = "DROP TABLE " + nombreTabla;
							System.out.println("La consulta para eliminar la tabla es: " + consulta);
							insertarModificarEliminar(consulta, s);
						} else {
							System.out.println("De acuerdo, no se ha borrado nada");
						}
					}

				}
			}

			// Finalizamos la conexión
			conexion.close();

			// Mostramos un mensaje de confirmación de que la conexión se ha cerrado con
			// éxito
			System.out.println("Conexión cerrada con exito");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("No se ha podido establecer la conexión correctamente");
			e.printStackTrace();
		}

	}

	// Métodos

	// Método para crear, modificar y eliminar usuarios
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

}
