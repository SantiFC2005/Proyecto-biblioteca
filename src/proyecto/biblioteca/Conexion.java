package proyecto.biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static Conexion instancia;
    private Connection connection;
    private static final String URL = "jdbc:postgresql://aws-1-us-east-2.pooler.supabase.com:5432/postgres?sslmode=require";
    private static final String USER = "postgres.tsgffqsqorqvjxwteffw";
    private static final String PASSWORD = "Santi_200520";

    private Conexion() {
        conectar();
    }

    private void conectar() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error conectando a Supabase: " + e.getMessage());
        }
    }

    public static synchronized Conexion getInstance() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                conectar();
            }
        } catch (SQLException e) {
            conectar();
        }
        return connection;
    }
}