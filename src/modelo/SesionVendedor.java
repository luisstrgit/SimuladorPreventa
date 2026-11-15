package modelo;
 
public class SesionVendedor {
    private String codigoAsesor;
 
    public SesionVendedor(String codigoAsesor) {
        this.codigoAsesor = codigoAsesor;
    }
 
    /** Formato válido: VEN- seguido de 4 dígitos (ejemplo VEN-7101). */
    public boolean autenticar() {
        return codigoAsesor != null && codigoAsesor.matches("VEN-\\d{4}");
    }
 
    public String getCodigoAsesor() { return codigoAsesor; }
    public void setCodigoAsesor(String codigoAsesor) { this.codigoAsesor = codigoAsesor; }
}
