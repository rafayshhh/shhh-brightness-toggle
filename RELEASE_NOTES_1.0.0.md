# Shhh Brightness Toggle 1.0.0 (Minecraft 1.16.5) Release Notes

Port of Shhh Brightness Toggle to Minecraft 1.16.5 on Fabric.

## Features
- Fullbright Toggle: Press B (default) to turn brightness on and off while playing.
- Brightness Adjustment: Press = to increase brightness and - to decrease brightness in 10% steps (from 10% to 100%).
- Smooth Visuals: Clean in-memory lightmap scaling without flashing, potion swirl particles, or HUD icons.
- Safe & Clean: Leaves options.txt and system gamma completely untampered.
- Colored Feedback: Dynamic actionbar messages and slider color transitions (red -> yellow -> green), with an option to disable colored messages in settings.
- In-Game Config Screen: Adjust settings directly in-game via Mod Menu.
- Client-Side Only: Runs entirely on your client and does not need to be installed on servers.

## Requirements
- Minecraft: 1.16.5
- Fabric Loader: 0.14.21 or newer
- Fabric API: 0.42.0+1.16
- Java: 8+
- Mod Menu: 1.16.23 (Optional, for in-game settings screen)

## Server Rules Warning
Some multiplayer servers do not allow fullbright or brightness adjustment mods. Check your server's rules before using this mod online. Use at your own risk.

## Known Risks and Compatibility
- Shader Packs (Iris / Canvas / OptiFine): Fullbright does not work while an external shader pack is active because shaders replace the vanilla lightmap pipeline with their own custom GLSL lighting passes. When shader packs are disabled, fullbright works normally.
- Sodium: Sodium replaces vanilla's lightmap generation with its own optimized pipeline. Shhh Brightness Toggle uses non-destructive SpongePowered Mixin `@Redirect` hooks with `require = 0`, ensuring it will never crash if Sodium or another custom renderer is installed; however, vanilla lightmap injection is bypassed when Sodium's custom lighting pipeline takes over.

## Support and Feedback
- Report bugs or request features: https://github.com/rafayshhh/shhh-brightness-toggle/issues
- Source code: https://github.com/rafayshhh/shhh-brightness-toggle
