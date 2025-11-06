package com.campusdual.classroom;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;

public class JSONFileCreator {
    public static void createDocument() {
        JsonObject root = new JsonObject();
        JsonArray itemsArray = new JsonArray();

        itemsArray.add(createItem("Manzana", 2));
        itemsArray.add(createItem("Leche", 1));
        itemsArray.add(createItem("Pan", 3));
        itemsArray.add(createItem("Huevo", 2));
        itemsArray.add(createItem("Queso", 1));
        itemsArray.add(createItem("Cereal", 1));
        itemsArray.add(createItem("Agua", 4));
        itemsArray.add(createItem("Yogur", 6));
        itemsArray.add(createItem("Arroz", 2));

        root.add("items", itemsArray);

        try (FileWriter writer = new FileWriter("src/main/resources/shoppingList.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(root, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JsonObject createItem(String text, int quantity) {
        JsonObject item = new JsonObject();
        item.addProperty("text", text);
        item.addProperty("quantity", quantity);
        return item;
    }
}
