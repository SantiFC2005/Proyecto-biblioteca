package proyecto.biblioteca.Singleton;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public void sincronizarConBD() {
        listaRecursos.clear();
        String sql = "SELECT l.id_libro, l.titulo, " +"COALESCE(STRING_AGG(DISTINCT c.nombre_categoria, ', '), 'LIBRO') AS tipo, " +"CASE WHEN EXISTS (SELECT 1 FROM public.prestamo p " +"    WHERE p.id_libro = l.id_libro " +"    AND (p.fecha_devolucion IS NULL OR p.estado_devolucion = 'Prestado')) " +"THEN 'PRESTADO' ELSE 'DISPONIBLE' END AS estado " +"FROM public.libro l " +"LEFT JOIN public.libro_categoria lc ON l.id_libro = lc.id_libro " +"LEFT JOIN public.categoria c ON lc.id_categoria = c.id_categoria " +"GROUP BY l.id_libro, l.titulo ORDER BY l.id_libro";

        Connection con = Conexion.getInstance().getConnection();
        if (con == null) return;

        try (Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id_libro"));
                listaRecursos.add(new Recurso(
                    id,
                    "LIB-" + id,
                    rs.getString("titulo"),
                    rs.getString("tipo"),
                    rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error cargando catálogo único: " + e.getMessage());
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