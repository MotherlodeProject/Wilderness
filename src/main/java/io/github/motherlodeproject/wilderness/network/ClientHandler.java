package io.github.motherlodeproject.wilderness.network;

import io.github.motherlodeproject.wilderness.entity.FlyingInsectEntity;
import net.fabricmc.fabric.networking.PacketContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.PacketByteBuf;

import java.util.function.BiConsumer;

public class ClientHandler {
	public static final BiConsumer<PacketContext, PacketByteBuf> SYNC_FLYING_INSECT = ClientHandler::syncFlyingInsect;

	public static void syncFlyingInsect(PacketContext packetContext, PacketByteBuf packetByteBuf) {
		boolean sitting = packetByteBuf.readBoolean();
		int facing = packetByteBuf.readInt();
		int entityId = packetByteBuf.readInt();
		if (MinecraftClient.getInstance().world.getEntityById(entityId) instanceof FlyingInsectEntity) {
			FlyingInsectEntity insect = (FlyingInsectEntity) MinecraftClient.getInstance().world.getEntityById(entityId);
			insect.setSitting(sitting);
			insect.setFacing(facing);
		}
	}
}
