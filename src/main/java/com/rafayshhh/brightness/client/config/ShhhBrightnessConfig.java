package com.rafayshhh.brightness.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.minecraft.client.Minecraft;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ShhhBrightnessConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Object LOCK = new Object();
    private static ShhhBrightnessConfig instance;

    public boolean enabled = true;
    public float level = 1.0f;
    public boolean coloredText = true;

    private transient boolean lastSavedEnabled = true;
    private transient float lastSavedLevel = 1.0f;
    private transient boolean lastSavedColoredText = true;

    public static ShhhBrightnessConfig get() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = load();
            }
            return instance;
        }
    }

    private static File getConfigFile() {
        File configDir = new File(Minecraft.getMinecraft().gameDir, "config");
        if (!configDir.exists()) {
            configDir.mkdirs();
        }
        return new File(configDir, "shhh-brightness-toggle.json");
    }

    public static ShhhBrightnessConfig load() {
        File file = getConfigFile();
        if (!file.exists() || file.length() == 0) {
            ShhhBrightnessConfig config = new ShhhBrightnessConfig();
            config.save();
            return config;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            ShhhBrightnessConfig loaded = GSON.fromJson(reader, ShhhBrightnessConfig.class);
            if (loaded == null) {
                throw new JsonSyntaxException("Empty or invalid JSON");
            }
            loaded.level = clampLevel(loaded.level);
            loaded.markSaved();
            return loaded;
        } catch (JsonSyntaxException | IOException e) {
            System.err.println("[Shhh Brightness Toggle] Corrupted or invalid config file, resetting to safe defaults: " + e.getMessage());
            ShhhBrightnessConfig config = new ShhhBrightnessConfig();
            config.save();
            return config;
        }
    }

    public void save() {
        synchronized (LOCK) {
            this.level = clampLevel(this.level);
            if (isClean()) {
                return;
            }

            File file = getConfigFile();
            File tempFile = new File(file.getParentFile(), file.getName() + ".tmp");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                GSON.toJson(this, writer);
                writer.flush();
                if (file.exists()) {
                    file.delete();
                }
                tempFile.renameTo(file);
                markSaved();
            } catch (IOException e) {
                System.err.println("[Shhh Brightness Toggle] Failed to save config: " + e.getMessage());
            }
        }
    }

    public static float clampLevel(float value) {
        if (Float.isNaN(value)) {
            return 1.0f;
        }
        if (value < 0.1f) return 0.1f;
        if (value > 1.0f) return 1.0f;
        return Math.round(value * 10.0f) / 10.0f;
    }

    private boolean isClean() {
        return this.enabled == this.lastSavedEnabled
                && Math.abs(this.level - this.lastSavedLevel) < 0.0001f
                && this.coloredText == this.lastSavedColoredText;
    }

    private void markSaved() {
        this.lastSavedEnabled = this.enabled;
        this.lastSavedLevel = this.level;
        this.lastSavedColoredText = this.coloredText;
    }
}
