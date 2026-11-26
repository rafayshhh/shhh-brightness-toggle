package com.rafayshhh.brightness.client.gui;

import com.rafayshhh.brightness.client.config.ShhhBrightnessConfig;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextFormatting;

import java.io.IOException;

public class ShhhBrightnessConfigScreen extends GuiScreen {
    private final GuiScreen parentScreen;
    private GuiButton toggleButton;
    private GuiBrightnessSlider brightnessSlider;
    private GuiButton coloredTextButton;

    public ShhhBrightnessConfigScreen(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();

        int centerX = this.width / 2;
        int startY = this.height / 4 + 8;
        int buttonWidth = 200;
        int buttonHeight = 20;
        int spacing = 26;

        ShhhBrightnessConfig config = ShhhBrightnessConfig.get();

        this.toggleButton = new GuiButton(1, centerX - buttonWidth / 2, startY, buttonWidth, buttonHeight, getToggleText(config.enabled));
        this.buttonList.add(this.toggleButton);

        this.brightnessSlider = new GuiBrightnessSlider(2, centerX - buttonWidth / 2, startY + spacing, buttonWidth, buttonHeight, config.level);
        this.buttonList.add(this.brightnessSlider);

        this.coloredTextButton = new GuiButton(3, centerX - buttonWidth / 2, startY + spacing * 2, buttonWidth, buttonHeight, getColoredText(config.coloredText));
        this.buttonList.add(this.coloredTextButton);

        this.buttonList.add(new GuiButton(4, centerX - buttonWidth / 2, startY + spacing * 4, buttonWidth, buttonHeight, "Done"));
    }

    private String getToggleText(boolean enabled) {
        if (enabled) {
            return "Fullbright: " + TextFormatting.GREEN + "Enabled" + TextFormatting.RESET;
        } else {
            return "Fullbright: " + TextFormatting.RED + "Disabled" + TextFormatting.RESET;
        }
    }

    private String getColoredText(boolean coloredText) {
        if (coloredText) {
            return "Colored Text: " + TextFormatting.GREEN + "Enabled" + TextFormatting.RESET;
        } else {
            return "Colored Text: " + TextFormatting.RED + "Disabled" + TextFormatting.RESET;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        ShhhBrightnessConfig config = ShhhBrightnessConfig.get();

        if (button.id == 1) {
            config.enabled = !config.enabled;
            this.toggleButton.displayString = getToggleText(config.enabled);
            config.save();
        } else if (button.id == 3) {
            config.coloredText = !config.coloredText;
            this.coloredTextButton.displayString = getColoredText(config.coloredText);
            this.brightnessSlider.updateText();
            config.save();
        } else if (button.id == 4) {
            config.save();
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) { // ESC key
            ShhhBrightnessConfig.get().save();
            this.mc.displayGuiScreen(this.parentScreen);
            return;
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "Shhh Brightness Toggle - Configuration", this.width / 2, 15, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
}
