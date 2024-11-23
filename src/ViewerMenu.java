import java.sql.Connection;
import java.util.Scanner;

public class ViewerMenu {
    public static void showMenu(Connection connection, Scanner scanner) { 
        while (true) {
            System.out.println("\n=== Welcome to Library Management System (Viewer) ===");
            System.out.println("1. List All Books");
            System.out.println("2. Find Book by Title");
            System.out.println("3. List All Members");
            System.out.println("4. View Member Details");
            System.out.println("5. Exit");
            System.out.print("Please choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    BookService.listBooks(connection);
                    break;

                case 2:
                    System.out.print("Enter the book title: ");
                    String title = scanner.nextLine();
                    BookService.findBookByTitle(title, connection);
                    break;

                case 3:
                    MemberService.listMembers(connection);
                    break;

                case 4:
                    System.out.print("Enter the member ID: ");
                    int memberId = scanner.nextInt();
                    scanner.nextLine();
                    MemberService.viewMemberDetails(memberId, connection);
                    break;

                case 5:
                    System.out.println("Exiting Viewer Menu...");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
