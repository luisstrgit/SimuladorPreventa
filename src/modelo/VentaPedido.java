package modelo;
 
public class VentaPedido {
    public static final double TASA_IGV = 0.18;
    public static final int JAVAS_PARA_PROMOCION = 3;
    public static final double DESCUENTO_VOLUMEN = 0.12;
 
    private String skuCodigo;
    private int javas;
    private int botellas;
    private double precioJava;
    private int botellasPorJava;
 
    /** Constructor original (12 botellas por java). */
    public VentaPedido(String skuCodigo, int javas, int botellas, double precioJava) {
        this(skuCodigo, javas, botellas, precioJava, 12);
    }
 
    public VentaPedido(String skuCodigo, int javas, int botellas, double precioJava, int botellasPorJava) {
        if (javas < 0 || botellas < 0) {
            throw new IllegalArgumentException("Las cantidades no pueden ser negativas");
        }
        if (precioJava <= 0 || botellasPorJava <= 0) {
            throw new IllegalArgumentException("Precio y botellas por java deben ser mayores que cero");
        }
        this.skuCodigo = skuCodigo;
        this.javas = javas;
        this.botellas = botellas;
        this.precioJava = precioJava;
        this.botellasPorJava = botellasPorJava;
    }
 
    private static double redondear(double v) { return Math.round(v * 100.0) / 100.0; }
 
    public double calcularSubtotal() {
        double montoJavas = javas * precioJava;
        double montoBotellas = botellas * (precioJava / botellasPorJava);
        return redondear(montoJavas + montoBotellas);
    }
 
    /** 12 % de descuento cuando se piden 3 javas o más. */
    public double calcularPromocion() {
        return javas >= JAVAS_PARA_PROMOCION ? redondear(calcularSubtotal() * DESCUENTO_VOLUMEN) : 0.0;
    }
 
    public double calcularBaseImponible() { return redondear(calcularSubtotal() - calcularPromocion()); }
 
    public double calcularIGV() { return redondear(calcularBaseImponible() * TASA_IGV); }
 
    public double calcularTotal() { return redondear(calcularBaseImponible() + calcularIGV()); }
 
    public String getSkuCodigo() { return skuCodigo; }
}
