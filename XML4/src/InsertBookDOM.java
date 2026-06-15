import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;

public class InsertBookDOM {
    public static void main(String[] args) {
        try {
            File inputFile = new File("books.xml");
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputFile);
            doc.getDocumentElement().normalize();

            Element rootElement = doc.getDocumentElement();
            Element newBook = doc.createElement("book");
            Element title = doc.createElement("title");
            title.setTextContent("C++ Master");
            newBook.appendChild(title);
            Element author = doc.createElement("author");
            author.setTextContent("John");
            newBook.appendChild(author);
            Element year = doc.createElement("year");
            year.setTextContent("2018");
            newBook.appendChild(year);
            Element price = doc.createElement("price");
            price.setTextContent("80");
            newBook.appendChild(price);

            rootElement.appendChild(newBook);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("books.xml"));
            transformer.transform(source, result);
            System.out.println("Thêm node và cập nhật file XML thành công!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}