import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:library.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connexion SQLite établie avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à SQLite : " + e.getMessage());
        }
        return conn;
    }
}
