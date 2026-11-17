package com.rafayshhh.brightness.client.command;

import com.rafayshhh.brightness.client.BrightnessHandler;
import com.rafayshhh.brightness.client.config.ShhhBrightnessConfig;
import com.rafayshhh.brightness.client.gui.ShhhBrightnessConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.Arrays;
import java.util.List;

public class CommandShhh extends CommandBase {
    @Override
    public String getCommandName() {
        return "shhh";
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("brightness", "fullbright");
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/shhh [config|toggle]";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 0 && args[0].equalsIgnoreCase("toggle")) {
            ShhhBrightnessConfig config = ShhhBrightnessConfig.get();
            config.enabled = !config.enabled;
            config.save();
            BrightnessHandler.showBrightnessMessage();
            return;
        }

        Minecraft.getMinecraft().addScheduledTask(new Runnable() {
            @Override
            public void run() {
                Minecraft.getMinecraft().displayGuiScreen(new ShhhBrightnessConfigScreen(null));
            }
        });
    }
}
