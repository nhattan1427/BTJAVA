import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class DocFile {
    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập đường dẫn tập tin: ");
        String path = scanner.nextLine();
        File file = new File(path);
        if (!file.exists()){
            System.out.println("Đường dẫn tập tin không tồn tại");
            return;
        }
        if (!file.isFile()){
            System.out.println("Đây không phải tập tin thường");
            return;
        }
        if (!file.canRead()){
            System.out.println("Không thể đọc tập tin này");
            return;
        }
        System.out.println("Thông tin tập tin:");
        System.out.println("Tên file: " + file.getName());
        System.out.println("Đường dẫn tuyệt đối: " + file.getAbsolutePath());
        System.out.println("Kích thước file: " + file.length() + "bytes");

        System.out.println("Nội dung tập tin");
        try(FileInputStream fis = new FileInputStream(file)){
            int byteData;
            while ((byteData = fis.read()) != -1){
                System.out.print((char) byteData);
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("Xảy ra lỗi tập tin: " + e.getMessage());
        }finally {
            scanner.close();
        }
    }
}