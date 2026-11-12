# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2026-11-12

### Added
- Brightness toggle keybind (B by default) to turn fullbright on or off while playing.
- Increase and decrease keybinds (Up Arrow and Down Arrow by default) to adjust brightness between 10% and 100% in 10% steps.
- Lightmap-based brightness adjustment that avoids fake night vision potion effects, so no potion particles or HUD icons appear.
- Dynamic color feedback on on-screen messages and slider labels, changing from red at low brightness to yellow at mid levels and green at maximum.
- Colored feedback toggle in settings for players who prefer uncolored text.
- In-game settings screen with a toggle button, brightness slider, and colored messages switch.
- Mod Menu integration as an optional dependency.
- JSON configuration file (`.minecraft/config/shhh-brightness-toggle.json`) that saves settings and recovers automatically if corrupted.
- Automatic restoration of your normal vanilla brightness when toggling off, leaving a world, disconnecting from a server, or closing the game.
- Client-side only design with zero server network traffic.
