package proyecto.biblioteca.Factory;

import proyecto.biblioteca.Vista.Estudiante;

public class EstudianteCreator extends PantallaCreator {
    @Override
    public Pantalla crearPantalla() {
        return new Estudiante();
    }
}