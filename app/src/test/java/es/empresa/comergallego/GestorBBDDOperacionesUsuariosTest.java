package es.empresa.comergallego;

import junit.framework.TestCase;

import java.sql.SQLException;

public class GestorBBDDOperacionesUsuariosTest extends TestCase {

    private GestorBBDD bd;
    private GestorBBDDOperacionesUsuarios bou;

    public void setUp() throws SQLException {
        bd = new GestorBBDD();
        bou = new GestorBBDDOperacionesUsuarios();
    }
    public void testInsertarUsuarioExito() throws SQLException {

        Usuarios usuario = new Usuarios();
        usuario.setNombreUsuario("usuario_prueba1");
        usuario.setNombre("NombrePrueba");
        usuario.setApellido("ApellidoPrueba");
        usuario.setFechaNacimiento("01/01/2000");
        usuario.setCorreoElectronico("1@gmail.com");
        usuario.setPassword("123");
        usuario.setRolUsuario(false); //

        boolean resultado = bou.insertarUsuarioTest(usuario, bd);

        assertTrue(resultado);
    }

    public void testInsertarUsuarioError() throws SQLException {

        Usuarios usuario = new Usuarios();
        usuario.setNombreUsuario("1234");
        usuario.setNombre("NombrePrueba");
        usuario.setApellido("ApellidoPrueba");
        usuario.setFechaNacimiento("01/01/2000");
        usuario.setCorreoElectronico("1@gmail.com");
        usuario.setPassword("123");
        usuario.setRolUsuario(false); //

        boolean resultado = bou.insertarUsuarioTest(usuario, bd);

        assertTrue(resultado);
    }
}