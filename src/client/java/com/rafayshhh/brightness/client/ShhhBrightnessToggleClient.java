package com.rafayshhh.brightness.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.rafayshhh.brightness.client.config.ShhhBrightnessConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;

/**
 * Main client entrypoint for Shhh Brightness Toggle.
 * Handles keybind registration, gamma backup/restoration across game lifecycle events,
 * and dynamic color feedback (red at low brightness to green at high brightness).
 */
public class ShhhBrightnessToggleClient implements ClientModInitializer {
	public static final String MOD_ID = "shhh-brightness-toggle";

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	// Custom controls settings category
	public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(id("main"));

	// Keybind 1: Toggle Fullbright ON/OFF (Default: B)
	public static final KeyMapping TOGGLE_KEY = KeyMappingHelper.registerKeyMapping(
		new KeyMapping(
			"key.shhh-brightness-toggle.toggle",
			InputConstants.KEY_B,
			CATEGORY
		)
	);

	// Keybind 2: Increase Brightness Level (Default: Up Arrow)
	public static final KeyMapping INCREASE_KEY = KeyMappingHelper.registerKeyMapping(
		new KeyMapping(
			"key.shhh-brightness-toggle.increase",
			InputConstants.KEY_UP,
			CATEGORY
		)
	);

	// Keybind 3: Decrease Brightness Level (Default: Down Arrow)
	public static final KeyMapping DECREASE_KEY = KeyMappingHelper.registerKeyMapping(
		new KeyMapping(
			"key.shhh-brightness-toggle.decrease",
			InputConstants.KEY_DOWN,
			CATEGORY
		)
	);

	// Tracks original vanilla gamma so player's preference is always preserved
	private static double savedGamma = 1.0;
	private static boolean hasSavedGamma = false;

	@Override
	public void onInitializeClient() {
		// 1. Initialize configuration from disk
		ShhhBrightnessConfig.load();

		// 2. Preserve gamma on world connect/join
		ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
			if (ShhhBrightnessConfig.get().enabled) {
				saveVanillaGamma(client);
				applyState();
			}
		});

		// 3. Restore original vanilla gamma whenever disconnecting or leaving world
		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
			restoreVanillaGamma(client);
		});

		// 4. Restore original vanilla gamma when the game client shuts down
		ClientLifecycleEvents.CLIENT_STOPPING.register(ShhhBrightnessToggleClient::restoreVanillaGamma);

		// 5. Client tick listener for responsive keybind handling
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			// Guard: Do nothing if not actively in-game (avoids menu/loading NPEs)
			if (client.player == null || client.options == null) {
				return;
			}

			// Handle Toggle Key (B)
			while (TOGGLE_KEY.consumeClick()) {
				ShhhBrightnessConfig config = ShhhBrightnessConfig.get();
				config.enabled = !config.enabled;
				ShhhBrightnessConfig.save();

				if (config.enabled) {
					saveVanillaGamma(client);
					applyState();
					int percent = Math.round(config.level * 100.0f);
					sendFeedback(
						client,
						Component.translatable("shhh-brightness-toggle.message.on", percent),
						getLevelColor(config.level)
					);
				} else {
					restoreVanillaGamma(client);
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
					saveVanillaGamma(client);
					config.enabled = true;
				}
				float next = Math.min(1.0f, (float) Math.round((config.level + 0.1f) * 10.0f) / 10.0f);
				config.level = next;
				ShhhBrightnessConfig.save();

				applyState();
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
					saveVanillaGamma(client);
					config.enabled = true;
				}
				float next = Math.max(0.1f, (float) Math.round((config.level - 0.1f) * 10.0f) / 10.0f);
				config.level = next;
				ShhhBrightnessConfig.save();

				applyState();
				int percent = Math.round(next * 100.0f);
				sendFeedback(
					client,
					Component.translatable("shhh-brightness-toggle.message.level", percent),
					getLevelColor(next)
				);
			}

			// Maintain vanilla gamma at 1.0 while brightness is active
			if (ShhhBrightnessConfig.get().enabled && client.options.gamma().get() < 1.0) {
				client.options.gamma().set(1.0);
			}
		});
	}

	/**
	 * Backs up the user's authentic vanilla brightness setting before modifying it.
	 */
	public static synchronized void saveVanillaGamma(Minecraft client) {
		if (!hasSavedGamma && client != null && client.options != null) {
			double current = client.options.gamma().get();
			if (current <= 1.0) {
				savedGamma = current;
				hasSavedGamma = true;
			}
		}
	}

	/**
	 * Restores the user's true vanilla brightness setting.
	 */
	public static synchronized void restoreVanillaGamma(Minecraft client) {
		if (hasSavedGamma && client != null && client.options != null) {
			client.options.gamma().set(savedGamma);
			hasSavedGamma = false;
		}
	}

	/**
	 * Applies maximum gamma to the vanilla slider when fullbright is active.
	 */
	public static void applyState() {
		Minecraft client = Minecraft.getInstance();
		if (client != null && client.options != null && ShhhBrightnessConfig.get().enabled) {
			saveVanillaGamma(client);
			if (client.options.gamma().get() < 1.0) {
				client.options.gamma().set(1.0);
			}
		}
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
		float t = Math.clamp((level - 0.1f) / 0.9f, 0.0f, 1.0f);
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
			message.withColor(rgbColor);
		}

		client.player.sendOverlayMessage(message);
	}
}
