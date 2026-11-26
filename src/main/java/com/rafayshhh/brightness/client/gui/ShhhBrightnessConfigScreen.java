package com.rafayshhh.brightness.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.rafayshhh.brightness.client.ShhhBrightnessToggleClient;
import com.rafayshhh.brightness.client.config.ShhhBrightnessConfig;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

/**
 * In-game configuration screen for Shhh Brightness Toggle (Minecraft 1.18.2).
 * Accessible via Mod Menu or custom keybinds.
 */
public class ShhhBrightnessConfigScreen extends Screen {
	private final Screen parent;

	public ShhhBrightnessConfigScreen(Screen parent) {
		super(new TranslatableComponent("shhh-brightness-toggle.config.title"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		int centerX = this.width / 2 - 105;
		int startY = this.height / 4 + 24;

		// 1. Fullbright ON/OFF Toggle Button
		Button toggleButton = new Button(
			centerX, startY, 210, 20,
			getToggleMessage(),
			button -> {
				ShhhBrightnessConfig config = ShhhBrightnessConfig.get();
				config.enabled = !config.enabled;
				ShhhBrightnessConfig.save();
				ShhhBrightnessToggleClient.resetDiagnostic();
				button.setMessage(getToggleMessage());
			}
		);
		this.addButton(toggleButton);

		// 2. Brightness Level Slider (10% to 100%)
		double initialSliderValue = (ShhhBrightnessConfig.get().level - 0.1) / 0.9;
		AbstractSliderButton slider = new AbstractSliderButton(centerX, startY + 28, 210, 20, TextComponent.EMPTY, initialSliderValue) {
			@Override
			protected void updateMessage() {
				float currentLevel = ShhhBrightnessConfig.get().level;
				int percent = Math.round(currentLevel * 100.0f);
				MutableComponent text = new TranslatableComponent("shhh-brightness-toggle.config.level", percent);
				if (ShhhBrightnessConfig.get().coloredText) {
					text.withStyle(style -> style.withColor(TextColor.fromRgb(ShhhBrightnessToggleClient.getLevelColor(currentLevel))));
				}
				setMessage(text);
			}

			@Override
			protected void applyValue() {
				float newLevel = (float) Math.round((0.1 + this.value * 0.9) * 10.0) / 10.0f;
				ShhhBrightnessConfig config = ShhhBrightnessConfig.get();
				config.level = Math.max(0.1f, Math.min(1.0f, newLevel));
				ShhhBrightnessConfig.save();
				updateMessage();
			}
		};
		int initialPercent = Math.round(ShhhBrightnessConfig.get().level * 100.0f);
		MutableComponent initialSliderText = new TranslatableComponent("shhh-brightness-toggle.config.level", initialPercent);
		if (ShhhBrightnessConfig.get().coloredText) {
			initialSliderText.withStyle(style -> style.withColor(TextColor.fromRgb(ShhhBrightnessToggleClient.getLevelColor(ShhhBrightnessConfig.get().level))));
		}
		slider.setMessage(initialSliderText);
		this.addButton(slider);

		// 3. Colored Messages ON/OFF Toggle Button
		Button colorToggleButton = new Button(
			centerX, startY + 56, 210, 20,
			getColorToggleMessage(),
			button -> {
				ShhhBrightnessConfig config = ShhhBrightnessConfig.get();
				config.coloredText = !config.coloredText;
				ShhhBrightnessConfig.save();
				button.setMessage(getColorToggleMessage());
				int p = Math.round(config.level * 100.0f);
				MutableComponent updatedText = new TranslatableComponent("shhh-brightness-toggle.config.level", p);
				if (config.coloredText) {
					updatedText.withStyle(style -> style.withColor(TextColor.fromRgb(ShhhBrightnessToggleClient.getLevelColor(config.level))));
				}
				slider.setMessage(updatedText);
			}
		);
		this.addButton(colorToggleButton);

		// 4. Done Button
		Button doneButton = new Button(
			centerX, startY + 96, 210, 20,
			CommonComponents.GUI_DONE,
			button -> onClose()
		);
		this.addButton(doneButton);
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		this.renderBackground(poseStack);
		drawCenteredString(poseStack, this.font, this.title, this.width / 2, this.height / 4, 0xFFFFFF);
		super.render(poseStack, mouseX, mouseY, partialTick);
	}

	@Override
	public void onClose() {
		ShhhBrightnessConfig.save();
		if (this.minecraft != null) {
			this.minecraft.setScreen(this.parent);
		}
	}

	private Component getToggleMessage() {
		return CommonComponents.optionStatus(
			new TranslatableComponent("shhh-brightness-toggle.config.toggle"),
			ShhhBrightnessConfig.get().enabled
		);
	}

	private Component getColorToggleMessage() {
		return CommonComponents.optionStatus(
			new TranslatableComponent("shhh-brightness-toggle.config.colored_text"),
			ShhhBrightnessConfig.get().coloredText
		);
	}
}
