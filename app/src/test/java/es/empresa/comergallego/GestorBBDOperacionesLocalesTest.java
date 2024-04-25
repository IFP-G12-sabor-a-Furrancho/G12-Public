package es.empresa.comergallego;

import junit.framework.TestCase;

import java.sql.SQLException;

public class GestorBBDOperacionesLocalesTest extends TestCase {

    public void testNumeroRegistrosUsuarioAdministrador() throws SQLException {

        // Para este ejemplo, vamos a simular un resultado de 3 registros para idUsuario = 1
        // Sabemos que dicho usuario tiene 3 locales creados
        GestorBBDOperacionesLocales numeroRegistros = new GestorBBDOperacionesLocales();

        // Comprobar que devuelve 3 registros para idUsuario = 1
        int registrosEsperados = 3;
        int registrosReales = numeroRegistros.numeroRegistrosUsuarioAdministrador(1);

        assertEquals(registrosEsperados, registrosReales);
    }

    public void testNumeroRegistrosUsuarioAdministradorError() throws SQLException {

        // Para este ejemplo, vamos a simular un resultado de 4 registros para idUsuario = 1
        // Sabemos que dicho usuario tiene 3 locales creados
        GestorBBDOperacionesLocales numeroRegistros = new GestorBBDOperacionesLocales();

        // Comprobar que devuelve 4 registros para idUsuario = 1
        int registrosEsperados = 4;
        int registrosReales = numeroRegistros.numeroRegistrosUsuarioAdministrador(1);

        assertEquals(registrosEsperados, registrosReales);
    }
}