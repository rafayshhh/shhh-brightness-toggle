package com.rafayshhh.brightness.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.rafayshhh.brightness.client.config.ShhhBrightnessConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main client entrypoint for Shhh Brightness Toggle (Minecraft 1.20.1).
 * Option B: Pure lightmap night-vision scaling via LightTextureMixin.
 * Includes safe diagnostics to detect silent mixin failures without per-frame overhead.
 */
public class ShhhBrightnessToggleClient implements ClientModInitializer {
	public static final String MOD_ID = "shhh-brightness-toggle";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final String CATEGORY = "key.category.shhh-brightness-toggle.main";

	// Keybind 1: Toggle Fullbright ON/OFF (Default: B)
	public static final KeyMapping TOGGLE_KEY = KeyBindingHelper.registerKeyBinding(
		new KeyMapping(
			"key.shhh-brightness-toggle.toggle",
			InputConstants.KEY_B,
			CATEGORY
		)
	);

	// Keybind 2: Increase Brightness Level (Default: Up Arrow)
	public static final KeyMapping INCREASE_KEY = KeyBindingHelper.registerKeyBinding(
		new KeyMapping(
			"key.shhh-brightness-toggle.increase",
			InputConstants.KEY_UP,
			CATEGORY
		)
	);

	// Keybind 3: Decrease Brightness Level (Default: Down Arrow)
	public static final KeyMapping DECREASE_KEY = KeyBindingHelper.registerKeyBinding(
		new KeyMapping(
			"key.shhh-brightness-toggle.decrease",
			InputConstants.KEY_DOWN,
			CATEGORY
		)
	);

	// Diagnostic state: flag set by LightTextureMixin when its wrapped call runs
	public static volatile boolean isMixinActive = false;
	private static boolean hasWarned = false;
	private static int diagnosticTicks = 0;

	/**
	 * Vanilla gamma preservation hooks.
	 * In Option B (pure LightTexture mixin), options.gamma and options.txt are never modified,
	 * ensuring 100% safety on world exit, disconnect, and client termination.
	 */
	public static void saveVanillaGamma() {
		// Option B does not modify options.gamma or options.txt
	}

	public static void restoreVanillaGamma() {
		// Option B does not modify options.gamma or options.txt
	}

	/**
	 * Resets diagnostic tracking so checks begin anew upon toggle or world transition.
	 */
	public static void resetDiagnostic() {
		isMixinActive = false;
		hasWarned = false;
		diagnosticTicks = 0;
	}

