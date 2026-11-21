package proyecto.biblioteca.Vista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import proyecto.biblioteca.Conexion;
import proyecto.biblioteca.Factory.Pantalla;
import proyecto.biblioteca.Strategy.ContextoPrestamo;
import proyecto.biblioteca.modelo.Usuario;

public class Estudiante extends javax.swing.JFrame implements Pantalla {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Estudiante.class.getName());
    private boolean cargandoDatos = true;
    private TableRowSorter<DefaultTableModel> sorter;
    
    // Sesión del usuario recibida desde el Factory Method
    private Usuario usuarioActual;
    // Patrón Strategy para reglas de préstamo (plazos y límites)
    private final ContextoPrestamo contextoPrestamo = new ContextoPrestamo();

    public Estudiante() {
        initComponents();
        setLocationRelativeTo(null);
        jComboBox2.setModel(new DefaultComboBoxModel<>(
                new String[]{"Todos", "Disponible", "No disponible"}));

        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filtrar(); }
            @Override public void removeUpdate(DocumentEvent e) { filtrar(); }
            @Override public void changedUpdate(DocumentEvent e) { filtrar(); }
        });

        cargarCategoriasEnComboBox();
        cargarLibrosPorCategoria("TODAS LAS CATEGORÍAS");
    }

    // Método del Patrón Factory Method (Pantalla)
    @Override
    public void mostrar(Usuario usuario) {
        this.usuarioActual = usuario;
        this.setTitle("Biblioteca - " + usuario.getNombreCompleto() + " [" + usuario.getRol() + "]");
        cargarLibrosPorCategoria((String) jComboBox1.getSelectedItem());
        this.setVisible(true);
    }

    private void filtrar() {
        if (sorter == null) return;

        List<RowFilter<Object, Object>> filtros = new ArrayList<>();

        String texto = jTextField1.getText().trim();
        if (!texto.isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i)" + Pattern.quote(texto), 1, 4));
        }

        String estado = (String) jComboBox2.getSelectedItem();
        if ("Disponible".equals(estado)) {
            filtros.add(RowFilter.regexFilter("^Disponible$", 6));
        } else if ("No disponible".equals(estado)) {
            filtros.add(RowFilter.regexFilter("^Prestado$", 6));
        }

        sorter.setRowFilter(filtros.isEmpty() ? null : RowFilter.andFilter(filtros));
    }

    private void cargarCategoriasEnComboBox() {
        cargandoDatos = true;
        jComboBox1.removeAllItems();
        jComboBox1.addItem("TODAS LAS CATEGORÍAS");

        String sql = "SELECT nombre_categoria FROM public.categoria ORDER BY nombre_categoria ASC";

        // Uso del Patrón Singleton en Conexion
        try (Statement st = Conexion.getInstance().getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                jComboBox1.addItem(rs.getString("nombre_categoria"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar categorías: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            logger.log(java.util.logging.Level.SEVERE, "Error al cargar categorías", e);
        }
        cargandoDatos = false;
    }

    private void cargarLibrosPorCategoria(String categoriaSeleccionada) {
        jTable1.setRowSorter(null);
        sorter = null;

        String[] columnas = {"ID Libro", "Título", "Año", "Editorial", "Autor(es)", "Categoría(s)", "Estado"};
        DefaultTableModel modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String sql = "SELECT l.id_libro, l.titulo, l.anio_publicacion, e.nombre_editorial, " +
                     "COALESCE(STRING_AGG(DISTINCT a.nombre, ', '), 'Sin autor') AS autor, " +
                     "COALESCE(STRING_AGG(DISTINCT c.nombre_categoria, ', '), 'Sin categoría') AS categoria, " +
                     "CASE WHEN EXISTS (SELECT 1 FROM public.prestamo p " +
                     "    WHERE p.id_libro = l.id_libro " +
                     "    AND (p.fecha_devolucion IS NULL OR p.estado_devolucion = 'Prestado')) " +
                     "THEN 'Prestado' ELSE 'Disponible' END AS estado_libro " +
                     "FROM public.libro l " +
                     "LEFT JOIN public.editorial e ON l.id_editorial = e.id_editorial " +
                     "LEFT JOIN public.libro_autor la ON l.id_libro = la.id_libro " +
                     "LEFT JOIN public.autor a ON la.id_autor = a.id_autor " +
                     "LEFT JOIN public.libro_categoria lc ON l.id_libro = lc.id_libro " +
                     "LEFT JOIN public.categoria c ON lc.id_categoria = c.id_categoria ";

        boolean filtrarCategoria = categoriaSeleccionada != null
                && !categoriaSeleccionada.equals("TODAS LAS CATEGORÍAS");
        if (filtrarCategoria) {
            sql += " WHERE l.id_libro IN (SELECT lc2.id_libro FROM public.libro_categoria lc2 " +
                   "JOIN public.categoria c2 ON lc2.id_categoria = c2.id_categoria " +
                   "WHERE c2.nombre_categoria = ?)";
        }
        sql += " GROUP BY l.id_libro, l.titulo, l.anio_publicacion, e.nombre_editorial ORDER BY l.id_libro";

        try (PreparedStatement pst = Conexion.getInstance().getConnection().prepareStatement(sql)) {
            if (filtrarCategoria) {
                pst.setString(1, categoriaSeleccionada);
            }

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    modeloTabla.addRow(new String[]{
                        rs.getString("id_libro"),
                        rs.getString("titulo"),
                        rs.getString("anio_publicacion"),
                        rs.getString("nombre_editorial"),
                        rs.getString("autor"),
                        rs.getString("categoria"),
                        rs.getString("estado_libro")
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage(),
                    "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            logger.log(java.util.logging.Level.SEVERE, "Error al consultar libros", e);
        }

        jTable1.setModel(modeloTabla);
        sorter = new TableRowSorter<>(modeloTabla);
        jTable1.setRowSorter(sorter);
        filtrar();
    }

    private void prestarLibro() {
        int fila = jTable1.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un recurso de la tabla.");
            return;
        }
        int filaModelo = jTable1.convertRowIndexToModel(fila);
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        int idLibro = Integer.parseInt(modelo.getValueAt(filaModelo, 0).toString());
        String titulo = modelo.getValueAt(filaModelo, 1).toString();
        String categoria = modelo.getValueAt(filaModelo, 5).toString();
        String estado = modelo.getValueAt(filaModelo, 6).toString();

        if (!"Disponible".equals(estado)) {
            JOptionPane.showMessageDialog(this, "El recurso \"" + titulo + "\" no está disponible.",
                    "No disponible", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idUsuario;
        String nombreUsuario;
        Connection con = Conexion.getInstance().getConnection();

        try {
            if (usuarioActual != null) {
                idUsuario = Integer.parseInt(usuarioActual.getId());
                nombreUsuario = usuarioActual.getNombreCompleto();
            } else {
                String documento = JOptionPane.showInputDialog(this, "Documento del usuario:");
                if (documento == null || documento.trim().isEmpty()) return;

                try (PreparedStatement pst = con.prepareStatement(
                        "SELECT id_usuario, nombre, id_rol, bloqueado FROM public.usuario WHERE documento = ?")) {
                    pst.setString(1, documento.trim());
                    try (ResultSet rs = pst.executeQuery()) {
                        if (!rs.next()) {
                            JOptionPane.showMessageDialog(this, "No existe un usuario con ese documento.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (rs.getBoolean("bloqueado")) {
                            JOptionPane.showMessageDialog(this, "Usuario bloqueado por el administrador.",
                                    "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        idUsuario = rs.getInt("id_usuario");
                        nombreUsuario = rs.getString("nombre");
                        String idRol = rs.getString("id_rol");
                        String rolNombre = "2".equals(idRol) ? "BIBLIOTECARIO" : "ESTUDIANTE";
                        int dias = "2".equals(idRol) ? 30 : 7;
                        int max = "2".equals(idRol) ? 6 : 3;
                        usuarioActual = new Usuario(String.valueOf(idUsuario), documento, nombreUsuario, "", rolNombre, false, dias, max);
                    }
                }
            }

            // 1. Verificar si tiene sanciones pendientes (tabla public.sancion sin tilde)
            try (PreparedStatement pstMulta = con.prepareStatement(
                    "SELECT COUNT(*) FROM public.sancion WHERE id_usuario = ? AND estado = 'PENDIENTE'")) {
                pstMulta.setInt(1, idUsuario);
                try (ResultSet rs = pstMulta.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(this,
                                "No puedes realizar préstamos porque tienes sanciones PENDIENTES.",
                                "Usuario Sancionado", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            // 2. Contar préstamos activos actuales del usuario
            int prestamosActivos = 0;
            try (PreparedStatement pstActivos = con.prepareStatement(
                    "SELECT COUNT(*) FROM public.prestamo WHERE id_usuario = ? AND estado_devolucion = 'Prestado'")) {
                pstActivos.setInt(1, idUsuario);
                try (ResultSet rs = pstActivos.executeQuery()) {
                    if (rs.next()) prestamosActivos = rs.getInt(1);
                }
            }

            // 3. Aplicar Patrón Strategy según la categoría/tipo de recurso y el rol del usuario
            contextoPrestamo.seleccionarEstrategiaPorTipo(categoria);
            if (!contextoPrestamo.validarPermisoPrestamo(usuarioActual, prestamosActivos)) {
                JOptionPane.showMessageDialog(this,
                        "Has alcanzado el límite máximo de préstamos simultáneos (" + usuarioActual.getMaxRecursosSimultaneos() + ") o tu cuenta está bloqueada.",
                        "Límite Excedido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            LocalDateTime fechaVencimiento = contextoPrestamo.obtenerFechaVencimiento(usuarioActual);

            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Confirmas el préstamo de \"" + titulo + "\" a " + nombreUsuario + "?\n" +
                    "Rol: " + usuarioActual.getRol() + " | Plazo: " + usuarioActual.getDiasPlazoBase() + " días\n" +
                    "Fecha límite de devolución: " + fechaVencimiento.toLocalDate(),
                    "Confirmar préstamo", JOptionPane.YES_NO_OPTION);
            if (opcion != JOptionPane.YES_OPTION) return;

            // Verificar concurrencia antes de insertar
            try (PreparedStatement pst = con.prepareStatement(
                    "SELECT 1 FROM public.prestamo WHERE id_libro = ? " +
                    "AND (fecha_devolucion IS NULL OR estado_devolucion = 'Prestado')")) {
                pst.setInt(1, idLibro);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(this, "Ese recurso ya fue prestado a otra persona.",
                                "No disponible", JOptionPane.WARNING_MESSAGE);
                        cargarLibrosPorCategoria((String) jComboBox1.getSelectedItem());
                        return;
                    }
                }
            }

            // Registrar préstamo con su fecha_vencimiento calculada por Strategy
            try (PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO public.prestamo (id_libro, id_usuario, fecha_prestamo, fecha_vencimiento, estado_devolucion) " +
                    "VALUES (?, ?, NOW(), ?, 'Prestado')")) {
                pst.setInt(1, idLibro);
                pst.setInt(2, idUsuario);
                pst.setTimestamp(3, Timestamp.valueOf(fechaVencimiento));
                pst.executeUpdate();
            }

            JOptionPane.showMessageDialog(this,
                    "Préstamo registrado exitosamente.\nDevolver antes del: " + fechaVencimiento.toLocalDate(),
                    "Préstamo Exitoso", JOptionPane.INFORMATION_MESSAGE);
            cargarLibrosPorCategoria((String) jComboBox1.getSelectedItem());

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al registrar el préstamo: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            logger.log(java.util.logging.Level.SEVERE, "Error al prestar", e);
        }
    }

    // ===== DEVOLVER =====
    private void devolverLibro() {
        int fila = jTable1.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un recurso de la tabla.");
            return;
        }
        int filaModelo = jTable1.convertRowIndexToModel(fila);
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        int idLibro = Integer.parseInt(modelo.getValueAt(filaModelo, 0).toString());
        String titulo = modelo.getValueAt(filaModelo, 1).toString();
        String estado = modelo.getValueAt(filaModelo, 6).toString();

        if (!"Prestado".equals(estado)) {
            JOptionPane.showMessageDialog(this, "El recurso \"" + titulo + "\" no está prestado.",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Connection con = Conexion.getInstance().getConnection();
        try {
            int idPrestamo;
            int idUsuarioPrestamo;
            String nombreUsuario;
            try (PreparedStatement pst = con.prepareStatement(
                    "SELECT p.id_prestamo, p.id_usuario, u.nombre FROM public.prestamo p " +
                    "LEFT JOIN public.usuario u ON p.id_usuario = u.id_usuario " +
                    "WHERE p.id_libro = ? AND (p.fecha_devolucion IS NULL OR p.estado_devolucion = 'Prestado') " +
                    "ORDER BY p.fecha_prestamo DESC LIMIT 1")) {
                pst.setInt(1, idLibro);
                try (ResultSet rs = pst.executeQuery()) {
                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(this, "No se encontró un préstamo activo para este recurso.");
                        return;
                    }
                    idPrestamo = rs.getInt("id_prestamo");
                    idUsuarioPrestamo = rs.getInt("id_usuario");
                    nombreUsuario = rs.getString("nombre");
                }
            }

            // Validar que el usuario solo devuelva sus propios préstamos
            if (usuarioActual != null && Integer.parseInt(usuarioActual.getId()) != idUsuarioPrestamo) {
                JOptionPane.showMessageDialog(this,
                        "Este recurso está prestado a " + nombreUsuario + ". Solo el titular puede devolverlo.",
                        "Acción no permitida", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Confirmas la devolución de \"" + titulo + "\"?",
                    "Confirmar devolución", JOptionPane.YES_NO_OPTION);
            if (opcion != JOptionPane.YES_OPTION) return;

            try (PreparedStatement pst = con.prepareStatement(
                    "UPDATE public.prestamo SET fecha_devolucion = NOW(), estado_devolucion = 'Devuelto' " +
                    "WHERE id_prestamo = ?")) {
                pst.setInt(1, idPrestamo);
                pst.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Devolución registrada.", "Listo",
                    JOptionPane.INFORMATION_MESSAGE);
            cargarLibrosPorCategoria((String) jComboBox1.getSelectedItem());

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al registrar la devolución: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            logger.log(java.util.logging.Level.SEVERE, "Error al devolver", e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Novela", "Realismo mágico", "Clásicos", "Cuento", "Ciencia ficción", "Fantasía", "Infantil", "Historia", "Ciencia", "Tecnología", "Misterio" }));
        jComboBox1.addActionListener(this::jComboBox1ActionPerformed);

        jTextField1.addActionListener(this::jTextField1ActionPerformed);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disponible", "No disponible", " " }));
        jComboBox2.addActionListener(this::jComboBox2ActionPerformed);

        jLabel1.setText("Titulo o Autor");

        jButton3.setText("Pedir prestado");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jButton4.setText("Devolver");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addGap(22, 22, 22))
        );

        jTabbedPane1.addTab("Prestamos", jPanel2);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 884, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 472, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Sanciones", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if (cargandoDatos) return;
        String categoriaSeleccionada = (String) jComboBox1.getSelectedItem();
        if (categoriaSeleccionada != null) {
            cargarLibrosPorCategoria(categoriaSeleccionada);
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        filtrar();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        filtrar();
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        prestarLibro();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        devolverLibro();
    }//GEN-LAST:event_jButton4ActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new Estudiante().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}