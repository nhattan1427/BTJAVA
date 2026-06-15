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

public class DeleteNodeDOM {
    public static void main(String[] args) {
        try {
            File inputFile = new File("books.xml");
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputFile);
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();

            NodeList bookList = doc.getElementsByTagName("book");
            for (int i = bookList.getLength() - 1; i >= 0; i--) {
                Element book = (Element) bookList.item(i);
                String yearStr = book.getElementsByTagName("year").item(0).getTextContent();
                int year = Integer.parseInt(yearStr);
                if (year < 2018) {
                    root.removeChild(book);
                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("books.xml"));
            transformer.transform(source, result);

            System.out.println("Đã xóa các sách có năm xuất bản trước 2018 thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}