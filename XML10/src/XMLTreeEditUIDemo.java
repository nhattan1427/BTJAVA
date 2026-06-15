import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import java.io.File;

public class XMLTreeEditUIDemo extends JFrame {
    private Document doc;
    private JTree xmlTree;
    public XMLTreeEditUIDemo() {
        setTitle("Bài 10 — Edit trên UI cập nhật XML");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            File inputFile = new File("books.xml");
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputFile);
            doc.getDocumentElement().normalize();

            Element rootElement = doc.getDocumentElement();
            DefaultMutableTreeNode rootNode = createTreeNodes(rootElement);

            xmlTree = new JTree(rootNode);
            xmlTree.setEditable(true);
            for (int i = 0; i < xmlTree.getRowCount(); i++) {
                xmlTree.expandRow(i);
            }
            xmlTree.getCellEditor().addCellEditorListener(new CellEditorListener() {
                @Override
                public void editingStopped(ChangeEvent e) {
                    String newValue = xmlTree.getCellEditor().getCellEditorValue().toString();
                    TreePath path = xmlTree.getSelectionPath();
                    if (path == null) return;

                    DefaultMutableTreeNode editedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                    if (newValue.startsWith("title: ")) {
                        String newTitleText = newValue.replace("title: ", "").trim();
                        updateXMLTitle(editedNode, newTitleText);
                    }
                }

                @Override
                public void editingCanceled(ChangeEvent e) {
                }
            });

            add(new JScrollPane(xmlTree));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateXMLTitle(DefaultMutableTreeNode editedNode, String newTitleText) {
        try {
            DefaultMutableTreeNode bookNode = (DefaultMutableTreeNode) editedNode.getParent();
            DefaultMutableTreeNode booksRootNode = (DefaultMutableTreeNode) bookNode.getParent();

            int bookIndex = booksRootNode.getIndex(bookNode);

            NodeList bookList = doc.getElementsByTagName("book");
            Element bookElement = (Element) bookList.item(bookIndex);

            bookElement.getElementsByTagName("title").item(0).setTextContent(newTitleText);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("books.xml"));
            transformer.transform(source, result);

            System.out.println("XML updated successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
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
        SwingUtilities.invokeLater(() -> new XMLTreeEditUIDemo().setVisible(true));
    }
}