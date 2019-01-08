package io.github.motherlodeproject.wilderness.client;

import io.github.motherlodeproject.wilderness.client.particle.FireflyTailParticle;
import io.github.motherlodeproject.wilderness.client.render.FireflyEntityRenderer;
import io.github.motherlodeproject.wilderness.entity.FireflyEntity;
import io.github.motherlodeproject.wilderness.registry.WildernessParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.client.render.EntityRendererRegistry;
import net.fabricmc.fabric.events.client.SpriteEvent;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.particle.ParticleParameters;
import net.minecraft.particle.ParticleType;

import java.util.function.BiConsumer;

public class WildernessClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		registerEntityRenderers();
		registerSprites();
	}

	public static void registerEntityRenderers() {
		EntityRendererRegistry.INSTANCE.register(FireflyEntity.class, (manager, context) -> new FireflyEntityRenderer(manager));
	}

	public static void registerSprites() {
		SpriteEvent.PROVIDE.register(registry -> registry.register(FireflyTailParticle.getTexture()));
	}

	public static <T extends ParticleParameters> void registerParticles(BiConsumer<ParticleType<T>, ParticleFactory<T>> registry) {
		registerParticle(WildernessParticles.FIREFLY_TAIL, new FireflyTailParticle.Factory(), registry);
	}

	public static <T extends ParticleParameters> void registerParticle(ParticleType type, ParticleFactory factory, BiConsumer<ParticleType<T>, ParticleFactory<T>> registry) {
		registry.accept((ParticleType<T>) type, (ParticleFactory<T>) factory);
	}
}
