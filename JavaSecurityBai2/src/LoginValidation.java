import java.util.Scanner;

public class LoginValidation {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            boolean isUsernameValid = !username.trim().isEmpty();
            boolean isEmailValid = email.contains("@") && email.contains(".");

            if (isUsernameValid && isEmailValid) {
                System.out.println("Login accepted");
            } else {
                System.out.println("Invalid input");
            }
        }
    }
}