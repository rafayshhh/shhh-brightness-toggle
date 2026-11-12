# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2026-11-12

### Added
- **Fullbright Toggle**: Instantly toggle maximum ambient brightness using the `B` key.
- **Customizable Brightness Stepping**: Fine-tune brightness levels from 10% to 100% using `Up Arrow` (increase) and `Down Arrow` (decrease).
- **Shader-Native Illumination**: Modifies ambient lightmap state without fake night vision potion effects, preventing unwanted potion particle effects or HUD icons.
- **Dynamic Color Indicators**: Overlay text and settings screen slider labels smoothly shift colors across a heat-map gradient (Red -> Yellow/Gold -> Soft Green) based on brightness intensity.
- **Color Configuration**: Added a setting to easily toggle colored feedback messages on or off.
- **In-Game Settings Screen**: Built-in configuration GUI featuring an on/off switch, an interactive brightness slider, and colored text toggle.
- **Mod Menu Integration**: Fully integrated with Mod Menu as an optional dependency (zero hard dependency required).
- **Persistent Configuration**: Automatically stores user settings in `.minecraft/config/shhh-brightness-toggle.json` with self-healing error recovery and dirty-checking saves.
- **Gamma Preservation**: Automatically saves and restores authentic player gamma settings when toggling off, disconnecting from servers, leaving worlds, or shutting down the game.
- **Client-Side Architecture**: Pure client-side implementation with zero server packet traffic or server requirements.
