package proyecto.biblioteca.Strategy;

import java.time.LocalDateTime;

import proyecto.biblioteca.modelo.Usuario;

public class ReglaEquipoComputo implements ReglaPrestamo {
    @Override
    public LocalDateTime calcularFechaVencimiento(Usuario usuario) {
        int dias = Math.min(usuario.getDiasPlazoBase(), 7);
        return LocalDateTime.now().plusDays(dias);
    }

    @Override
    public int getMaxRenovacionesPermitidas() {
        return 0;
    }

    @Override
    public boolean puedePrestar(Usuario usuario, int prestamosActivosActuales) {
        return !usuario.isBloqueado() && prestamosActivosActuales < usuario.getMaxRecursosSimultaneos();
    }
}