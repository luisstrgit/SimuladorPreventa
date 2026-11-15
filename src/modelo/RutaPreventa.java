package modelo;
 
import java.util.ArrayList;
import java.util.List;
 
public class RutaPreventa {
    private String nombreRuta = "Ruta 104 - Huancayo";
    private List<Cliente> listaClientes;
 
    public RutaPreventa(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes == null ? new ArrayList<>() : listaClientes;
    }
 
    public RutaPreventa(String nombreRuta, List<Cliente> listaClientes) {
        this(listaClientes);
        this.nombreRuta = nombreRuta;
    }
 
    public int getTotalClientes() { return listaClientes.size(); }
    public String getNombreRuta() { return nombreRuta; }
 
    public int contarPorCondicionPago(String condicion) {
        int n = 0;
        for (Cliente c : listaClientes) {
            if (c.getCondicionPago() != null && c.getCondicionPago().equalsIgnoreCase(condicion)) n++;
        }
        return n;
    }
 
    // >>> NUEVO: suma de los saldos (créditos) de todos los clientes (mosaico Saldos)
    public double calcularSaldoTotal() {
        double total = 0;
        for (Cliente c : listaClientes) total += c.getSaldo();
        return total;
    }
 
    // >>> NUEVO: cantidad de clientes con refrigeradora prestada (Inventario equipo frío)
    public int contarEquiposFrio() {
        int n = 0;
        for (Cliente c : listaClientes) if (c.tieneEquipoFrio()) n++;
        return n;
    }
}
 
