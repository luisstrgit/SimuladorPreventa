package modelo;
 
public class ServicioPreventa {
    private static final double TASA_IGV = 0.18;
 
    public boolean validarCantidadMinima(int cajas, int botellas) {
        return (cajas > 0 || botellas > 0) && (cajas >= 0 && botellas >= 0);
    }
 
    public double calcularIGV(double baseImponible) {
        return Math.round(baseImponible * TASA_IGV * 100.0) / 100.0;
    }
 
    public boolean validarLimiteCredito(double totalPedido, double limiteCredito) {
        return totalPedido <= limiteCredito;
    }
 
    /** El DNI peruano tiene exactamente 8 dígitos. */
    public boolean validarDNI(String dni) {
        return dni != null && dni.matches("\\d{8}");
    }
 
    // >>> NUEVO: litros = (cajas x botellas por caja + botellas) x ml / 1000
    public double calcularLitros(int cajas, int botellas, int botellasPorCaja, int mlPorBotella) {
        return (cajas * botellasPorCaja + botellas) * mlPorBotella / 1000.0;
    }
 
    // >>> NUEVO: cuota mensual = litros por visita x visitas por día x días (18 x 41 x 30 = 22 140 L)
    public double calcularCuotaMensual(double litrosPorVisita, int visitasPorDia, int dias) {
        return litrosPorVisita * visitasPorDia * dias;
    }
 
    // >>> NUEVO: porcentaje entero de avance sobre meta (0 si la meta es 0)
    public int calcularPorcentaje(double avance, double meta) {
        return meta > 0 ? (int) Math.round(avance / meta * 100) : 0;
    }
 
    // >>> NUEVO: score diario = bodegas atendidas hoy / bodegas programadas (solo bodegas)
    public int calcularScoreDiario(int visitadas, int programadas) {
        return calcularPorcentaje(visitadas, programadas);
    }
 
    // >>> NUEVO: cumplimiento mensual = bodegas atendidas en el mes / 984 visitas del mes
    public int calcularCumplimientoMensual(int atendidas, int visitasMes) {
        return calcularPorcentaje(atendidas, visitasMes);
    }
}
 
