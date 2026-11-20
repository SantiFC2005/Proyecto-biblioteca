package proyecto.biblioteca.modelo;

import java.time.LocalDateTime;

public class Prestamo {
    private int idPrestamo;
    private int idLibro;
    private int idUsuario;
    private LocalDateTime fechaVencimiento;
    private String estadoDevolucion;

    public Prestamo(int idPrestamo, int idLibro, int idUsuario, LocalDateTime fechaVencimiento, String estadoDevolucion) {
        this.idPrestamo = idPrestamo;
        this.idLibro = idLibro;
        this.idUsuario = idUsuario;
        this.fechaVencimiento = fechaVencimiento;
        this.estadoDevolucion = estadoDevolucion;
    }

    public int getIdPrestamo() { return idPrestamo; }
    public int getIdLibro() { return idLibro; }
    public int getIdUsuario() { return idUsuario; }
    public LocalDateTime getFechaVencimiento() { return fechaVencimiento; }
    public String getEstadoDevolucion() { return estadoDevolucion; }
}