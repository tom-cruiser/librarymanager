import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.connect()) {
            System.out.println("Bienvenue dans le système de gestion de bibliothèque !");

            DatabaseSetup.initializeDatabase(connection);
            DatabaseSetup.seedDatabase(connection);

            // Authen
            Scanner scanner = new Scanner(System.in);
            System.out.print("Nom d'utilisateur : ");
            String username = scanner.nextLine();
            System.out.print("Mot de passe : ");
            String password = scanner.nextLine();

            // Verifie info
            String role = UserService.authenticate(username, password, connection);

            if (role == null) {
                System.out.println("Identifiants incorrects. Veuillez réessayer.");
            }

            System.out.println("Connexion réussie en tant que " + role + "!");

            // Redirection
            if (role.equalsIgnoreCase("Admin")) {
                AdminMenu.showMenu(connection, scanner); // Menu Admin
            } else if (role.equalsIgnoreCase("Viewer")) {
                ViewerMenu.showMenu(connection, scanner); // Menu Viewer
            }

            scanner.close();
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}
