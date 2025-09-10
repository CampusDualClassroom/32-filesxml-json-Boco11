package com.campusdual.classroom;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ExerciseTest {
    @Test
    @DisplayName("Test if exist shoppingList.xml with correct data")
    void testCreateXML() {
        XMLFileCreator.createDocument();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File("src/main/resources/shoppingList.xml"));
            XPath xpath = XPathFactory.newInstance().newXPath();
            Node rootNode = (Node) xpath.evaluate("/shoppinglist", doc, XPathConstants.NODE);
            assertEquals("shoppinglist", rootNode.getNodeName(), "Root element is not \"shoppinglist\"");
            Node itemsNode = (Node) xpath.evaluate("/shoppinglist/*[1]", doc, XPathConstants.NODE);
            assertEquals("items", itemsNode.getNodeName(), "First \"shoppinglist\" descendant element is not \"items\"");
            NodeList itemsNodeList = (NodeList) xpath.evaluate("/shoppinglist/items/*", doc, XPathConstants.NODESET);
            assertEquals(9, itemsNodeList.getLength(), "Items node has not exactly 9 elements");
            boolean hasAppleNode = (boolean) xpath.evaluate("/shoppinglist/items/item[contains(., 'Manzana') and @quantity='2']", doc, XPathConstants.BOOLEAN);
            assertTrue(hasAppleNode, "<item quantity=\"2\">Manzana<item> not found as children of <items> node");
            boolean hasMilkNode = (boolean) xpath.evaluate("/shoppinglist/items/item[contains(., 'Leche') and @quantity='1']", doc, XPathConstants.BOOLEAN);
            assertTrue(hasMilkNode, "<item quantity=\"1\">Leche<item> not found as children of <items> node");
            boolean hasBreadNode = (boolean) xpath.evaluate("/shoppinglist/items/item[contains(., 'Pan') and @quantity='3']", doc, XPathConstants.BOOLEAN);
            assertTrue(hasBreadNode, "<item quantity=\"3\">Bread<item> not found as children of <items> node");
            boolean hasEggNode = (boolean) xpath.evaluate("/shoppinglist/items/item[contains(., 'Huevo') and @quantity='2']", doc, XPathConstants.BOOLEAN);
            assertTrue(hasEggNode, "<item quantity=\"2\">Huevo<item> not found as children of <items> node");
            boolean hasCheeseNode = (boolean) xpath.evaluate("/shoppinglist/items/item[contains(., 'Queso') and @quantity='1']", doc, XPathConstants.BOOLEAN);
            assertTrue(hasCheeseNode, "<item quantity=\"1\">Queso<item> not found as children of <items> node");
            boolean hasCerealNode = (boolean) xpath.evaluate("/shoppinglist/items/item[contains(., 'Cereal') and @quantity='1']", doc, XPathConstants.BOOLEAN);
            assertTrue(hasCerealNode, "<item quantity=\"1\">Cereal<item> not found as children of <items> node");
            boolean hasWaterNode = (boolean) xpath.evaluate("/shoppinglist/items/item[contains(., 'Agua') and @quantity='4']", doc, XPathConstants.BOOLEAN);
            assertTrue(hasWaterNode, "<item quantity=\"4\">Agua<item> not found as children of <items> node");
            boolean hasYogurtNode = (boolean) xpath.evaluate("/shoppinglist/items/item[contains(., 'Yogur') and @quantity='6']", doc, XPathConstants.BOOLEAN);
            assertTrue(hasYogurtNode, "<item quantity=\"6\">Yogur<item> not found as children of <items> node");
            boolean hasRiceNode = (boolean) xpath.evaluate("/shoppinglist/items/item[contains(., 'Arroz') and @quantity='2']", doc, XPathConstants.BOOLEAN);
            assertTrue(hasRiceNode, "<item quantity=\"2\">Arroz<item> not found as children of <items> node");
        } catch (XPathExpressionException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException("File not found: src/main/resources/shoppingList.xml");
        }

    }

    @Test
    @DisplayName("Test if exist shoppingList.json with correct data")
    void testCreateJSON() {
        JSONFileCreator.createDocument();
        try (FileReader fileReader = new FileReader("src/main/resources/shoppingList.json")) {
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(fileReader);
            assertTrue(jsonElement.isJsonObject(), "File not contains a JSON object");
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray itemsArray = jsonObject.getAsJsonArray("items");
            assertNotNull(itemsArray, "\"items\" key not exist");
            assertEquals(9, itemsArray.size(), "The value of the key \"items\" does not contain an array of 9 elements");

            Set<JsonObject> expectedElements = new HashSet<>(Arrays.asList(
                    createItem("Manzana", 2),
                    createItem("Leche", 1),
                    createItem("Pan", 3),
                    createItem("Huevo", 2),
                    createItem("Queso", 1),
                    createItem("Cereal", 1),
                    createItem("Agua", 4),
                    createItem("Yogur", 6),
                    createItem("Arroz", 2)
            ));

            for (JsonElement itemElement : itemsArray) {
                assertTrue(itemElement.isJsonObject(), "Element in array is not a json object" + itemElement);
                JsonObject itemObject = itemElement.getAsJsonObject();
                assertTrue(expectedElements.stream()
                                .anyMatch(expectedItem ->
                                        expectedItem.get("text").getAsString().equals(itemObject.get("text").getAsString())
                                                && expectedItem.get("quantity").getAsInt() == itemObject.get("quantity").getAsInt()),
                        "Unexpected element in array: " + itemElement);
            }
        } catch (IOException e) {
            throw new RuntimeException("File not found: src/main/resources/shoppingList.json");
        }
    }

    private JsonObject createItem(String text, int quantity) {
        JsonObject item = new JsonObject();
        item.addProperty("text", text);
        item.addProperty("quantity", quantity);
        return item;
    }
}
