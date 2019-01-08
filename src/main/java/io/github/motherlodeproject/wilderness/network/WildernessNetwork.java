package io.github.motherlodeproject.wilderness.network;

import io.github.motherlodeproject.wilderness.Wilderness;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.networking.CustomPayloadPacketRegistry;
import net.minecraft.util.Identifier;

public class WildernessNetwork implements ModInitializer {

	public static final Identifier SYNC_FLYING_INSECT = new Identifier(Wilderness.MOD_ID, "sync_flying_insect");

	@Override
	public void onInitialize() {
		CustomPayloadPacketRegistry.CLIENT.register(SYNC_FLYING_INSECT, ClientHandler.SYNC_FLYING_INSECT);
	}
}
