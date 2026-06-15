import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;

public class FilterBooks {
    public static void main(String[] args) {
        try {
            File inputFile = new File("books.xml");
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList bookList = doc.getElementsByTagName("book");

            for (int i = 0; i < bookList.getLength(); i++) {
                Element book = (Element) bookList.item(i);
                String priceStr = book.getElementsByTagName("price").item(0).getTextContent();
                int price = Integer.parseInt(priceStr);
                if (price > 60) {
                    String title = book.getElementsByTagName("title").item(0).getTextContent();
                    System.out.println(title);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}