package proyecto.biblioteca.modelo;

public class Usuario {
    private String id;
    private String codigoInstitucional;
    private String nombreCompleto;
    private String email;
    private String rol;
    private boolean bloqueado;
    private int diasPlazoBase;
    private int maxRecursosSimultaneos;

    public Usuario(String id, String codigoInstitucional, String nombreCompleto,
                   String email, String rol, boolean bloqueado,
                   int diasPlazoBase, int maxRecursosSimultaneos) {
        this.id = id;
        this.codigoInstitucional = codigoInstitucional;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.rol = rol;
        this.bloqueado = bloqueado;
        this.diasPlazoBase = diasPlazoBase;
        this.maxRecursosSimultaneos = maxRecursosSimultaneos;
    }

    public String getId() { return id; }
    public String getCodigoInstitucional() { return codigoInstitucional; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getEmail() { return email; }
    public String getRol() { return rol; }
    public boolean isBloqueado() { return bloqueado; }
    public int getDiasPlazoBase() { return diasPlazoBase; }
    public int getMaxRecursosSimultaneos() { return maxRecursosSimultaneos; }
}