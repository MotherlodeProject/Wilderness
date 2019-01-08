package io.github.motherlodeproject.wilderness.mixin;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import io.github.motherlodeproject.wilderness.Wilderness;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Schema.class)
public class SchemaMixin {
	@Inject(method = "getChoiceType", at = @At("HEAD"), cancellable = true)
	public void skipWildernessChoices(final DSL.TypeReference type, final String choiceName, CallbackInfoReturnable callbackInfo) {
		if (choiceName.startsWith(Wilderness.MOD_ID + ":")) {
			callbackInfo.cancel();
		}
	}
}
