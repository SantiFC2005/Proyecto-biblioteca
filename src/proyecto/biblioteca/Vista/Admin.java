package proyecto.biblioteca.Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import proyecto.biblioteca.Conexion;
import proyecto.biblioteca.Factory.Pantalla;
import proyecto.biblioteca.Inicio;
import proyecto.biblioteca.modelo.Usuario;

public class Admin extends javax.swing.JFrame implements Pantalla {

    private Usuario usuarioActual;

    
    private JTable tablaUsuarios;
    private DefaultTableModel modeloUsuarios;
    private JTextField txtNombre, txtDocumento, txtCorreo, txtContrasenia, txtTelefono;
    private JComboBox<String> cmbRol;

    private JTable tablaMultas;
    private DefaultTableModel modeloMultas;

    public Admin() {
        initComponents();
        construirInterfazAdmin();
        setSize(950, 600);
        setLocationRelativeTo(null);
    }

    @Override
    public void mostrar(Usuario usuario) {
        if (usuario == null || !"ADMINISTRADOR".equalsIgnoreCase(usuario.getRol())) {
            JOptionPane.showMessageDialog(null, "Acceso denegado: Se requiere rol de Administrador.",
                    "Error de Autorización", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.usuarioActual = usuario;
        this.setTitle("Panel de Administración - " + usuario.getNombreCompleto());
        cargarTablaUsuarios();
        cargarTablaMultas();
        this.setVisible(true);
    }

    private void construirInterfazAdmin() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());


        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(31, 58, 95));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel lblTitulo = new JLabel("ADMINISTRACIÓN DE BIBLIOTECA — CONTROL DE USUARIOS Y MULTAS");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panelSuperior.add(lblTitulo, BorderLayout.WEST);

        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.addActionListener(e -> {
            new Inicio().setVisible(true);
            this.dispose();
        });
        panelSuperior.add(btnCerrarSesion, BorderLayout.EAST);
        add(panelSuperior, BorderLayout.NORTH);

