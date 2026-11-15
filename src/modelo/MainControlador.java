package modelo;
 
public class MainControlador {
    public static void main(String[] args) {
        // Inicializar conexión a SQLite (genera preventa.db)
        ConexionBD.conectar();
 
        // Validar sesión de asesor
        SesionVendedor sesion = new SesionVendedor("VEN-7101");
        if (sesion.autenticar()) {
            System.out.println("Asesor autenticado correctamente: " + sesion.getCodigoAsesor());
        }
 
        // Simular un pedido de prueba (1 java a S/ 24.00)
        VentaPedido pedido = new VentaPedido("001", 1, 0, 24.00);
        System.out.println("Subtotal Pedido: S/ " + pedido.calcularSubtotal());
        System.out.println("IGV (18%): S/ " + pedido.calcularIGV());
        System.out.println("Total: S/ " + pedido.calcularTotal());
    }
}
 
