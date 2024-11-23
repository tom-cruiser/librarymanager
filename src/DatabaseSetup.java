import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {

    // Méthode pour créer les tables
    public static void initializeDatabase(Connection connection) {
        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL,
                role TEXT CHECK(role IN ('Admin', 'Viewer')) NOT NULL
            );
        """;

        String createBooksTable = """
            CREATE TABLE IF NOT EXISTS books (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                author TEXT NOT NULL,
                isbn TEXT UNIQUE NOT NULL,
                is_available BOOLEAN DEFAULT 1,
                popularity_score INTEGER DEFAULT 0
            );
        """;

        String createMembersTable = """
            CREATE TABLE IF NOT EXISTS members (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                member_type TEXT CHECK(member_type IN ('Regular', 'Premium')) NOT NULL,
                borrowed_count INTEGER DEFAULT 0
            );
        """;

        String createBorrowedBooksTable = """
            CREATE TABLE IF NOT EXISTS borrowed_books (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                member_id INTEGER NOT NULL,
                book_id INTEGER NOT NULL,
                borrow_date DATE NOT NULL,
                return_date DATE,
                FOREIGN KEY (member_id) REFERENCES members (id),
                FOREIGN KEY (book_id) REFERENCES books (id)
            );
        """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createBooksTable);
            stmt.execute(createMembersTable);
            stmt.execute(createBorrowedBooksTable);
            System.out.println("Tables créées ou déjà existantes !");
        } catch (Exception e) {
            System.out.println("Erreur lors de la création des tables : " + e.getMessage());
        }
    }

    //Données initiales
    public static void seedDatabase(Connection connection) {
        String insertAdmin = """
            INSERT OR IGNORE INTO users (username, password, role) 
            VALUES ('victoire', 'admin123', 'Admin'),
                     ('vic', 'admin8123', 'Admin');
        """;

        String insertViewer = """
            INSERT OR IGNORE INTO users (username, password, role) 
            VALUES ('viewer', 'viewer123', 'Viewer');
        """;

        String insertBooks = """
            INSERT OR IGNORE INTO books (title, author, isbn) 
            VALUES 
            ('Effective Java', 'Joshua Bloch', '978-0134685991'),
            ('Clean Code', 'Robert C. Martin', '978-0132350884'),
            ('Java: The Complete Reference', 'Herbert Schildt', '978-1260440232');
        """;

        String insertMembers = """
            INSERT OR IGNORE INTO members (name, member_type) 
            VALUES 
            ('Kayembe', 'Regular'),
            ('Mwami', 'Premium');
        """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(insertAdmin);
            stmt.execute(insertViewer);
            stmt.execute(insertBooks);
            stmt.execute(insertMembers);
            System.out.println("Données initiales ajoutées !");
        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout des données initiales : " + e.getMessage());
        }
    }
}