        // Contenedor de Pestañas
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));

        tabs.addTab("1. Registro y Bloqueo de Usuarios", crearPanelUsuarios());
        tabs.addTab("2. Gestión de Multas y Sanciones", crearPanelMultas());

        add(tabs, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private JPanel crearPanelUsuarios() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Formulario superior de registro
        JPanel formPanel = new JPanel(new GridLayout(2, 6, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Registrar Nuevo Usuario"));

        txtNombre = new JTextField();
        txtDocumento = new JTextField();
        txtCorreo = new JTextField();
        txtContrasenia = new JTextField();
        txtTelefono = new JTextField();
        cmbRol = new JComboBox<>(new String[]{"3 - ESTUDIANTE", "2 - DOCENTE", "1 - ADMINISTRADOR"});

        formPanel.add(new JLabel("Nombre Completo:"));
        formPanel.add(new JLabel("Documento / Cédula:"));
        formPanel.add(new JLabel("Correo Electrónico:"));
        formPanel.add(new JLabel("Contraseña:"));
        formPanel.add(new JLabel("Teléfono:"));
        formPanel.add(new JLabel("Rol:"));

        formPanel.add(txtNombre);
        formPanel.add(txtDocumento);
        formPanel.add(txtCorreo);
        formPanel.add(txtContrasenia);
        formPanel.add(txtTelefono);
        formPanel.add(cmbRol);

        panel.add(formPanel, BorderLayout.NORTH);

        // Tabla central de usuarios
        String[] cols = {"ID", "Nombre", "Documento", "Correo", "Rol", "Estado", "Motivo Bloqueo"};
        modeloUsuarios = new DefaultTableModel(null, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaUsuarios = new JTable(modeloUsuarios);
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(tablaUsuarios), BorderLayout.CENTER);

        // Botonera inferior
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));

        JButton btnRegistrar = new JButton("Registrar Usuario");
        btnRegistrar.setBackground(new Color(20, 184, 166));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.addActionListener(e -> registrarUsuario());

        JButton btnBloquear = new JButton("Bloquear Usuario");
        btnBloquear.setBackground(new Color(220, 38, 38));
        btnBloquear.setForeground(Color.WHITE);
        btnBloquear.addActionListener(e -> cambiarEstadoBloqueo(true));

        JButton btnDesbloquear = new JButton("Desbloquear Usuario");
        btnDesbloquear.setBackground(new Color(37, 99, 235));
        btnDesbloquear.setForeground(Color.WHITE);
        btnDesbloquear.addActionListener(e -> cambiarEstadoBloqueo(false));

        JButton btnRefrescar = new JButton("Actualizar Lista");
        btnRefrescar.addActionListener(e -> cargarTablaUsuarios());

        botonesPanel.add(btnRegistrar);
        botonesPanel.add(btnBloquear);
        botonesPanel.add(btnDesbloquear);
        botonesPanel.add(btnRefrescar);

        panel.add(botonesPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelMultas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"ID Multa", "ID Usuario", "Usuario", "Documento", "Monto ($)", "Motivo", "Estado", "Fecha"};
        modeloMultas = new DefaultTableModel(null, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaMultas = new JTable(modeloMultas);
        tablaMultas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(tablaMultas), BorderLayout.CENTER);

        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));

        JButton btnAplicarMulta = new JButton("Aplicar Nueva Multa");
        btnAplicarMulta.setBackground(new Color(217, 119, 6));
        btnAplicarMulta.setForeground(Color.WHITE);
        btnAplicarMulta.addActionListener(e -> aplicarMulta());

        JButton btnQuitarMulta = new JButton("Quitar / Anular Multa Seleccionada");
        btnQuitarMulta.setBackground(new Color(22, 163, 74));
        btnQuitarMulta.setForeground(Color.WHITE);
        btnQuitarMulta.addActionListener(e -> quitarMulta());

        JButton btnRefrescar = new JButton("Actualizar Multas");
        btnRefrescar.addActionListener(e -> cargarTablaMultas());

        botonesPanel.add(btnAplicarMulta);
        botonesPanel.add(btnQuitarMulta);
        botonesPanel.add(btnRefrescar);

        panel.add(botonesPanel, BorderLayout.SOUTH);
        return panel;
    }

    // ==================== LÓGICA DE BASE DE DATOS (SUPABASE) ====================

    private void cargarTablaUsuarios() {
        modeloUsuarios.setRowCount(0);
        String sql = "SELECT id_usuario, nombre, documento, correo, id_rol, bloqueado, motivo_bloqueo " +
                     "FROM public.usuario ORDER BY id_usuario ASC";
        try (Statement st = Conexion.getInstance().getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String idRol = rs.getString("id_rol");
                String rolTexto = "1".equals(idRol) ? "ADMINISTRADOR" : ("2".equals(idRol) ? "DOCENTE" : "ESTUDIANTE");
                boolean bloqueado = rs.getBoolean("bloqueado");
                modeloUsuarios.addRow(new Object[]{
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("documento"),
                    rs.getString("correo"),
                    rolTexto,
                    bloqueado ? "BLOQUEADO" : "ACTIVO",
                    rs.getString("motivo_bloqueo") != null ? rs.getString("motivo_bloqueo") : ""
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando usuarios: " + e.getMessage());
        }
    }

    private void registrarUsuario() {
        String nombre = txtNombre.getText().trim();
        String doc = txtDocumento.getText().trim();
        String correo = txtCorreo.getText().trim();
        String pass = txtContrasenia.getText().trim();
        String tel = txtTelefono.getText().trim();
        String rolSel = (String) cmbRol.getSelectedItem();
        int idRol = Integer.parseInt(rolSel.substring(0, 1));

        if (nombre.isEmpty() || doc.isEmpty() || correo.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre, documento, correo y contraseña son obligatorios.");
            return;
        }

        String sql = "INSERT INTO public.usuario (nombre, documento, telefono, correo, contrasenia, fecha_nacimiento, id_rol, bloqueado) " +
                     "VALUES (?, ?, ?, ?, ?, CURRENT_DATE, ?, false)";

        try (PreparedStatement ps = Conexion.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, doc);
            ps.setString(3, tel.isEmpty() ? null : tel);
            ps.setString(4, correo);
            ps.setString(5, pass);
            ps.setInt(6, idRol);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
            txtNombre.setText("");
            txtDocumento.setText("");
            txtCorreo.setText("");
            txtContrasenia.setText("");
            txtTelefono.setText("");
            cargarTablaUsuarios();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al registrar usuario: " + e.getMessage());
        }
    }

    private void cambiarEstadoBloqueo(boolean bloquear) {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario de la tabla.");
            return;
        }

        int idUsuario = (int) modeloUsuarios.getValueAt(fila, 0);
        String nombreUsuario = modeloUsuarios.getValueAt(fila, 1).toString();
        String rolUsuario = modeloUsuarios.getValueAt(fila, 4).toString();

        if ("ADMINISTRADOR".equals(rolUsuario)) {
            JOptionPane.showMessageDialog(this, "No se puede bloquear a otro Administrador.",
                    "Acción no permitida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String motivo = null;
        if (bloquear) {
            motivo = JOptionPane.showInputDialog(this, "Ingresa el motivo del bloqueo para " + nombreUsuario + ":");
            if (motivo == null || motivo.trim().isEmpty()) return;
        }

        String sql = "UPDATE public.usuario SET bloqueado = ?, motivo_bloqueo = ? WHERE id_usuario = ?";
        try (PreparedStatement ps = Conexion.getInstance().getConnection().prepareStatement(sql)) {
            ps.setBoolean(1, bloquear);
            ps.setString(2, bloquear ? motivo.trim() : null);
            ps.setInt(3, idUsuario);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, bloquear
                    ? "Usuario " + nombreUsuario + " ha sido BLOQUEADO."
                    : "Usuario " + nombreUsuario + " ha sido DESBLOQUEADO.");
            cargarTablaUsuarios();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar estado: " + e.getMessage());
        }
    }

    private void cargarTablaMultas() {
        modeloMultas.setRowCount(0);
        String sql = "SELECT m.id_multa, u.id_usuario, u.nombre, u.documento, m.monto, m.motivo, m.estado, m.fecha_registro " +
                     "FROM public.multa m JOIN public.usuario u ON m.id_usuario = u.id_usuario " +
                     "ORDER BY m.id_multa DESC";
        try (Statement st = Conexion.getInstance().getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                modeloMultas.addRow(new Object[]{
                    rs.getInt("id_multa"),
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("documento"),
                    rs.getBigDecimal("monto"),
                    rs.getString("motivo"),
                    rs.getString("estado"),
                    rs.getTimestamp("fecha_registro")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando multas: " + e.getMessage());
        }
    }

    private void aplicarMulta() {
        String documento = JOptionPane.showInputDialog(this, "Documento del Estudiante o Docente a multar:");
        if (documento == null || documento.trim().isEmpty()) return;

        Connection con = Conexion.getInstance().getConnection();
        try {
            int idUsuario;
            String nombreUsuario;
            String idRol;

            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT id_usuario, nombre, id_rol FROM public.usuario WHERE documento = ?")) {
                ps.setString(1, documento.trim());
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(this, "No se encontró ningún usuario con ese documento.");
                        return;
                    }
                    idUsuario = rs.getInt("id_usuario");
                    nombreUsuario = rs.getString("nombre");
                    idRol = rs.getString("id_rol");
                }
            }

            if ("1".equals(idRol)) {
                JOptionPane.showMessageDialog(this, "No se pueden aplicar multas a un Administrador.");
                return;
            }

            String montoStr = JOptionPane.showInputDialog(this, "Monto de la multa ($) para " + nombreUsuario + ":");
            if (montoStr == null || montoStr.trim().isEmpty()) return;
            double monto = Double.parseDouble(montoStr.trim());

            String motivo = JOptionPane.showInputDialog(this, "Motivo de la multa (ej. Retraso en entrega / Daño):");
            if (motivo == null || motivo.trim().isEmpty()) return;

            try (PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO public.multa (id_usuario, monto, motivo, estado) VALUES (?, ?, ?, 'PENDIENTE')")) {
                ps.setInt(1, idUsuario);
                ps.setDouble(2, monto);
                ps.setString(3, motivo.trim());
                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Multa aplicada correctamente a " + nombreUsuario + ".");
            cargarTablaMultas();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "El monto debe ser un número válido.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al aplicar multa: " + e.getMessage());
        }
    }

    private void quitarMulta() {
        int fila = tablaMultas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una multa de la tabla.");
            return;
        }

        int idMulta = (int) modeloMultas.getValueAt(fila, 0);
        String usuario = modeloMultas.getValueAt(fila, 2).toString();
        String estadoActual = modeloMultas.getValueAt(fila, 6).toString();

        if (!"PENDIENTE".equalsIgnoreCase(estadoActual)) {
            JOptionPane.showMessageDialog(this, "Esta multa ya se encuentra en estado: " + estadoActual);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Deseas quitar/anular la multa #" + idMulta + " de " + usuario + "?",
                "Confirmar Anulación", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try (PreparedStatement ps = Conexion.getInstance().getConnection().prepareStatement(
                "UPDATE public.multa SET estado = 'ANULADA' WHERE id_multa = ?")) {
            ps.setInt(1, idMulta);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Multa anulada. El usuario ya puede volver a pedir préstamos.");
            cargarTablaMultas();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al anular multa: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Admin().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}