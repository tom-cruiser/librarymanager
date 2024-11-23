import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserService {

    // Méthode pour authentifier l'utilisateur
    public static String authenticate(String username, String password, Connection connection) {
        String query = "SELECT role FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role"); // Retourne le role (Admin/Viewer)
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de l'authentification : " + e.getMessage());
        }
        return null; // lorsq les info sont incorrect
    }
}
