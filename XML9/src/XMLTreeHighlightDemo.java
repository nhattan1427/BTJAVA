import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.awt.Component;
import java.awt.Color;
import java.io.File;

public class XMLTreeHighlightDemo extends JFrame {
    public XMLTreeHighlightDemo() {
        setTitle("Bài 9 — JTree Highlight");
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
            xmlTree.setCellRenderer(new DefaultTreeCellRenderer() {
                @Override
                public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel,
                                                              boolean expanded, boolean leaf, int row, boolean hasFocus) {
                    Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

                    if (value instanceof DefaultMutableTreeNode) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                        String nodeText = node.getUserObject().toString();
                        if (nodeText.equals("book")) {
                            String title = "";
                            int price = 0;
                            for (int i = 0; i < node.getChildCount(); i++) {
                                DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
                                String childStr = child.getUserObject().toString();
                                if (childStr.startsWith("title: ")) {
                                    title = childStr.replace("title: ", "");
                                } else if (childStr.startsWith("price: ")) {
                                    try {
                                        price = Integer.parseInt(childStr.replace("price: ", "").trim());
                                    } catch (NumberFormatException ex) {
                                        price = 0;
                                    }
                                }
                            }
                            if (price > 60) {
                                setText(title + " (highlight)");
                                setForeground(Color.RED);
                            } else if (!title.isEmpty()) {
                                setText(title);
                                setForeground(sel ? getTextSelectionColor() : getTextNonSelectionColor());
                            }
                        }
                    }
                    return c;
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
        SwingUtilities.invokeLater(() -> new XMLTreeHighlightDemo().setVisible(true));
    }
}