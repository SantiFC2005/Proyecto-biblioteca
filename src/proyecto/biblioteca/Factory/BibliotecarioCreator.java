package proyecto.biblioteca.Factory;

import proyecto.biblioteca.Vista.Bibliotecario;

public class BibliotecarioCreator extends PantallaCreator {
    @Override
    public Pantalla crearPantalla() {
        return new Bibliotecario();
    }
}