package com.rafayshhh.brightness.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.rafayshhh.brightness.client.config.ShhhBrightnessConfig;
import com.rafayshhh.brightness.client.gui.ShhhBrightnessConfigScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShhhBrightnessToggleClient {
    public static final String MOD_ID = "shhh_brightness_toggle";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final String CATEGORY = "key.category.shhh-brightness-toggle.main";

    public static final KeyMapping TOGGLE_KEY = new KeyMapping(
        "key.shhh-brightness-toggle.toggle",
        InputConstants.KEY_B,
        CATEGORY
    );

    public static final KeyMapping INCREASE_KEY = new KeyMapping(
        "key.shhh-brightness-toggle.increase",
        InputConstants.KEY_UP,
        CATEGORY
    );

    public static final KeyMapping DECREASE_KEY = new KeyMapping(
        "key.shhh-brightness-toggle.decrease",
        InputConstants.KEY_DOWN,
        CATEGORY
    );

    public static volatile boolean isMixinActive = false;
    private static boolean hasWarned = false;
    private static int diagnosticTicks = 0;

    public static void saveVanillaGamma() {
    }

    public static void restoreVanillaGamma() {
    }

    public static void resetDiagnostic() {
        isMixinActive = false;
        hasWarned = false;
        diagnosticTicks = 0;
    }

    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_KEY);
        event.register(INCREASE_KEY);
        event.register(DECREASE_KEY);
    }

    @SubscribeEvent
    public static void onLoggingIn(ClientPlayerNetworkEvent.LoggingIn event) {
        saveVanillaGamma();
        resetDiagnostic();
    }

    @SubscribeEvent
    public static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        restoreVanillaGamma();
        resetDiagnostic();
    }

    @SubscribeEvent
    public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(
            Commands.literal("shhh")
                .executes(ctx -> {
                    Minecraft mc = Minecraft.getInstance();
                    mc.tell(() -> mc.setScreen(new ShhhBrightnessConfigScreen(null)));
                    return 1;
                })
                .then(Commands.literal("toggle")
                    .executes(ctx -> {
                        ShhhBrightnessConfig config = ShhhBrightnessConfig.get();
                        config.enabled = !config.enabled;
                        ShhhBrightnessConfig.save();
                        resetDiagnostic();

                        Minecraft client = Minecraft.getInstance();
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
                                0xAAAAAA
                            );
                        }
                        return 1;
                    })
                )
        );
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft client = Minecraft.getInstance();
        if (client.player == null) {
            return;
        }

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
                    0xAAAAAA
                );
            }
        }

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
    }

    public static boolean isBrightnessOn() {
        return ShhhBrightnessConfig.get().enabled;
    }

    public static float getBrightnessLevel() {
        return ShhhBrightnessConfig.get().level;
    }

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
