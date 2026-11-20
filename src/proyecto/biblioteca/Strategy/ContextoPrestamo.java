package proyecto.biblioteca.Strategy;

import java.time.LocalDateTime;
import proyecto.biblioteca.modelo.Usuario;

public class ContextoPrestamo {
    private ReglaPrestamo estrategia;

    public void seleccionarEstrategiaPorTipo(String tipoRecurso) {
        switch (tipoRecurso.toUpperCase()) {
            case "LIBRO":
                this.estrategia = new ReglaLibro();
                break;
            case "EQUIPO_COMPUTO":
                this.estrategia = new ReglaEquipoComputo();
                break;
            case "MATERIAL_AUDIOVISUAL":
            default:
                this.estrategia = new ReglaAudiovisual();
                break;
        }
    }

    public LocalDateTime obtenerFechaVencimiento(Usuario usuario) {
        return estrategia.calcularFechaVencimiento(usuario);
    }

    public int obtenerMaxRenovaciones() {
        return estrategia.getMaxRenovacionesPermitidas();
    }

    public boolean validarPermisoPrestamo(Usuario usuario, int activos) {
        return estrategia.puedePrestar(usuario, activos);
    }
}