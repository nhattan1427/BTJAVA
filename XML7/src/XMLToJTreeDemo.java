import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.File;

public class XMLToJTreeDemo extends JFrame {

    public XMLToJTreeDemo() {
        setTitle("Bài 7 — Hiển thị JTree XML");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Hiển thị cửa sổ ở chính giữa màn hình

        try {
            File inputFile = new File("books.xml");
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputFile);
            doc.getDocumentElement().normalize();

            Element rootElement = doc.getDocumentElement();

            DefaultMutableTreeNode rootNode = parseXMLToJTreeNode(rootElement);
            JTree xmlTree = new JTree(rootNode);
            for (int i = 0; i < xmlTree.getRowCount(); i++) {
                xmlTree.expandRow(i);
            }
            JScrollPane scrollPane = new JScrollPane(xmlTree);
            add(scrollPane);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi không thể đọc file XML: " + e.getMessage(), "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
        }
    }
    private DefaultMutableTreeNode parseXMLToJTreeNode(Element element) {
        String tagName = element.getTagName();
        NodeList childNodes = element.getChildNodes();
        boolean hasChildElements = false;
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                hasChildElements = true;
                break;
            }
        }

        DefaultMutableTreeNode currentTreeNode;
        if (hasChildElements) {
            currentTreeNode = new DefaultMutableTreeNode(tagName);
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node childNode = childNodes.item(i);
                if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                    currentTreeNode.add(parseXMLToJTreeNode((Element) childNode));
                }
            }
        } else {
            String textValue = element.getTextContent().trim();
            currentTreeNode = new DefaultMutableTreeNode(tagName + ": " + textValue);
        }
        return currentTreeNode;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new XMLToJTreeDemo().setVisible(true);
        });
    }
}