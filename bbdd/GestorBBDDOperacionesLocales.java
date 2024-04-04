package bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.ArrayList;

/* Se debe de revisar el cierre de ResulSet (rs.close)
Se debe de añadir el JDBC de Postgre en el Manifest.xml*/

public class GestorBBDDOperacionesLocales {

	public static void main(String[] args) {

	//Código Gestor BBDDOperacionesLocales actualizado
		GestorBBDD gestorBBDD;
    //Definición de atributos
    static String nombreTabla = "localizaciones";

    //Constructor
    public GestorBBDDOperacionesLocales(String url, String usuario, String contrasena) throws SQLException {
        this.gestorBBDD = new GestorBBDD(url, usuario, contrasena);
    }

    // Método genérico para insertar, modificar, eliminar
    public void insertarModificarEliminar(String consulta) throws SQLException {
            gestorBBDD.getStatement().executeUpdate(consulta);
            System.out.println("Operación realizada correctamente");
    }

    public String getLocales(Integer id) throws SQLException {
        String nombreLocal = "";
        String direccion = "";
        String descripcion = "";
        String tipoLocal = "";
        String horario = "";
        String telefono = "";
        String coordenadasGPS = "";
        String datosLocal = "";
        Integer idLocal = 0;
        String atributosLocal = "";


            String consulta = "SELECT * FROM " + nombreTabla + " WHERE id=" + id;
            ResultSet rs = gestorBBDD.getStatement().executeQuery(consulta);
            System.out.println("Datos obtenidos correctamente");

            while (rs.next()) {
                idLocal = rs.getInt(1);
                nombreLocal = rs.getCharacterStream(2).toString();
                direccion = rs.getString(3);
                descripcion = rs.getString(4);
                tipoLocal = rs.getString(5);
                horario = rs.getString(6);
                telefono = rs.getString(7);
                coordenadasGPS = rs.getString(8);

                //Metemos todos los datos en un único String para rellenar los campos de la actividad 3C
                datosLocal = Integer.toString(idLocal) + "-" + nombreLocal + "-" + direccion + "-" + descripcion + "-" + tipoLocal + "-" + horario + "-" + telefono + "-" + coordenadasGPS;
            }
            //No se cierra el statement por si se vuelve a ejecutar otra consulta
        return datosLocal;
    }
		public ArrayList<String> getNombresLocales() throws SQLException {
        ArrayList<String> filas = new ArrayList<>();
        String nombreLocal = "";
        Integer idLocal = 0;
        String idNombreLocal = "";

            String consulta = "SELECT * FROM " + nombreTabla + " ORDER BY id ASC";
            ResultSet rs = gestorBBDD.getStatement().executeQuery(consulta);
            System.out.println("Datos obtenidos correctamente");

            while (rs.next()) {
                idLocal = rs.getInt(1);
                nombreLocal = rs.getCharacterStream(2).toString();
                //De esta forma añadimos en la tabla el id y el nombre de los locales
                idNombreLocal = Integer.toString(idLocal) + "-" + nombreLocal;
                filas.add(idNombreLocal);
            }
       //No se cierra el statement por si se vuelve a ejecutar otra consulta

        return filas;
    }


    //Métodos para crear un nuevo local

    public void insertarNuevoLocal(String nombreLocal, String direccion, String descripcion, String tipoLocal, String horario, String telefono, String coordenadasGPS) throws SQLException {
        String consulta = "INSERT INTO " + nombreTabla + " (nombreLocal, direccion, descripcion, tipoLocal, horario, telefono, coordenadasGPS) VALUES ('"
                + nombreLocal + "', '"
                + direccion + "', '"
                + descripcion + "', '"
                + tipoLocal + "', '"
                + horario + "', '"
                + telefono + "', '"
                + coordenadasGPS + "')";
        insertarModificarEliminar(consulta);
    }


    //Métodos para actualizar atributos de los locales

    // Método para actualizar el nombre del local
    public void actualizarNombreLocal(int idLocal, String nuevoNombreLocal) throws SQLException {
        String consulta = "UPDATE " + nombreTabla + " SET nombreLocal = '" + nuevoNombreLocal + "' WHERE id_Local = '" + idLocal + "'";
        insertarModificarEliminar(consulta);
    }

    // Método para actualizar la dirección
    public void actualizarDireccion(int idLocal, String nuevaDireccion) throws SQLException {
        String consulta = "UPDATE " + nombreTabla + " SET direccion = '" + nuevaDireccion + "' WHERE id_Local = '" + idLocal + "'";
        insertarModificarEliminar(consulta);
    }

    // Método para actualizar la descripción
    public void actualizarDescripcion(int idLocal, String nuevaDescripcion) throws SQLException {
        String consulta = "UPDATE " + nombreTabla + " SET descripcion = '" + nuevaDescripcion + "' WHERE id_Local = '" + idLocal + "'";
        insertarModificarEliminar(consulta);
    }

    // Método para actualizar el tipo de local (Furrancho o Pulpeiro)
    public void actualizarTipoLocal(int idLocal, String nuevoTipoLocal) throws SQLException {
        String consulta = "UPDATE " + nombreTabla + " SET tipoLocal = '" + nuevoTipoLocal + "' WHERE id_Local = '" + idLocal + "' AND (tipoLocal = 'Furrancho' OR tipoLocal = 'Pulpeiro')";
        insertarModificarEliminar(consulta);
    }

    // Método para actualizar el horario
    public void actualizarHorario(int idLocal, String nuevoHorario) throws SQLException {
        String consulta = "UPDATE " + nombreTabla + " SET horario = '" + nuevoHorario + "' WHERE id_Local = '" + idLocal + "'";
        insertarModificarEliminar(consulta);
    }

    // Método para actualizar el teléfono
    public void actualizarTelefono(int idLocal, String nuevoTelefono) throws SQLException {
        String consulta = "UPDATE " + nombreTabla + " SET telefono = '" + nuevoTelefono + "' WHERE id_Local = '" + idLocal + "'";
        insertarModificarEliminar(consulta);
    }

    // Método para actualizar las coordenadas GPS
    public void actualizarCoordenadasGPS(int idLocal, String nuevasCoordenadasGPS) throws SQLException {
        String consulta = "UPDATE " + nombreTabla + " SET coordenadasGPS = '" + nuevasCoordenadasGPS + "' WHERE id_Local = '" + idLocal + "'";
        insertarModificarEliminar(consulta);
    }
		
	//--------------------------------------------------------------------------
	//A partir de aquí si no se usa ningún método se puede eliminar este código
	//El código empleado en las actividades 3B y 3C es el anterior
	//--------------------------------------------------------------------------

	
		// Importamos el método scanner para introducir datos por pantalla
		Scanner entradaDatos = new Scanner(System.in);

		// Declaración de variables
		int opcion = 1;
		int numeroCampos = 10;
		int i = 0;
		int j = 0;
		String parentesisConsulta = "";
		String nombreTabla = "localizaciones";
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
				System.out.println("1 - Consultar locales");
				System.out.println("2 - Agregar local");
				System.out.println("3 - Modificar local");
				System.out.println("4 - Eliminar local");
				System.out.println("");
				System.out.println("");
				opcion = entradaDatos.nextInt();

				// En el caso de que escoja la opción 0 (Salir), el programa finaliza

				// Consultar locales

				if (opcion == 1) {

					// Establecemos un canal de comunicación de consultas a SQL
					Statement s = (Statement) conexion.createStatement();

					// Damos al usuario la opcion de escoger si quiere leer una fila o la tabla
					// entera
					System.out.println("Ha escogido la opcion consultar localizaciones");
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
						// Agregamos comillas simples alrededor de valorCampo para indicar a PostgreSQL
						// que se trata de una cadena de texto
						consulta = "SELECT * FROM " + nombreTabla + " WHERE " + tipoCampo + " = '" + valorCampo + "'";
						System.out.println(consulta);
					} else {
						// Leer tabla, en este caso el usuario no debe de indicar nada
						consulta = "SELECT * FROM " + nombreTabla;
					}
					/*
					 * Tras tener los datos suficientes para realizar la consulta, llamamos al
					 * método necesario para realizar la consulta
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

					System.out.println("Ha escogido la opcion agregar local");
					parentesisConsulta = "";

					// Indicamos los nombres de los campos
					String[] nombreCampo = new String[numeroCampos];
					nombreCampo[0] = "id_localizacion";
					nombreCampo[1] = "nombrelocal";
					nombreCampo[2] = "direccion";
					nombreCampo[3] = "descripcion";
					nombreCampo[4] = "tipolocal";
					nombreCampo[5] = "horario";
					nombreCampo[6] = "telefono";
					nombreCampo[7] = "coordenadasgps";
					nombreCampo[8] = "capacidad";
					nombreCampo[9] = "valoracion";

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
					consulta = "INSERT INTO LOCALIZACIONES VALUES " + parentesisConsulta;
					System.out.println("La consulta para insertar los datos es: " + consulta);
					System.out.println();
					insertarModificarEliminar(consulta, s);
				}

				// Modificar local

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
							// Se debe de modificar para que se respeten las mayúsculas (Borrar comilla
							// simple antes y después de nombreCampo[j]
							parentesisConsulta = parentesisConsulta + "\"" + nombreCampo[j] + "\"" + "=" + "'"
									+ valorCampo + "'";
						} else {
							System.out.println("Introduzca el nombre del campo " + i + " que desea modificar:");
							nombreCampo[j] = entradaDatos.nextLine();
							System.out.println("Introduzca el nuevo valor del campo " + nombreCampo[j] + ":");
							valorCampo = entradaDatos.nextLine();
							// Se debe de modificar para que se respeten las mayúsculas
							parentesisConsulta = parentesisConsulta + "\"" + nombreCampo[j] + "\"" + "=" + "'"
									+ valorCampo + "'" + ",";
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
					consulta = consulta + " WHERE " + "\"" + tipoCampo + "\"" + " = '" + valorCampo + "'";
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
					entradaDatos.nextLine();
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
