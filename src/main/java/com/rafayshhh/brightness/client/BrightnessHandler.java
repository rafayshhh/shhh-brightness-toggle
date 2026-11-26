package com.rafayshhh.brightness.client;

import com.rafayshhh.brightness.client.config.ShhhBrightnessConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.lwjgl.input.Keyboard;

public class BrightnessHandler {
    public static final KeyBinding KEY_TOGGLE = new KeyBinding("key.shhh-brightness-toggle.toggle", Keyboard.KEY_B, "key.categories.shhh-brightness-toggle");
    public static final KeyBinding KEY_INCREASE = new KeyBinding("key.shhh-brightness-toggle.increase", Keyboard.KEY_UP, "key.categories.shhh-brightness-toggle");
    public static final KeyBinding KEY_DECREASE = new KeyBinding("key.shhh-brightness-toggle.decrease", Keyboard.KEY_DOWN, "key.categories.shhh-brightness-toggle");

    private static float savedVanillaGamma = 1.0f;
    private static boolean hasCapturedVanillaGamma = false;

    public static void init() {
        ClientRegistry.registerKeyBinding(KEY_TOGGLE);
        ClientRegistry.registerKeyBinding(KEY_INCREASE);
        ClientRegistry.registerKeyBinding(KEY_DECREASE);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                restoreVanillaGamma();
            }
        }, "Shhh-Brightness-Restore"));
    }

    public static void captureVanillaGamma() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.gameSettings != null) {
            float current = mc.gameSettings.gammaSetting;
            if (current <= 1.0f) {
                savedVanillaGamma = current;
                hasCapturedVanillaGamma = true;
            }
        }
    }

    public static void restoreVanillaGamma() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc != null && mc.gameSettings != null) {
            mc.gameSettings.gammaSetting = savedVanillaGamma;
            mc.gameSettings.saveOptions();
        }
    }

    public static void showBrightnessMessage() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null || mc.ingameGUI == null) {
            return;
        }

        ShhhBrightnessConfig config = ShhhBrightnessConfig.get();
        String text;
        if (!config.enabled) {
            text = config.coloredText
                    ? TextFormatting.RED + "Brightness: Off" + TextFormatting.RESET
                    : "Brightness: Off";
        } else {
            int percent = Math.round(config.level * 100.0f);
            if (config.coloredText) {
                TextFormatting color;
                if (percent <= 30) {
                    color = TextFormatting.RED;
                } else if (percent <= 70) {
                    color = TextFormatting.YELLOW;
                } else {
                    color = TextFormatting.GREEN;
                }
                text = "Brightness: " + color + percent + "%" + TextFormatting.RESET;
            } else {
                text = "Brightness: " + percent + "%";
            }
        }

        mc.ingameGUI.setOverlayMessage(text, false);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null) {
            return;
        }

        ShhhBrightnessConfig config = ShhhBrightnessConfig.get();

        if (KEY_TOGGLE.isPressed()) {
            config.enabled = !config.enabled;
            config.save();
            showBrightnessMessage();
        } else if (KEY_INCREASE.isPressed()) {
            config.enabled = true;
            config.level = Math.min(1.0f, config.level + 0.1f);
            config.level = Math.round(config.level * 10.0f) / 10.0f;
            config.save();
            showBrightnessMessage();
        } else if (KEY_DECREASE.isPressed()) {
            config.enabled = true;
            config.level = Math.max(0.1f, config.level - 0.1f);
            config.level = Math.round(config.level * 10.0f) / 10.0f;
            config.save();
            showBrightnessMessage();
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.gameSettings == null) {
            return;
        }

        if (!hasCapturedVanillaGamma) {
            captureVanillaGamma();
        }

        if (mc.currentScreen instanceof GuiVideoSettings || mc.currentScreen instanceof GuiOptions) {
            return;
        }

        ShhhBrightnessConfig config = ShhhBrightnessConfig.get();
        if (config.enabled) {
            float targetGamma = config.level * 100.0f;
            if (Math.abs(mc.gameSettings.gammaSetting - targetGamma) > 0.01f) {
                mc.gameSettings.gammaSetting = targetGamma;
            }
        } else {
            if (Math.abs(mc.gameSettings.gammaSetting - savedVanillaGamma) > 0.01f) {
                mc.gameSettings.gammaSetting = savedVanillaGamma;
            }
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiVideoSettings || event.getGui() instanceof GuiOptions) {
            restoreVanillaGamma();
        }
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        restoreVanillaGamma();
    }

    @SubscribeEvent
    public void onClientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        restoreVanillaGamma();
    }
}
