package proyecto.biblioteca.Singleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import proyecto.biblioteca.Conexion;
import proyecto.biblioteca.modelo.Recurso;

public class CatalogoRecursos {
    private static CatalogoRecursos instancia;
    private List<Recurso> listaRecursos;

    private CatalogoRecursos() {
        this.listaRecursos = new ArrayList<>();
        sincronizarConBD();
    }

    public static synchronized CatalogoRecursos getInstance() {
        if (instancia == null) {
            instancia = new CatalogoRecursos();
        }
        return instancia;
    }

    // Recarga desde Supabase para mantener consistencia
    public void sincronizarConBD() {
        listaRecursos.clear();
        String sql = "SELECT r.id, r.codigo_inventario, r.titulo_nombre, t.nombre AS tipo, r.estado " +"FROM recursos r JOIN tipos_recurso t ON r.tipo_recurso_id = t.id";
        try (Statement st = Conexion.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                listaRecursos.add(new Recurso(
                    rs.getString("id"),
                    rs.getString("codigo_inventario"),
                    rs.getString("titulo_nombre"),
                    rs.getString("tipo"),
                    rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error cargando catálogo: " + e.getMessage());
        }
    }

    public List<Recurso> getRecursosDisponibles() {
        sincronizarConBD();
        List<Recurso> disponibles = new ArrayList<>();
        for (Recurso r : listaRecursos) {
            if ("DISPONIBLE".equalsIgnoreCase(r.getEstado())) {
                disponibles.add(r);
            }
        }
        return disponibles;
    }

    public List<Recurso> getTodos() {
        sincronizarConBD();
        return listaRecursos;
    }
}