	@Override
	public void onInitializeClient() {
		// 1. Initialize configuration from disk
		ShhhBrightnessConfig.load();

		LOGGER.info("[Shhh Brightness Toggle] Initialized for Minecraft 1.21.1 (Lightmap Night-Vision Scaling).");

		// 2. Lifecycle hooks for state consistency and diagnostic reset
		ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
			saveVanillaGamma();
			resetDiagnostic();
		});

		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
			restoreVanillaGamma();
			resetDiagnostic();
		});

		ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
			restoreVanillaGamma();
		});

		// 3. Client tick listener for keybind handling and diagnostic check
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player == null) {
				return;
			}

			// Handle Toggle Key (B)
			while (TOGGLE_KEY.consumeClick()) {
				ShhhBrightnessConfig config = ShhhBrightnessConfig.get();
				config.enabled = !config.enabled;
				ShhhBrightnessConfig.save();
				resetDiagnostic();

				if (config.enabled) {
					int percent = Math.round(config.level * 100.0f);
					sendFeedback(
						client,
						Component.translatable("shhh-brightness-toggle.message.on", percent),
						getLevelColor(config.level)
					);
				} else {
					sendFeedback(
						client,
						Component.translatable("shhh-brightness-toggle.message.off"),
						0xAAAAAA // Soft neutral gray for OFF state
					);
				}
			}

			// Handle Increase Key (Up Arrow)
			while (INCREASE_KEY.consumeClick()) {
				ShhhBrightnessConfig config = ShhhBrightnessConfig.get();
				if (!config.enabled) {
					config.enabled = true;
					resetDiagnostic();
				}
				float next = Math.min(1.0f, (float) Math.round((config.level + 0.1f) * 10.0f) / 10.0f);
				config.level = next;
				ShhhBrightnessConfig.save();

				int percent = Math.round(next * 100.0f);
				sendFeedback(
					client,
					Component.translatable("shhh-brightness-toggle.message.level", percent),
					getLevelColor(next)
				);
			}

			// Handle Decrease Key (Down Arrow)
			while (DECREASE_KEY.consumeClick()) {
				ShhhBrightnessConfig config = ShhhBrightnessConfig.get();
				if (!config.enabled) {
					config.enabled = true;
					resetDiagnostic();
				}
				float next = Math.max(0.1f, (float) Math.round((config.level - 0.1f) * 10.0f) / 10.0f);
				config.level = next;
				ShhhBrightnessConfig.save();

				int percent = Math.round(next * 100.0f);
				sendFeedback(
					client,
					Component.translatable("shhh-brightness-toggle.message.level", percent),
					getLevelColor(next)
				);
			}

			// Safe diagnostic: if enabled but hook has not run after ~5 seconds (100 ticks) of active gameplay, warn once
			if (ShhhBrightnessConfig.get().enabled && !isMixinActive && !client.isPaused()) {
				diagnosticTicks++;
				if (diagnosticTicks >= 100 && !hasWarned) {
					hasWarned = true;
					LOGGER.warn("Shhh Brightness Toggle: the lightmap hook did not run. Another mod (for example a lighting mod) may be replacing it.");
					client.player.displayClientMessage(
						Component.literal("Shhh Brightness Toggle: the lightmap hook did not run. Another mod (for example a lighting mod) may be replacing it.")
							.withStyle(ChatFormatting.GOLD),
						false
					);
					client.player.displayClientMessage(
						Component.literal("Shhh Brightness Toggle: the lightmap hook did not run. Another mod may be replacing it.")
							.withStyle(ChatFormatting.RED),
						true
					);
				}
			}
		});
	}

	public static boolean isBrightnessOn() {
		return ShhhBrightnessConfig.get().enabled;
	}

	public static float getBrightnessLevel() {
		return ShhhBrightnessConfig.get().level;
	}

	/**
	 * Computes a smooth color interpolation based on the brightness level.
	 * 0.1 (10% low)  -> Vivid Red (#FF3C3C)
	 * 0.55 (55% mid) -> Warm Gold / Yellow (#FFD700)
	 * 1.0 (100% max) -> Clean Soft Green (#55FF88)
	 */
	public static int getLevelColor(float level) {
		float t = Math.max(0.0f, Math.min(1.0f, (level - 0.1f) / 0.9f));
		int r;
		int g;
		int b;
		if (t < 0.5f) {
			float factor = t / 0.5f;
			r = 255;
			g = Math.round(60.0f + factor * 155.0f);
			b = Math.round(60.0f * (1.0f - factor));
		} else {
			float factor = (t - 0.5f) / 0.5f;
			r = Math.round(255.0f + factor * (85.0f - 255.0f));
			g = Math.round(215.0f + factor * (255.0f - 215.0f));
			b = Math.round(factor * 136.0f);
		}
		return (r << 16) | (g << 8) | b;
	}

	/**
	 * Sends an on-screen overlay message with optional dynamic color styling.
	 */
	private static void sendFeedback(Minecraft client, MutableComponent message, int rgbColor) {
		if (client.player == null) {
			return;
		}

		if (ShhhBrightnessConfig.get().coloredText) {
			message.withStyle(style -> style.withColor(TextColor.fromRgb(rgbColor)));
		}

		client.player.displayClientMessage(message, true);
	}
}
