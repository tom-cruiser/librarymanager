import java.sql.Connection;
import java.util.Scanner;

public class AdminMenu {
    public static void showMenu(Connection connection, Scanner scanner) {
        while (true) {
            System.out.println("\n=== Menu Admin ===");
            System.out.println("1. Ajouter un livre");
            System.out.println("2. Lister tous les livres");
            System.out.println("3. Trouver les livres");
            System.out.println("4. Emprunt les livres");
            System.out.println("5. Retourne livres");
            System.out.println("6. Ajouter un membre");
            System.out.println("7. Lister  membres");
            System.out.println("8. Voir details de membres");
            System.out.println("9. Supprimer un livre");
            System.out.println("10. Supprimer membre");
            System.out.println("11. Metre a jour les details du livre");
            System.out.println("12. Overdue Books");
            System.out.println("13. Manage Users");
            System.out.println("14. Exit");


            System.out.print("Choisissez une option : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    System.out.print("Titre du livre : ");
                    String title = scanner.nextLine();
                    System.out.print("Auteur du livre : ");
                    String author = scanner.nextLine();
                    System.out.print("ISBN : ");
                    String isbn = scanner.nextLine();
                    BookService.addBook(title, author, isbn, connection);
                    break;

                case 2:
                    BookService.listBooks(connection);
                    break;

                case 3:
                System.out.print("Enter the book title: ");
                String title1 = scanner.nextLine();
                BookService.findBookByTitle(title1, connection);
                break;

                case 4:
                    System.out.print("Entrez l'ID du livre : ");
                    int borrowBookId = scanner.nextInt();
                    System.out.print("Entrez l'ID du membre : ");
                    int borrowerId = scanner.nextInt();
                    scanner.nextLine();
                    BookService.borrowBook(borrowBookId, borrowerId, connection);
                    break;

                case 5:
                    System.out.print("Entrez l'ID du livre à retourner : ");
                    int returnBookId = scanner.nextInt();
                    scanner.nextLine(); 
                    BookService.returnBook(returnBookId, connection);
                    break;

                case 6:
                    System.out.print("Nom du membre : ");
                    String name = scanner.nextLine();
                    System.out.print("Type de membre (Regular/Premium) : ");
                    String type = scanner.nextLine();
                    MemberService.addMember(name, type, connection);
                    break;

                case 7:
                    MemberService.listMembers(connection);
                    break;

                case 8:
                    System.out.print("Enter the member ID: ");
                    int memberId = scanner.nextInt();
                    scanner.nextLine(); 
                    MemberService.viewMemberDetails(memberId, connection);
                    break;

                case 9:
                    System.out.print("Entrez l'ISBN ou id du livre à supprimer : ");
                    String isbnToRemove = scanner.nextLine();
                    BookService.removeBookByISBN(isbnToRemove, connection);
                    break;

                case 10:
                System.out.print("Entrez l'ID du membre à supprimer : ");
                int memberIdToRemove = scanner.nextInt();
                scanner.nextLine();
                MemberService.removeMember(memberIdToRemove, connection);
                break;


                case 11:
                System.out.print("Entrez l'ID du livre à mettre à jour : ");
                int bookId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Nouveau titre : ");
                String newTitle = scanner.nextLine();
                System.out.print("Nouvel auteur : ");
                String newAuthor = scanner.nextLine();
                System.out.print("Nouveau ISBN : ");
                String newISBN = scanner.nextLine();
                BookService.updateBookDetails(bookId, newTitle, newAuthor, newISBN, connection);
                break;

                case 12:
                    BookService.viewOverdueBooks(connection);
                    break;
                
                case 13:
                    BookService.manageUsers(connection);
                    break;

                case 14:
                System.out.println("Exiting Viewer Menu...");
                return;

                default:
                    System.out.println("Option invalide !");
            }
        }
    }
}
