package proyecto.biblioteca.Strategy;

import java.time.LocalDateTime;

import proyecto.biblioteca.modelo.Usuario;

public class ReglaAudiovisual implements ReglaPrestamo {
    @Override
    public LocalDateTime calcularFechaVencimiento(Usuario usuario) {
        return LocalDateTime.now().plusDays(usuario.getDiasPlazoBase());
    }

    @Override
    public int getMaxRenovacionesPermitidas() {
        return 1;
    }

    @Override
    public boolean puedePrestar(Usuario usuario, int prestamosActivosActuales) {
        return !usuario.isBloqueado() && prestamosActivosActuales < usuario.getMaxRecursosSimultaneos();
    }
}