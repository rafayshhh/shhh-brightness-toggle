# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0+mc1.20.1-forge] - 2026-11-26

### Ported
- Initial release of Shhh Brightness Toggle for Minecraft 1.20.1 on Minecraft Forge.
- Compiled against Java 17 bytecode, Minecraft Forge 47.3.0, and Mojang official mappings (`mojmap`) via Unimined.
- Non-destructive lightmap manipulation via Mixin on `LightTexture.updateLightTexture`.

### Features
- Brightness toggle keybind (B by default) to turn fullbright on or off instantly.
- Increase and decrease keybinds (Up Arrow and Down Arrow by default) to step brightness between 10% and 100% in 10% steps.
- In-memory night-vision factor scaling without modifying `options.gamma` or game files.
- Dynamic color feedback on actionbar messages, changing from red at low brightness to yellow and green at maximum.
- Colored feedback toggle in settings for players who prefer uncolored text.
- In-game settings screen accessible via Forge Mod Options ("Config" button) or `/shhh` command.
- Client-side only design with zero server network traffic.
