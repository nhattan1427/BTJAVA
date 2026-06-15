import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.StringWriter;

public class XSLTConditionalFormatDemo {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("books.xml");
            File xslFile = new File("transform.xsl");

            StreamSource xmlSource = new StreamSource(xmlFile);
            StreamSource xslSource = new StreamSource(xslFile);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(xslSource);

            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(xmlSource, result);

            System.out.println(writer.toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}