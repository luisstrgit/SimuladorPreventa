package modelo;
 
import java.util.LinkedHashMap;
import java.util.Map;
 
/** Catálogo central de demostración (mismos SKU que el simulador HTML). */
public class CatalogoProductos {
    private static final Map<String, Producto> PRODUCTOS = new LinkedHashMap<>();
 
    static {
        // sku, descripción, precio por caja, botellas por caja, ml por botella, marca
        agregar(new Producto("123", "INCA KOLA VR192X24", 17.29, 24, 192, "Inca Kola"));
        agregar(new Producto("124", "COCA COLA PET 1L X9", 38.50, 9, 1000, "Coca-Cola"));
        agregar(new Producto("125", "INCA KOLA PET 1L X9", 38.50, 9, 1000, "Inca Kola"));
        agregar(new Producto("126", "SAN LUIS 600ML X15", 14.20, 15, 600, "San Luis"));
        agregar(new Producto("127", "FRUGOS VALLE 1L X6", 21.90, 6, 1000, "Frugos"));
        agregar(new Producto("128", "SPORADE 500ML X12", 22.40, 12, 500, "Sporade"));
    }
 
    private static void agregar(Producto p) { PRODUCTOS.put(p.getSku(), p); }
 
    public static Producto buscar(String sku) { return PRODUCTOS.get(sku); }
    public static boolean existe(String sku) { return PRODUCTOS.containsKey(sku); }
    public static int total() { return PRODUCTOS.size(); }
}
 
