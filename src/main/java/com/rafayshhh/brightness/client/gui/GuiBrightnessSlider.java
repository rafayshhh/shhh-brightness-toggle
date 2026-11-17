package com.rafayshhh.brightness.client.gui;

import com.rafayshhh.brightness.client.config.ShhhBrightnessConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;

public class GuiBrightnessSlider extends GuiButton {
    private float sliderValue;
    public boolean dragging;

    public GuiBrightnessSlider(int buttonId, int x, int y, int widthIn, int heightIn, float currentLevel) {
        super(buttonId, x, y, widthIn, heightIn, "");
        this.sliderValue = (currentLevel - 0.1f) / 0.9f;
        updateText();
    }

    public float getBrightnessLevel() {
        float raw = 0.1f + this.sliderValue * 0.9f;
        return ShhhBrightnessConfig.clampLevel(raw);
    }

    public void updateText() {
        int percent = Math.round(getBrightnessLevel() * 100.0f);
        ShhhBrightnessConfig config = ShhhBrightnessConfig.get();
        if (config.coloredText) {
            EnumChatFormatting color;
            if (percent <= 30) {
                color = EnumChatFormatting.RED;
            } else if (percent <= 70) {
                color = EnumChatFormatting.YELLOW;
            } else {
                color = EnumChatFormatting.GREEN;
            }
            this.displayString = "Brightness: " + color + percent + "%" + EnumChatFormatting.RESET;
        } else {
            this.displayString = "Brightness: " + percent + "%";
        }
    }

    @Override
    protected int getHoverState(boolean mouseOver) {
        return 0;
    }

    @Override
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible && this.dragging) {
            this.sliderValue = (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);
            if (this.sliderValue < 0.0f) {
                this.sliderValue = 0.0f;
            }
            if (this.sliderValue > 1.0f) {
                this.sliderValue = 1.0f;
            }

            float newLevel = getBrightnessLevel();
            ShhhBrightnessConfig.get().level = newLevel;
            updateText();
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (super.mousePressed(mc, mouseX, mouseY)) {
            this.sliderValue = (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);
            if (this.sliderValue < 0.0f) {
                this.sliderValue = 0.0f;
            }
            if (this.sliderValue > 1.0f) {
                this.sliderValue = 1.0f;
            }

            float newLevel = getBrightnessLevel();
            ShhhBrightnessConfig.get().level = newLevel;
            updateText();
            this.dragging = true;
            return true;
        }
        return false;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        this.dragging = false;
        ShhhBrightnessConfig.get().save();
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46, this.width / 2, this.height);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46, this.width / 2, this.height);

            this.mouseDragged(mc, mouseX, mouseY);

            int handleX = (int) (this.sliderValue * (float) (this.width - 8));
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.xPosition + handleX, this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + handleX + 4, this.yPosition, 196, 66, 4, 20);

            this.drawCenteredString(mc.fontRendererObj, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 14737632);
        }
    }
}
