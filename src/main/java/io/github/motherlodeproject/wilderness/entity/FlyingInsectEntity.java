package io.github.motherlodeproject.wilderness.entity;

import io.github.motherlodeproject.wilderness.entity.ai.InsectFlyRandomlyGoal;
import io.github.motherlodeproject.wilderness.network.ServerHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class FlyingInsectEntity extends FlyingEntity {
	private float scale;

	private float width;
	private float height;
	private float speed;
	private Vec3d spawnPos;
	private boolean sitting;
	private Direction facing;
	private int renderDistance;

	public FlyingInsectEntity(EntityType<? extends FlyingInsectEntity> entityType, World world) {
		super(entityType, world);
		this.sitting = false;
		this.facing = Direction.UP;
		this.setSpawnPos(Vec3d.ZERO);
		this.renderDistance = 2000;
	}

	@Override
	public void method_5959() {
		this.goalSelector.add(10, new InsectFlyRandomlyGoal(this));
	}

	@Environment(EnvType.CLIENT)
	@Override
	public boolean shouldRenderAtDistance(double distance) {
		PlayerEntity player = MinecraftClient.getInstance().player;
		double x = player.x - this.x;
		double y = player.y - this.y;
		double z = player.z - this.z;

		return x * x + y * y + z * z < this.renderDistance;
	}

	@Override
	public CompoundTag toTag(CompoundTag compound) {
		compound.putBoolean("Sitting", this.sitting);
		compound.putInt("Facing", this.facing.ordinal());
		return super.toTag(compound);
	}

	@Override
	public void fromTag(CompoundTag compound) {
		super.fromTag(compound);
		if (compound.containsKey("Sitting")) {
			this.sitting = compound.getBoolean("Sitting");
		}
		if (compound.containsKey("Facing")) {
			this.facing = Direction.byId(compound.getInt("Facing"));
		}
		this.updateClients();
	}

	public void updateClients() {
		if (!this.world.isClient) {
			ServerHandler.syncFlyingInsectEntity(this);
		}
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected boolean method_5658() {
		return false;
	}

	public Direction getFacing() {
		return this.facing;
	}

	public void setFacing(int facing) {
		this.facing = Direction.byId(facing);
	}

	public void setFacing(Direction facing) {
		this.facing = facing;
	}

	public int getRenderDistance() {
		return renderDistance;
	}

	public void setRenderDistance(int renderDistance) {
		this.renderDistance = renderDistance;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
		this.setSize(this.scale * this.width, this.scale * this.height);
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
		this.setSize(this.scale * this.width, this.scale * this.height);
	}

	public void setSitting(boolean sitting) {
		this.sitting = sitting;
	}

	public boolean isSitting() {
		return this.sitting;
	}

	public void setSpawnPos(Vec3d pos) {
		this.spawnPos = pos;
	}

	public Vec3d getSpawnPos() {
		return this.spawnPos;
	}

	public void setScale(float scale) {
		this.scale = scale;
		this.setSize(this.scale * this.width, this.scale * this.height);
	}

	public float getScale() {
		return this.scale;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getSpeed() {
		return this.speed;
	}

	public void moveInDirection(Vec3d v) {
		this.yaw = (float) MathHelper.atan2(v.z, v.x) * 180F / (float) Math.PI;
		this.setYaw(yaw);
		this.setPositionAndAngles(this.x, this.y, this.z, yaw, 0.0F);

		this.velocityX = v.x;
		this.velocityY = v.y;
		this.velocityZ = v.z;
		this.velocityModified = true;
	}

}
