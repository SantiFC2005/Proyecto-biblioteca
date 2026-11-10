package proyecto.biblioteca.Factory;

import proyecto.biblioteca.Vista.Bibliotecario;

public class BibliotecarioCreator extends PantallaCreator {
    @Override
    protected Pantalla crearPantalla() {
        return new Bibliotecario();
    }
}