package proyecto.biblioteca; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

   
    private static final String URL = "jdbc:postgresql://aws-1-us-east-2.pooler.supabase.com:5432/postgres?sslmode=require";
    
  
    private static final String USUARIO = "postgres.tsgffqsqorqvjxwteffw";
    
    
    private static final String CONTRASENA = "?";

    public static Connection conectar() {
        Connection conexion = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Falta agregar el archivo .jar de PostgreSQL. " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error de Supabase: " + e.getMessage());
        }
        return conexion;
    }
}
