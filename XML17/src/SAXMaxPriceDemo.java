import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.File;

public class SAXMaxPriceDemo {
    public static void main(String[] args) {
        try {
            File inputFile = new File("books.xml");

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                private int maxPrice = 0;
                private boolean bPrice = false;
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("price")) {
                        bPrice = true;
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    if (bPrice) {
                        String priceText = new String(ch, start, length).trim();
                        if (!priceText.isEmpty()) {
                            try {
                                int currentPrice = Integer.parseInt(priceText);
                                if (currentPrice > maxPrice) {
                                    maxPrice = currentPrice;
                                }
                            } catch (NumberFormatException e) {
                            }
                        }
                    }
                }
                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.equalsIgnoreCase("price")) {
                        bPrice = false;
                    }
                }
                @Override
                public void endDocument() throws SAXException {
                    System.out.println("Max price: " + maxPrice);
                }
            };
            saxParser.parse(inputFile, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}