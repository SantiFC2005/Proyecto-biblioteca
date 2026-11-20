package proyecto.biblioteca.modelo;

public class Multa {
    private int idMulta;
    private int idUsuario;
    private double monto;
    private String motivo;
    private String estado;

    public Multa(int idMulta, int idUsuario, double monto, String motivo, String estado) {
        this.idMulta = idMulta;
        this.idUsuario = idUsuario;
        this.monto = monto;
        this.motivo = motivo;
        this.estado = estado;
    }

    public int getIdMulta() { return idMulta; }
    public int getIdUsuario() { return idUsuario; }
    public double getMonto() { return monto; }
    public String getMotivo() { return motivo; }
    public String getEstado() { return estado; }
}