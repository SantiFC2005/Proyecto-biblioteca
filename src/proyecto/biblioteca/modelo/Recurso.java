package proyecto.biblioteca.modelo;

public class Recurso {
    private String id;
    private String codigoInventario;
    private String tituloNombre;
    private String tipoRecurso;
    private String estado;

    public Recurso(String id, String codigoInventario, String tituloNombre, String tipoRecurso, String estado) {
        this.id = id;
        this.codigoInventario = codigoInventario;
        this.tituloNombre = tituloNombre;
        this.tipoRecurso = tipoRecurso;
        this.estado = estado;
    }

    public String getId() { return id; }
    public String getCodigoInventario() { return codigoInventario; }
    public String getTituloNombre() { return tituloNombre; }
    public String getTipoRecurso() { return tipoRecurso; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}