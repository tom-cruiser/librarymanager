import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberService {

    public static void addMember(String name, String type, Connection connection) {
        String query = "INSERT INTO members (name, member_type) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.executeUpdate();
            System.out.println("Membre ajouté avec succès !");
        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout du membre : " + e.getMessage());
        }
    }

    public static void listMembers(Connection connection) {
        String query = "SELECT * FROM members";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            System.out.println("\nListe des membres :");
            while (rs.next()) {
                System.out.printf("ID: %d, Nom: %s, Type: %s, Livres empruntés: %d\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("member_type"),
                        rs.getInt("borrowed_count"));
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des membres : " + e.getMessage());
        }
    }

    public static void viewMemberDetails(int memberId, Connection connection) {
        String query = "SELECT * FROM members WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.printf("\nDétails du membre :\nID: %d\nNom: %s\nType: %s\nLivres empruntés: %d\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("member_type"),
                        rs.getInt("borrowed_count"));
            } else {
                System.out.println("Aucun membre trouvé avec cet ID.");
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des détails du membre : " + e.getMessage());
        }
    }

    public static void removeMember(int memberId, Connection connection) {
        String query = "DELETE FROM members WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, memberId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Membre supprimé avec succès !");
            } else {
                System.out.println("Aucun membre trouvé avec cet ID.");
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression du membre : " + e.getMessage());
        }
    }
}
