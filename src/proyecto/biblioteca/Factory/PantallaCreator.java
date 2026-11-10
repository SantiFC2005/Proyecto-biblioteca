package proyecto.biblioteca.Factory;

public abstract class PantallaCreator {


    protected abstract Pantalla crearPantalla();

    public void abrir() {
        Pantalla pantalla = crearPantalla();
        pantalla.mostrar();
    }
}