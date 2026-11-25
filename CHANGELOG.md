# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0+mc1.19.4] - 2026-11-25

### Ported
- Initial port of Shhh Brightness Toggle to Minecraft 1.19.4 on Fabric.
- Compiled against Java 17, Fabric Loader >=0.14.21, and Fabric API 0.87.0+1.19.4 with official Mojang mappings.
- Adapted `ShhhBrightnessConfigScreen` to use `PoseStack` and manual coordinate layout compatible with Minecraft 1.19.4.

### Features
- Brightness toggle keybind (B by default) to turn fullbright on or off while playing.
- Increase and decrease keybinds (Up Arrow and Down Arrow by default) to adjust brightness between 10% and 100% in 10% steps.
- Lightmap night-vision scaling in `LightTexture` using non-destructive SpongePowered Mixin `@Redirect` hooks with `require = 0`, completely avoiding potion particles, swirls, or HUD status icons.
- If real Night Vision potion effect is active, respects the higher value seamlessly.
- Zero `options.txt` tampering: does not touch or overwrite vanilla `options.gamma`.
- Dynamic color feedback on on-screen actionbar messages and slider labels, changing from red at low brightness to yellow at mid levels and green at maximum.
- Colored feedback toggle in settings for players who prefer uncolored text.
- In-game settings screen with a toggle button, brightness slider, and colored messages switch.
- Mod Menu 6.3.1 integration for Minecraft 1.19.4 as an optional dependency.
- JSON configuration file (`.minecraft/config/shhh-brightness-toggle.json`) that saves settings and recovers automatically if corrupted.
- Client-side only design with zero server network traffic.
