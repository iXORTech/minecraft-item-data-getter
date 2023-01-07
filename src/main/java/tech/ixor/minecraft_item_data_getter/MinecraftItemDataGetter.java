package tech.ixor.minecraft_item_data_getter;

import net.minecraft.util.registry.Registry;
import net.fabricmc.api.ModInitializer;

public class MinecraftItemDataGetter implements ModInitializer {
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		System.out.println("Hello Fabric world!");

		System.out.println("Printing Minecraft Item Registry Data to console...");
		Registry.ITEM.forEach(item -> {
			System.out.println("Minecraft Item: " + item.toString());
		});
		System.out.println("Printed Minecraft Item Registry Data to console.");
	}
}
