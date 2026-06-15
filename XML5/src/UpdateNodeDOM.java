import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;

public class UpdateNodeDOM {
    public static void main(String[] args) {
        try {
            File inputFile = new File("books.xml");
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList bookList = doc.getElementsByTagName("book");

            boolean isUpdated = false;
            for (int i = 0; i < bookList.getLength(); i++) {
                Element book = (Element) bookList.item(i);
                String title = book.getElementsByTagName("title").item(0).getTextContent();
                if ("Java Core".equals(title)) {
                    book.getElementsByTagName("price").item(0).setTextContent("60");
                    isUpdated = true;
                    break;
                }
            }
            if (isUpdated) {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("books.xml"));
                transformer.transform(source, result);
                System.out.println("Java Core price updated to 60");
            } else {
                System.out.println("Không tìm thấy cuốn sách có tên 'Java Core'!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}