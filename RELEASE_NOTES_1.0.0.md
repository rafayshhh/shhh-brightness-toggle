# Shhh Brightness Toggle 1.0.0 (Minecraft 26.1.2) Release Notes

Port of Shhh Brightness Toggle to Minecraft 26.1.2 on Fabric.

## Features
- Fullbright Toggle: Press B (default) to turn brightness on and off while playing.
- Brightness Level Stepping: Use the Up Arrow and Down Arrow keys (default) to increase or decrease brightness between 10% and 100% in 10% steps.
- Lightmap-Based Rendering: Adjusts ambient light levels directly through Minecraft's lightmap rendering instead of using a fake Night Vision status effect. This avoids potion swirl particles and inventory HUD potion icons.
- Dynamic Color Feedback: Actionbar messages and the settings slider display color feedback that changes based on your brightness level (red at 10%, yellow at mid levels, green at 100%). This can be turned off in the settings if you prefer plain text.
- Vanilla Gamma Restoration: Automatically remembers your original vanilla brightness setting and restores it whenever you toggle the mod off, leave a world, disconnect from a server, or close the game.
- In-Game Settings Screen: An in-game configuration menu accessible through Mod Menu (optional), allowing you to toggle fullbright, adjust the brightness slider, and toggle colored messages.
- Configuration Persistence: Settings are saved to `.minecraft/config/shhh-brightness-toggle.json` and automatically reset to safe defaults if the file is missing, empty, or corrupted.
- Client-Side Only: The mod runs entirely on your client and does not need to be installed on servers.

## Requirements
- Minecraft: 26.1.2
- Fabric Loader: 0.20.1 or newer
- Fabric API: 0.155.3+26.1.2
- Java: 25
- Mod Menu: (Optional) for opening the settings screen in-game

## Server Rules Warning
Some multiplayer servers do not allow fullbright or brightness adjustment mods. Check your server's rules before using this mod online. Use at your own risk.

## Known Risks and Compatibility
- Minecraft Internal Classes: This mod injects into Minecraft's internal lightmap rendering class (`LightmapRenderStateExtractor`). In future Minecraft updates, Mojang may alter or rename this class, which may require an update to the mod to remain compatible.
- Mod Compatibility: Designed to work alongside lighting mods like Sodium and Iris by injecting at the end of the rendering pipeline. However, it has not yet been tested with every combination of mods. If you encounter any conflicts, please report them on GitHub.

## Support and Feedback
- Report bugs or request features: https://github.com/rafayshhh/shhh-brightness-toggle/issues
- Source code: https://github.com/rafayshhh/shhh-brightness-toggle
