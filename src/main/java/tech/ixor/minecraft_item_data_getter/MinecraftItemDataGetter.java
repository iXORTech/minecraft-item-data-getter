package tech.ixor.minecraft_item_data_getter;

import java.util.List;
import java.util.ArrayList;
import java.io.Reader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

import net.minecraft.util.registry.Registry;
import net.fabricmc.api.ModInitializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MinecraftItemDataGetter implements ModInitializer {
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		System.out.println("Hello Fabric world!");

		List<String> itemNames = new ArrayList<>();

		System.out.println("Printing Minecraft Item Registry Data to console...");
		Registry.ITEM.forEach(item -> {
			String itemName = item.toString();
			System.out.println("Minecraft Item: " + itemName);
			if (!itemNames.contains(itemName)) {
				itemNames.add(itemName);
			}
		});
		System.out.println("Printed Minecraft Item Registry Data to console.");


		System.out.println("Printing Minecraft Block Registry Data to console...");
		Registry.BLOCK.forEach(block -> {
			String blockName = block.toString()
					.replace("Block{minecraft:", "")
					.replace("}", "");
			System.out.println("Minecraft Block: " + blockName);
			if (!itemNames.contains(blockName)) {
				itemNames.add(blockName);
			}
		});
		System.out.println("Printed Minecraft Block Registry Data to console.");


		Path legacyItemDataPath = Paths.get("legacy_items.json");
		System.out.println("Reading legacy item data from " + legacyItemDataPath.toAbsolutePath() + "...");
		try (Reader reader = Files.newBufferedReader(legacyItemDataPath, StandardCharsets.UTF_8)) {
            JsonParser parser = new JsonParser();
            JsonElement legacyItems = parser.parse(reader);
			JsonArray legacyItemsArray = legacyItems.getAsJsonArray();
            for (JsonElement legacyItem: legacyItemsArray) {
                if (legacyItem.isJsonObject()) {
                    itemNames.add(legacyItem.getAsJsonObject().get("id").getAsString());
                }
            }
        } catch (IOException e) {
			e.printStackTrace();
		}

		itemNames.sort(String::compareToIgnoreCase);

		JsonArray items = new JsonArray();
		for (String itemName : itemNames) {
			items.add(itemName);
		}

		System.out.println("Writing Minecraft Item Registry Data to file...");
		JsonObject minecraftItemData = new JsonObject();
		minecraftItemData.add("minecraft_item", items);

		try (FileWriter file = new FileWriter("MinecraftItemData.json")) {
			System.out.println("File Path: " + Paths.get("MinecraftItemData.json").toAbsolutePath());
			file.write(minecraftItemData.toString());
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
