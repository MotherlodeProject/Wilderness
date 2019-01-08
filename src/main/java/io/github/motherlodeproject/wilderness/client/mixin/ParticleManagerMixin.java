package io.github.motherlodeproject.wilderness.client.mixin;

import io.github.motherlodeproject.wilderness.client.WildernessClient;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleParameters;
import net.minecraft.particle.ParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public abstract class ParticleManagerMixin {
	@Shadow
	public abstract <T extends ParticleParameters> void registerFactory(ParticleType<T> particleType_1, ParticleFactory<T> particleFactory_1);

	@Inject(method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/client/texture/TextureManager;)V", at = @At("RETURN"))
	public void ctor(CallbackInfo info) {
		WildernessClient.registerParticles(this::registerFactory);
	}
}
