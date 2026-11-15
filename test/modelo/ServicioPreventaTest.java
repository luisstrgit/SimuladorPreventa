package modelo;

import org.junit.Test;
import static org.junit.Assert.*;

public class ServicioPreventaTest {

    private final ServicioPreventa servicio = new ServicioPreventa();

    @Test
    public void testAutenticacionVendedor() {
        SesionVendedor sesion = new SesionVendedor("VEN-7101");
        assertTrue("Debe permitir asesor válido", sesion.autenticar());
    }

    @Test
    public void testCalcularIGV18Porciento() {
        double igv = servicio.calcularIGV(100.00);
        assertEquals(18.00, igv, 0.01);
    }

    @Test
    public void testValidarLimiteCredito() {
        assertTrue(servicio.validarLimiteCredito(200.00, 450.00));
        assertFalse(servicio.validarLimiteCredito(500.00, 250.00));
    }

    @Test
    public void testCambioProducto() {
        CambioProducto cambio = new CambioProducto("001", "Vencido", 1);
        assertTrue(cambio.getReporteCambio().contains("VENCIDO"));
    }
}