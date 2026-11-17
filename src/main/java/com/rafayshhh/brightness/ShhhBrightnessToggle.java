package com.rafayshhh.brightness;

import com.rafayshhh.brightness.client.BrightnessHandler;
import com.rafayshhh.brightness.client.command.CommandShhh;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(
        modid = ShhhBrightnessToggle.MODID,
        name = ShhhBrightnessToggle.NAME,
        version = ShhhBrightnessToggle.VERSION,
        clientSideOnly = true,
        guiFactory = "com.rafayshhh.brightness.client.gui.ShhhGuiFactory"
)
public class ShhhBrightnessToggle {
    public static final String MODID = "shhh-brightness-toggle";
    public static final String NAME = "Shhh Brightness Toggle";
    public static final String VERSION = "1.0.0+mc1.8.9";

    @EventHandler
    public void init(FMLInitializationEvent event) {
        BrightnessHandler.init();
        MinecraftForge.EVENT_BUS.register(new BrightnessHandler());
        ClientCommandHandler.instance.registerCommand(new CommandShhh());
        System.out.println("[Shhh Brightness Toggle] Initialized for Minecraft 1.8.9 (Forge).");
    }
}
