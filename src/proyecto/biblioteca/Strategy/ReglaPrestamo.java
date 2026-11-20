package proyecto.biblioteca.Strategy;

import java.time.LocalDateTime;
import proyecto.biblioteca.modelo.Usuario;

public interface ReglaPrestamo {
    LocalDateTime calcularFechaVencimiento(Usuario usuario);
    int getMaxRenovacionesPermitidas();
    boolean puedePrestar(Usuario usuario, int prestamosActivosActuales);
}