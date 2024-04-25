package es.empresa.comergallego;

import junit.framework.TestCase;

public class GestorBBDDTest extends TestCase {

    public void testConectarBBDDExito() {
        GestorBBDD conexion = new GestorBBDD();

        String url = "jdbc:postgresql://ep-proud-queen-a25i44xx.eu-central-1.aws.neon.tech/comergallego";
        String user = "comergallego_owner";
        String password = "MnexL8Y1OZCc";
        boolean resultado = conexion.conectarBBDD(url, user, password);

        assertTrue(resultado);
    }

    public void testConectarBBDDFallo() {

        GestorBBDD conexion = new GestorBBDD();

        String url = "jdbc:postgresql://localhost:5432/db_inexistente";
        String user = "usuario_inexistente";
        String password = "password_incorrecta";
        boolean resultado = conexion.conectarBBDD(url, user, password);

        assertFalse(resultado);
    }
}