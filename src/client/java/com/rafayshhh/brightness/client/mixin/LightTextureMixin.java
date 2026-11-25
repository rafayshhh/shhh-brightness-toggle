package com.rafayshhh.brightness.client.mixin;

import com.rafayshhh.brightness.client.ShhhBrightnessToggleClient;
import com.rafayshhh.brightness.client.config.ShhhBrightnessConfig;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Mixin into LightTexture.updateLightTexture to scale the lightmap night-vision factor
 * cleanly without potion icons, particle effects, or modifying game options.
 * Adapted for Minecraft 1.21.1 (Holder<MobEffect> registry architecture).
 */
@Mixin(LightTexture.class)
public class LightTextureMixin {

	/**
	 * Vanilla LightTexture gates getNightVisionScale behind player.hasEffect(MobEffects.NIGHT_VISION).
	 * If the player does not have a real potion effect, getNightVisionScale is skipped and the night
	 * vision factor defaults to 0.0f.
	 *
	 * When the mod is enabled, we report true for NIGHT_VISION so vanilla enters the night-vision branch.
	 */
	@Redirect(
		method = "updateLightTexture",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/player/LocalPlayer;hasEffect(Lnet/minecraft/core/Holder;)Z"
		),
		require = 0
	)
	private boolean shhh_fakeNightVisionEffect(LocalPlayer player, Holder<MobEffect> effect) {
		if (effect.equals(MobEffects.NIGHT_VISION) && ShhhBrightnessConfig.get().enabled) {
			return true;
		}
		return player.hasEffect(effect);
	}

	/**
	 * Intercepts GameRenderer.getNightVisionScale to supply our custom brightness level (10% to 100%).
	 * If the player actually has a real Night Vision effect, the higher of the two values is used.
	 * When the mod is disabled, vanilla's value is returned untouched.
	 */
	@Redirect(
		method = "updateLightTexture",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/GameRenderer;getNightVisionScale(Lnet/minecraft/world/entity/LivingEntity;F)F"
		),
		require = 0
	)
	private float shhh_modifyNightVisionScale(LivingEntity entity, float partialTicks) {
		ShhhBrightnessToggleClient.isMixinActive = true;
		float vanillaValue = 0.0f;
		if (entity.hasEffect(MobEffects.NIGHT_VISION)) {
			vanillaValue = GameRenderer.getNightVisionScale(entity, partialTicks);
		}
		if (!ShhhBrightnessConfig.get().enabled) {
			return vanillaValue;
		}
		return Math.max(vanillaValue, ShhhBrightnessToggleClient.getBrightnessLevel());
	}
}
