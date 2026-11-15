package modelo;
 
/** Cambio de producto por merma, vencimiento o defecto. */
public class CambioProducto {
    public static final String MERMA = "MERMA";
    public static final String VENCIDO = "VENCIDO";
    public static final String DEFECTUOSO = "DEFECTUOSO";
 
    private String sku;
    private String motivo;
    private int cantidad;
 
    public CambioProducto(String sku, String motivo, int cantidad) {
        if (sku == null || sku.trim().isEmpty()) {
            throw new IllegalArgumentException("El SKU es obligatorio");
        }
        if (motivo == null) {
            throw new IllegalArgumentException("El motivo es obligatorio");
        }
        String m = motivo.trim().toUpperCase();
        if (!m.equals(MERMA) && !m.equals(VENCIDO) && !m.equals(DEFECTUOSO)) {
            throw new IllegalArgumentException("Motivo no válido: " + motivo);
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        this.sku = sku;
        this.motivo = m;
        this.cantidad = cantidad;
    }
 
    public String getSku() { return sku; }
    public String getMotivo() { return motivo; }
    public int getCantidad() { return cantidad; }
 
    public String getReporteCambio() {
        return cantidad + "x SKU " + sku + " [" + motivo + "]";
    }
}
 
