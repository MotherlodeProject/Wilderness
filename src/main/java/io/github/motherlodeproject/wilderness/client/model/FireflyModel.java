package io.github.motherlodeproject.wilderness.client.model;

import io.github.motherlodeproject.wilderness.entity.FireflyEntity;
import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.model.EntityModel;

public class FireflyModel extends EntityModel<FireflyEntity> {
	public Cuboid base;

	public FireflyModel() {
		this.textureHeight = 1;
		this.textureWidth = 1;
		this.base = new Cuboid(this, 0, 0);
		this.base.addBox(-0.5F, 0F, -0.5F, 1, 1, 1, 0F);
	}

	@Override
	public void render(FireflyEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.base.render(scale);
	}

}
