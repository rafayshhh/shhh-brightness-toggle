package com.rafayshhh.brightness.client.gui;

import com.rafayshhh.brightness.client.config.ShhhBrightnessConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;

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
            TextFormatting color;
            if (percent <= 30) {
                color = TextFormatting.RED;
            } else if (percent <= 70) {
                color = TextFormatting.YELLOW;
            } else {
                color = TextFormatting.GREEN;
            }
            this.displayString = "Brightness: " + color + percent + "%" + TextFormatting.RESET;
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
            this.sliderValue = (float) (mouseX - (this.x + 4)) / (float) (this.width - 8);
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
            this.sliderValue = (float) (mouseX - (this.x + 4)) / (float) (this.width - 8);
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
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int hoverState = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            this.drawTexturedModalRect(this.x, this.y, 0, 46 + hoverState * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + hoverState * 20, this.width / 2, this.height);
            this.mouseDragged(mc, mouseX, mouseY);

            int knobX = this.x + (int) (this.sliderValue * (float) (this.width - 8));
            int knobY = this.y;
            this.drawTexturedModalRect(knobX, knobY, 0, 66, 4, 20);
            this.drawTexturedModalRect(knobX + 4, knobY, 196, 66, 4, 20);

            int textColor = 14737632;
            if (!this.enabled) {
                textColor = 10526880;
            } else if (this.hovered) {
                textColor = 16777120;
            }

            this.drawCenteredString(mc.fontRenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, textColor);
        }
    }
}
