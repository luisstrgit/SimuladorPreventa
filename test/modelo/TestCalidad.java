package modelo;

import static org.junit.Assert.*;

import java.util.Arrays; 
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;

/**
 * TestCalidad: clase ÚNICA de pruebas de calidad (JUnit 4) del modelo de AC Móvil.
 * Reúne las pruebas de Cliente, CambioProducto, VentaPedido, ServicioPreventa,
 * SesionVendedor, RutaPreventa, CatalogoProductos y un flujo de integración.
 * No necesita base de datos (las pruebas de SQLite están en TestCalidadBD).
 */
public class TestCalidad {
    private static final double DELTA = 0.001;
    private ServicioPreventa servicio;

    @Before
    public void preparar() {
        servicio = new ServicioPreventa();
    }

    // ═════════════ 1. CLIENTE ═════════════
    // Cliente: el constructor guarda todos los datos
    @Test
    public void clienteConstructorGuardaDatos() {
        Cliente c = new Cliente(1, "BODEGA LA ESQUINA", "MI MICRO", "CONTADO", 500.0);
        assertEquals(1, c.getSecuencia());
        assertEquals("BODEGA LA ESQUINA", c.getNombre());
        assertEquals("MI MICRO", c.getSegmento());
        assertEquals("CONTADO", c.getCondicionPago());
        assertEquals(500.0, c.getLimiteCredito(), DELTA);
    }

    // Cliente: los setters actualizan límite de crédito y condición de pago
    @Test
    public void clienteSettersActualizan() {
        Cliente c = new Cliente(1, "TIENDA SANTA ROSA", "BODEGA", "CONTADO", 0);
        c.setLimiteCredito(1200.50);
        c.setCondicionPago("CREDITO");
        assertEquals(1200.50, c.getLimiteCredito(), DELTA);
        assertEquals("CREDITO", c.getCondicionPago());
    }

    // Cliente: saldo y equipo frío
    @Test
    public void clienteSaldoYEquipoFrio() {
        Cliente sin = new Cliente(1, "A", "BODEGA", "CONTADO", 0);
        assertEquals(0.0, sin.getSaldo(), DELTA);
        assertFalse(sin.tieneEquipoFrio());
        Cliente con = new Cliente(2, "B", "GR GRANDE", "CREDITO", 1000, 500.0, "EF-104-0231");
        assertEquals(500.0, con.getSaldo(), DELTA);
        assertTrue(con.tieneEquipoFrio());
        assertEquals("EF-104-0231", con.getCodigoEquipoFrio());
    }

    // ═════════════ 2. CAMBIO DE PRODUCTO ═════════════
    // CambioProducto: el reporte muestra cantidad, SKU y motivo
    @Test
    public void cambioReporteCorrecto() {
        CambioProducto c = new CambioProducto("125", "vencido", 2);
        assertEquals("2x SKU 125 [VENCIDO]", c.getReporteCambio());
    }

    // CambioProducto: acepta los tres motivos oficiales
    @Test
    public void cambioAceptaMotivosValidos() {
        assertEquals("MERMA", new CambioProducto("123", "Merma", 1).getMotivo());
        assertEquals("VENCIDO", new CambioProducto("123", "Vencido", 1).getMotivo());
        assertEquals("DEFECTUOSO", new CambioProducto("123", "Defectuoso", 1).getMotivo());
    }

