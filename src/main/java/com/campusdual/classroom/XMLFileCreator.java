package com.campusdual.classroom;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLFileCreator {
    public static void createDocument() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("shoppinglist");
            document.appendChild(root);

            Element items = document.createElement("items");
            root.appendChild(items);

            items.appendChild(createItem(document, "2", "Manzana", "item"));
            items.appendChild(createItem(document, "1", "Leche", "item"));
            items.appendChild(createItem(document, "3", "Pan", "item"));
            items.appendChild(createItem(document, "2", "Huevo", "item"));
            items.appendChild(createItem(document, "1", "Queso", "item"));
            items.appendChild(createItem(document, "1", "Cereal", "item"));
            items.appendChild(createItem(document, "4", "Agua", "item"));
            items.appendChild(createItem(document, "6", "Yogur", "item"));
            items.appendChild(createItem(document, "2", "Arroz", "item"));

            writeToFile(document, "src/main/resources/shoppingList.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Element createItem(Document document, String quantity, String itemDesc, String tag) {
        Element item = document.createElement(tag);
        item.setAttribute("quantity", quantity);
        item.setTextContent(itemDesc);
        return item;
    }

    private static void writeToFile(Document document, String pathName) throws Exception {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "3");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        File file = new File(pathName);
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);
    }
}

