# Shhh Brightness Toggle 1.0.0 (Minecraft 1.19.4) Release Notes

Port of Shhh Brightness Toggle to Minecraft 1.19.4 on Fabric.

## Features
- Fullbright Toggle: Press B (default) to turn brightness on and off while playing.
- Brightness Level Stepping: Use the Up Arrow and Down Arrow keys (default) to increase or decrease brightness between 10% and 100% in 10% steps.
- Lightmap Night-Vision Scaling: Adjusts ambient light levels directly through Minecraft's internal `LightTexture` via SpongePowered Mixin `@Redirect`, completely avoiding potion swirl particles, bubbles, or inventory HUD status icons.
- Real Night Vision Compatibility: If the player has an active Night Vision potion effect, the mod uses the higher of the two values so your potion effect is never degraded.
- Zero Options Tampering: Does not touch or overwrite vanilla `options.gamma` or `options.txt`. When disabled, vanilla lighting runs completely untouched on the very next frame.
- Dynamic Color Feedback: Actionbar messages and the settings slider display color feedback that changes based on your brightness level (red at 10%, yellow at mid levels, green at 100%). This can be turned off in the settings if you prefer plain text.
- In-Game Settings Screen: An in-game configuration menu accessible through Mod Menu (optional), allowing you to toggle fullbright, adjust the brightness slider, and toggle colored messages.
- Configuration Persistence: Settings are saved to `.minecraft/config/shhh-brightness-toggle.json` and automatically reset to safe defaults if the file is missing, empty, or corrupted.
- Client-Side Only: The mod runs entirely on your client and does not need to be installed on servers.

## Requirements
- Minecraft: 1.19.4
- Fabric Loader: 0.14.21 or newer
- Fabric API: 0.87.0+1.19.4
- Java: 17
- Mod Menu: 6.3.1 (Optional, for opening the settings screen in-game)

## Server Rules Warning
Some multiplayer servers do not allow fullbright or brightness adjustment mods. Check your server's rules before using this mod online. Use at your own risk.

## Known Risks and Compatibility
- Shader Packs (Iris / Canvas / OptiFine): Fullbright does not work while an external shader pack is active because shaders replace the vanilla lightmap pipeline with their own custom GLSL lighting passes. When shader packs are disabled, fullbright works normally.
- Sodium: Sodium replaces vanilla's lightmap generation with its own optimized pipeline. Shhh Brightness Toggle uses non-destructive SpongePowered Mixin `@Redirect` hooks with `require = 0`, ensuring it will never crash if Sodium or another custom renderer is installed; however, vanilla lightmap injection is bypassed when Sodium's custom lighting pipeline takes over.

## Support and Feedback
- Report bugs or request features: https://github.com/rafayshhh/shhh-brightness-toggle/issues
- Source code: https://github.com/rafayshhh/shhh-brightness-toggle
