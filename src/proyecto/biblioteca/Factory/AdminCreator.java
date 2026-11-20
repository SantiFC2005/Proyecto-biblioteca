package proyecto.biblioteca.Factory;

import proyecto.biblioteca.Vista.Admin;

public class AdminCreator extends PantallaCreator {
    @Override
    public Pantalla crearPantalla() {
        return new Admin();
    }
}