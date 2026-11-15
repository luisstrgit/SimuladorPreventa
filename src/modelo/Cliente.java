package modelo;
 
public class Cliente {
    private int secuencia;
    private String nombre;
    private String segmento;
    private String condicionPago;
    private double limiteCredito;
    private double saldo;                 // >>> NUEVO: crédito pendiente hasta fin de mes
    private String codigoEquipoFrio;      // >>> NUEVO: código de la refrigeradora prestada ("" si no tiene)
 
    public Cliente(int secuencia, String nombre, String segmento, String condicionPago, double limiteCredito) {
        this(secuencia, nombre, segmento, condicionPago, limiteCredito, 0.0, "");
    }
 
    // >>> NUEVO: constructor con saldo y equipo frío
    public Cliente(int secuencia, String nombre, String segmento, String condicionPago,
                   double limiteCredito, double saldo, String codigoEquipoFrio) {
        this.secuencia = secuencia;
        this.nombre = nombre;
        this.segmento = segmento;
        this.condicionPago = condicionPago;
        this.limiteCredito = limiteCredito;
        this.saldo = saldo;
        this.codigoEquipoFrio = codigoEquipoFrio == null ? "" : codigoEquipoFrio;
    }
 
    public int getSecuencia() { return secuencia; }
    public void setSecuencia(int secuencia) { this.secuencia = secuencia; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getSegmento() { return segmento; }
    public void setSegmento(String segmento) { this.segmento = segmento; }
    public String getCondicionPago() { return condicionPago; }
    public void setCondicionPago(String condicionPago) { this.condicionPago = condicionPago; }
    public double getLimiteCredito() { return limiteCredito; }
    public void setLimiteCredito(double limiteCredito) { this.limiteCredito = limiteCredito; }
 
    // >>> NUEVO
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public String getCodigoEquipoFrio() { return codigoEquipoFrio; }
    public boolean tieneEquipoFrio() { return !codigoEquipoFrio.isEmpty(); }
}
 
