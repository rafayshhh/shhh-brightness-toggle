# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0+mc1.8.9] - 2026-11-17

### Added
- Initial release of Shhh Brightness Toggle for Minecraft 1.8.9 on Minecraft Forge.
- Brightness toggle keybind (B by default) to turn fullbright on or off while playing.
- Increase and decrease keybinds (Up Arrow and Down Arrow by default) to adjust brightness between 10% and 100% in 10% steps.
- Clean in-memory gamma lighting that operates without fake Night Vision effects, avoiding potion swirls or inventory HUD status icons.
- Zero `options.txt` tampering: vanilla gamma is automatically restored on world leave, server disconnect, and game shutdown.
- Dynamic color feedback on on-screen actionbar messages and slider labels, changing from red at low brightness to yellow at mid levels and green at maximum.
- Colored feedback toggle in settings for players who prefer uncolored text.
- In-game settings screen with a toggle button, brightness slider, and colored messages switch.
- In-game slash command `/shhh` to open the configuration GUI or toggle brightness directly.
- Forge Mod List Config GUI integration (`IModGuiFactory`).
- JSON configuration file (`.minecraft/config/shhh-brightness-toggle.json`) that saves settings and recovers automatically if corrupted.
- Client-side only design with zero server network traffic.
