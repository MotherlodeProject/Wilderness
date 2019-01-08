package io.github.motherlodeproject.wilderness.network;

import io.github.motherlodeproject.wilderness.entity.FlyingInsectEntity;
import io.netty.buffer.Unpooled;
import net.minecraft.client.network.packet.CustomPayloadClientPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.PacketByteBuf;

public class ServerHandler {
	public static void syncFlyingInsectEntity(FlyingInsectEntity entity) {
		if (entity.world instanceof ServerWorld) {
			PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
			buf.writeBoolean(entity.isSitting());
			buf.writeInt(entity.getFacing().ordinal());
			buf.writeInt(entity.getEntityId());
			((ServerWorld) entity.world).getEntityTracker().method_14079(entity, new CustomPayloadClientPacket(WildernessNetwork.SYNC_FLYING_INSECT, buf));
		}
	}
}
