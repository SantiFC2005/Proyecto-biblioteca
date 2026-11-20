package proyecto.biblioteca.Strategy;

import java.time.LocalDateTime;
import proyecto.biblioteca.modelo.Usuario;

public class ReglaLibro implements ReglaPrestamo {
    @Override
    public LocalDateTime calcularFechaVencimiento(Usuario usuario) {
        return LocalDateTime.now().plusDays(usuario.getDiasPlazoBase());
    }

    @Override
    public int getMaxRenovacionesPermitidas() {
        return 2;
    }

    @Override
    public boolean puedePrestar(Usuario usuario, int prestamosActivosActuales) {
        return !usuario.isBloqueado() && prestamosActivosActuales < usuario.getMaxRecursosSimultaneos();
    }
}