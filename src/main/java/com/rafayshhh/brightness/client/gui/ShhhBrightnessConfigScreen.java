package com.rafayshhh.brightness.client.gui;

import com.rafayshhh.brightness.client.ShhhBrightnessToggleClient;
import com.rafayshhh.brightness.client.config.ShhhBrightnessConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;

/**
 * In-game configuration screen for Shhh Brightness Toggle (Minecraft 1.20.1).
 * Accessible via Mod Menu or custom keybinds.
 */
public class ShhhBrightnessConfigScreen extends Screen {
	private final Screen parent;
	private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);

	public ShhhBrightnessConfigScreen(Screen parent) {
		super(Component.translatable("shhh-brightness-toggle.config.title"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		StringWidget titleWidget = new StringWidget(this.title, this.font);
		this.layout.addToHeader(titleWidget);
		addRenderableWidget(titleWidget);

		GridLayout content = new GridLayout();
		content.defaultCellSetting().padding(5);
		GridLayout.RowHelper rowHelper = content.createRowHelper(1);

		// 1. Fullbright ON/OFF Toggle Button
		Button toggleButton = Button.builder(
			getToggleMessage(),
			button -> {
				ShhhBrightnessConfig config = ShhhBrightnessConfig.get();
				config.enabled = !config.enabled;
				ShhhBrightnessConfig.save();
				ShhhBrightnessToggleClient.resetDiagnostic();
				button.setMessage(getToggleMessage());
			}
		).width(210).build();
		rowHelper.addChild(toggleButton);
		addRenderableWidget(toggleButton);

		// 2. Brightness Level Slider (10% to 100%)
		double initialSliderValue = (ShhhBrightnessConfig.get().level - 0.1) / 0.9;
		AbstractSliderButton slider = new AbstractSliderButton(0, 0, 210, 20, CommonComponents.EMPTY, initialSliderValue) {
			@Override
			protected void updateMessage() {
				float currentLevel = ShhhBrightnessConfig.get().level;
				int percent = Math.round(currentLevel * 100.0f);
				MutableComponent text = Component.translatable("shhh-brightness-toggle.config.level", percent);
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
		MutableComponent initialSliderText = Component.translatable("shhh-brightness-toggle.config.level", initialPercent);
		if (ShhhBrightnessConfig.get().coloredText) {
			initialSliderText.withStyle(style -> style.withColor(TextColor.fromRgb(ShhhBrightnessToggleClient.getLevelColor(ShhhBrightnessConfig.get().level))));
		}
		slider.setMessage(initialSliderText);
		rowHelper.addChild(slider);
		addRenderableWidget(slider);

		// 3. Colored Messages ON/OFF Toggle Button
		Button colorToggleButton = Button.builder(
			getColorToggleMessage(),
			button -> {
				ShhhBrightnessConfig config = ShhhBrightnessConfig.get();
				config.coloredText = !config.coloredText;
				ShhhBrightnessConfig.save();
				button.setMessage(getColorToggleMessage());
				int p = Math.round(config.level * 100.0f);
				MutableComponent updatedText = Component.translatable("shhh-brightness-toggle.config.level", p);
				if (config.coloredText) {
					updatedText.withStyle(style -> style.withColor(TextColor.fromRgb(ShhhBrightnessToggleClient.getLevelColor(config.level))));
				}
				slider.setMessage(updatedText);
			}
		).width(210).build();
		rowHelper.addChild(colorToggleButton);
		addRenderableWidget(colorToggleButton);

		this.layout.addToContents(content);

		// 4. Done Button
		Button doneButton = Button.builder(CommonComponents.GUI_DONE, button -> onClose()).width(210).build();
		this.layout.addToFooter(doneButton);
		addRenderableWidget(doneButton);

		repositionElements();
	}

	@Override
	protected void repositionElements() {
		this.layout.arrangeElements();
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTick);
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
			Component.translatable("shhh-brightness-toggle.config.toggle"),
			ShhhBrightnessConfig.get().enabled
		);
	}

	private Component getColorToggleMessage() {
		return CommonComponents.optionStatus(
			Component.translatable("shhh-brightness-toggle.config.colored_text"),
			ShhhBrightnessConfig.get().coloredText
		);
	}
}
