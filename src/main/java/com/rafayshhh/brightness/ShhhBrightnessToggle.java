package com.rafayshhh.brightness;

import com.rafayshhh.brightness.client.ShhhBrightnessToggleClient;
import com.rafayshhh.brightness.client.config.ShhhBrightnessConfig;
import com.rafayshhh.brightness.client.gui.ShhhBrightnessConfigScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(ShhhBrightnessToggle.MOD_ID)
public class ShhhBrightnessToggle {
    public static final String MOD_ID = "shhh_brightness_toggle";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public ShhhBrightnessToggle() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            modEventBus.addListener(ShhhBrightnessToggleClient::onRegisterKeyMappings);
            modEventBus.addListener(this::clientSetup);
            MinecraftForge.EVENT_BUS.register(ShhhBrightnessToggleClient.class);
            ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((mc, screen) -> new ShhhBrightnessConfigScreen(screen))
            );
        });
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        ShhhBrightnessConfig.load();
        LOGGER.info("[Shhh Brightness Toggle] Initialized for Minecraft 1.20.1 (Forge).");
    }

    private void clientSetup(FMLClientSetupEvent event) {
    }
}
