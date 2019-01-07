package io.github.motherlodeproject.wilderness.registry;

import io.github.motherlodeproject.wilderness.Wilderness;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class WildernessItems {

	public static Item add(String name, Item item) {
		WildernessRegistry.ITEMS.put(new Identifier(Wilderness.MOD_ID, name), item);
		return item;
	}

	public static void loadClass() {}
}
