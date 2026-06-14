import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class SQLInjectionDemo {
    private static final String URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Nhập username: ");
            String username = scanner.nextLine();

            System.out.print("Nhập password: ");
            String password = scanner.nextLine();

            System.out.println("\nTHỰC THI VERSION 1");
            loginVersion1(username, password);

            System.out.println("\nTHỰC THI VERSION 2");
            loginVersion2(username, password);
        }
    }

    public static void loginVersion1(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
        System.out.println("SQL Query thực tế: " + sql);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                System.out.println("Result V1: Đăng nhập thành công (Tài khoản: " + rs.getString("username") + ")");
            } else {
                System.out.println("Result V1: Đăng nhập thất bại");
            }
        } catch (Exception e) {
            System.out.println("Lỗi hệ thống ở V1: " + e.getMessage());
        }
    }

    public static void loginVersion2(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Result V2: Đăng nhập thành công (Tài khoản: " + rs.getString("username") + ")");
                } else {
                    System.out.println("Result V2: Đăng nhập thất bại");
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi hệ thống ở V2: " + e.getMessage());
        }
    }
}