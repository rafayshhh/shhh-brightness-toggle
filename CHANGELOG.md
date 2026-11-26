# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0+mc1.12.2] - 2026-11-26

### Ported
- Port of Shhh Brightness Toggle to Minecraft 1.12.2 on Minecraft Forge.
- Compiled against Java 8 bytecode, Minecraft Forge 14.23.5.2860, and MCP stable-39-1.12 mappings via Unimined.
- Client-side in-memory gamma manipulation with automatic options restoration.

### Features
- Brightness toggle keybind (B by default) to turn fullbright on or off while playing.
- Increase and decrease keybinds (Up Arrow and Down Arrow by default) to adjust brightness between 10% and 100% in 10% steps.
- In-memory gamma scaling without modifying game files during normal gameplay.
- Zero `options.txt` tampering: vanilla brightness is safely captured and restored on world exit, server disconnect, GUI opens, and game shutdown.
- Dynamic color feedback on on-screen actionbar record messages, changing from red at low brightness to yellow at mid levels and green at maximum.
- Colored feedback toggle in settings for players who prefer uncolored text.
- In-game settings screen accessible via Forge Mod Options Config button or `/shhh` command.
- Client-side only design with zero server network traffic.
