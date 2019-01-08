package io.github.motherlodeproject.wilderness.client.render.layer;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.motherlodeproject.wilderness.Wilderness;
import io.github.motherlodeproject.wilderness.client.model.FireflyModel;
import io.github.motherlodeproject.wilderness.client.render.FireflyEntityRenderer;
import io.github.motherlodeproject.wilderness.entity.FireflyEntity;
import net.minecraft.class_295;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

public class FireflyGlowFeatureRenderer extends FeatureRenderer<FireflyEntity, FireflyModel> {

	private static final Identifier GLOW_TEXTURE = new Identifier(Wilderness.MOD_ID, "textures/particle/particle_glow.png");
	private float alpha;
	private float scale;

	public FireflyGlowFeatureRenderer(FireflyEntityRenderer fireflyRenderer) {
		super(fireflyRenderer);
		this.alpha = 1.0F;
		this.scale = 0.25F;
	}

	@Override
	public void render(FireflyEntity firefly, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

		float rotationX = class_295.method_1375();
		float rotationXZ = class_295.method_1377();
		float rotationZ = class_295.method_1380();
		float rotationYZ = class_295.method_1381();
		float rotationXY = class_295.method_1378();

		PlayerEntity player = MinecraftClient.getInstance().player;

		double interpPosX = player.prevX + (player.x - player.prevX) * (double) partialTicks;
		double interpPosY = player.prevY + (player.y - player.prevY) * (double) partialTicks;
		double interpPosZ = player.prevZ + (player.z - player.prevZ) * (double) partialTicks;

		double x = firefly.prevX + (firefly.x - firefly.prevX) * (double) partialTicks - interpPosX;
		double y = firefly.prevY + (firefly.y - firefly.prevY) * (double) partialTicks - interpPosY;
		double z = firefly.prevZ + (firefly.z - firefly.prevZ) * (double) partialTicks - interpPosZ;

		int combinedBrightness = firefly.getLightmapCoordinates();
		int skyLightTimes16 = combinedBrightness >> 16 & 65535;
		int blockLightTimes16 = combinedBrightness & 65535;

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SrcBlendFactor.SRC_ALPHA, GlStateManager.DstBlendFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.alphaFunc(516, 0.003921569F);

		GlStateManager.depthMask(false);

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBufferBuilder();

		buffer.begin(GL11.GL_QUADS, VertexFormats.POSITION_UV_LMAP_COLOR);
		this.bindTexture(GLOW_TEXTURE);

		double minU = 0D;
		double maxU = 1D;
		double minV = 0D;
		double maxV = 1D;

		buffer.vertex(x - rotationX * this.scale - rotationYZ * this.scale,
			y - rotationXZ * this.scale,
			z - rotationZ * this.scale - rotationXY * this.scale)
			.texture(maxU, maxV)
			.color(firefly.getRed(), firefly.getGreen(), firefly.getBlue(), this.alpha)
			.texture(skyLightTimes16, blockLightTimes16).next();

		buffer.vertex(x - rotationX * this.scale + rotationYZ * this.scale,
			y + rotationXZ * this.scale,
			z - rotationZ * this.scale + rotationXY * this.scale)
			.texture(maxU, minV)
			.color(firefly.getRed(), firefly.getGreen(), firefly.getBlue(), this.alpha)
			.texture(skyLightTimes16, blockLightTimes16).next();

		buffer.vertex(x + rotationX * this.scale + rotationYZ * this.scale,
			y + rotationXZ * this.scale,
			z + rotationZ * this.scale + rotationXY * this.scale)
			.texture(minU, minV)
			.color(firefly.getRed(), firefly.getGreen(), firefly.getBlue(), this.alpha)
			.texture(skyLightTimes16, blockLightTimes16).next();

		buffer.vertex(x + rotationX * this.scale - rotationYZ * this.scale,
			y - rotationXZ * this.scale,
			z + rotationZ * this.scale - rotationXY * this.scale)
			.texture(minU, maxV)
			.color(firefly.getRed(), firefly.getGreen(), firefly.getBlue(), this.alpha)
			.texture(skyLightTimes16, blockLightTimes16).next();

		tessellator.draw();

		GlStateManager.disableBlend();
		GlStateManager.depthMask(true);

	}

	@Override
	public boolean method_4200() {
		return false;
	}

}
