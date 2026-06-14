import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.util.Base64;

public class SecureNotesApp extends JFrame {
    private JTextArea textArea;
    private final String FILE_NAME = "secure_note.txt";
    private final String AES_KEY = "MySecretKey12345";

    public SecureNotesApp() {
        setTitle("Secure Notes Desktop App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel panelButtons = new JPanel();
        JButton btnSave = new JButton("Save Note");
        JButton btnLoad = new JButton("Load Note");
        panelButtons.add(btnSave);
        panelButtons.add(btnLoad);
        add(panelButtons, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> saveNote());
        btnLoad.addActionListener(e -> loadNote());
    }

    private void saveNote() {
        String plainText = textArea.getText();
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec secretKey = new SecretKeySpec(AES_KEY.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            String base64Encrypted = Base64.getEncoder().encodeToString(encryptedBytes);

            Files.writeString(new File(FILE_NAME).toPath(), base64Encrypted);
            JOptionPane.showMessageDialog(this, "Đã mã hóa và lưu file thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu file: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadNote() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy file lưu trữ dữ liệu trước đó!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String base64Encrypted = Files.readString(file.toPath()).trim();
            byte[] encryptedBytes = Base64.getDecoder().decode(base64Encrypted);

            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec secretKey = new SecretKeySpec(AES_KEY.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            String plainText = new String(decryptedBytes);

            textArea.setText(plainText);
            JOptionPane.showMessageDialog(this, "Đọc và giải mã dữ liệu thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi giải mã! File có thể đã bị sửa đổi hoặc hỏng.", "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SecureNotesApp().setVisible(true));
    }
}