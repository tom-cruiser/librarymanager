import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.time.LocalDate;

public class BookService {

    public static void addBook(String title, String author, String isbn, Connection connection) {
        String query = "INSERT INTO books (title, author, isbn) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, isbn);
            pstmt.executeUpdate();
            System.out.println("Livre ajouté avec succès !");
        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout du livre : " + e.getMessage());
        }
    }

    public static void listBooks(Connection connection) {
        String query = "SELECT * FROM books";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.printf("ID: %d, Titre: %s, Auteur: %s, ISBN: %s, Disponible: %s\n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getBoolean("is_available") ? "Oui" : "Non");
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des livres : " + e.getMessage());
        }
    }

    public static void findBookByTitle(String title, Connection connection) {
        String query = "SELECT * FROM books WHERE title LIKE ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, "%" + title + "%");
            ResultSet rs = pstmt.executeQuery();
            System.out.println("\nSearch Results:");
            if (rs.next()) {
                System.out.printf("ID: %d, Title: %s, Author: %s, ISBN: %s, Available: %s\n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getBoolean("is_available") ? "Yes" : "No");
            } else {
                System.out.println("No books found matching the title: " + title);
            }
        } catch (Exception e) {
            System.out.println("Error searching for book: " + e.getMessage());
        }
    }

    public static void removeBookByISBN(String isbn, Connection connection) {
        String query = "DELETE FROM books WHERE isbn = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, isbn);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Le livre avec l'ISBN " + isbn + " a été supprimé avec succès.");
            } else {
                System.out.println("Aucun livre trouvé avec l'ISBN " + isbn + ".");
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression du livre : " + e.getMessage());
        }
    }
    
    public static void removeBookByID(int bookId, Connection connection) {
        String query = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, bookId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Le livre avec l'ID " + bookId + " a été supprimé avec succès.");
            } else {
                System.out.println("Aucun livre trouvé avec l'ID " + bookId + ".");
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression du livre : " + e.getMessage());
        }
    }

    public static void viewOverdueBooks(Connection connection) {
        String query = """
            SELECT b.id, b.title, b.author, l.due_date
            FROM books b
            INNER JOIN loans l ON b.id = l.book_id
            WHERE l.return_date IS NULL AND l.due_date < ?""";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setDate(1, Date.valueOf(LocalDate.now()));
            ResultSet rs = pstmt.executeQuery();
            System.out.println("\n=== Livres en retard ===");
            while (rs.next()) {
                System.out.printf("ID: %d, Titre: %s, Auteur: %s, Date d'échéance: %s\n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDate("due_date"));
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des livres en retard : " + e.getMessage());
        }
    }

    public static void updateBookDetails(int bookId, String newTitle, String newAuthor, String newISBN, Connection connection) {
        String query = "UPDATE books SET title = ?, author = ?, isbn = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, newTitle);
            pstmt.setString(2, newAuthor);
            pstmt.setString(3, newISBN);
            pstmt.setInt(4, bookId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Les détails du livre avec l'ID " + bookId + " ont été mis à jour avec succès.");
            } else {
                System.out.println("Aucun livre trouvé avec l'ID " + bookId + ".");
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la mise à jour des détails du livre : " + e.getMessage());
        }
    }

    public static void borrowBook(int bookId, int memberId, Connection connection) {
        String query = "UPDATE books SET is_available = 0, borrowed_by = ? WHERE id = ? AND is_available = 1";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, memberId);
            pstmt.setInt(2, bookId);
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Livre emprunté avec succès !");
            } else {
                System.out.println("Échec : Le livre n'est pas disponible.");
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de l'emprunt du livre : " + e.getMessage());
        }
    }

    public static void returnBook(int bookId, Connection connection) {
        String query = "UPDATE books SET is_available = 1, borrowed_by = NULL WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, bookId);
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Livre retourné avec succès !");
            } else {
                System.out.println("Échec : Livre introuvable ou déjà disponible.");
            }
        } catch (Exception e) {
            System.out.println("Erreur lors du retour du livre : " + e.getMessage());
        }
    }

    public static void manageUsers(Connection connection) {
        String query = "SELECT * FROM users";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            System.out.println("\nListe des utilisateurs :");
            while (rs.next()) {
                System.out.printf("ID: %d, Nom d'utilisateur: %s, Rôle: %s\n",
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("role"));
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la gestion des utilisateurs : " + e.getMessage());
        }
    }
}
