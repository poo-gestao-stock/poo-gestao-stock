package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
    public static Connection getConnection() {
        try {
            var conexao = DriverManager.getConnection(
                "jdbc:sqlserver://localhost:1433;databaseName=GestaoStock;user=sa;password=1q2w3e4r@#$;encrypt=true;trustServerCertificate=true;"
            );

            System.out.println("CONECTADO AO SERVIDOR o/");

            return conexao;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}