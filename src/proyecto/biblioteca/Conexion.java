package proyecto.biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mariadb://localhost:3306/biblioteca_db";
    private static final String USUARIO = "root";
    private static final String CONTRASEÑA = ""; 

    public static Connection conectar() {
        Connection conexion = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
            System.out.println("¡Conexión exitosa a MariaDB!");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver de MariaDB. " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error de conexión a la base de datos: " + e.getMessage());
        }
        return conexion;
    }


    public static void main(String[] args) {
        conectar();
    }
}