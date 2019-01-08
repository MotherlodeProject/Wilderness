package io.github.motherlodeproject.wilderness.registry;

import io.github.motherlodeproject.wilderness.Wilderness;
import io.github.motherlodeproject.wilderness.entity.FireflyEntity;
import net.fabricmc.fabric.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;

public class WildernessEntities {

	public static final EntityType<FireflyEntity> FIREFLY = add("firefly", FabricEntityTypeBuilder.create(FireflyEntity.class, FireflyEntity::new).trackable(64, 20, true).build());

	public static <T extends Entity> EntityType<T> add(String name, EntityType<T> entity) {
		WildernessRegistry.ENTITY_TYPES.put(new Identifier(Wilderness.MOD_ID, name), entity);
		return entity;
	}

	public static void loadClass() {}
}
