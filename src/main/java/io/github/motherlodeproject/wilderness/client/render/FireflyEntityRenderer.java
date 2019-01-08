package io.github.motherlodeproject.wilderness.client.render;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import io.github.motherlodeproject.wilderness.Wilderness;
import io.github.motherlodeproject.wilderness.client.model.FireflyModel;
import io.github.motherlodeproject.wilderness.client.render.layer.FireflyGlowFeatureRenderer;
import io.github.motherlodeproject.wilderness.entity.FireflyEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.util.Identifier;

public class FireflyEntityRenderer extends MobEntityRenderer<FireflyEntity, FireflyModel> {
	private static final Identifier FIREFLY_TEXTURE = new Identifier(Wilderness.MOD_ID, "textures/entity/firefly.png");

	public FireflyModel model;

	public FireflyEntityRenderer(EntityRenderDispatcher dispatcher) {
		super(dispatcher, new FireflyModel(), 0F);
		this.model = new FireflyModel();
		this.addFeature(new FireflyGlowFeatureRenderer(this));
	}

	@Override
	protected Identifier getTexture(FireflyEntity var1) {
		return FIREFLY_TEXTURE;
	}

	@Override
	public void render(FireflyEntity firefly, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.translatef((float) x, (float) y, (float) z);
		GlStateManager.rotatef(firefly.yaw, 0F, 1F, 0F);
		GlStateManager.scalef(firefly.getScale(), firefly.getScale(), firefly.getScale());
		bindTexture(FIREFLY_TEXTURE);
		GlStateManager.color4f(firefly.getRed(), firefly.getGreen(), firefly.getBlue(), 1.0F);

		GlStateManager.disableLighting();
		int combinedBrightness = firefly.getLightmapCoordinates(); // Brightness, sort of. Code borrowed from spiders/endermen
		int skyLightTimes16 = combinedBrightness % 65536;
		int blockLightTimes16 = combinedBrightness / 65536;
		GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, (float) skyLightTimes16, (float) blockLightTimes16);
		GlStateManager.enableLighting();
		//        Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
		model.render(firefly, 0F, 0F, 0F, 0F, 0F, 0.1F);
		//        Minecraft.getMinecraft().entityRenderer.setupFogColor(false);

		this.applyLightmapCoordinates(firefly);
		GlStateManager.enableAlphaTest();

		GlStateManager.popMatrix();

		FeatureRenderer feature = (FeatureRenderer) this.features.get(0);
		feature.render(firefly, 0F, 0F, partialTicks, firefly.age, 0F, 0F, 1F);
	}
}