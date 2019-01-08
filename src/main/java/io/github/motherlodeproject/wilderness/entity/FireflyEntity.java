package io.github.motherlodeproject.wilderness.entity;

import io.github.motherlodeproject.wilderness.client.particle.FireflyTailParticle;
import io.github.motherlodeproject.wilderness.registry.WildernessEntities;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;

public class FireflyEntity extends FlyingInsectEntity {

	private float red;
	private float green;
	private float blue;

	public FireflyEntity(World worldIn) {
		super(WildernessEntities.FIREFLY, worldIn);
		this.setWidth(0.2F);
		this.setHeight(0.2F);
		this.setScale(0.8F);
		this.setSpeed(0.07F);
		this.setSpawnPos(Vec3d.ZERO);
		this.setRenderDistance(4000);
	}

	@Override
	public void method_5959() {
		super.method_5959();
		goalSelector.add(0, new AIFlyToGroundAndDespawn(this));
	}

	public void setColor(String colorHex) {
		String sRed = colorHex.substring(0, 2);
		String sGreen = colorHex.substring(2, 4);
		String sBlue = colorHex.substring(4, 6);

		float fRed = (float) Integer.parseInt(sRed, 16) / 255F;
		float fGreen = (float) Integer.parseInt(sGreen, 16) / 255F;
		float fBlue = (float) Integer.parseInt(sBlue, 16) / 255F;

		this.setRGB(fRed, fGreen, fBlue);
	}

	@Override
	public EntityData prepareEntityData(IWorld world, LocalDifficulty localDifficulty, SpawnType spawnType, EntityData data, CompoundTag compound) {
		this.setSpawnPos(getPosVector());
		return super.prepareEntityData(world, localDifficulty, spawnType, data, compound);
	}

	@Override
	public boolean canSpawn(IWorld world, SpawnType spawnType) {
		return !world.getWorld().isDaylight() && super.canSpawn(world, spawnType);
	}

	@Override
	public CompoundTag toTag(CompoundTag compound) {
		compound.put("SpawnPos", toListTag(this.getSpawnPos().x, this.getSpawnPos().y, this.getSpawnPos().z));
		return super.toTag(compound);
	}

	@Override
	public void fromTag(CompoundTag compound) {
		super.fromTag(compound);
		if (compound.containsKey("SpawnPos") && compound.getTag("SpawnPos") instanceof ListTag) {
			ListTag tag = (ListTag) compound.getTag("SpawnPos");
			this.setSpawnPos(new Vec3d(tag.getDouble(0), tag.getDouble(1), tag.getDouble(2)));
		}
	}

	@Override
	public int getLightmapCoordinates() {
		return 15728880;
	}

	public float getRed() {
		return this.red;
	}

	public float getGreen() {
		return this.green;
	}

	public float getBlue() {
		return this.blue;
	}

	public void setRGB(float red, float green, float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	@Override
	protected void mobTick() {
		if (this.world.isClient) {
			if (this.age % 5 == 0) {
				FireflyTailParticle tailParticle = new FireflyTailParticle(
					this.world, this.prevX, this.prevY, this.prevZ, 0, 0, 0);
				tailParticle.setColor(this.red, this.green, this.blue);
				MinecraftClient.getInstance().particleManager.addParticle(tailParticle);
			}
		}
		super.mobTick();
	}

	// Has priority over any other AI task. Causes the firefly to fly downwards and despawn
	// when it hits a block whenever the sun goes up. Uses same type of movement as 
	// AIFlyRandomly
	static class AIFlyToGroundAndDespawn extends Goal {

		private FireflyEntity firefly;
		private int timer;

		public AIFlyToGroundAndDespawn(FireflyEntity firefly) {
			this.firefly = firefly;
			this.setControlBits(1);
		}

		@Override
		public boolean canStart() {
			return firefly.getEntityWorld().isDaylight();
		}

		@Override
		public void start() {
			timer = 0;
		}

		@Override
		public boolean shouldContinue() {
			doFlyDown();
			if (isTouchingBlock()) {
				firefly.dead = true;
				return false;
			}
			return true;
		}

		public void doFlyDown() {
			if (timer % 2 == 0) {
				double periodicFactor1 = 0.07D * MathHelper.sin((float) timer / 10F);
				double periodicFactor2 = 0.07D * MathHelper.sin((float) timer / 10F + (float) Math.PI / 2F);
				Vec3d dir = new Vec3d(periodicFactor1, -firefly.getSpeed(), periodicFactor2);
				firefly.moveInDirection(dir);
			}
			timer++;
		}

		public boolean isTouchingBlock() {
			return firefly.collided;
		}
	}
}