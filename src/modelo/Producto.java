package modelo;
 
/**
 * Producto del catálogo: SKU, descripción, precio por caja (java),
 * botellas por caja, mililitros por botella y marca.
 */
public class Producto {
    private final String sku;
    private final String descripcion;
    private final double precioCaja;
    private final int botellasPorCaja;
    private final int mlPorBotella;   // >>> NUEVO: sirve para calcular litros
    private final String marca;       // >>> NUEVO: sirve para los resúmenes por marca
 
    public Producto(String sku, String descripcion, double precioCaja,
                    int botellasPorCaja, int mlPorBotella, String marca) {
        if (sku == null || sku.trim().isEmpty()) {
            throw new IllegalArgumentException("El SKU es obligatorio");
        }
        if (precioCaja <= 0 || botellasPorCaja <= 0 || mlPorBotella <= 0) {
            throw new IllegalArgumentException("Precio, botellas por caja y mililitros deben ser mayores que cero");
        }
        this.sku = sku;
        this.descripcion = descripcion;
        this.precioCaja = precioCaja;
        this.botellasPorCaja = botellasPorCaja;
        this.mlPorBotella = mlPorBotella;
        this.marca = marca;
    }
 
    public String getSku() { return sku; }
    public String getDescripcion() { return descripcion; }
    public double getPrecioCaja() { return precioCaja; }
    public int getBotellasPorCaja() { return botellasPorCaja; }
    public int getMlPorBotella() { return mlPorBotella; }
    public String getMarca() { return marca; }
}
