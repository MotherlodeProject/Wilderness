package io.github.motherlodeproject.wilderness.registry;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.entity.EntityTrackingRegistry;
import net.fabricmc.fabric.events.client.ClientTickEvent;
import net.minecraft.block.Block;
import net.minecraft.client.network.packet.EntitySpawnClientPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class WildernessRegistry implements ModInitializer {
	public static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();
	public static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();
	public static final Map<Identifier, EntityType<?>> ENTITY_TYPES = new LinkedHashMap<>();
	public static final Map<Identifier, ParticleType<?>> PARTICLE_TYPES = new LinkedHashMap<>();

	@Override
	public void onInitialize() {
		WildernessItems.loadClass();
		WildernessBlocks.loadClass();
		WildernessEntities.loadClass();
		WildernessParticles.loadClass();

		for (Identifier id : ITEMS.keySet()) {
			Registry.register(Registry.ITEM, id, ITEMS.get(id));
		}
		for (Identifier id : BLOCKS.keySet()) {
			Registry.register(Registry.BLOCK, id, BLOCKS.get(id));
		}
		for (Identifier id : ENTITY_TYPES.keySet()) {
			EntityType type = Registry.register(Registry.ENTITY_TYPE, id, ENTITY_TYPES.get(id));
			EntityTrackingRegistry.INSTANCE.registerSpawnPacketProvider(type, entity -> new EntitySpawnClientPacket(entity, Registry.ENTITY_TYPE.getRawId(type)));
		}
		for (Identifier id : PARTICLE_TYPES.keySet()) {
			Registry.register(Registry.PARTICLE_TYPE, id, PARTICLE_TYPES.get(id));
		}
	}
}
