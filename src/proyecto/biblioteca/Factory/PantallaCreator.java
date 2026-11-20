package proyecto.biblioteca.Factory;

import proyecto.biblioteca.modelo.Usuario;

public abstract class PantallaCreator {
    public abstract Pantalla crearPantalla();

    public void abrirPantalla(Usuario usuario) {
        Pantalla pantalla = crearPantalla();
        pantalla.mostrar(usuario);
    }
}