package com.rafayshhh.brightness.client.mixin;

import com.rafayshhh.brightness.client.ShhhBrightnessToggleClient;
import net.minecraft.client.renderer.LightmapRenderStateExtractor;
import net.minecraft.client.renderer.state.LightmapRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin injecting into Minecraft's LightmapRenderStateExtractor.
 * Applies brightness ambient illumination to the lightmap state without applying
 * any potion effects or status particles to the player.
 *
 * Designed to be compatible with Sodium and Iris by injecting at TAIL without replacing
 * or canceling vanilla rendering pipelines.
 */
@Mixin(LightmapRenderStateExtractor.class)
public class LightmapRenderStateExtractorMixin {
	// require = 0 allows the mixin to fail gracefully with a log warning instead of crashing
	// if Mojang alters or refactors the extract() method in future snapshots.
	@Inject(method = "extract", at = @At("TAIL"), require = 0)
	private void onExtract(LightmapRenderState state, float delta, CallbackInfo ci) {
		if (ShhhBrightnessToggleClient.isBrightnessOn()) {
			float level = ShhhBrightnessToggleClient.getBrightnessLevel();
			// Lift ambient illumination using the shader-native night vision curve
			state.nightVisionEffectIntensity = Math.max(state.nightVisionEffectIntensity, level);
			state.nightVisionColor = LightmapRenderStateExtractor.WHITE;
			state.needsUpdate = true;
		}
	}
}
