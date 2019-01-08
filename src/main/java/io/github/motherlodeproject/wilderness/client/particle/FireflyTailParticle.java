package io.github.motherlodeproject.wilderness.client.particle;

import io.github.motherlodeproject.wilderness.Wilderness;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.texture.Sprite;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class FireflyTailParticle extends Particle {

	private static final Identifier TEXTURE = new Identifier(Wilderness.MOD_ID, "entity/firefly");

	public FireflyTailParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
	                           double ySpeedIn, double zSpeedIn) {

		super(worldIn, xCoordIn, yCoordIn, zCoordIn);
		this.velocityX = xSpeedIn + 0.01 * (this.random.nextDouble() - 0.5);
		this.velocityY = ySpeedIn - 0.01 * this.random.nextDouble();
		this.velocityZ = zSpeedIn + 0.01 * (this.random.nextDouble() - 0.5);
		this.size = 0.25F;
		this.maxAge = 10;

		Sprite sprite = MinecraftClient.getInstance().getSpriteAtlas().getSprite(TEXTURE.toString());
		this.setSprite(sprite);
	}

	public static Identifier getTexture() {
		return TEXTURE;
	}

	@Override
	public void update() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.size *= 0.9;

		if (this.age++ >= this.maxAge) {
			this.markDead();
		}

		this.move(this.velocityX, this.velocityY, this.velocityZ);
	}

	@Override
	public int getColorMultiplier(float delta) {
		return 15728800;
	}

	// Use the block+item TEXTURE sheet onto which we have stitched our sprite in event handler
	@Override
	public int getParticleGroup() {
		return 1;
	}

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<DefaultParticleType> {
		public Particle createParticle(DefaultParticleType particleType, World world, double double_1, double double_2, double double_3, double double_4, double double_5, double double_6) {
			return new FireflyTailParticle(world, double_1, double_2, double_3, double_4, double_5, double_6);
		}
	}
}
