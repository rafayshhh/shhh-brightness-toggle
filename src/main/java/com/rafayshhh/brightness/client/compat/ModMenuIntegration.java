package com.rafayshhh.brightness.client.compat;

import com.rafayshhh.brightness.client.gui.ShhhBrightnessConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuIntegration implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return ShhhBrightnessConfigScreen::new;
	}
}
