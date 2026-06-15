import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.File;

public class XMLTreeClickDemo extends JFrame {
    public XMLTreeClickDemo() {
        setTitle("Bài 8 — Click Node JTree");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            File inputFile = new File("books.xml");
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputFile);
            doc.getDocumentElement().normalize();

            Element rootElement = doc.getDocumentElement();
            DefaultMutableTreeNode rootNode = createTreeNodes(rootElement);

            JTree xmlTree = new JTree(rootNode);
            for (int i = 0; i < xmlTree.getRowCount(); i++) {
                xmlTree.expandRow(i);
            }
            xmlTree.addTreeSelectionListener(e -> {
                TreePath path = e.getPath();
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                if (selectedNode != null) {
                    DefaultMutableTreeNode bookNode = null;
                    if (selectedNode.getUserObject().toString().equals("book")) {
                        bookNode = selectedNode;
                    } else if (selectedNode.getParent() != null && selectedNode.getParent().toString().equals("book")) {
                        bookNode = (DefaultMutableTreeNode) selectedNode.getParent();
                    }
                    if (bookNode != null) {
                        String title = "";
                        String author = "";
                        for (int i = 0; i < bookNode.getChildCount(); i++) {
                            DefaultMutableTreeNode child = (DefaultMutableTreeNode) bookNode.getChildAt(i);
                            String childStr = child.getUserObject().toString();

                            if (childStr.startsWith("title: ")) {
                                title = childStr.replace("title: ", "");
                            } else if (childStr.startsWith("author: ")) {
                                author = childStr.replace("author: ", "");
                            }
                        }
                        System.out.println("Selected Book:");
                        System.out.println("Title: " + title);
                        System.out.println("Author: " + author);
                        System.out.println("-------------------------");
                    }
                }
            });
            add(new JScrollPane(xmlTree));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private DefaultMutableTreeNode createTreeNodes(Element element) {
        String tagName = element.getTagName();
        NodeList childNodes = element.getChildNodes();

        boolean hasChildElements = false;
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                hasChildElements = true;
                break;
            }
        }

        DefaultMutableTreeNode treeNode;
        if (hasChildElements) {
            treeNode = new DefaultMutableTreeNode(tagName);
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node childNode = childNodes.item(i);
                if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                    treeNode.add(createTreeNodes((Element) childNode));
                }
            }
        } else {
            String nodeValue = element.getTextContent().trim();
            treeNode = new DefaultMutableTreeNode(tagName + ": " + nodeValue);
        }
        return treeNode;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new XMLTreeClickDemo().setVisible(true));
    }
}