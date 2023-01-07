package tech.ixor.minecraft_item_data_getter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import net.minecraft.util.registry.Registry;
import net.fabricmc.api.ModInitializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class MinecraftItemDataGetter implements ModInitializer {
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		System.out.println("Hello Fabric world!");

		JsonArray items = new JsonArray();

		System.out.println("Printing Minecraft Item Registry Data to console...");
		Registry.ITEM.forEach(item -> {
			String itemName = item.toString();
			System.out.println("Minecraft Item: " + itemName);
			items.add(itemName);
		});
		System.out.println("Printed Minecraft Item Registry Data to console.");

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
