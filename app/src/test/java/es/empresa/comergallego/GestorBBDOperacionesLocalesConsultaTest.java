package es.empresa.comergallego;

import junit.framework.TestCase;

import java.sql.SQLException;
import java.util.ArrayList;

public class GestorBBDOperacionesLocalesConsultaTest extends TestCase {

    private GestorBBDD bd;
    private GestorBBDOperacionesLocales bol;


    public void setUp() throws SQLException {
        bd = new GestorBBDD();
        bol = new GestorBBDOperacionesLocales();
    }

    public void testConsultaDatosEncontrados() throws SQLException {
        // Supongamos que hay datos en la base de datos que coinciden con la consulta
        String nombreLocalExistente = "Furancho de Juanito";
        ArrayList<String> resultado = bol.consulta(nombreLocalExistente);

        // Verificar que el resultado no esté vacío
        assertEquals(false, resultado.isEmpty());
    }

    public void testConsultaDatosNoEncontrados() throws SQLException {
        // Supongamos que no hay datos en la base de datos que coincidan con la consulta
        String nombreLocalNoExistente = "nombre_local_no_existente";
        ArrayList<String> resultado = bol.consulta(nombreLocalNoExistente);

        // Verificar que el resultado esté vacío y contenga el mensaje "Datos no encontrados"
        assertEquals("Datos no encontrados", resultado.get(0));
    }
}