    // CambioProducto: rechaza motivo desconocido, cantidad inválida y SKU vacío
    @Test
    public void cambioRechazaDatosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> new CambioProducto("123", "ROBO", 1));
        assertThrows(IllegalArgumentException.class, () -> new CambioProducto("123", "MERMA", 0));
        assertThrows(IllegalArgumentException.class, () -> new CambioProducto("123", "MERMA", -3));
        assertThrows(IllegalArgumentException.class, () -> new CambioProducto(" ", "MERMA", 1));
    }

    // ═════════════ 3. VENTA / PEDIDO ═════════════
    // VentaPedido: caso del Main, 1 java a S/ 24.00, IGV 4.32
    @Test
    public void pedidoCasoDelMain() {
        VentaPedido p = new VentaPedido("001", 1, 0, 24.00);
        assertEquals(24.00, p.calcularSubtotal(), DELTA);
        assertEquals(4.32, p.calcularIGV(), DELTA);
    }

    // VentaPedido: 3 cajas del SKU 124, promoción 12 %, IGV y total
    @Test
    public void pedidoTresCajasConPromocion() {
        Producto prod = CatalogoProductos.buscar("124");
        VentaPedido p = new VentaPedido("124", 3, 0, prod.getPrecioCaja(), prod.getBotellasPorCaja());
        assertEquals(115.50, p.calcularSubtotal(), DELTA);
        assertEquals(13.86, p.calcularPromocion(), DELTA);
        assertEquals(101.64, p.calcularBaseImponible(), DELTA);
        assertEquals(18.30, p.calcularIGV(), DELTA);
        assertEquals(119.94, p.calcularTotal(), DELTA);
    }

    // VentaPedido: con 2 cajas no hay promoción
    @Test
    public void pedidoDosCajasSinPromocion() {
        assertEquals(0.0, new VentaPedido("124", 2, 0, 38.50, 9).calcularPromocion(), DELTA);
    }

    // VentaPedido: botellas sueltas prorrateadas (SKU 123, 24 por caja)
    @Test
    public void pedidoBotellasSueltas() {
        assertEquals(1.44, new VentaPedido("123", 0, 2, 17.29, 24).calcularSubtotal(), DELTA);
    }

    // VentaPedido: rechaza cantidades negativas y precios no positivos
    @Test
    public void pedidoValidaDatos() {
        assertThrows(IllegalArgumentException.class, () -> new VentaPedido("1", -1, 0, 10.0));
        assertThrows(IllegalArgumentException.class, () -> new VentaPedido("1", 1, 0, 0.0));
        assertThrows(IllegalArgumentException.class, () -> new VentaPedido("1", 1, 0, 10.0, 0));
    }

    // ═════════════ 4. SERVICIO DE PREVENTA ═════════════
    // Servicio: cantidad mínima en cajas o botellas
    @Test
    public void servicioCantidadMinima() {
        assertTrue(servicio.validarCantidadMinima(1, 0));
        assertTrue(servicio.validarCantidadMinima(0, 5));
        assertFalse(servicio.validarCantidadMinima(0, 0));
        assertFalse(servicio.validarCantidadMinima(-1, 3));
    }

    // Servicio: IGV 18 % redondeado a 2 decimales
    @Test
    public void servicioIgv() {
        assertEquals(18.30, servicio.calcularIGV(101.64), DELTA);
        assertEquals(0.0, servicio.calcularIGV(0), DELTA);
    }

    // Servicio: el pedido no puede superar el límite de crédito
    @Test
    public void servicioLimiteCredito() {
        assertTrue(servicio.validarLimiteCredito(100, 100));
        assertFalse(servicio.validarLimiteCredito(100.01, 100));
    }

    // Servicio: DNI de exactamente 8 dígitos
    @Test
    public void servicioDni() {
        assertTrue(servicio.validarDNI("72136836"));
        assertFalse(servicio.validarDNI("1234567"));
        assertFalse(servicio.validarDNI("1234567A"));
        assertFalse(servicio.validarDNI(null));
    }

    // Servicio: litros de 3 cajas del SKU 124 = 27 L
    @Test
    public void servicioLitros() {
        Producto p = CatalogoProductos.buscar("124");
        assertEquals(27.0, servicio.calcularLitros(3, 0, p.getBotellasPorCaja(), p.getMlPorBotella()), DELTA);
        Producto inca = CatalogoProductos.buscar("123");
        assertEquals(4.608, servicio.calcularLitros(1, 0, inca.getBotellasPorCaja(), inca.getMlPorBotella()), DELTA);
    }

    // Servicio: cuota mensual 18 L x 41 visitas x 30 días = 22 140 L
    @Test
    public void servicioCuotaMensual() {
        assertEquals(738.0, servicio.calcularCuotaMensual(18, 41, 1), DELTA);
        assertEquals(22140.0, servicio.calcularCuotaMensual(18, 41, 30), DELTA);
    }

    // Servicio: porcentajes de cuota, score diario y cumplimiento mensual
    @Test
    public void servicioPorcentajes() {
        assertEquals(41, servicio.calcularPorcentaje(9093, 22140));
        assertEquals(15, servicio.calcularScoreDiario(6, 41));
        assertEquals(32, servicio.calcularCumplimientoMensual(318, 984));
        assertEquals(0, servicio.calcularPorcentaje(100, 0));
    }

    // ═════════════ 5. SESIÓN DEL VENDEDOR ═════════════
    // Sesión: el código VEN-#### autentica; otros formatos no
    @Test
    public void sesionAutenticacion() {
        assertTrue(new SesionVendedor("VEN-7101").autenticar());
        assertFalse(new SesionVendedor("7101").autenticar());
        assertFalse(new SesionVendedor("VEN-71").autenticar());
        assertFalse(new SesionVendedor(null).autenticar());
        assertEquals("VEN-7101", new SesionVendedor("VEN-7101").getCodigoAsesor());
    }

    // ═════════════ 6. RUTA ═════════════
    // Ruta: cuenta clientes, condición de pago y nombre
    @Test
    public void rutaCuentaClientes() {
        RutaPreventa r = new RutaPreventa(Arrays.asList(
            new Cliente(1, "A", "MI MICRO", "CONTADO", 0),
            new Cliente(2, "B", "BODEGA", "CREDITO", 300)));
        assertEquals(2, r.getTotalClientes());
        assertEquals(1, r.contarPorCondicionPago("credito"));
        assertEquals("Ruta 104 - Huancayo", r.getNombreRuta());
    }

    // Ruta: una lista nula equivale a una ruta vacía
    @Test
    public void rutaListaNula() {
        assertEquals(0, new RutaPreventa(null).getTotalClientes());
        assertEquals(0, new RutaPreventa(Collections.<Cliente>emptyList()).getTotalClientes());
    }

    // Ruta: saldo total (1 800) y equipos fríos (2)
    @Test
    public void rutaSaldosYEquiposFrio() {
        RutaPreventa r = new RutaPreventa(Arrays.asList(
            new Cliente(1, "A", "BODEGA", "CREDITO", 1500, 1000.0, "EF-104-0001"),
            new Cliente(2, "B", "BODEGA", "CREDITO", 800, 500.0, ""),
            new Cliente(3, "C", "GR GRANDE", "CREDITO", 800, 300.0, "EF-104-0002"),
            new Cliente(4, "D", "BODEGA", "CONTADO", 0)));
        assertEquals(1800.0, r.calcularSaldoTotal(), DELTA);
        assertEquals(2, r.contarEquiposFrio());
    }

    // ═════════════ 7. CATÁLOGO ═════════════
    // Catálogo: 6 SKU; el 123 es INCA KOLA VR192X24 de 192 ml
    @Test
    public void catalogoBase() {
        assertEquals(6, CatalogoProductos.total());
        Producto p = CatalogoProductos.buscar("123");
        assertEquals("INCA KOLA VR192X24", p.getDescripcion());
        assertEquals(24, p.getBotellasPorCaja());
        assertEquals(192, p.getMlPorBotella());
        assertEquals("Inca Kola", p.getMarca());
    }

    // Catálogo: un SKU inexistente no se encuentra
    @Test
    public void catalogoSkuInexistente() {
        assertFalse(CatalogoProductos.existe("999"));
        assertNull(CatalogoProductos.buscar("999"));
    }

    // ═════════════ 8. INTEGRACIÓN ═════════════
    // Integración: sesión, catálogo, pedido, IGV y límite de crédito
    @Test
    public void integracionFlujoCompleto() {
        assertTrue(new SesionVendedor("VEN-7101").autenticar());
        assertTrue(servicio.validarCantidadMinima(3, 0));
        Producto prod = CatalogoProductos.buscar("124");
        VentaPedido pedido = new VentaPedido(prod.getSku(), 3, 0, prod.getPrecioCaja(), prod.getBotellasPorCaja());
        assertEquals(servicio.calcularIGV(pedido.calcularBaseImponible()), pedido.calcularIGV(), DELTA);
        assertTrue(servicio.validarLimiteCredito(pedido.calcularTotal(), 500.0));
        assertFalse(servicio.validarLimiteCredito(pedido.calcularTotal(), 100.0));
        // los litros del pedido alimentan la cuota mensual
        double litros = servicio.calcularLitros(3, 0, prod.getBotellasPorCaja(), prod.getMlPorBotella());
        assertEquals(27.0, litros, DELTA);
        assertEquals(41, servicio.calcularPorcentaje(9000 + litros, 22140));
    }
}
