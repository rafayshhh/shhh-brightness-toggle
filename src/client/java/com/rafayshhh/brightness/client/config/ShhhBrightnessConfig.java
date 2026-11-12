package com.rafayshhh.brightness.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Thread-safe configuration manager for Shhh Brightness Toggle.
 * Handles loading, automatic error recovery, clamping, and dirty-checking saves.
 */
public class ShhhBrightnessConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger("shhh-brightness-toggle");
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("shhh-brightness-toggle.json");

	private static ShhhBrightnessConfig instance;

	// Configurable settings
	public boolean enabled = true;
	public float level = 1.0f; // Range: 0.1f (10%) to 1.0f (100%)
	public boolean coloredText = true; // Enables smooth color grading on messages & slider

	// State tracking to ensure we only save when values actually change
	private transient boolean lastSavedEnabled = true;
	private transient float lastSavedLevel = 1.0f;
	private transient boolean lastSavedColoredText = true;
	private transient boolean isInitialized = false;

	public static ShhhBrightnessConfig get() {
		if (instance == null) {
			load();
		}
		return instance;
	}

	public static synchronized void load() {
		boolean needsRewrite = false;

		if (Files.exists(CONFIG_PATH)) {
			try {
				long fileSize = Files.size(CONFIG_PATH);
				if (fileSize == 0) {
					LOGGER.warn("[Shhh Brightness Toggle] Config file is empty. Restoring defaults.");
					instance = new ShhhBrightnessConfig();
					needsRewrite = true;
				} else {
					try (Reader reader = Files.newBufferedReader(CONFIG_PATH, StandardCharsets.UTF_8)) {
						instance = GSON.fromJson(reader, ShhhBrightnessConfig.class);
					}
					if (instance == null) {
						LOGGER.warn("[Shhh Brightness Toggle] Config file contained null JSON. Restoring defaults.");
						instance = new ShhhBrightnessConfig();
						needsRewrite = true;
					}
				}
			} catch (JsonParseException e) {
				LOGGER.warn("[Shhh Brightness Toggle] Config file has invalid JSON syntax. Recovering with defaults: {}", e.getMessage());
				instance = new ShhhBrightnessConfig();
				needsRewrite = true;
			} catch (Exception e) {
				LOGGER.error("[Shhh Brightness Toggle] Failed to read config file. Using defaults.", e);
				instance = new ShhhBrightnessConfig();
				needsRewrite = true;
			}
		} else {
			// First launch - create default configuration
			instance = new ShhhBrightnessConfig();
			needsRewrite = true;
		}

		// Sanitize and clamp values to valid ranges
		sanitize(instance);

		// Record the saved state
		instance.lastSavedEnabled = instance.enabled;
		instance.lastSavedLevel = instance.level;
		instance.lastSavedColoredText = instance.coloredText;
		instance.isInitialized = true;

		// Rewrite clean file if it was missing, corrupted, or empty
		if (needsRewrite) {
			forceSave();
		}
	}

	public static synchronized void save() {
		if (instance == null) {
			instance = new ShhhBrightnessConfig();
		}

		sanitize(instance);

		// Check if anything actually changed before writing to disk
		if (instance.isInitialized
				&& instance.enabled == instance.lastSavedEnabled
				&& Float.compare(instance.level, instance.lastSavedLevel) == 0
				&& instance.coloredText == instance.lastSavedColoredText) {
			return; // Nothing changed, skip file I/O
		}

		forceSave();
	}

	private static void forceSave() {
		try {
			if (CONFIG_PATH.getParent() != null) {
				Files.createDirectories(CONFIG_PATH.getParent());
			}
			try (Writer writer = Files.newBufferedWriter(CONFIG_PATH, StandardCharsets.UTF_8)) {
				GSON.toJson(instance, writer);
			}
			instance.lastSavedEnabled = instance.enabled;
			instance.lastSavedLevel = instance.level;
			instance.lastSavedColoredText = instance.coloredText;
			instance.isInitialized = true;
		} catch (Exception e) {
			LOGGER.error("[Shhh Brightness Toggle] Failed to save config file", e);
		}
	}

	private static void sanitize(ShhhBrightnessConfig cfg) {
		if (Float.isNaN(cfg.level) || Float.isInfinite(cfg.level)) {
			cfg.level = 1.0f;
		} else {
			// Round to nearest 0.1 step and clamp between 0.1 (10%) and 1.0 (100%)
			float rounded = Math.round(cfg.level * 10.0f) / 10.0f;
			cfg.level = Math.clamp(rounded, 0.1f, 1.0f);
		}
	}
}
