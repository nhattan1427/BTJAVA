import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import java.io.File;

public class XPathAttributeDemo {
    public static void main(String[] args) {
        try {
            File inputFile = new File("books.xml");
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputFile);
            doc.getDocumentElement().normalize();

            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "//book/@id";
            NodeList idNodes = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
            StringBuilder output = new StringBuilder();
            for (int i = 0; i < idNodes.getLength(); i++) {
                output.append(idNodes.item(i).getNodeValue()).append(" ");
            }
            System.out.println(output.toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}