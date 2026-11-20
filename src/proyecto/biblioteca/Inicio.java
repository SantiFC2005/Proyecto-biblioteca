package proyecto.biblioteca;

import java.awt.Color;
import java.awt.Font;
import java.sql.*;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import proyecto.biblioteca.Factory.AdminCreator;
import proyecto.biblioteca.Factory.BibliotecarioCreator;
import proyecto.biblioteca.Factory.EstudianteCreator;
import proyecto.biblioteca.Factory.PantallaCreator;
import proyecto.biblioteca.modelo.Usuario;

public class Inicio extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Inicio.class.getName());

    public Inicio() {
        initComponents();
        Color azul = new Color(31, 58, 95);

        jTabbedPane2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        jTabbedPane2.setForeground(azul);
        jTabbedPane2.setBackground(Color.WHITE);
        jTabbedPane2.setBorder(BorderFactory.createEmptyBorder());
        jTabbedPane2.setFocusable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        correo_txt = new javax.swing.JTextField();
        contraseña_txt1 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        correo_txt2 = new javax.swing.JTextField();
        Ncontraseña_txt = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        Ccontraseña_txt = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();

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
        setBackground(new java.awt.Color(255, 0, 0));

        jPanel1.setBackground(new java.awt.Color(241, 245, 249));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(36, 59, 83));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("BIBLIOTECA DE ALEJANDRIA");

        jTabbedPane2.setPreferredSize(new java.awt.Dimension(228, 215));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        correo_txt.addActionListener(this::correo_txtActionPerformed);

        contraseña_txt1.addActionListener(this::contraseña_txt1ActionPerformed);

        jButton5.setBackground(new java.awt.Color(20, 184, 166));
        jButton5.setText("Iniciar sesión");
        jButton5.addActionListener(this::jButton5ActionPerformed);

        jLabel9.setFont(new java.awt.Font("Elephant", 0, 12)); // NOI18N
        jLabel9.setText("CONTRASEÑA");

        jLabel11.setFont(new java.awt.Font("Elephant", 0, 12)); // NOI18N
        jLabel11.setText("CORREO");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel9)
                        .addComponent(contraseña_txt1, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel11)
                        .addComponent(correo_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(correo_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contraseña_txt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(15, 15, 15))
        );

        jTabbedPane2.addTab("INICIAR SESION", jPanel4);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        correo_txt2.addActionListener(this::correo_txt2ActionPerformed);

        Ncontraseña_txt.addActionListener(this::Ncontraseña_txtActionPerformed);

        jButton6.setBackground(new java.awt.Color(20, 184, 166));
        jButton6.setText("Regitrarse");
        jButton6.addActionListener(this::jButton6ActionPerformed);

        jLabel12.setFont(new java.awt.Font("Elephant", 0, 12)); // NOI18N
        jLabel12.setText("NUEVA CONTRASEÑA");

        jLabel14.setFont(new java.awt.Font("Elephant", 0, 12)); // NOI18N
        jLabel14.setText("CORREO");

        jLabel13.setFont(new java.awt.Font("Elephant", 0, 12)); // NOI18N
        jLabel13.setText("CONFIRMAR CONTRASEÑA");

        Ccontraseña_txt.addActionListener(this::Ccontraseña_txtActionPerformed);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(correo_txt2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Ccontraseña_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(Ncontraseña_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(correo_txt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Ncontraseña_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Ccontraseña_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 253, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 26, Short.MAX_VALUE)))
        );

        jTabbedPane2.addTab("REGISTRARSE", jPanel5);

        jPanel2.setBackground(new java.awt.Color(241, 245, 249));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(241, 245, 249));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Ccontraseña_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Ccontraseña_txtActionPerformed
    }//GEN-LAST:event_Ccontraseña_txtActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        String correo = correo_txt2.getText().trim();
        String nueva = Ncontraseña_txt.getText();
        String confirmar = Ccontraseña_txt.getText();

        if (correo.isEmpty() || nueva.isEmpty() || confirmar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe tu correo y la contraseña");
            return;
        }
        if (!correo.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
            JOptionPane.showMessageDialog(this, "El correo no tiene un formato válido");
            return;
        }
        if (nueva.length() < 6) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 6 caracteres");
            return;
        }
        if (!nueva.equals(confirmar)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden");
            return;
        }

        javax.swing.JTextField txtNombre = new javax.swing.JTextField(20);
        javax.swing.JTextField txtDocumento = new javax.swing.JTextField(20);
        javax.swing.JTextField txtTelefono = new javax.swing.JTextField(20);
        javax.swing.JTextField txtFecha = new javax.swing.JTextField(20);

        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(0, 1, 0, 4));
        panel.add(new javax.swing.JLabel("Nombre completo:"));
        panel.add(txtNombre);
        panel.add(new javax.swing.JLabel("Cédula:"));
        panel.add(txtDocumento);
        panel.add(new javax.swing.JLabel("Teléfono (opcional):"));
        panel.add(txtTelefono);
        panel.add(new javax.swing.JLabel("Fecha de nacimiento (dd/mm/aaaa):"));
        panel.add(txtFecha);

        java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter
                .ofPattern("dd/MM/uuuu")
                .withResolverStyle(java.time.format.ResolverStyle.STRICT);

        String nombre, documento, telefono;
        java.time.LocalDate fechaNac;

        while (true) {
            int opcion = JOptionPane.showConfirmDialog(this, panel, "Completa tus datos",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (opcion != JOptionPane.OK_OPTION) {
                return;
            }

            nombre = txtNombre.getText().trim();
            documento = txtDocumento.getText().trim();
            telefono = txtTelefono.getText().trim();

            fechaNac = null;
            try {
                fechaNac = java.time.LocalDate.parse(txtFecha.getText().trim(), fmt);
            } catch (java.time.format.DateTimeParseException e) {
            }

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
            } else if (!documento.matches("\\d{5,12}")) {
                JOptionPane.showMessageDialog(this, "La cédula debe tener solo números (5 a 12 dígitos)");
            } else if (!telefono.isEmpty() && !telefono.matches("\\d{7,10}")) {
                JOptionPane.showMessageDialog(this, "El teléfono debe tener solo números (7 a 10 dígitos)");
            } else if (fechaNac == null) {
                JOptionPane.showMessageDialog(this, "Fecha no válida. Usa el formato dd/mm/aaaa, por ejemplo 25/03/2001");
            } else if (fechaNac.isAfter(java.time.LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "La fecha de nacimiento no puede ser futura");
            } else if (fechaNac.isBefore(java.time.LocalDate.now().minusYears(120))) {
                JOptionPane.showMessageDialog(this, "La fecha de nacimiento no es válida");
            } else {
                break;
            }
        }

        String sqlExiste = "SELECT 1 FROM usuario WHERE correo = ? OR documento = ?";
        String sqlInsert = "INSERT INTO usuario (nombre, documento, telefono, correo, contrasenia, fecha_nacimiento, id_rol, bloqueado) "
                + "VALUES (?, ?, ?, ?, ?, ?, 3, false)"; // 3 = Estudiante

        // Uso del Patrón Singleton en Conexion (NO cerramos la conexión global con try-with-resources)
        Connection con = Conexion.getInstance().getConnection();
        if (con == null) {
            JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
            return;
        }

        try {
            try (PreparedStatement ps = con.prepareStatement(sqlExiste)) {
                ps.setString(1, correo);
                ps.setString(2, documento);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(this, "Ya existe un usuario con ese correo o cédula");
                        return;
                    }
                }
            }

            try (PreparedStatement ps = con.prepareStatement(sqlInsert)) {
                ps.setString(1, nombre);
                ps.setString(2, documento);
                if (telefono.isEmpty()) {
                    ps.setNull(3, Types.VARCHAR);
                } else {
                    ps.setString(3, telefono);
                }
                ps.setString(4, correo);
                ps.setString(5, nueva);
                ps.setDate(6, java.sql.Date.valueOf(fechaNac));
                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Registro exitoso. Ya puedes iniciar sesión");
            correo_txt2.setText("");
            Ncontraseña_txt.setText("");
            Ccontraseña_txt.setText("");
            jTabbedPane2.setSelectedIndex(0);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar: " + ex.getMessage());
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void Ncontraseña_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Ncontraseña_txtActionPerformed
    }//GEN-LAST:event_Ncontraseña_txtActionPerformed

    private void correo_txt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_correo_txt2ActionPerformed
    }//GEN-LAST:event_correo_txt2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String correo = correo_txt.getText().trim();
        String contrasenia = contraseña_txt1.getText();

        String sql = "SELECT id_usuario, nombre, documento, correo, contrasenia, id_rol, bloqueado, motivo_bloqueo " +
                    "FROM usuario WHERE correo = ?";

        Connection conexion = Conexion.getInstance().getConnection();
        if (conexion == null) {
            JOptionPane.showMessageDialog(this, "No se pudo conectar con la base de datos.");
            return;
        }

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String p = rs.getString("contrasenia");
                    String idRol = rs.getString("id_rol");
                    boolean bloqueado = rs.getBoolean("bloqueado");

                    if (!contrasenia.equals(p)) {
                        JOptionPane.showMessageDialog(this, "CONTRASEÑA INCORRECTA", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Verificar si fue bloqueado por el Administrador
                    if (bloqueado) {
                        String motivo = rs.getString("motivo_bloqueo");
                        JOptionPane.showMessageDialog(this,
                                "Tu cuenta está BLOQUEADA por el administrador.\nMotivo: " +
                                (motivo != null ? motivo : "Sanción administrativa"),
                                "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Configurar reglas según rol: 1 = Admin, 2 = Docente (30 días), 3 = Estudiante (7 días)
                    String nombreRol;
                    int diasPlazo;
                    int maxSimultaneos;

                    switch (idRol) {
                        case "1":
                            nombreRol = "ADMINISTRADOR";
                            diasPlazo = 0;
                            maxSimultaneos = 0;
                            break;
                        case "2":
                            nombreRol = "DOCENTE";
                            diasPlazo = 30;
                            maxSimultaneos = 6;
                            break;
                        case "3":
                        default:
                            nombreRol = "ESTUDIANTE";
                            diasPlazo = 7;
                            maxSimultaneos = 3;
                            break;
                    }

                    Usuario usuarioLogueado = new Usuario(
                            String.valueOf(rs.getInt("id_usuario")),
                            rs.getString("documento"),
                            rs.getString("nombre"),
                            rs.getString("correo"),
                            nombreRol,
                            bloqueado,
                            diasPlazo,
                            maxSimultaneos
                    );

                    // Aplicación del Patrón Factory Method
                    PantallaCreator creador = obtenerCreador(idRol);
                    if (creador != null) {
                        creador.abrirPantalla(usuarioLogueado);
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "ROL NO VÁLIDO");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "EL USUARIO NO EXISTE");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error en la consulta: " + ex.getMessage());
            logger.log(java.util.logging.Level.SEVERE, "Error en login", ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void contraseña_txt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contraseña_txt1ActionPerformed
    }//GEN-LAST:event_contraseña_txt1ActionPerformed

    private void correo_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_correo_txtActionPerformed
    }//GEN-LAST:event_correo_txtActionPerformed

    private PantallaCreator obtenerCreador(String idRol) {
        switch (idRol) {
            case "1":
                return new AdminCreator();
            case "2":
                return new DocenteCreator();
            case "3":
                return new EstudianteCreator();
            default:
                return null;
        }
    }

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

        java.awt.EventQueue.invokeLater(() -> new Inicio().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Ccontraseña_txt;
    private javax.swing.JTextField Ncontraseña_txt;
    private javax.swing.JTextField contraseña_txt1;
    private javax.swing.JTextField correo_txt;
    private javax.swing.JTextField correo_txt2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JTabbedPane jTabbedPane2;
    // End of variables declaration//GEN-END:variables
}