package modelo;

import static org.junit.Assert.*;

import java.sql.Connection;
import org.junit.Test;

/**
 * TestCalidadBD: prueba de integración con SQLite en memoria.
 * Requiere la librería sqlite-jdbc en el proyecto (ver la guía).
 */
public class TestCalidadBD {
    // BD: guarda un pedido y lo cuenta
    @Test
    public void guardaYCuentaPedido() throws Exception {
        try (Connection conn = ConexionBD.conectar("jdbc:sqlite::memory:")) {
            assertNotNull(conn);
            assertEquals(0, ConexionBD.contarPedidos(conn));
            assertTrue(ConexionBD.guardarPedido(conn, "BODEGA LA ESQUINA", "124", 119.94));
            assertEquals(1, ConexionBD.contarPedidos(conn));
        }
    }
}