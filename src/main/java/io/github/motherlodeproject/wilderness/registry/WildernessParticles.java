package io.github.motherlodeproject.wilderness.registry;

import io.github.motherlodeproject.wilderness.Wilderness;
import io.github.prospector.silk.particle.SilkDefaultParticleType;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;

public class WildernessParticles {

	public static final DefaultParticleType FIREFLY_TAIL = add("firefly_tail", false);

	public static DefaultParticleType add(String name, boolean b) {
		DefaultParticleType particleType = new SilkDefaultParticleType(b);
		WildernessRegistry.PARTICLE_TYPES.put(new Identifier(Wilderness.MOD_ID, name), particleType);
		return particleType;
	}

	public static void loadClass() {}
}
